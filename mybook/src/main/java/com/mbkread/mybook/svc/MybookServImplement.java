package com.mbkread.mybook.svc;

import java.util.Collection;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import javax.transaction.Transactional;

import com.mbkread.mybook.core.Writer;
import com.mbkread.mybook.core.WriterLines;
import com.mbkread.mybook.repo.BookRepository;
import com.mbkread.mybook.repo.OrderRepository;
import com.mbkread.mybook.repo.OrigLangRepository;
import com.mbkread.mybook.repo.PublisherRepository;
import com.mbkread.mybook.repo.RatingRepository;
import com.mbkread.mybook.repo.TranslatorRepository;
import com.mbkread.mybook.repo.TypeCoverRepository;
import com.mbkread.mybook.repo.UserRepository;
import com.mbkread.mybook.repo.WriterRepository;
import com.mbkread.mybook.repo.WriterLinesRepository;
import com.mbkread.mybook.core.Translator;
import com.mbkread.mybook.core.Publisher;
import com.mbkread.mybook.core.OriginalLanguage;
import com.mbkread.mybook.core.Rating;
import com.mbkread.mybook.core.TypeCover;
import com.mbkread.mybook.core.User;
import com.mbkread.mybook.core.Book;
import com.mbkread.mybook.core.Order;

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

	@Autowired
	private OrigLangRepository origlangz;
	
	@Autowired
	private PublisherRepository publisherz;
	
	@Autowired
	private RatingRepository ratingz;
	
	@Autowired
	private TranslatorRepository translatorz;
	
	@Autowired
	private TypeCoverRepository typecoverz;
	
	@Autowired
	private UserRepository userz;
	
	@Autowired
	private OrderRepository orderz;
	
	protected final Log log = LogFactory.getLog(getClass());

	private User user;
	
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
		return writerlinez.findAll();
	}

	@Override
	public Collection<Publisher> publishers() {
		return publisherz.findAll();
	}

	@Override
	public Collection<OriginalLanguage> origlangs() {
		return origlangz.findAll();
	}

	@Override
	public Collection<TypeCover> typecovers() {
		return typecoverz.findAll();
	}

	@Override
	public Collection<Rating> ratings() {
		return ratingz.findAll();
	}

	@Override
	public Collection<Writer> writers() {
		return writerz.findAll();
	}

	@Override
	public Collection<Translator> translators() {
		return translatorz.findAll();
	}

	@Override
	public WriterLines createWriterLine(Book bookID, Writer writerID) {
		return writerlinez.save(new WriterLines(bookID, writerID));
	}

	@Override
	public Book createBook(Book book) {
		return bookz.save(book);
	}

	@Override
	public Publisher publisher(Long id) {
			return publisherz.getOne(id);
	}

	@Override
	public Publisher createPublisher(String name) {
		return publisherz.save(new Publisher(name));
	}

	@Override
	public Collection<User> users() {
		return userz.findAll();
	}

	@Override
	public Collection<Order> orders() {
		return orderz.findAll();
	}

	@Override
	public User user(Long id) {
		return userz.getOne(id);
	}

	@Override
	public User user(String userName) {
		for(User us:userz.findAll())
		{
			if(us.getUserName().equals(userName))
			{
				return us;
			}
		}
		return null;
	}

	@Override
	public Order createOrder(Book book, User user) {
		return orderz.save(new Order(book, user));
	}

	@SuppressWarnings("null")
	public Collection<Order> orders(User user) {
		Collection<Order> orderNew = orderz.findAll();
		for(Order order: orderz.findAll())
			{
				if(order.getUser().getId() != user.getId())
					{
						orderNew.remove(order);
					}
			}
		return orderNew;
	}

	@Override
	public User createUser(String userName, String userPassword) {
		return userz.save(new User(userName, userPassword, false));
	}

	@Override
	public Collection<Book> books(String search) {
		Collection<Book> bookNew = bookz.findAll();
		for(Book book: bookz.findAll())
			{
				if(!(book.getPublisher().contains(search) ||book.getWriters().contains(search)))
					{
						bookNew.remove(book);
					}
			}
		return bookNew;
	}


}
