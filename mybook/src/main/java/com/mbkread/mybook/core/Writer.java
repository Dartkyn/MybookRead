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

/** Класс JPA-сущности для таблицы писателей, внутри используются аннотации, которые описывают, как поля класса будут маппиться в реляционную БД */
@Entity
@Table(name = "writers")
public class Writer implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Это идентификатор записи, генерируется автоматически */
	@Id
	@GeneratedValue
	private Long id;

	/** Имя - непустая уникальная колонка */	
	@Column(nullable = false, unique = false)
	private String name;

	/** Фамилия - непустая уникальная колонка */	
	@Column(nullable = false, unique = false)
	private String surname;
	
	/** Отчество - непустая уникальная колонка */	
	@Column(nullable = true, unique = false)
	private String midname;
	
	/** Дата рождения - непустая уникальная колонка */	
	@Column(nullable = false, unique = false)
	private String birthDate;
	
	protected Writer() {
		//
	}
	
	public Writer(String name, String surname, String midname, String birthDate ) {
		this.name = name;
		this.surname = surname;
		this.midname = midname;
		this.birthDate = birthDate;
	}
	
	public Writer(String name, String surname, String birthDate ) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getSurName() {
		return surname;
	}
	
	public String getMidName() {
		return midname;
	}
	
	public String getBirthDate() {
		return birthDate;
	}

	@Override
	public String toString() {
		return String.format("%s %s", name, surname);
	}

}
