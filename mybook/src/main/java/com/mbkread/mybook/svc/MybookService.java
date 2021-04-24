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
import com.mbkread.mybook.core.WriterLines;

public interface MybookService {
	Collection<Book> books();
	Collection<Publisher> publishers();
	Collection<OriginalLanguage> origlangs();
	Collection<TypeCover> typecovers();
	Collection<Rating> ratings();
	Collection<Writer> writers();
	Collection<Translator> translators();
	Book book(Long id);
	Publisher publisher(Long id);
	void setWriterLines(Book book, Long[] writerIds);

	Book createBook(String title, Double cost, Short pagecnt, String annotation, String imgPath, Publisher publishID, 
			Translator translatorID, OriginalLanguage origlangID, Rating ratingID, TypeCover typecoverID);
	Book createBook(Book book);
	WriterLines createWriterLine(Book bookID, Writer writerID );

	void deleteBook(Long id);

	Object writerlines(Book book);
	Publisher createPublisher(String name);

}
