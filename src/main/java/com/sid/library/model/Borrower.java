package com.sid.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Borrower {

    @Id
    @GeneratedValue
    private Integer borrowerId;

    private String name;

    private String email;

    @ManyToMany
    private List<Book> books;
}
