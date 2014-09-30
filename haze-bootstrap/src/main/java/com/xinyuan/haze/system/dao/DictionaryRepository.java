package com.xinyuan.haze.system.dao;

import com.xinyuan.haze.system.entity.Dictionary;

import java.util.List;

/**
 * 自定义字典数据操作接口 DictionaryDao接口集成该接口，具体实现在该接口实现类中
 * 
 * @author Sofar
 *
 */
public interface DictionaryRepository {

    /**
     * 根据字典类别顶级代码和字典代码查找字典对象
     * <p><b>说明：</b>该方法将从字典类别根节点查询所有符合条件的字典对象，针对多级树状字典数据。
     * <p><b>注意：</b>该方法区别于{@link com.xinyuan.haze.system.dao.DictionaryDao.findByParentCodeAndCode(String parentCode, String code)}</p>
     * @param rootCode 字典所在根节点代码。
     * @param code 字典代码
     * @return 根节点下面所有字典代码=参数code的字典列表
     */
    List<Dictionary> findByRootCodeAndCode(String rootCode, String code);

}
