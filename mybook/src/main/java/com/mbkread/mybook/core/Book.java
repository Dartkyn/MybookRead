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

/** Класс JPA-сущности для таблицы студентов, внутри используются аннотации, которые описывают, как поля класса будут маппиться в реляционную БД */
@Entity
@Table(name = "books")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Это идентификатор записи, генерируется автоматически */
	@Id
	@GeneratedValue
	private Long id;

	/** Наименование - непустая уникальная колонка */	
	@Column(nullable = false, unique = true)
	private String title;

	/** Стоимость - непустая уникальная колонка */	
	@Column(nullable = false, unique = true)
	private Double cost;
	
	/** Количество страниц - непустая уникальная колонка */	
	@Column(nullable = false, unique = true)
	private Short pageCount;
	
	/** Аннотация - непустая уникальная колонка */	
	@Column(nullable = false, unique = true)
	private String annotation;
	
	/** Путь к изображению - непустая уникальная колонка */	
	@Column(nullable = false, unique = true)
	private String imagePath;
	
	/** Коллекция строк писателей */
	@OneToMany(mappedBy = "id.book", cascade = CascadeType.REMOVE)
	private Collection<WriterLines> writerlines;

	protected Book() {
		//
	}

	public Book(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return title;
	}

	@Override
	public String toString() {
		return String.format("(book %d %s)", id, title);
	}

}

