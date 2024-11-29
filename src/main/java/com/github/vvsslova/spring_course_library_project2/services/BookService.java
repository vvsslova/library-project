package com.github.vvsslova.spring_course_library_project2.services;

import com.github.vvsslova.spring_course_library_project2.models.Book;
import com.github.vvsslova.spring_course_library_project2.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(Sort var) {
        return bookRepository.findAll(var);
    }

    public Page<Book> findAll(Pageable var) {
        return bookRepository.findAll(var);
    }

    public Book findOne(int id) {
        Optional<Book> fontBook = bookRepository.findById(id);
        return fontBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public List <Book> searchBook(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }
}
