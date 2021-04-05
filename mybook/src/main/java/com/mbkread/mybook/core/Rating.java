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

/** Класс JPA-сущности для таблицы издателей, внутри используются аннотации, которые описывают, как поля класса будут маппиться в реляционную БД */
@Entity
@Table(name = "ratings")
public class Rating implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Это идентификатор записи, генерируется автоматически */
	@Id
	@GeneratedValue
	private Long id;

	/** Имя - непустая уникальная колонка */	
	@Column(nullable = false, unique = false)
	private String age;
	
	protected Rating() {
		//
	}
	
	public Rating(String name) {
		this.age = name;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return age;
	}
	
	@Override
	public String toString() {
		return String.format("(rating %d %s %s)", id, age);
	}

}

