package com.github.vvsslova.spring_course_library_project2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Title should not be empty!")
    @Size(min = 5, max = 70, message = "Title should be between 2 and 40 characters!")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author should not be empty!")
    @Size(min = 5, max = 70, message = "Author should be between 2 and 40 characters!")
    @Column(name = "author")
    private String author;

    @Max(value = 2024, message = "Year of publication should not be grater than 2024!")
    @Column(name = "year_of_production")
    private int yearOfPublication;

    @Column(name = "lend_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lendDate;

    @Transient
    private boolean deadlineCheckResult;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person lentPerson;

    public Book(String title, String author, int yearOfPublication) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }
}
