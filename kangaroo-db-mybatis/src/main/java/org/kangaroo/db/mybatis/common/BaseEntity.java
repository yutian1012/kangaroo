package org.kangaroo.db.mybatis.common;

import java.io.Serializable;
import java.util.Date;

/**
 * MappedSuperclass：设置子类继承父类的注解
 *
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键字段
	 */
	private Long id;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long userId;
	/**
	 * 组织ID
	 */
	private Long orgId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}	
}
