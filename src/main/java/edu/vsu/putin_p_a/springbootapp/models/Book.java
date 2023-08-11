package edu.vsu.putin_p_a.springbootapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(max = 512, message = "Name must be less than 512 characters.")
    @NotEmpty(message = "Name can't be empty.")
    private String name;

    @Column(name = "author")
    @Size(max = 512, message = "Author name must be less than 512 characters.")
    @NotEmpty(message = "Author name can't be empty.")
    private String author;

    @Column(name = "publish_year")
    private Integer publishYear;

    // TODO: set fetch type LAZY
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "capture_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date captureDate;

    public Book(int id, String name, String author, Integer publishYear) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public Optional<Person> getOwner() {
        return Optional.ofNullable(owner);
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    public boolean isExpired() {
        Date now = new Date();
        long diffInMilliseconds = Math.abs(now.getTime() - captureDate.getTime());
        final int expiredIntervalInDays = 10;
        return TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS) > expiredIntervalInDays;
    }
}
