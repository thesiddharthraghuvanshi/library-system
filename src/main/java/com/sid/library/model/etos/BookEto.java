package com.sid.library.model.etos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookEto {

    private Integer bookId;

    private String bookISBN;

    private String bookTitle;

    private String bookAuthor;

    private boolean borrowed;
}
