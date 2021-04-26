package com.mbkread.mybook.core;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private Id id;
	public Order(Book book, User user) {
		id = new Id();
		id.book = book;
		id.user = user;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + "]";
	}
	/** Это класс ключа таблицы заказов, он состоит из двух внешних ключей: до таблицы пользователя и до таблицы книг */
	@Embeddable
	// TODO: override equals(), hashCode()
	public static class Id implements Serializable {



		private static final long serialVersionUID = 1L;

		// N.B.
		@ManyToOne(optional = false)
		@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE"))
		private Book book;

		@ManyToOne(optional = false)
		@JoinColumn(foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT"))
		private User user;
		
		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		@Override
		public String toString() {
			return String.format("%s %s", book, user);
		}

	}
}
