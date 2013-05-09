package com.founder.sipbus.common.resource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.restlet.data.Reference;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.resource.ServerResource;

import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.po.BaseEntity;
import com.founder.sipbus.common.util.DateJsonValueProcessor;
import com.founder.sipbus.common.util.TimeUtils;

public class BaseResource extends ServerResource {
	
	//FreeMarkerConfigurer freemarkerConfig;
	
	protected JsonConfig config = new JsonConfig(); 

	public static final String ORDERTYPE_ASC="asc";
	
	public static final String ORDERTYPE_DESC="desc";
	
    public BaseResource() {
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd")); //date processor register
		config.registerJsonValueProcessor(java.sql.Date.class,new DateJsonValueProcessor("yyyy-MM-dd")); //date processor register
		config.setExcludes(new String[] {// 只要设置这个数组，指定过滤哪些字段。
				"consignee", "contract", "coalInfo", "coalType",
						"startStation", "balanceMan", "endStation" });
	}
    
    
	/**
     * Returns the reference of a resource according to its id and the reference
     * of its "parent".
     * 
     * @param parentRef
     *            parent reference.
     * @param childId
     *            id of this resource
     * @return the reference object of the child resource.
     */
    protected Reference getChildReference(Reference parentRef, String childId) {
        if (parentRef.getIdentifier().endsWith("/")) {
            return new Reference(parentRef.getIdentifier() + childId);
        } else {
            return new Reference(parentRef.getIdentifier() + "/" + childId);
        }
    }
    
    /**
     * 获取HttpRequest
     * @author Tracy Chen  2012-2-29
     * @return
     */
    protected HttpServletRequest getHttpRequest(){
    	return ServletUtils.getRequest(getRequest());   
    }

    /**
     * 获取HttpResponse
     * @author Tracy Chen 2012-2-29
     * @return
     */
    protected HttpServletResponse getHttpResponse(){
    	return ServletUtils.getResponse(getResponse());   
    }

    /**
     * 获取Session
     * @author Tracy Chen 2012-2-29
     * @return
     */
    protected HttpSession getHttpSession(){
    	return getHttpRequest().getSession();   
    }

	/**
     * Returns a templated representation dedicated to HTML content.
     * 
     * @param templateName
     *            the name of the template.
     * @param dataModel
     *            the collection of data processed by the template engine.
     * @return the representation.
     */
//    protected Representation getHTMLTemplateRepresentation(String templateName,
//			Map<String, Object> dataModel) {
//		// The template representation is based on Freemarker.
//		return new TemplateRepresentation(templateName, freemarkerConfig
//				.getConfiguration(), dataModel, MediaType.TEXT_HTML);
//	}
    
//    public FreeMarkerConfigurer getFreemarkerConfig() {
//		return freemarkerConfig;
//	}
//
//	public void setFreemarkerConfig(FreeMarkerConfigurer freemarkerConfig) {
//		this.freemarkerConfig = freemarkerConfig;
//	}
	
	
	@SuppressWarnings("unchecked")
	protected PageResponse getPageResponse(PageResponse oP){
		String pageNumShown=ServletUtils.getRequest(getRequest()).getParameter("pageNumShown");
		String currentPage=ServletUtils.getRequest(getRequest()).getParameter("pageNum");
//		String totalCount=ServletUtils.getRequest(getRequest()).getParameter("totalCount");
		String numPerPage=ServletUtils.getRequest(getRequest()).getParameter("numPerPage");
		
//		PageResponse p=new PageResponse();
//		p.setList(oP.getList());
		oP.setNumPerPage(Long.valueOf(numPerPage==null?"10":numPerPage));
		oP.setPageNumShown(Long.valueOf(pageNumShown==null?"10":pageNumShown));
		oP.setCurrentPage(Long.valueOf(currentPage==null?"1":currentPage));
		//p.setTotalCount(Long.valueOf(totalCount==null?"100":totalCount));
		return oP;
	}
	
	protected PageRequest getPageRequest(){
		String currentPage=ServletUtils.getRequest(getRequest()).getParameter("pageNum");
		String numPerPage=ServletUtils.getRequest(getRequest()).getParameter("numPerPage");
		PageRequest pr=new PageRequest();
		pr.setPageNumber(Integer.parseInt(currentPage==null?"1":currentPage));
		pr.setPageSize(Integer.parseInt(numPerPage==null?"10":numPerPage));
		return pr;
	}

	protected String getOrderField(){
		return getHttpRequest().getParameter("orderField");
	}
	
	protected String getOrderDirection(){
		return getHttpRequest().getParameter("orderDirection");
	}
	
	
	protected String getParameter(String paramName) {
		return getHttpRequest().getParameter(paramName);
	}
	
	
	/**
	 * 获取 默认DetachedCriteria 以便查询
	 * @author founder 2012-3-7
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DetachedCriteria getDetachedCriteria(Class c){
		DetachedCriteria dcri = DetachedCriteria.forClass(c);
		if(c.getSuperclass().equals(BaseEntity.class))
			dcri.add(Restrictions.eq(BaseEntity.DEL_FLAG,0));
		return dcri;
	}
	
	
	
	
	
	/**
	 * 填充查询条件
	 */
	public DetachedCriteria fillDetachedCriteria( Class entityClass,Object vo){
		DetachedCriteria dcri=getDetachedCriteria(entityClass);
		if(null!=vo){
			Class voClass= vo.getClass();
			try {
				Field[] f=entityClass.getFields();
				String voFiledName;
				String filedName;
				Class dbFiledType;
				String voValue;
				for(Field x:f){
					filedName=x.getName();
					if(!"DEL_FLAG".equals(filedName)&&!"CREATE_BY".equals(filedName)&&!"UPDATE_BY".equals(filedName)){
						try {
							voFiledName=(String)entityClass.getField(x.getName()).get(entityClass.newInstance());
							voValue=getVOFieldValue(voFiledName,vo);
							dbFiledType=entityClass.getMethod(getGetMethod(voFiledName), null).getReturnType();
							dcri=fillDetachedCriteria(dcri,dbFiledType,vo,voFiledName);
						} catch (Exception e) {
							continue;
						}
					}
				}
			} catch ( Exception e) {
				e.printStackTrace();
			}
		}
		if(entityClass.getSuperclass().equals(BaseEntity.class)){
			dcri=dcri.add(Restrictions.or(Restrictions.eq(BaseEntity.DEL_FLAG,Integer.valueOf(0)), Restrictions.isNull(BaseEntity.DEL_FLAG)));
		}
		if(StringUtils.isNotBlank(getOrderField())){
			if(ORDERTYPE_DESC.equalsIgnoreCase(getOrderDirection()))
				dcri.addOrder(Order.desc(getOrderField()));
			else
				dcri.addOrder(Order.asc(getOrderField()));
		}
		return dcri;
	}
	
	private DetachedCriteria fillDetachedCriteria(DetachedCriteria dcri , Class dbFiledType,Object vo,String voFiledName) throws Exception{
		//处理Short类型的高级搜索
		if(dbFiledType.equals(Short.class)){
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo))){
				dcri=dcri.add(Restrictions.eq(voFiledName,new Short(getVOFieldValue(voFiledName, vo))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "start"))){
				dcri=dcri.add(Restrictions.ge(voFiledName,new Short((getVOFieldValue(voFiledName, vo, "start")))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "end"))){
				dcri=dcri.add(Restrictions.le(voFiledName,new Short(getVOFieldValue(voFiledName, vo, "end"))));
			}
			
			
		}
		//处理Integer类型的高级搜索
		if(dbFiledType.equals(Integer.class)){
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo))){
				dcri=dcri.add(Restrictions.eq(voFiledName,new Integer(getVOFieldValue(voFiledName, vo))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "start"))){
				dcri=dcri.add(Restrictions.ge(voFiledName,new Integer((getVOFieldValue(voFiledName, vo, "start")))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "end"))){
				dcri=dcri.add(Restrictions.le(voFiledName,new Integer(getVOFieldValue(voFiledName, vo, "end"))));
			}
		}

		//处理Long类型的高级搜索
		if(dbFiledType.equals(Long.class)){
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo))){
				dcri=dcri.add(Restrictions.eq(voFiledName,new Long(getVOFieldValue(voFiledName, vo))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "start"))){
				dcri=dcri.add(Restrictions.ge(voFiledName,new Long((getVOFieldValue(voFiledName, vo, "start")))));
			}

			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "end"))){
				dcri=dcri.add(Restrictions.le(voFiledName,new Long(getVOFieldValue(voFiledName, vo, "end"))));
			}
			
			
		}
		//处理BigDecimal类型的高级搜索
		if(dbFiledType.equals(BigDecimal.class) ){
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo))){
				dcri=dcri.add(Restrictions.eq(voFiledName,new BigDecimal(getVOFieldValue(voFiledName, vo))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "start"))){
				dcri=dcri.add(Restrictions.ge(voFiledName,new BigDecimal((getVOFieldValue(voFiledName, vo, "start")))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "end"))){
				dcri=dcri.add(Restrictions.le(voFiledName,new BigDecimal(getVOFieldValue(voFiledName, vo, "end"))));
			}
			
		}

		//处理Timestamp类型的高级搜索
		if(dbFiledType.equals(Timestamp.class)){
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo))){
				dcri=dcri.add(Restrictions.eq(voFiledName,TimeUtils.string2Timestamp("yyyy-MM-dd", getVOFieldValue(voFiledName, vo))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "start"))){
				dcri=dcri.add(Restrictions.ge(voFiledName,TimeUtils.string2Timestamp("yyyy-MM-dd", getVOFieldValue(voFiledName, vo, "start"))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "end"))){
				dcri=dcri.add(Restrictions.le(voFiledName,TimeUtils.string2Timestamp("yyyy-MM-dd", getVOFieldValue(voFiledName, vo, "end"))));
			}
		
		}

		//处理java.util.Date类型的高级搜索
		if(dbFiledType.equals(java.util.Date.class)){
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo))){
				dcri=dcri.add(Restrictions.eq(voFiledName,(Date)TimeUtils.string2Timestamp("yyyy-MM-dd", getVOFieldValue(voFiledName, vo))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "start"))){
				dcri=dcri.add(Restrictions.ge(voFiledName,(Date)TimeUtils.string2Timestamp("yyyy-MM-dd", getVOFieldValue(voFiledName, vo, "start"))));
			}
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo, "end"))){
				dcri=dcri.add(Restrictions.le(voFiledName,(Date)TimeUtils.string2Timestamp("yyyy-MM-dd", getVOFieldValue(voFiledName, vo, "end"))));
			}
			
		}
		

		//处理String类型的高级搜索 
		if(dbFiledType.equals(String.class)){
			if(StringUtils.isNotBlank(getVOFieldValue(voFiledName, vo))){
				 //增加对%的转义  认为搜索的为值%
				String voFiledValue=getVOFieldValue(voFiledName, vo);
				if(voFiledValue.indexOf("%")>=0 )
				{
					voFiledValue=voFiledValue.replaceAll("%", "\\[%\\]");
				}
				dcri=dcri.add(Restrictions.like(voFiledName, voFiledValue,MatchMode.ANYWHERE));
			}
		}
		
		return dcri;
	}
	
	/**
	 * 获取参数Get方法名称
	 *
	 * @param filedName 字段名
	 * @return
	 * @author 陈文智
	 */
	public static String getGetMethod(String filedName){
		return "get"+filedName.substring(0,1).toUpperCase()+filedName.subSequence(1, filedName.length());
	}
	
	/**
	 * 获取vo的voFiledName字段值
	 *
	 * @param voFiledName
	 * @param vo
	 * @return
	 * @author 陈文智
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static String getVOFieldValue(String voFiledName,Object vo) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object obj=vo.getClass().getMethod(getGetMethod(voFiledName), null).invoke(vo, null)==null?"":vo.getClass().getMethod(getGetMethod(voFiledName), null).invoke(vo, null);
		return obj==null?"":obj.toString();
	}

	/**
	 * 获取vo的voFiledName字段值
	 *
	 * @param voFiledName
	 * @param vo
	 * @return
	 * @author 陈文智
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static Object getVOFieldValueObj(String voFiledName,Object vo) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object obj=vo.getClass().getMethod(getGetMethod(voFiledName), null).invoke(vo, null)==null?"":vo.getClass().getMethod(getGetMethod(voFiledName), null).invoke(vo, null);
		return obj;
	}
	
	/**
	 * 获取vo的voFiledName_append字段值  如获取 vo.getMoney_start
	 *
	 * @param voFiledName
	 * @param vo
	 * @param append
	 * @return
	 * @author 陈文智
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	private String getVOFieldValue(String voFiledName,Object vo,String append) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return getVOFieldValue(voFiledName+"_"+append,vo);
	}
}