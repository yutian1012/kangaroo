package org.kangaroo.db.mybatis.base;

import java.util.List;

import org.kangaroo.db.mybatis.common.PageResult;

/**
 * dao层通用接口类
 */
public interface IGenericDao<E> {
	/**
	 * 向关联表中插入记录
	 * @param mapper
	 * @param params
	 * @return
	 */
	public int add(String mapper, E params);
	/**
	 * 删除
	 * @param mapper
	 * @param param
	 * @return
	 */
	public int del(String mapper,Long primaryKey);
	/**
	 * 更新
	 * @param mapper
	 * @param param
	 * @return
	 */
	public int update(String mapper,E param);
	/**
	 * 根据查询条件，返回唯一对象
	 * @param mapper
	 * @param param
	 * @return
	 */
	public E getUnique(String mapper,Long primaryKey);
	/**
	 * 获取符合条件的对象集合
	 * @param mapper
	 * @param param
	 * @return
	 */
	public List<E> getList(String mapper, Object param);
	/**
	 * 获取分页数据集合
	 * @param mapper
	 * @param startPage
	 * @param pageSize
	 * @param param
	 * @return
	 */
	public PageResult<E> getPage(String mapper,int startPage,int pageSize,Object param);
}
