package edu.vsu.putin_p_a.springbootapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @Size(min = 2, max = 512, message = "Full name length must be between 2 and 512 include.")
    private String fullName;

    @Column(name = "birthday_year")
    private int birthdayYear;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Person(int id, String fullName, int birthdayYear) {
        this.id = id;
        this.fullName = fullName;
        this.birthdayYear = birthdayYear;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        book.setOwner(this);
    }
}
