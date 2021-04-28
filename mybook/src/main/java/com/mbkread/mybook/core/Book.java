package com.mbkread.mybook.core;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** Класс JPA-сущности для таблицы книг, внутри используются аннотации, которые описывают, как поля класса будут маппиться в реляционную БД */
@Entity
@Table(name = "books")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Это идентификатор записи, генерируется автоматически */
	@Id
	@GeneratedValue
	private Long id;

	/** Наименование - непустая  колонка */	
	@Column(nullable = false, unique = false)
	private String title;

	/** Стоимость - непустая  колонка */	
	@Column(nullable = false, unique = false)
	private Double cost;
	
	/** Количество страниц - непустая  колонка */	
	@Column(nullable = false, unique = false)
	private Short pageCount;
	
	/** Аннотация - непустая  колонка */	
	@Column(nullable = false, unique = false)
	private String annotation;
	
	/** Путь к изображению - непустая  колонка */	
	@Column(nullable = false, unique = false)
	private String imagePath;
	
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (publish_id) REFERENCES publishers (id) ON DELETE CASCADE"))
	private Publisher publishID;
	
	@ManyToOne(optional = true)
	@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (translator_id) REFERENCES translators (id) ON DELETE RESTRICT"))
	private Translator translatorID;
	
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (originalLanguage_id) REFERENCES originallanguage (id) ON DELETE RESTRICT"))
	private OriginalLanguage origlangID;
	
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (rating_id) REFERENCES ratings (id) ON DELETE RESTRICT"))
	private Rating ratingID;
	
	@ManyToOne(optional = false)
	@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (typecover_id) REFERENCES typecovers (id) ON DELETE RESTRICT"))
	private TypeCover typecoverID;
	
	/** Коллекция строк писателей */
	@OneToMany(mappedBy = "id.book", cascade = CascadeType.REMOVE)
	private Collection<WriterLines> writerlines;

	protected Book() {
		//
	}

	public Book(String title, Double cost, Short pagecnt, String annotation, String imgPath, Publisher publish, 
			Translator translatorID, OriginalLanguage origlangID, Rating ratingID, TypeCover typecoverID) {
		this.title = title;
		this.cost = cost;
		this.pageCount = pagecnt;
		this.annotation = annotation;
		this.imagePath = imgPath;
		this.publishID = publish;
		this.translatorID = translatorID;
		this.origlangID = origlangID;
		this.ratingID = ratingID;
		this.typecoverID = typecoverID;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return title;
	}
	
	public Double getCost() {
		return cost;
	}
	
	public Short getPageCount() {
		return pageCount;
	}
	
	public String getAnnotation() {
		return annotation;
	}
	
	public String getImage() {
		return imagePath;
	}
	
	public String getPublisher() {
		return publishID.getName();
	}
	
	public String getTranslator() {
		return translatorID.toString();
	}
	
	public String getOrigLang() {
		return origlangID.getName();
	}
	
	public String getRating() {
		return ratingID.getName();
	}
	
	public String getTypeCover() {
		return typecoverID.getName();
	}
	
	public String getWriters() {
		String s = "";
		for(WriterLines writer:writerlines)
			{
				s=s.concat(writer.getWriter().toString());
			}
		return s;
	}
	
	public void setName(String title) {
		this.title = title;
	}
	
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	public void setPageCount(Short pageCount) {
		this.pageCount = pageCount;
		}
	
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
	
	public void setImage(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public void setPublisher(Publisher publishID) {
		this.publishID = publishID;
	}
	
	public void setTranslator(Translator translatorID) {
		this.translatorID = translatorID;
	}
	
	public void setOrigLang(OriginalLanguage origlangID) {
		this.origlangID = origlangID;
	}
	
	public void setRating(Rating ratingID) {
		this.ratingID = ratingID;
	}
	
	public void setTypeCover(TypeCover typecoverID) {
		this.typecoverID = typecoverID;
	}
	
	public void setWriters(Writer writer) {
		writerlines.clear();
		writerlines.add(new WriterLines(this, writer));
	}
	
	@Override
	public String toString() {
		return String.format("(book %d %s)", id, title);
	}

}

