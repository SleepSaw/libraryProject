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
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public List<Person> index(){
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();
        return people;
    }
    @Transactional
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Person.class,id);
    }
    @Transactional
    public List<Book> showPersonsBooks(int id){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.find(Person.class,id);
        return person.getBooks();
    }
    @Transactional
    public void save(Person person){
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        Session session = sessionFactory.getCurrentSession();
        Person person =  session.find(Person.class, id);
        person.setName(updatedPerson.getName());
        person.setBirthDate(updatedPerson.getBirthDate());
        person.setEmail(updatedPerson.getEmail());
    }
    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.find(Person.class,id);
        session.remove(person);
    }
}
