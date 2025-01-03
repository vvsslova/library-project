package com.github.vvsslova.spring_course_library_project2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 5, max = 70, message = "Name should be between 2 and 60 characters!")
    @Pattern(regexp = "^[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+$",
            message = "Введите данные в корректном формате")
    @Column(name = "name")
    private String name;

    @Min(value = 1900, message = "Year of birth should not be smaller than 1900!")
    @Max(value = 2024, message = "Year of birth should not be grater than 2024!")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "lentPerson")
    private List<Book> lentBooks;

    public Person(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }
}
