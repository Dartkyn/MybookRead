package com.mbkread.mybook;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;
import com.mbkread.mybook.core.Book;
import com.mbkread.mybook.core.Order;
import com.mbkread.mybook.core.OriginalLanguage;
import com.mbkread.mybook.core.Publisher;
import com.mbkread.mybook.core.Rating;
import com.mbkread.mybook.core.Translator;
import com.mbkread.mybook.core.TypeCover;
import com.mbkread.mybook.core.User;
import com.mbkread.mybook.core.Writer;
import com.mbkread.mybook.core.WriterLines;
import com.mbkread.mybook.svc.MybookService;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** Аннотация сообщает контейнеру компонентов, что этот класс является контроллером*/
@Controller
public class MybookController {

	/** Инъекция сервиса бизнес-логики */
	@Autowired
	private MybookService hwJavaService;
	
	private User activeUser = new User("def","def", false);
	private Boolean isLogin = false;

	protected final Log log = LogFactory.getLog(getClass());

	/** Дальше пошли обработчики маршрутов */
	
	/** Список книг */
	@GetMapping("/")
	public String index(Model vars) {
		/* Заполняем модель для представления */
		vars.addAttribute("books", hwJavaService.books()).addAttribute("user",activeUser).addAttribute("isLogin", isLogin); 
		/* Возвращаем имя шаблона, который надо рендерить */
		return "books";
	}
	
	/** Загрузка формы логинки*/
	@GetMapping("/login")
	public String login(Model vars) {
		vars.addAttribute("user",activeUser).addAttribute("isLogin", isLogin);
		/* Возвращаем имя шаблона, который надо рендерить */
		return "login";
	}
	
	/** Маршрут на поиск и проверку пользователя */
	@PostMapping("/login")
	public String loginUser(@RequestParam String userName,@RequestParam String userPassword) {
		User user = hwJavaService.user(userName);
		if((user != null) && (user.getUserPassword().equals(userPassword)))
			{
				activeUser = user;
				isLogin = true;
				return "redirect:/";
			}
		else
		{
			return "redirect:/login?error";
		}
		
	}
	
	/** Загрузка формы добавления нового пользователя*/
	@GetMapping("/signup")
	public String signupUser(Model vars) {
		vars.addAttribute("user",activeUser).addAttribute("isLogin", isLogin);
		/* Возвращаем имя шаблона, который надо рендерить */
		return "signup";
	}
	
	/** Маршрут на поиск и проверку пользователя */
	@PostMapping("/signup")
	public String signupUser(@RequestParam String userName,@RequestParam String userPassword) {
		User user = hwJavaService.createUser(userName, userPassword);
		return "redirect:/login?signup";
	}
	
	/** Маршрут на логаут */
	@PostMapping("/logout")
	public String logoutUser() {
		User user = new User("def","def", false);
		activeUser = user;
		isLogin = false;
		return "redirect:/";
		
	}
	/** Список справочников */
	@GetMapping("/catalog")
	public String catalog(Model vars) {
		if(activeUser.getIsAdmin())
		{/* Заполняем модель для представления */
			vars.addAttribute("publishers", hwJavaService.publishers()).
				addAttribute("typecovers", hwJavaService.typecovers()).
				addAttribute("origlangs", 
				hwJavaService.origlangs()).addAttribute("ratings", 
				hwJavaService.ratings()); 
			/* Возвращаем имя шаблона, который надо рендерить */
			return "catalog";
		}
		else
		{
			return "redirect:/";
		}
	}

	/** Страница одной книги, маршрут с параметром */
	@GetMapping("/books/{id}")
	public String book(@PathVariable Long id, Model vars) {
		Book book = hwJavaService.book(id);
		vars.addAttribute("book", book).addAttribute("user",activeUser).addAttribute("isLogin", isLogin);
		return "book";
	}
	
	/** Страница редактирования одной книги, маршрут с параметром */
	@GetMapping("/edit/{id}")
	public String editBook(@PathVariable Long id, Model vars) {
		if(activeUser.getIsAdmin())
		{
			Book book = hwJavaService.book(id);
			vars.addAttribute("book", book).addAttribute("publishers", hwJavaService.publishers()).addAttribute("writers", 
				hwJavaService.writers()).addAttribute("typecovers", hwJavaService.typecovers()).addAttribute("origlangs", 
				hwJavaService.origlangs()).addAttribute("translators", hwJavaService.translators()).addAttribute("ratings", 
				hwJavaService.ratings()).addAttribute("user",activeUser).addAttribute("isLogin", isLogin);
			return "edit";
		}
		else
		{
			return "redirect:/";
		}
	}
	
	/** Маршрут на форму редактирования книги */
	@PostMapping("/edit/{id}")
	public String editNewBook(@PathVariable("id") Long id,@RequestParam String title,@RequestParam Double cost, 
			@RequestParam Short pagecnt, @RequestParam String annotation, @RequestParam String imgPath,
			@RequestParam Publisher publishID, @RequestParam Translator translatorID, 
			@RequestParam OriginalLanguage origlangID, @RequestParam Rating ratingID, 
			@RequestParam TypeCover typecoverID, @RequestParam Writer writerID) {
		Book book = hwJavaService.book(id);
		book.setName(title);
		book.setAnnotation(annotation);
		book.setCost(cost);
		book.setImage("/images/"+imgPath);
		book.setPageCount(pagecnt);
		book.setOrigLang(origlangID);
		book.setPublisher(publishID);
		book.setRating(ratingID);
		book.setTranslator(translatorID);
		book.setWriters(hwJavaService.createWriterLine(book, writerID).getWriter());
		book.setTypeCover(typecoverID);
		book = hwJavaService.createBook(book);
		/* В этом случае у нас перенаправление, поэтому возвращаем не имя шаблона, а адрес перенаправления */
		return "redirect:/";
	}

	/** Маршрут на добавление книги */
	@GetMapping("/new")
	public String createBook(Model vars) {
		if(activeUser.getIsAdmin())
		{
		vars.addAttribute("publishers", hwJavaService.publishers()).addAttribute("writers", 
				hwJavaService.writers()).addAttribute("typecovers", hwJavaService.typecovers()).addAttribute("origlangs", 
				hwJavaService.origlangs()).addAttribute("translators", hwJavaService.translators()).addAttribute("ratings", 
				hwJavaService.ratings()).addAttribute("user",activeUser).addAttribute("isLogin", isLogin);
		return "new";
		}
		else
		{
			return "redirect:/";
		}
		
	}
	
	/** Маршрут на добавление книги */
	@PostMapping("/new")
	public String createNewBook(@RequestParam String title,@RequestParam Double cost, 
			@RequestParam Short pagecnt, @RequestParam String annotation, @RequestParam String imgPath,
			@RequestParam Publisher publishID, @RequestParam Translator translatorID, 
			@RequestParam OriginalLanguage origlangID, @RequestParam Rating ratingID, 
			@RequestParam TypeCover typecoverID, @RequestParam Writer writerID) {
		Book book = hwJavaService.createBook(title, cost, pagecnt, annotation,   "/images/" + imgPath, publishID, 
				translatorID, origlangID, ratingID, typecoverID);
		WriterLines writerLine = hwJavaService.createWriterLine(book, writerID);
		/* В этом случае у нас перенаправление, поэтому возвращаем не имя шаблона, а адрес перенаправления */
		return "redirect:/";
	}

	/** Маршрут на удаление книги */
	@DeleteMapping("/edit/{id}")
	public String deleteStudent(@PathVariable Long id) {
		hwJavaService.deleteBook(id);
		return "redirect:/";
	}

	/** Маршрут на редактирование авторов книги */
	@PutMapping("/books/{id}/writerlines")
	public String setWriterlines(@PathVariable Long id, @RequestParam("writerIds[]") Long[] writerIds) {
		Book book = hwJavaService.book(id);
		hwJavaService.setWriterLines(book, writerIds);
		return "redirect:/books/" + book.getId();
	}
	
	@RequestMapping(value="/publishers", method=RequestMethod.GET)
	public @ResponseBody String getPublishers(Model vars) throws JsonParseException, IOException
	{
		Gson gson = new Gson();
		vars.addAttribute("publishers", hwJavaService.publishers());
		String str = "";
		str = gson.toJson(hwJavaService.publishers());
		return str;
	}
	/** Страница редактирования одного издателя, маршрут с параметром */
	@GetMapping("/publisher/{id}")
	public String editPublisher(@PathVariable Long id, Model vars) {
		
		Publisher publisher = hwJavaService.publisher(id);
		return publisher.getName();
	}
	
	/** Маршрут на добавление издателя */
	@RequestMapping(value="/publisher/new", method=RequestMethod.GET)
	public @ResponseBody String createPublisher(String s) {
		return s;
	}
	@PostMapping("/publisher/addnew")
	public @ResponseBody String createNewPublisher(@RequestParam String name) {
		Publisher publisher = hwJavaService.createPublisher(name);
		/* В этом случае у нас перенаправление, поэтому возвращаем не имя шаблона, а адрес перенаправления */
		return publisher.getName();
	}
	
	/** Список заказов */
	@GetMapping("/orders")
	public String orders(Model vars) {
		if(isLogin)
		{	
		/* Заполняем модель для представления */
		vars.addAttribute("books", hwJavaService.books()).addAttribute("user",activeUser).addAttribute("isLogin", isLogin);
		vars.addAttribute("orders", hwJavaService.orders(activeUser));
		/* Возвращаем имя шаблона, который надо рендерить */
		return "orders";
		}
		else
		{
			return "redirect:/";
		}
	}
	
	/** Маршрут на форму редактирования книги */
	@PostMapping("/order/{id}")
	public String createNewBookOrder(@PathVariable("id") Long id) {
		if(isLogin)
		{	
			Book book = hwJavaService.book(id);
			Order order = hwJavaService.createOrder(book, activeUser);
			/* В этом случае у нас перенаправление, поэтому возвращаем не имя шаблона, а адрес перенаправления */
			return "redirect:/orders";
		}
		else
		{
			return "redirect:/";
		}
	}
	
	/** Список заказов */
	@GetMapping("/search")
	public String search(@RequestParam String search, Model vars) {
		/* Заполняем модель для представления */
		vars.addAttribute("user",activeUser).addAttribute("isLogin", isLogin);
		vars.addAttribute("books", hwJavaService.books(search));
		/* Возвращаем имя шаблона, который надо рендерить */
		return "search";
	}

}
