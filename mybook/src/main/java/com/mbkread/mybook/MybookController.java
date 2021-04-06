package com.mbkread.mybook;
import com.mbkread.mybook.core.Book;
import com.mbkread.mybook.core.OriginalLanguage;
import com.mbkread.mybook.core.Publisher;
import com.mbkread.mybook.core.Rating;
import com.mbkread.mybook.core.Translator;
import com.mbkread.mybook.core.TypeCover;
import com.mbkread.mybook.svc.MybookService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/** Аннотация сообщает контейнеру компонентов, что этот класс является контроллером*/
@Controller
public class MybookController {

	/** Инъекция сервиса бизнес-логики */
	@Autowired
	private MybookService hwJavaService;

	protected final Log log = LogFactory.getLog(getClass());

	/** Дальше пошли обработчики маршрутов */
	
	/** Список книг */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String index(Model vars) {
		/* Заполняем модель для представления */
		vars.addAttribute("books", hwJavaService.books()); 
		/* Возвращаем имя шаблона, который надо рендерить */
		return "books";
	}

	/** Страница одной книги, маршрут с параметром */
	@RequestMapping(method = RequestMethod.GET, value = "/books/{id}")
	public String book(@PathVariable Long id, Model vars) {
		Book book = hwJavaService.book(id);
		vars.addAttribute("book", book);
		return "book";
	}

	/** Маршрут на добавление книги */
	@RequestMapping(method = RequestMethod.POST, value = "/books/addbook")
	public String createBook(@RequestParam String title,@RequestParam Double cost, 
			@RequestParam Short pagecnt, @RequestParam String annotation, @RequestParam String imgPath,
			@RequestParam Publisher publishID, @RequestParam Translator translatorID, 
			@RequestParam OriginalLanguage origlangID, @RequestParam Rating ratingID, 
			@RequestParam TypeCover typecoverID) {
		Book book = hwJavaService.createBook(title, cost, pagecnt, annotation, imgPath, publishID, 
				translatorID, origlangID, ratingID, typecoverID);
		/* В этом случае у нас перенаправление, поэтому возвращаем не имя шаблона, а адрес перенаправления */
		return "redirect:/books/" + book.getId();
	}

	/** Маршрут на удаление книги */
	@RequestMapping(method = RequestMethod.DELETE, value = "/books/{id}")
	public String deleteStudent(@PathVariable Long id) {
		hwJavaService.deleteBook(id);
		return "redirect:/";
	}

	/** Маршрут на редактирование авторов книги */
	@RequestMapping(method = RequestMethod.PUT, value = "/books/{id}/writerlines")
	public String setWriterlines(@PathVariable Long id, @RequestParam("writerIds[]") Long[] writerIds) {
		Book book = hwJavaService.book(id);
		hwJavaService.setWriterLines(book, writerIds);
		return "redirect:/books/" + book.getId();
	}

}
