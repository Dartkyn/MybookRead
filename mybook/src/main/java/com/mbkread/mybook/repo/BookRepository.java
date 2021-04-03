package com.mbkread.mybook.repo;

import com.mbkread.mybook.core.Book;

import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book, Long>{

}
