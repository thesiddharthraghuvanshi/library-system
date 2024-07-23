package com.sid.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Borrower {

    @Id
    @GeneratedValue
    private Integer borrowerId;

    private String name;

    private String email;

    @ManyToMany
    private List<Book> books;
}
