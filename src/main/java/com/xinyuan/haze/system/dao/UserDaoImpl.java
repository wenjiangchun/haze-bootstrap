package com.xinyuan.haze.system.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 自定义用户数据接口实现类
 * @author Sofar
 *
 */
public class UserDaoImpl implements UserRepository {

	@PersistenceContext
	private EntityManager em;

	/*@SuppressWarnings("unchecked")
	@Override
	public List<User> findBySex(Sex sex) {
		
		//使用JPQL查询语句构造Query对象
		Query query = em.createQuery("from User u where u.sex=:sex");
		
		//设置查询命名参数
		query.setParameter("sex", sex);
		return query.getResultList();
		
		//CriteriaBuilder cb = em.getCriteriaBuilder();
		//CriteriaQuery<User> c = cb.createQuery(User.class).where(Specifications.get());
		//em.createQuery(c).getResultList();
		
	}

	@Override
	public List<User> findByTest(List<Long> ids) {
		Query query = em.createQuery("from User u where u.id in(:ids)");
		query.setParameter("ids", ids);
		query.getResultList();
		
		//下面为切换到HibernateAPI方法调用
		Session session = (Session) em.getDelegate();
		org.hibernate.Query query1 = session.getSessionFactory().openSession().createQuery("from User u where u.id in(:ids)");
		query1.setParameterList("ids", ids);
		query1.list();
		Long c = session.getSessionFactory().getStatistics().getSecondLevelCachePutCount();
		return query1.list();
	}*/

}
