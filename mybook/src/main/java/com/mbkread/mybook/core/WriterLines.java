package com.mbkread.mybook.core;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** Это класс JPA-сущности для хранения оценок */
@Entity
@Table(name = "writerLines")
public class WriterLines implements Serializable {

	private static final long serialVersionUID = 1L;

	/** идентификатор у этой таблицы составной, задается специальным объектом */
	@EmbeddedId
	private Id id;

	protected WriterLines() {
		//
	}

	public WriterLines(Book book, Writer writer) {
		id = new Id();
		id.book = book;
		id.writer = writer;
	}

	public Writer getWriter() {
		return id.writer;
	}


	@Override
	public String toString() {
		return String.format("(writerline %s)", id);
	}

	/** Это класс ключа таблицы оценок, он состоит из двух внешних ключей: до таблицы студентов и до таблицы дисциплин */
	@Embeddable
	// TODO: override equals(), hashCode()
	public static class Id implements Serializable {

		private static final long serialVersionUID = 1L;

		// N.B.
		@ManyToOne(optional = false)
		@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE"))
		private Book book;

		@ManyToOne(optional = false)
		private Writer writer;

		@Override
		public String toString() {
			return String.format("%s %s", book, writer);
		}

	}

}
