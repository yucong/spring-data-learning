package com.yucong.pojo.m2m;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_roles")
public class Roles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="roleid")
	private Integer roleid;
	
	@Column(name="rolename")
	private String rolename;
	
	/*
	 * fetch - 配置关系数据的抓取策略。就是查询方式。
	 * FetchType.EAGER - 不延迟加载，在查询Roles的同时，就将关系数据Menus查询出来。
	 * FetchType.LAZY - 延迟加载，默认值。只有使用关系数据Menus的时候，才访问数据库查询。
	 */
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	/*
	 * @JoinTable:配置中间表信息
	 * name - 中间关系表的表名。
	 * joinColumns:关系表中，指向当前表格的外键字段名。
	 * inverseJoinColumn:关系表中，指向另外一段表格的外键字段名。
	 */
	@JoinTable(name="t_roles_menus",joinColumns=@JoinColumn(name="role_id"),
		inverseJoinColumns=@JoinColumn(name="menu_id"))
	private Set<Menus> menus = new HashSet<>();
	
	
	public Set<Menus> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menus> menus) {
		this.menus = menus;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Override
	public String toString() {
		return "Roles [roleid=" + roleid + ", rolename=" + rolename + "]";
	}

	
	
}
