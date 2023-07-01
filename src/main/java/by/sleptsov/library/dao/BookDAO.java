package by.sleptsov.library.dao;

import by.sleptsov.library.models.Book;
import by.sleptsov.library.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;
    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public List<Book> index(){
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("select b from Book b", Book.class).getResultList();
        return books;
    }
   @Transactional
    public Book show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.find(Book.class,id);
    }
    @Transactional
    public void save(Book newBook){
        Session session = sessionFactory.getCurrentSession();
        session.persist(newBook);
    }
    @Transactional
    public void assignBook(int bookId, int personId){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class, bookId);
        Person owner = session.find(Person.class, personId);
        book.setOwner(owner);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        Session session = sessionFactory.getCurrentSession();
        Book book =  session.find(Book.class, id);
        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setDescription(updatedBook.getDescription());
    }
    @Transactional
    public Person getOwnerById(int bookId){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class, bookId);
        return book.getOwner();
    }
    @Transactional
    public List<Book> findBooks(String str){
        return null;
    }
    @Transactional
    public void release(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class, id);
        book.setOwner(null);
//        jdbcTemplate.update("UPDATE book SET reader=null WHERE book.id=?",id);
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.find(Book.class,id);
        session.remove(book);
    }

}
