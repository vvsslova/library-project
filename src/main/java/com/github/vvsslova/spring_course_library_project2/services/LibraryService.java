package com.github.vvsslova.spring_course_library_project2.services;

import com.github.vvsslova.spring_course_library_project2.models.Book;
import com.github.vvsslova.spring_course_library_project2.models.Person;
import com.github.vvsslova.spring_course_library_project2.repositories.BookRepository;
import com.github.vvsslova.spring_course_library_project2.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LibraryService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    private final BookService bookService;

    @Autowired
    public LibraryService(PersonRepository personRepository, BookRepository bookRepository, BookService bookService) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public List<Book> lentBooks(Person person) {
        List<Book> lentBooks = bookRepository.findAllByLentPersonIs(person);
        for (Book book : lentBooks) {
            long today = new Date().getTime();
            if (book.getLendDate() != null) {
                book.setDeadlineCheckResult(today - book.getLendDate().getTime() >= 864000000);
            }
        }
        return lentBooks;
    }

    public Person getLentPerson(int id) {
        Optional<Book> fontBook = bookRepository.findById(id);
        return fontBook.map(Book::getLentPerson).orElse(null);
    }

    @Transactional
    public void lendBook(int bookId, int personId) {
        Optional<Book> fontBook = bookRepository.findById(bookId);
        Optional<Person> fontPerson = personRepository.findById(personId);
        if (fontBook.isPresent() & fontPerson.isPresent()) {
            Book lendingBook = fontBook.get();
            lendingBook.setLentPerson(fontPerson.get());
            lendingBook.setLendDate(new Date());
            bookService.update(bookId, lendingBook);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Transactional
    public void returnBook(int bookId) {
        Optional<Book> fontBook = bookRepository.findById(bookId);
        if (fontBook.isPresent()){
            fontBook.get().setLentPerson(null);
            fontBook.get().setLendDate(null);
        }
//        fontBook.ifPresent(book -> book.setLentPerson(null));
    }
}
