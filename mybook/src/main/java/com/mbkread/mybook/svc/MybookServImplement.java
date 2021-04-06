package com.mbkread.mybook.svc;

import java.util.Collection;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import javax.transaction.Transactional;

import com.mbkread.mybook.core.Writer;
import com.mbkread.mybook.core.WriterLines;
import com.mbkread.mybook.repo.BookRepository;
import com.mbkread.mybook.repo.WriterRepository;
import com.mbkread.mybook.repo.WriterLinesRepository;
import com.mbkread.mybook.core.Translator;
import com.mbkread.mybook.core.Publisher;
import com.mbkread.mybook.core.OriginalLanguage;
import com.mbkread.mybook.core.Rating;
import com.mbkread.mybook.core.TypeCover;
import com.mbkread.mybook.core.Book;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/** Данная аннотация говорит, что этот класс используется как отдельный компонент-сервис
Класс является фасадом для модели приложения
*/
@Service
public class MybookServImplement implements MybookService{
	
	/** Инъекция зависимости от репозиториев таблиц */
	@Autowired
	private BookRepository bookz;

	@Autowired
	private WriterRepository writerz;

	@Autowired
	private WriterLinesRepository writerlinez;

	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	public Collection<Book> books() {
		return bookz.findAll();
	}
	
	@Override
	public Book book(Long id) {
		return bookz.getOne(id);
	}

	@Override
	public Book createBook(String title, Double cost, Short pagecnt, String annotation, String imgPath, Publisher publishID, 
			Translator translatorID, OriginalLanguage origlangID, Rating ratingID, TypeCover typecoverID) {
		return bookz.save(new Book(title, cost, pagecnt, annotation, imgPath, publishID, 
				translatorID, origlangID, ratingID, typecoverID));
	}

	@Override
	public void deleteBook(Long id) {
		bookz.deleteById(id);
	}

	@Override
	public void setWriterLines(Book book, Long[] writerIds) {
		writerlinez.deleteById(book.getId());
		for (Writer writer : writerz.findAllById(Arrays.asList(writerIds))) {
			writerlinez.save(new WriterLines(book, writer));
		}
		
	}

	@Override
	public Object writerlines(Book book) {
		// TODO Auto-generated method stub
		return null;
	}



}
