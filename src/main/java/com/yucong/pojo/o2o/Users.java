package com.yucong.pojo.o2o;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_users")
public class Users implements Serializable{
	private static final long serialVersionUID = 1760474751788614439L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//strategy=GenerationType.IDENTITY 自增长
	@Column(name="userid")
	private Integer userid;
	
	@Column(name="username")
	private String username;
	
	@Column(name="userage")
	private Integer userage;

	/*
	 * cascade - 代表级联写操作。在对当前的数据对象（用户）做写操作的时候（增、删、改），是否要同时操作关系对象。
	 */
	@OneToOne(cascade=CascadeType.PERSIST)
	//@JoinColumn：就是维护一个外键，定义在哪一个类型中，就是哪一个类型对应的表格来维护外键字段。
	//当前案例中，是Users类型中定义@JoinColumn，那么对应的表格t_users中有外键字段，引用Roles对应的表格的主键。
	@JoinColumn(name="roles_id")
	private Roles roles;
	
	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUserage() {
		return userage;
	}

	public void setUserage(Integer userage) {
		this.userage = userage;
	}

	@Override
	public String toString() {
		return "Users [userid=" + userid + ", username=" + username + ", userage=" 
					+ userage + ", roles=" + roles + "]";
	}

}
