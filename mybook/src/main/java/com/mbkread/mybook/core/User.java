package com.mbkread.mybook.core;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Это идентификатор записи, генерируется автоматически */
	@Id
	@GeneratedValue
	private Long id;

	/** Имя пользователя - непустая  колонка */	
	@Column(nullable = false, unique = true)
	private String userName;
	
	/** Пароль - непустая  колонка */	
	@Column(nullable = false, unique = false)
	private String userPassword;
	
	/** Коллекция строк писателей */
	@OneToMany(mappedBy = "id.user", cascade = CascadeType.REMOVE)
	private Collection<Order> orders;
	 
	private Boolean isActive;
	
	@Column(nullable = false, unique = false)
	private Boolean isAdmin;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + "]";
	}

	public User(Long id, String userName, String userPassword) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
	}
	protected User() {
		//
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	

}
