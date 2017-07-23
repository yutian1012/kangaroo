package org.kangaroo.db.mybatis.base;

import java.util.List;

import org.kangaroo.db.mybatis.common.PageResult;


public interface BaseEntityDao<E> {
	/**
	 * 添加对象
	 * @param obj
	 */
	public int add(E obj);
	/**
	 * 根据主键删除对象
	 * @param pramaryKey
	 * @return
	 */
	public int delById(Long pramaryKey);
	/**
	 * 更新对象
	 * @param obj
	 * @return
	 */
	public int update(E obj);
	/**
	 * 根据主键加载对象
	 * @param pramaryKey
	 * @return
	 */
	public E getById(Long pramaryKey);
	/**
	 * 根据参数获取查询对象的集合
	 * @param param
	 * @return
	 */
	public List<E> getList(Object param);
	/**
	 * 获取所有对象的集合
	 * @return
	 */
	public List<E> getAll();
	/**
	 *  获取分页集合
	 * @param startPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public PageResult<E> getPage(int startPage,int pageSize,Object param);
	
}
