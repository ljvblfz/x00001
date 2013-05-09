package com.founder.sipbus.common.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.po.AbstractEntity;
import com.founder.sipbus.common.po.BaseEntity;
import com.founder.sipbus.common.util.ReflectionUtils;

/**
 * 
 * @author cw peng
 *
 * @param <E>
 */

@SuppressWarnings("unchecked")
public abstract class AbstractBaseDaoImpl<E extends AbstractEntity,PK extends Serializable> extends
		HibernateDaoSupport{

	private Log logger = LogFactory.getLog(AbstractBaseDaoImpl.class);
	
	public E add(E entity){
		insert(entity);
		return entity;
	}

	
	public void batchUpdate(List<E> objects){
		for(E o :objects){
			update(o);
		}
	}
	
	public void batchInsert(List<E> objects){
		for(E o :objects){
			insert(o);
		}
	}
	
	public void batchInsert(List<E> list,Integer num)
	{
		if(num==null)
		{
			num=100;
		}
		for (int i=0; i<list.size(); i++ ) 
		{
			E entity=list.get(i);
			getHibernateTemplate().save(entity);
			if(i!=0&&i%num==0)
			{
				getSession().flush();
			}
			
			
		}
	}
	
	public void callProduce(String procStr,Object... params){
		SQLQuery sqlQuery = getSession().createSQLQuery("{call "+procStr+"}");
		if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				sqlQuery.setParameter(i, params[i]);
			}
		}
    	sqlQuery.executeUpdate();

	}
	
	
	public ClassMetadata getClassMetadata(String entityName){
//		System.out.println(getSessionFactory().());
//		getSession().get
		return getSessionFactory().getClassMetadata(entityName);
	}
	public void delete(PK id) {
		try {
			Object entity = findById(id);
			if(entity.getClass().getSuperclass().equals(BaseEntity.class)){
				((BaseEntity)entity).setDelFlag(1);
//				getSession().flush();
			}else{
				getHibernateTemplate().delete(entity);
				
//				getHibernateTemplate().setf
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Delete Error: " + e.getMessage());
		}
	}
	
	
	/**
	 * @deprecated
	 * @author founder 2012-3-7
	 * @param id
	 */
	public void deletePhysics(PK id) {
		try {
			Object entity = findById(id);
			getHibernateTemplate().delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Delete Error: " + e.getMessage());
		}
	}
	public List<E> findByHql(String hql){
		return findByHql(hql,null);
	}

	
	public List findByHqlNoEntityType(String hql,Object... objects){
		return getHibernateTemplate().find(hql,objects);
	}


	public List findByHqlNoEntityType(String hql,int firstResult ,int maxResult,Object... objects){

		Query query=getSession().createQuery(hql);

		if (null != objects && 0 < objects.length) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		
		// 取结果
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.list();
	}
	
	public List findByHqlNoEntityType(String hql){
		return findByHqlNoEntityType(hql,null);
	}
	
	public List<E> findByHql(String hql,Object... objects){
		return findByHqlNoEntityType(hql,objects);
	}


	public List<E> findByHql(LockMode lockMode,String alias ,String hql,Object... objects){
		return findByHqlNoEntityType(lockMode,alias,hql,objects);
	}
	

	public List findByHqlNoEntityType(LockMode lockMode,String alias , String hql,Object... objects){
		Query query=getSession().createQuery(hql);

		if(null!=lockMode)
			query.setLockMode(alias, lockMode);
		if (null != objects && 0 < objects.length) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query.list();
	}
	
	
	/**
	 * 根据Sql 查询唯一返回值
	 * */
//	public Object findPropertyBySql(String sql){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		return sqlQuery.uniqueResult();
//	}
	/**
	 * 根据Sql 查询唯一返回值
	 * */
//	public Object findPropertyBySql(String sql,Object... params){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				sqlQuery.setParameter(i, params[i]);
//			}
//		}
//		return sqlQuery.uniqueResult();
//	}

	/**
	 * 执行SQL更新
	 * */
//	public void excuteSql(String sql){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		sqlQuery.executeUpdate();
//	}
	/**
	 * 执行SQL更新
	 * */
//	public void excuteSql(String sql,Object... params ){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				sqlQuery.setParameter(i, params[i]);
//			}
//		}
//		sqlQuery.executeUpdate();
//	}

	/**
	 * 根据Hql 查询唯一返回值
	 * */
	public Object findPropertyByHql(String hql,Object... params){
		Query query=getSession().createQuery(hql);
		if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.uniqueResult();
	}
	
	
	public List<Object> findByHql(String hql, @SuppressWarnings("rawtypes") Class clazz,Object... params ){
		Query query=getSession().createQuery(hql);
		if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		return query.list();
	}
	

	public Object findUniqueResultByHql(String hql, @SuppressWarnings("rawtypes") Class clazz,Object... params ){
		Query query=getSession().createQuery(hql);
		if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		query.setResultTransformer(Transformers.aliasToBean(clazz));
		return query.uniqueResult();
	}
	
	/**
	 * 根据Hql 查询唯一返回值
	 * */
	public Object findPropertyByHql(String hql){
		Query query=getSession().createQuery(hql);
		return query.uniqueResult();
	}
	
	/**
	 * 根据sql 查询出一对key-name值列表
	 */
//	public List<String[]> findKeyNameBySql(String sql){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		
//		return sqlQuery.list();
//	}
//
//	public List<Object[]> findBySql(String sql){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		
//		return sqlQuery.list();
//	}


//	public List<Object[]> findBySql(String sql,Object... params){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				sqlQuery.setParameter(i, params[i]);
//			}
//		}
//		return sqlQuery.list();
//	}
	
	
	public E findUniqueByHql(String hql){
		
		return findUniqueByHql(hql,null);
	}

	
	public E findUniqueByHql(String hql,Object... params){
		return (E)findUniqueByHqlNoEntityType(hql,params);
	}
	
	public Object findUniqueByHqlNoEntityType(String hql,Object... params){
		Query query=getSession().createQuery(hql);
		if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return  query.uniqueResult();
	}
	
	
//	/**
//	 * sql查询 ， 返回在hibernate 中注册的entity 列表
//	 *
//	 * @param sql
//	 * @param entityClass
//	 * @return
//	 * @author 陈文智
//	 */
//	public List<E> findEntityBySql(String sql){
//		SQLQuery sqlQuery=getSession().createSQLQuery(sql);
//		sqlQuery.addEntity(getEntityClass());
//		return sqlQuery.list();
//	}
	
	 
	
	public PageResponse<E> findAll(PageRequest pageRequest) {
		PageResponse<E> pageResponse = new PageResponse<E>();
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM ").append(getEntityClass().getSimpleName());
		String defalutOrderFileds = getDefalutOrderFileds();
		if (StringUtils.isNotEmpty(defalutOrderFileds)) {
			hql.append(" order by " + defalutOrderFileds);
		}

		String queryString = hql.toString();
		Query query = getSession().createQuery(queryString);

		// 取总数
		Long count = getHQLReulstCount(hql.toString());
		pageResponse.setTotalCount(count);

		// 取结果
		query.setFirstResult(pageRequest.getStart());
		query.setMaxResults(pageRequest.getLimit());
		pageResponse.setList(query.list());

//		pageResponse.setStart(pageRequest.getStart() + 1);
//		pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);

		return pageResponse;
	}

	public List<E> findAll() {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM ").append(getEntityClass().getName());
		String defalutOrderFileds = getDefalutOrderFileds();
		if (StringUtils.isNotEmpty(defalutOrderFileds)) {
			hql.append(" order by " + defalutOrderFileds);
		}

		String queryString = hql.toString();
		return getHibernateTemplate().find(queryString);
	}
	
	public List<E> findNoDelAll() {
		StringBuffer hql = new StringBuffer();
		hql.append(" FROM ").append(getEntityClass().getName());
		String defalutOrderFileds = getDefalutOrderFileds();
		hql.append(" WHERE delFlag=0 ");
		if (StringUtils.isNotEmpty(defalutOrderFileds)) {
			hql.append(" order by " + defalutOrderFileds);
		}

		String queryString = hql.toString();
		return getHibernateTemplate().find(queryString);
	}

	protected Class getEntityClass(){
		return ReflectionUtils.getSuperClassGenricType(getClass());
	}

	private String defaultOrderFileds = "";
	protected String getDefalutOrderFileds() {
		return defaultOrderFileds;
	}
	
	protected void setDefalutOrderFileds(String defaultOrderFileds) {
		this.defaultOrderFileds = defaultOrderFileds;
	}

	public List<E> findByExample(E instance) {
		List results = getHibernateTemplate().findByExample(instance);
		return results;
	}

	public E findById(PK id) {
		return (E) findByIdNoEitityType(getEntityClass(),id);
	}
	
	public Object findByIdNoEitityType(Class _class,Serializable id) {
		return getHibernateTemplate().get(_class, id,null);
	}
	

	public E findById(LockMode lockMode , PK id) {
		return (E) findByIdNoEitityType(getEntityClass(),id,lockMode);
	}
	
	public Object findByIdNoEitityType(Class _class,Serializable id,LockMode lockMode) {
		if(null!=lockMode)
			return getHibernateTemplate().get(_class, id,lockMode);
		return getHibernateTemplate().get(_class, id);
	}

	public List<E> findByProperty(String propertyName, Object value) {
		if (value != null) {
			String queryString = "from " + getEntityClass().getName()
					+ " as model where model." + propertyName + "= ? and model.delFlag=0";
			return getHibernateTemplate().find(queryString, value);
		} else {
			String queryString = "from " + getEntityClass().getName()
					+ " as model where model." + propertyName + " is null and model.delFlag=0";
			return getHibernateTemplate().find(queryString);
		}
	}
	

	/**
	 * 执行带参数的hql更新
	 *
	 * @param hql
	 * @param params
	 * @author 陈文智
	 */
	public void excuteUpdate(String hql,Object... params) {
		getHibernateTemplate().bulkUpdate(hql,params);
	}

	public void delete(Object entity){
		getHibernateTemplate().delete(entity);
	}
	
	/**
	 * 执行hql更新
	 *
	 * @param hql
	 * @author 陈文智
	 */
	public void excuteUpdate(String hql) {
		getHibernateTemplate().bulkUpdate(hql);
	}

//	public PageResponse<E> findByProperty1(String propertyName, Object value,
//			PageRequest pageRequest) {
//		PageResponse<E> pageResponse = new PageResponse<E>();
//		if (value != null) {
//			String queryString = "from " + getEntityClass().getName()
//					+ " as model where model." + propertyName + "= :value";
//			Query query = getSession().createQuery(queryString);
//			query.setEntity("value", value);
//
//			// 取总数
//			Long totalCount = getHQLReulstCount(queryString);
//			pageResponse.setTotalCount(totalCount);
//
//			query.setFirstResult(pageRequest.getStart());
//			query.setMaxResults(pageRequest.getLimit());
//			pageResponse.setList(query.list());
//
//			pageResponse.setStart(pageRequest.getStart() + 1);
//			pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);
//
//			return pageResponse;
//		} else {
//			String queryString = "from " + getEntityClass().getName()
//					+ " as model where model." + propertyName + " is null";
//
//			Query query = getSession().createQuery(queryString);
//
//			// 取总数
//			Long count = getHQLReulstCount(queryString.toString());
//			pageResponse.setTotalCount(count);
//
//			query.setFirstResult(pageRequest.getStart());
//			query.setMaxResults(pageRequest.getLimit());
//			
//			pageResponse.setList(query.list());
//			pageResponse.setStart(pageRequest.getStart() + 1);
//			pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);
//			return pageResponse;
//		}
//	}

	public E insert(E entity) {
		getHibernateTemplate().save(entity);
		getSession().flush();
		return entity;
	}

    
    public E update(E entity) {
        getHibernateTemplate().update(entity);
		getSession().flush();
        return entity;
    }

    
    public E merge(E entity) {
        getHibernateTemplate().merge(entity);
		getSession().flush();
        return entity;
    }
    
    //将对象变成游离态
    public void evict(Object obj){
        getSession().evict(obj);
		getSession().flush();
    }
    
//	/**
//	 * 重要数据更新调用此方法，会同步备份数据至His表
//	 */
//	public E updateAndSaveHis(E entity){
//		try{
//			getHibernateTemplate().update(entity);
//			//Java invoke 
//			System.out.println(entity.getClass().getName()+"His");
//			E hisObj=(E)Class.forName(entity.getClass().getName()+"His").newInstance();
//			BeanUtils.copyProperties(hisObj, entity);
//			getHibernateTemplate().save(hisObj);
//			getSession().flush();
//		}catch(Exception ae){
//			ae.printStackTrace();
//		}
//		return entity;
//	}
	
	/**
	 * 返回给定HQL语句的记录总条数
	 * 
	 * @param hql
	 * @return
	 */
	protected Long getHQLReulstCount(String hql,Object[] params,Object[] values) {
		String countSql = removeSelect(hql);
		countSql = removeOrders(countSql);
		countSql = removeFetchKeyword(countSql);
		countSql = " select count(*)  " + countSql +"";
		Query query = getSession().createQuery(countSql);
		
        if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				if (values[i] instanceof List) {
					query.setParameterList((String)params[i], (List)values[i]);
				}else{
					query.setParameter((String)params[i], (String)values[i]); //numbered from 0
				}
				
			}
		}
        
		Long count = (Long) query.uniqueResult();
		return count;
	}

	/**
	 * 返回给定HQL语句的记录总条数
	 * 
	 * @param hql
	 * @return
	 */
	protected Long getHQLReulstCount(String hql,Object[] params) {
		String countSql = removeSelect(hql);
		countSql = removeOrders(countSql);
		countSql = removeFetchKeyword(countSql);
		countSql = " select count(*)  " + countSql +"";
		Query query = getSession().createQuery(countSql);
		
        if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]); //numbered from 0
			}
		}
        
		Long count = (Long) query.uniqueResult();
		return count;
	}

	protected Long getHQLReulstCount(String hql) {
		return getHQLReulstCount(hql,null);
	}
	/**
	 * 返回给定SQL语句的记录总条数
	 * 
	 * @param hql
	 * @return
	 */
//	protected Long getSQLReulstCount(String sql,Object[] params) {
//		String countSql = sql;
//		if(sql.indexOf("union")==-1){
//			countSql = removeSelect(sql);
//			countSql = removeOrders(countSql);
//			countSql = removeFetchKeyword(countSql);
//			countSql = " select count(1) from (select 1 " + countSql+")";
//		}else{
//			countSql = " select count(1) from (" + countSql+")";
//		}
//		
//		Query query = getSession().createSQLQuery(countSql);
//        if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				query.setParameter(i, params[i]); //numbered from 0
//			}
//		}
//        BigDecimal count = (BigDecimal) query.uniqueResult();
//		return count.longValue();
//	}
	
//	protected Long getSQLReulstCount(String sql) {
//		return getSQLReulstCount(sql,null);
//	}

	/**
	 * 去除select 子句，未考虑union的情况
	 * 
	 * @param hql
	 * @return
	 */
	protected static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	
//	protected static String removeOrders(String hql) {
//		Assert.hasText(hql);
//		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*^\\)",
//				Pattern.CASE_INSENSITIVE);
//		Matcher m = p.matcher(hql);
//		StringBuffer sb = new StringBuffer();
//		while (m.find()) {
//			m.appendReplacement(sb, "");
//		}
//		m.appendTail(sb);
//		return sb.toString();
//	}

	/**
	 * 去除orderby 子句
	 * 
	 * @param hql
	 * @return
	 */
	protected static String removeOrders(String hql) {
		String returnSql=hql;
		Assert.hasText(hql);
		int lastIndex = hql.lastIndexOf("order by");
//		System.out.println(lastIndex);
		if(lastIndex>0){
//			System.out.println(hql.substring(lastIndex,hql.length()));
//			System.out.println(hql.substring(lastIndex,hql.length()).indexOf("\\)"));
//			System.out.println(hql.substring(lastIndex,hql.length()).indexOf(")"));
			if(hql.substring(lastIndex,hql.length()).indexOf(")")==-1){
				returnSql=hql.substring(0,lastIndex);
			}
		}
		
//		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S|\\t|\\n|\\r]*",
//				Pattern.CASE_INSENSITIVE);
//		Matcher m = p.matcher(hql);
//		StringBuffer sb = new StringBuffer();
//		i0;
//		while (m.find()) {
//			System.out.println("match");
//			m.
//			m.appendReplacement(sb, "");
//		}
//		m.appendTail(sb);
		return returnSql;
	}
	
	
	protected static String removeFetchKeyword(String hql) {
		return hql.replaceAll("(?i)fetch", "");
	}
	
	/**
	 * 通过表达式得到查询结果
	 * @param criterions
	 * @return
	 * @author Googol Gu
	 */
	public List<E> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}
	
	/**
	 * 通过表达式得到查询结果
	 * @param criterions
	 * @return
	 * @author Googol Gu
	 */
	public List<E> find(Criteria criteria){
		return criteria.list();
	}
	
	/**
	 * 通过表达式得到查询结果
	 * @param dcriteria
	 * @return
	 * @author Googol Gu
	 */
	public List<E> find(DetachedCriteria dcriteria){
		return createCriteria(dcriteria).list();
	}
	
	
	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions 数量可变的Criterion.
	 * @author Googol Gu
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	   /**
     * 根据Criterion条件创建Criteria.
     * 
     * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     * 
     * @param criterions 数量可变的Criterion.
	 * @author Googol Gu
     */
    public Criteria createCriteria(final DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        return criteria;
    }
    
//    public PageResponse findPageBySQL(String sql,Object[] params,final PageRequest pageRequest) {
//        PageResponse pageResponse = new PageResponse();
//        pageResponse.setTotalCount(getSQLReulstCount(sql,params).intValue());
//        String[] orderByArray = StringUtils.split(pageRequest.getSortingColumn(), ',');
//		String[] orderArray = StringUtils.split(pageRequest.getSortingDirection(), ',');
//		if(null!=orderByArray&&null!=orderArray){
//			for (int i = 0; i < orderByArray.length; i++) {
//				if(sql.indexOf("order by")!=-1){
//					sql+=","+orderByArray+" "+orderArray[i];
//				}else{
//					sql+=" order by "+orderByArray+" "+orderArray[i];
//				}
//			}
//		}
//        Query query=getSession().createSQLQuery(sql);
//        query.setFirstResult(pageRequest.getStart());
//        query.setMaxResults(pageRequest.getPageSize());
//        if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				query.setParameter(i, params[i]); //numbered from 0
//			}
//		}
//        pageResponse.setList(query.list());
////		pageResponse.setStart(pageRequest.getStart() + 1);
////		pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);
//        return pageResponse;
//    
//    }
	
	/**
	 * 按Criteria分页查询.
	 * @param page
	 * @param criterions
	 * @return
	 * @author Googol Gu
	 */
    @SuppressWarnings("unchecked")
    public PageResponse<E> findPage(final PageRequest pageRequest, final DetachedCriteria detachedCriteria) {
        return (PageResponse<E>)findPageNoEntityType(pageRequest, detachedCriteria);
    }
    

    public PageResponse findPageNoEntityType(final PageRequest pageRequest, final DetachedCriteria detachedCriteria) {
        Assert.notNull(pageRequest, "pageRequest不能为空");
        PageResponse pageResponse = new PageResponse();
        Criteria c = createCriteria(detachedCriteria);
                 
        int totalCount = countCriteriaResult(c);
        pageResponse.setTotalCount(totalCount);

        setPageParameter(c, pageRequest);
        List result = c.list();
        pageResponse.setList(result);
//		pageResponse.setStart(pageRequest.getStart() + 1);
//		pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);
        return pageResponse;
    }
    
    public PageResponse  findPageNoEntityType(String hql,Object[] params,Object[] values,final PageRequest pageRequest){
        PageResponse pageResponse = new PageResponse();
        pageResponse.setTotalCount(getHQLReulstCount(hql,params,values).intValue());
        String[] orderByArray = StringUtils.split(pageRequest.getSortingColumn(), ',');
		String[] orderArray = StringUtils.split(pageRequest.getSortingDirection(), ',');
		if(null!=orderByArray&&null!=orderArray){
			for (int i = 0; i < orderByArray.length; i++) {
				if(hql.indexOf("order by")!=-1){
					hql+=","+orderByArray+" "+orderArray[i];
				}else{
					hql+=" order by "+orderByArray+" "+orderArray[i];
				}
			}
		}
        Query query=getSession().createQuery(hql);
        query.setFirstResult(pageRequest.getStart());
        query.setMaxResults(pageRequest.getPageSize());
        if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				if (values[i] instanceof List) {
					query.setParameterList((String)params[i], (List)values[i]);
				}else{
					query.setParameter((String)params[i], (String)values[i]); //numbered from 0
				}
				
			}
		}
        
        pageResponse.setList(query.list());
//		pageResponse.setStart(pageRequest.getStart() + 1);
//		pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);
        return pageResponse;
    
    }
    
    
    public PageResponse  findPageNoEntityType(String hql,Object[] params,final PageRequest pageRequest){
        PageResponse pageResponse = new PageResponse();
        pageResponse.setTotalCount(getHQLReulstCount(hql,params).intValue());
        String[] orderByArray = StringUtils.split(pageRequest.getSortingColumn(), ',');
		String[] orderArray = StringUtils.split(pageRequest.getSortingDirection(), ',');
		if(null!=orderByArray&&null!=orderArray){
			for (int i = 0; i < orderByArray.length; i++) {
				if(hql.indexOf("order by")!=-1){
					hql+=","+orderByArray+" "+orderArray[i];
				}else{
					hql+=" order by "+orderByArray+" "+orderArray[i];
				}
			}
		}
//        Query query=getSession().createQuery(hql);
//        query.setFirstResult(pageRequest.getStart());
//        query.setMaxResults(pageRequest.getPageSize());
//        if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				query.setParameter(i, params[i]); //numbered from 0
//			}
//		}
        pageResponse.setList(queryPage(hql,params,pageRequest.getStart(),pageRequest.getPageSize()));
//		pageResponse.setStart(pageRequest.getStart() + 1);
//		pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);
        return pageResponse;
    
    }
    
    
    public List queryPage(String hql,Object[] params , int start ,int size){
    	Query query=getSession().createQuery(hql);
        query.setFirstResult(start);
        query.setMaxResults(size);
        if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]); //numbered from 0
			}
		}
    	return query.list();
    }
    
    /**
     * HQL 查询
     *
     * @param hql hql语句
     * @param params 参数数组
     * @return
     * @author 
     */
    public List<E> find(String hql,Object[] params){
    	
    	Query query=getSession().createQuery(hql);
        if (null != params && 0 < params.length) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]); //numbered from 0
			}
		}
        
        return query.list();
    	
    }
    
    /**
     * HQL 分页查询
     *
     * @param hql hql语句
     * @param params 参数名数组
     * @param params 参数值数组
     * @param pageRequest  分页对象
     * @return
     * @author 陈文智
     */
    public PageResponse<E> findPage(String hql,Object[] params,Object[] values,final PageRequest pageRequest){
    	
    	return  (PageResponse<E>)findPageNoEntityType(hql, params,values,pageRequest);
    }
    
    /**
     * HQL 分页查询
     *
     * @param hql hql语句
     * @param params 参数数组
     * @param pageRequest  分页对象
     * @return
     * @author 陈文智
     */
    public PageResponse<E> findPage(String hql,Object[] params,final PageRequest pageRequest){
    	
    	return  (PageResponse<E>)findPageNoEntityType(hql, params, pageRequest);
    }
    
    
	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 * @author Googol Gu
	 */
	protected Criteria setPageParameter(final Criteria c, final PageRequest pageRequest) {
		//hibernate的firstResult的序号从0开始
		c.setFirstResult(pageRequest.getStart());
		c.setMaxResults(pageRequest.getPageSize());

			String[] orderByArray = StringUtils.split(pageRequest.getSortingColumn(), ',');
			String[] orderArray = StringUtils.split(pageRequest.getSortingDirection(), ',');
			if(null!=orderByArray&&null!=orderArray){
				Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");
	
				for (int i = 0; i < orderByArray.length; i++) {
					if ("asc".equals(orderArray[i])) {
						c.addOrder(Order.asc(orderByArray[i]));
					} else {
						c.addOrder(Order.desc(orderByArray[i]));
					}
				}
			}
		return c;
	}
	
	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 * @author Googol Gu
	 */
	@SuppressWarnings("unchecked")
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e);
		}

		// 执行Count查询
//		int totalCount = ((Long) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();

		int totalCount =0; 
		Object  obj=c.setProjection(Projections.rowCount()).uniqueResult();
		if(null!=obj){ 
			totalCount=((Long)obj ).intValue();
		}
		

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e);
		}

		return totalCount;
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions 数量可变的Criterion.
	 * @author Googol Gu
	 */
	public E findUnique(final Criterion... criterions) {
		return (E) createCriteria(criterions).uniqueResult();
	}
	
	
	/**
	 * 获取当前数据库连接 
	 */
	public Connection getConnection(){
		return getSession().connection();
	}

	public Long getSequence(String sequenceName){
		String countSql  = " select "+sequenceName+".nextval from dual";
		
		Query query = getSession().createSQLQuery(countSql);
        BigDecimal count = (BigDecimal) query.uniqueResult();
		return count.longValue();
		
	}
	
	/**
	 * 逻辑删除
	 * @author 陈文智
	 */
//	public int remove(String pkName,PK id){
//		String userId = LoginInfoUtils.retrieveUserId();
//		return getHibernateTemplate().bulkUpdate("update "+getEntityClass().getSimpleName() +" tp set tp.delFlag=1 , tp.updateBy='"+ userId +"' where tp."+pkName+"='"+id+"'");
//	}
	
//	/**
//	 * 带分页功能sql查询,返回的数据自动装配到bean中
//	 */
//    public PageResponse<E> findPageBySql(String sql,Object[] params,final PageRequest pageRequest,Class myClass){
//    	return  (PageResponse<E>)findPageNoEntityTypeBySql(sql, params, pageRequest,myClass);
//    }
//	
//	public PageResponse  findPageNoEntityTypeBySql(String sql,Object[] params,final PageRequest pageRequest,Class myClass){
//        PageResponse pageResponse = new PageResponse();
//        pageResponse.setTotalCount(getSQLReulstCountWithUnion(sql,params).intValue());
//        String[] orderByArray = StringUtils.split(pageRequest.getSortingColumn(), ',');
//		String[] orderArray = StringUtils.split(pageRequest.getSortingDirection(), ',');
//		if(null!=orderByArray&&null!=orderArray){
//			for (int i = 0; i < orderByArray.length; i++) {
//				if(sql.indexOf("order by")!=-1){
//					sql+=","+orderByArray+" "+orderArray[i];
//				}else{
//					sql+=" order by "+orderByArray+" "+orderArray[i];
//				}
//			}
//		}
//        Query query=getSession().createSQLQuery(sql).addEntity("a", myClass);
//        query.setFirstResult(pageRequest.getStart());
//        query.setMaxResults(pageRequest.getPageSize());
//        if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				query.setParameter(i, params[i]); //numbered from 0
//			}
//		}
//        pageResponse.setList(query.list());
////		pageResponse.setStart(pageRequest.getStart() + 1);
////		pageResponse.setEnd(pageResponse.getStart()+pageResponse.getList().size() - 1);
//        return pageResponse;
//    
//    }
	
	/**
	 * 返回给定SQL语句的记录总条数{a.*}
	 * 
	 * @param hql
	 * @return
	 */
//	protected Long getSQLReulstCountWithUnion(String sql,Object[] params) {
//		String countSql = sql;
//		countSql = " select count(1) from (" + countSql+")";
//		countSql= countSql.replaceAll("\\{\\w*.*\\**\\}", "1");
//		
//		Query query = getSession().createSQLQuery(countSql);
//        if (null != params && 0 < params.length) {
//			for (int i = 0; i < params.length; i++) {
//				query.setParameter(i, params[i]); //numbered from 0
//			}
//		}
//        BigDecimal count = (BigDecimal) query.uniqueResult();
//		return count.longValue();
//	}
	
}
