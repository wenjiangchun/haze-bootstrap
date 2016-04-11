package com.xinyuan.haze.core.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;

import java.io.Serializable;

/**
 * BaseRepository工厂Bean，通过{@code getTargetRepository()}方法创建BaseRepository接口实现类bean.
 * @param <R>
 * @param <T>
 * @param <I>
 */
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {

	@SuppressWarnings("rawtypes")
	protected RepositoryFactorySupport createRepositoryFactory(
			EntityManager entityManager) {
		return new BaseRepositoryFactory(entityManager);
	}

	private static class BaseRepositoryFactory<T, I extends Serializable>
			extends JpaRepositoryFactory {

        private EntityManager entityManager;

		public BaseRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			this.entityManager = entityManager;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected Object getTargetRepository(RepositoryInformation information) {
			JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
			return new SimpleBaseRepository(entityInformation,
					entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return BaseRepository.class;
		}
	}
}
