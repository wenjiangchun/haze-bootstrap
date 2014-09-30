package com.xinyuan.haze.core.jpa.entity;

import java.io.Serializable;

import javax.persistence.Transient;

/**
 * 系统持久化实体接口定义，
 * @author sofar
 *
 * @param <PK>
 */
public interface BaseEntity<PK extends Serializable> extends Serializable {

	PK getId();

	void setId(final PK id);

	/**
	 * 判断实体对象是否为新实体对象
	 * <p><b>说明：</b>当一个持久化实体对象中的{@code id == null}时，则表示该实体对象是新的未持久化对象。
	 * 
	 * @return 如果对象未关联持久化数据返回true,否则返回false.
	 */
	@Transient
	default boolean isNew() {
		return null == getId();
	}

}
