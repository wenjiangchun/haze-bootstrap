package com.xinyuan.haze.core.jpa;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.core.jpa.entity.BaseEntity;

/**
 * JPA动态条件查询类  使用方法：将查询条件封装成queryVirables对象，queryVirables格式如key=name_LIKE,value="sofar"
 *
 * @author sofar
 *
 * @param <T> 持久化实体对象
 */
public class HazeSpecification<T extends BaseEntity<?>> implements Specification<T> {

	private static final String SPLIT = "_";

	private Map<String, Object> queryVirables;
	
	public HazeSpecification(Map<String, Object> queryVirables) {
		this.queryVirables = queryVirables;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate p = null;
		if (queryVirables != null) {
			Set<String> keys = queryVirables.keySet();
			for (String value : keys) {
				String[] keyAndOperator = getAttributeAndOperator(value);
				String key = keyAndOperator[0];
				String operatator = keyAndOperator[1];
				Object queryValue = queryVirables.get(value);
				if (HazeStringUtils.isNotEmpty(key)) {
					Predicate predicate = null;
					Path expression = null;
					try {
						 String[] attributes = key.split("\\."); //支持多重查询 如在用户列表功能模块中根据机构名称查询用户信息可以使用group.name作为key
						 for (int i = 0; i < attributes.length; i++) {
							 if (i == 0) {
								 expression = root.get(attributes[0]);
							 } else {
								 expression = expression.get(attributes[i]);
							 }
						 }
					} catch(Exception e) {
						break;
					}
					if ("eq".equalsIgnoreCase(operatator)) {
						predicate = cb.equal(expression, queryValue);
					} else if ("like".equalsIgnoreCase(operatator)) {
						predicate = cb.like(expression, "%" + queryValue + "%");
					} else if ("ge".equalsIgnoreCase(operatator)) {
						predicate = cb.ge(expression, (Number)queryValue);
					} else if ("le".equalsIgnoreCase(operatator)) {
						predicate = cb.le(expression, (Number)queryValue);
					} else if ("isNull".equalsIgnoreCase(operatator)) {
						predicate = cb.isNull(expression);
					} else if ("isNotNull".equalsIgnoreCase(operatator)) {
						predicate = cb.isNotNull(expression);
					} else if ("isTrue".equalsIgnoreCase(operatator)) {
						predicate = cb.isTrue(expression);
					} else if ("isFalse".equalsIgnoreCase(operatator)) {
						predicate = cb.isFalse(expression);
					} else if ("notEqual".equalsIgnoreCase(operatator)) {
						predicate = cb.notEqual(expression, queryValue);
					} else if ("in".equalsIgnoreCase(operatator)) {
						predicate = expression.in(queryValue);
					} else if ("greatThan".equalsIgnoreCase(operatator)){
						predicate = cb.greaterThan(expression, (Date)queryValue);
					} else if ("between".equalsIgnoreCase(operatator)){
						predicate = cb.between(expression, (Date)queryValue,(Date)queryValue);
						/*try {
							predicate = cb.between(expression,HazeDateUtils.parseDate("2013-11-04 00:00:00","yyyy-MM-dd hh:mm:ss"),HazeDateUtils.parseDate("2013-11-04 23:59:59","yyyy-MM-dd hh:mm:ss"));
						} catch (ParseException e) {
							e.printStackTrace();
						}*/
					} else {
						predicate = cb.equal(expression, queryValue);
					}
					if (predicate != null) {
						if (p != null) {
							p = cb.and(predicate,p);
						} else {
							p = predicate;
						}
					}
				}
			}
		} 
		return p;
	}

	/**
	 * 解析map中查询参数名称和操作比较符
	 * @param key 查询参数key,由对象实体属性名称和操作比较符组成 如name_like
	 * @return 包含实体属性名称和操作符的数组如{name,like}
	 */
	private String[] getAttributeAndOperator(String key) {
		if (key.indexOf(SPLIT) == -1) {
			return new String[]{key,"equal"};
		}
		return key.split(SPLIT);
	}
}