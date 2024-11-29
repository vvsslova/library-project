package com.github.vvsslova.spring_course_library_project2.repositories;

import com.github.vvsslova.spring_course_library_project2.models.Book;
import com.github.vvsslova.spring_course_library_project2.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByLentPersonIs(Person lentPerson);

    List<Book> findAll (Sort var);

    Page<Book> findAll (Pageable var1);

    List<Book> findByTitleStartingWith (String title);
}
