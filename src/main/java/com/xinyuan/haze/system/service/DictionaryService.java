package com.xinyuan.haze.system.service;

import com.xinyuan.haze.core.jpa.service.AbstractBaseService;
import com.xinyuan.haze.system.dao.DictionaryDao;
import com.xinyuan.haze.system.entity.Dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>字典业务操作类  该类中涉及到缓存操作，因此在调用该类中参数为dictionary的方法时，如果该dictionary对象的parent不为空，则必须包含对应parent对象。</p>
 * 例如需要保存新的字典对象:<br>
 * 
 * 普通code: e.g.
 * <pre>
 * Dictionary dictionary = new Dictionary();
 * dictionary.setParent(new Dictionary(ParentId)); 
 * dictionaryService.saveOrUpdate(dictionary);
 * </pre>
 * <br>
 * 对于涉及到缓存code:
 * <pre> 
 * Dictionary dictionary = new Dictionary();
 * Dictionary parent= dictionaryService.findById(parent); //important! 需要先加载parent对象，因为缓存采用parent.code作为缓存key
 * dictionary.setParent(parent);
 * dictionaryService.saveOrUpdate(dictionary); 
 * </pre>
 * 
 * @author Sofar
 *
 */
@Service
@Transactional
public class DictionaryService extends AbstractBaseService<Dictionary, String> {

	private DictionaryDao dictionaryDao;
	
	@Autowired
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
		super.setDao(dictionaryDao);
	}
	
	/**
	 * 根据配置名称查找配置对象 
	 * @return 配置对象
	 */
	public Dictionary findByParentCodeAndCode(String parentCode, String code) {
		return this.dictionaryDao.findByParentCodeAndCode(parentCode, code);
	}

	/**
	 * 根据rootCode获取所有已启用子类字典,同时将查询结果缓存至名称为{@code dictionaryCache}的缓存,缓存key为{@code rootCode}
	 * @param rootCode 字典根节点code
	 * @return 已启用子类字典集合
	 */
	@Cacheable(value="dictionaryCache",key="#rootCode")
	public List<Dictionary> findChildsByRootCode(String rootCode) {
		return this.dictionaryDao.findChildsByRootCode(rootCode);
	}
	
	@CacheEvict(value="dictionaryCache", condition="#dictionary.parent != null", key="#dictionary.parent.code")
	@Transactional(readOnly=false)
	public Dictionary saveOrUpdate(Dictionary dictionary) throws Exception  {
		Assert.notNull(dictionary);
		Dictionary parent = dictionary.getParent();
		String parentCode = parent != null ? parent.getCode() : null;
		Dictionary c = this.dictionaryDao.findByParentCodeAndCode(parentCode, dictionary.getCode());
		if (c != null && dictionary.isNew()) {
			logger.error("字典代码{}已存在，请重试！",dictionary.getCode());
			//throw new DictionaryNameExistException("配置名称" + Dictionary.getDictionaryName() + "已存在，请重试");
		} else {
			if (dictionary.isNew()) { //保存配置对象
				logger.debug("添加字典信息，字典信息为：{}", dictionary);
			} else { //更新配置对象
				logger.debug("更新字典信息，字典信息为：{}", dictionary);
			}
		}
		return this.save(dictionary);
	}
	
	/**
	 * 删除配置对象 
	 * @param id 配置对象Id
	 */
	@Transactional(readOnly=false)
	public Dictionary deleteDictionary(String id) {
		Assert.notNull(id);
		Dictionary Dictionary = this.findById(id);
		return this.deleteDictionary(Dictionary);
	}
	
	@CacheEvict(value="dictionaryCache",allEntries=true)
	@Transactional(readOnly=false)
	public void deleteDictionarys(String[] ids) {
		for (String id : ids) {
			this.deleteDictionary(id);
		}
	}
	
	@CacheEvict(value="dictionaryCache", condition="#dictionary.parent != null", key="#dictionary.parent.code")
	@Transactional(readOnly=false)
	public Dictionary deleteDictionary(Dictionary dictionary) {
		 logger.debug("删除字典信息，字典信息为：{}",dictionary);
		 this.dictionaryDao.delete(dictionary);
		 return dictionary;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Dictionary t) {
		deleteDictionary(t);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(String id) {
		deleteDictionary(id);
	}

	@CacheEvict(value="dictionaryCache",allEntries=true)
	@Override
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
		deleteDictionarys(ids);
	}
	
	@CacheEvict(value="dictionaryCache", condition="#dictionary.parent != null", key="#dictionary.parent.code")
	@Transactional(readOnly=false)
	public Dictionary updateDictionary(Dictionary dictionary) throws Exception {
		Assert.notNull(dictionary);
		String id = dictionary.getId();
		dictionary = this.findById(id);
		this.save(dictionary);
		return dictionary;
	}

	public Boolean isExistDictionaryCode(String parentId, String code) {
		String parentCode = null;
		if (parentId != null) {
			Dictionary parent = this.findById(parentId);
			parentCode = parent != null ? parent.getCode() : null;
		} 
		Dictionary dictionary = this.dictionaryDao.findByParentCodeAndCode(parentCode, code);
		return dictionary != null;
	}
	
	/**
	 * 更新字典启用状态, 同时根据字典是否有所属分类(即该对象的parent属性是否为null)更新字典所在缓存.</br>
	 * 字典更新策略：字典状态和传递进来的isEnabled不同.</br>
	 * 缓存更新策略：当dictionary.parent !=null && dictionary.parent.code != null.</br>
	 * 注意：此时传递进来的Dictionary必须加载对应parent信息.</br>
	 * @param dictionary 字典信息(如果有parent则包含parent信息，更新字典缓存使用)
	 * @param isEnabled true表示启用 false表示禁用
	 * @return Dictionary 字典对象
	 */
	@CacheEvict(value="dictionaryCache", condition="#dictionary.parent != null && #dictionary.isEnabled != #isEnabled", key="#dictionary.parent.code")
	@Transactional(readOnly=false)
	public Dictionary updateDictionaryIsEnabled(Dictionary dictionary, boolean isEnabled) {
		Assert.notNull(dictionary);
		if (dictionary.getIsEnabled() != isEnabled) {
			this.dictionaryDao.updateDictionaryIsEnabled(dictionary.getId(), isEnabled);
		}
		return dictionary;
	}

    /**
     * 根据字典类别顶级代码和字典代码查找字典对象
     * <p><b>说明：</b>该方法将从字典类别根节点查询所有符合条件的字典对象，针对多级树状字典数据。</p>
     * @param rootCode 字典所在根节点代码。
     * @param code   字典代码
     * @return    根节点下面所有字典代码=参数code的字典列表
     */
    public List<Dictionary> findByRootCodeAndCode(String rootCode, String code) {
        Assert.notNull(code);
        return this.dictionaryDao.findByRootCodeAndCode(rootCode,code);
    }
}
