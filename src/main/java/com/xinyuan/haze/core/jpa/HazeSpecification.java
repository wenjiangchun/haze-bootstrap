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

	private Map<String, Object> queryParams;
	
	public HazeSpecification(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate p = null;
		if (queryParams != null) {
			Set<String> keys = queryParams.keySet();
			for (String value : keys) {
				String[] keyAndOperator = getAttributeAndOperator(value);
				String key = keyAndOperator[0];
				String operator = keyAndOperator[1];
				Object queryValue = queryParams.get(value);
				if (HazeStringUtils.isNotEmpty(key)) {
					Predicate predicate = null;
					Path expression = null;
                       if (operator.equalsIgnoreCase("or")) {
						   Path expression1 = null;
						   Path expression2 = null;
						   try {
							   String[] attributes = key.split("\\."); //支持多重查询 如在用户列表功能模块中根据机构名称查询用户信息可以使用group.name作为key
							   for (int i = 0; i < attributes.length; i++) {
								   if (i == 0) {
									   expression1 = root.get(attributes[0]);
								   } else {
									   expression1 = expression1.get(attributes[i]);
								   }
							   }
							   String[] attributes1 = keyAndOperator[2].split("\\."); //支持多重查询 如在用户列表功能模块中根据机构名称查询用户信息可以使用group.name作为key
							   for (int i = 0; i < attributes1.length; i++) {
								   if (i == 0) {
									   expression2 = root.get(attributes1[0]);
								   } else {
									   expression2 = expression2.get(attributes1[i]);
								   }
							   }
						   } catch(Exception e) {
							   break;
						   }
						   predicate = cb.or(cb.equal(expression1, queryValue),cb.equal(expression2, queryValue));

					   } else {
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

						   if ("eq".equalsIgnoreCase(operator)) {
							   predicate = cb.equal(expression, queryValue);
						   } else if ("like".equalsIgnoreCase(operator)) {
							   predicate = cb.like(expression, "%" + queryValue + "%");
						   } else if ("ge".equalsIgnoreCase(operator)) {
							   predicate = cb.ge(expression, (Number)queryValue);
						   } else if ("le".equalsIgnoreCase(operator)) {
							   predicate = cb.le(expression, (Number)queryValue);
						   } else if ("isNull".equalsIgnoreCase(operator)) {
							   predicate = cb.isNull(expression);
						   } else if ("isNotNull".equalsIgnoreCase(operator)) {
							   predicate = cb.isNotNull(expression);

						   } else if ("isEmpty".equalsIgnoreCase(operator)) {
							   predicate = cb.isEmpty(expression);
						   } else if ("isTrue".equalsIgnoreCase(operator)) {
							   predicate = cb.isTrue(expression);
						   } else if ("isFalse".equalsIgnoreCase(operator)) {
							   predicate = cb.isFalse(expression);
						   } else if ("notEqual".equalsIgnoreCase(operator)) {
							   predicate = cb.notEqual(expression, queryValue);
						   } else if ("in".equalsIgnoreCase(operator)) {
							   if (expression != null) {
								   predicate = expression.in(queryValue);
							   }
						   } else if ("greatThan".equalsIgnoreCase(operator)){
							   predicate = cb.greaterThan(expression, (Date)queryValue);
						   } else if ("between".equalsIgnoreCase(operator)){
							   predicate = cb.between(expression, (Date)queryValue,(Date)queryValue);
						/*try {
							predicate = cb.between(expression,HazeDateUtils.parseDate("2013-11-04 00:00:00","yyyy-MM-dd hh:mm:ss"),HazeDateUtils.parseDate("2013-11-04 23:59:59","yyyy-MM-dd hh:mm:ss"));
						} catch (ParseException e) {
							e.printStackTrace();
						}*/
						   } else if ("or".equalsIgnoreCase(operator)) {
							   cb.or(cb.equal(expression, queryValue));
						   } else if ("desc".equalsIgnoreCase(operator)){
							   query.orderBy(cb.desc(expression));
						/*try {
							predicate = cb.between(expression,HazeDateUtils.parseDate("2013-11-04 00:00:00","yyyy-MM-dd hh:mm:ss"),HazeDateUtils.parseDate("2013-11-04 23:59:59","yyyy-MM-dd hh:mm:ss"));
						} catch (ParseException e) {
							e.printStackTrace();
						}*/
						   } else if ("asc".equalsIgnoreCase(operator)) {
							   query.orderBy(cb.asc(expression));
						   } else {
							   predicate = cb.equal(expression, queryValue);
						   }
					   }

						if (p != null) {
							p = cb.and(predicate,p);
						} else {
							p = predicate;
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
		if (!key.contains(SPLIT)) {
			return new String[]{key,"equal"};
		}
		return key.split(SPLIT);
	}
}
