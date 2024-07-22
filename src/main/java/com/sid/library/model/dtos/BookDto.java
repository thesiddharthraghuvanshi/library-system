package com.sid.library.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private String bookISBN;

    private String bookTitle;

    private String bookAuthor;
}
