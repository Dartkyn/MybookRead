package com.mbkread.mybook.svc;

import java.util.Collection;
import java.util.Map;

import com.mbkread.mybook.core.Writer;
import com.mbkread.mybook.core.Translator;
import com.mbkread.mybook.core.Publisher;
import com.mbkread.mybook.core.OriginalLanguage;
import com.mbkread.mybook.core.Rating;
import com.mbkread.mybook.core.TypeCover;
import com.mbkread.mybook.core.Book;

public interface MybookService {
	Collection<Book> books();

	Book book(Long id);

	void setWriterLines(Book book, Long[] writerIds);

	Book createBook(String title, Double cost, Short pagecnt, String annotation, String imgPath, Publisher publishID, 
			Translator translatorID, OriginalLanguage origlangID, Rating ratingID, TypeCover typecoverID);

	void deleteBook(Long id);

}
