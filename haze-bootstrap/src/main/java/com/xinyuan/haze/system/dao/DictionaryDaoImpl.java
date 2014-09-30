package com.xinyuan.haze.system.dao;

import com.xinyuan.haze.system.entity.Dictionary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 * 自定义字典数据接口实现类
 * 
 * @author sofar
 *
 */
public class DictionaryDaoImpl implements DictionaryRepository {

	@PersistenceContext
	private EntityManager em;

    @SuppressWarnings("unchecked")
	@Override
    public List<Dictionary> findByRootCodeAndCode(String rootCode, String code) {
        String nativeSql = "SELECT T.* FROM SEC_DICTIONARY T WHERE T.CODE= ?1 START WITH ID=(SELECT ID FROM SEC_DICTIONARY WHERE CODE= ?2) CONNECT BY PRIOR ID = PARENT_ID";
        Query query = em.createNativeQuery(nativeSql,Dictionary.class);
        query.setParameter(1, code);
        query.setParameter(2, rootCode);
        return query.getResultList();
    }
}
