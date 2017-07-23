package org.kangaroo.db.mybatis.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kangaroo.db.mybatis.common.PageResult;
import org.kangaroo.util.reflect.BeanCopyUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class GenericDao<E> implements IGenericDao<E> {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public int add(String mapper, E params) {
		return sqlSessionTemplate.insert(mapper,params);
	}

	@Override
	public int del(String mapper,Long primaryKey) {
		return sqlSessionTemplate.delete(mapper, primaryKey);
	}

	@Override
	public int update(String mapper, E param) {
		return sqlSessionTemplate.update(mapper, param);
	}

	@Override
	public E getUnique(String mapper,Long primaryKey) {
		return sqlSessionTemplate.selectOne(mapper, primaryKey);
	}

	@Override
	public List<E> getList(String mapper, Object param) {
		return sqlSessionTemplate.selectList(mapper,param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageResult<E> getPage(String mapper,int startPage,int pageSize,Object param){
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
		List<E> list=sqlSessionTemplate.selectList(mapper, map);
		pageResult.setResult(list);
		map.remove("page");//在查询参数的map中去掉page对象
		return pageResult;
	}
}
