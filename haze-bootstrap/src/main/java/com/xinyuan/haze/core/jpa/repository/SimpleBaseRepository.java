package com.xinyuan.haze.core.jpa.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.xinyuan.haze.core.jpa.entity.SimpleBaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * BaseRepository接口实现类
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public class SimpleBaseRepository<T extends SimpleBaseEntity<ID>, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private EntityManager em;

    private Class<T> domainClass;

    public SimpleBaseRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.domainClass = domainClass;
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value, Sort... sorts) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(domainClass);
        Root<T> root = criteriaQuery.from(domainClass);
        Predicate predicate = criteriaBuilder.equal(root.get(propertyName), value);
        criteriaQuery.where(predicate);
        for (Sort sort : sorts) {
            Iterator<Sort.Order> it = sort.iterator();
            while (it.hasNext()) {
                Sort.Order order = it.next();
                Sort.Direction direction = order.getDirection();
                String orderPropertyName = order.getProperty();
                switch (direction) {
                    case ASC:
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderPropertyName)));
                        break;
                    case DESC:
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderPropertyName)));
                        break;
                    default:
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderPropertyName)));
                }
            }
        }
        return em.createQuery(criteriaQuery).getResultList();
    }
}
