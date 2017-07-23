package org.kangaroo.db.mybatis.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kangaroo.db.mybatis.common.PageResult;
import org.kangaroo.util.reflect.BeanCopyUtils;
import org.mybatis.spring.SqlSessionTemplate;

public abstract class BaseDao<E> implements BaseEntityDao<E> {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	/**
	 * 获取实体类的命名空间
	 * 子类需覆盖改方法
	 * @return
	 */
	public abstract Class<?> getEntityClass();
	/**
	 * 子类实现后返回实体类的Class对象，调用其getName()方法，作为mapper文件的命名空间。
	 * @return
	 */
	protected String getMybatisMapperNamespace(){
		return getEntityClass().getName();
	}

	@Override
	public int add(E obj) {
		String sqlKey=getMybatisMapperNamespace()+".add";
		return sqlSessionTemplate.insert(sqlKey, obj);
	}

	@Override
	public int delById(Long pramaryKey) {
		String sqlKey=getMybatisMapperNamespace()+".delById";
		return sqlSessionTemplate.delete(sqlKey, pramaryKey);
	}

	@Override
	public int update(E obj) {
		String sqlKey=getMybatisMapperNamespace()+".update";
		return sqlSessionTemplate.update(sqlKey, obj);
	}

	@Override
	public E getById(Long pramaryKey) {
		String sqlKy=getMybatisMapperNamespace()+".getById";
		return sqlSessionTemplate.selectOne(sqlKy,pramaryKey);
	}

	@Override
	public List<E> getList(Object param) {
		String sqlKey=getMybatisMapperNamespace()+"。getList";
		return sqlSessionTemplate.selectList(sqlKey,param);
	}

	@Override
	public List<E> getAll() {
		String sqlKey=getMybatisMapperNamespace()+".getAll";
		return sqlSessionTemplate.selectList(sqlKey);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageResult<E> getPage(int startPage,int pageSize,Object param){
		String sqlKey=getMybatisMapperNamespace()+".getPage";
		PageResult<E> pageResult=new PageResult<E>();
		pageResult.setStartPage(startPage);
		pageResult.setPageSize(pageSize);
		
		Map<String,Object> map=null;
		if(null==param){
			map=new HashMap<String,Object>();
			map.put("page", pageResult);
		}
		else if(param instanceof Map<?, ?>){
			map=(Map<String, Object>) param;
			map.put("page", pageResult);
		}else{
			map=new HashMap<String,Object>();
			Map<String,Object> temp=BeanCopyUtils.bean2Map(param);
			if(null!=temp&&temp.size()>0){
				map.putAll(temp);
				temp.clear();
			}
			map.put("page", pageResult);
		}
		List<E> list=sqlSessionTemplate.selectList(sqlKey, map);//new RowBound
		pageResult.setResult(list);
		return pageResult;
	}
	/**
	 * 通过传递key和参数map，查询出数据库中唯一的数据值
	 * @param mapper
	 * @param param
	 * @return
	 */
	public E getUnique(String mapper,Map<String,Object> param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.selectOne(sqlKey,param);
	}
	/**
	 * 更新用户信息
	 * @param mapper
	 * @param param
	 * @return
	 */
	public int update(String mapper,Map<String,Object> param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.update(sqlKey,param);
	}
	
	/**
	 * 一般用于有关联表的组合sql的查询情况
	 * @param mapper
	 * @param startPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageResult<E> getPage(String mapper,int startPage,int pageSize,Object param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		PageResult<E> pageResult=new PageResult<E>();
		pageResult.setStartPage(startPage);
		pageResult.setPageSize(pageSize);
		
		Map<String,Object> map=null;
		if(null==param){
			map=new HashMap<String,Object>();
			map.put("page", pageResult);
		}
		else if(param instanceof Map<?, ?>){
			map=(Map<String, Object>) param;
			map.put("page", pageResult);
		}else{
			map=new HashMap<String,Object>();
			Map<String,Object> temp=BeanCopyUtils.bean2Map(param);
			if(null!=temp&&temp.size()>0){
				map.putAll(temp);
				temp.clear();
			}
			map.put("page", pageResult);
		}
		List<E> list=sqlSessionTemplate.selectList(sqlKey, map);
		pageResult.setResult(list);
		return pageResult;
	}
	/**
	 * 一般用于有关联表的组合sql的查询情况
	 * @param mapper
	 * @param startPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageResult<Map<String,Object>> getMapPage(String mapper,int startPage,int pageSize,Object param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		PageResult<Map<String,Object>> pageResult=new PageResult<Map<String,Object>>();
		pageResult.setStartPage(startPage);
		pageResult.setPageSize(pageSize);
		
		Map<String,Object> map=null;
		if(null==param){
			map=new HashMap<String,Object>();
			map.put("page", pageResult);
		}
		else if(param instanceof Map<?, ?>){
			map=(Map<String, Object>) param;
			map.put("page", pageResult);
		}else{
			map=new HashMap<String,Object>();
			Map<String,Object> temp=BeanCopyUtils.bean2Map(param);
			if(null!=temp&&temp.size()>0){
				map.putAll(temp);
				temp.clear();
			}
			map.put("page", pageResult);
		}
		List<Map<String,Object>> list=sqlSessionTemplate.selectList(sqlKey, map);
		pageResult.setResult(list);
		return pageResult;
	}
	/**
	 * 一般用于向中间表插入数据
	 * @param mapper
	 * @param param
	 * @return
	 */
	public int add(String mapper,Map<String,Object> param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.insert(sqlKey, param);
	}
	/**
	 * 一般用于删除中间表数据
	 * @param mapper
	 * @param param
	 * @return
	 */
	public int del(String mapper,Map<String,Object> param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.delete(sqlKey, param);
	}
	/**
	 * 执行特殊的查询，返回map对象
	 * @param mapper
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getMap(String mapper,Map<String,Object> param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.selectList(sqlKey, param);
	}
	/**
	 * 获取对象列表
	 * @param mapper
	 * @param param
	 * @return
	 */
	public List<E> getAll(String mapper,Map<String,Object> param){
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.selectList(sqlKey,param);
	}
	public List<E> getList(String mapper,Map<String,Object> param) {
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.selectList(sqlKey,param);
	}
	
	public int  getCount(String mapper,Map<String,Object> param) {
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.selectOne(sqlKey,param);
	}
	
	public float getAmount(String mapper,Map<String,Object> param) {
		String sqlKey=getMybatisMapperNamespace()+"."+mapper;
		return sqlSessionTemplate.selectOne(sqlKey,param);
	}
}
