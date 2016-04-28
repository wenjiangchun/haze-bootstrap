package com.xinyuan.haze.core.jpa.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.xinyuan.haze.core.jpa.entity.BaseEntity;

/**
 * 系统Repository标记接口定义  该Repository接口继承JpaRepository和JpaSpecificationExecutor接口
 *
 * @param <T>  实体类对象
 * @param <ID> 实体类主键
 * @author sofar
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 根据对象属性查询对象信息列表
     *
     * @param propertyName 属性名称
     * @param value        属性值
     * @param sorts        排序对象
     * @return List<T>     满足查询条件的对象信息列表
     */
    List<T> findByProperty(String propertyName, Object value, Sort... sorts);

    List<T> findByJql(String jpql, Map<String, Object> queryParams);

    List<T> findBySql(String sql, Map<String, Object> queryParams);
}
