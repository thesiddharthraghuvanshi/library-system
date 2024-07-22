package com.sid.library.model.etos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerEto {

    private Integer borrowerId;

    private String borrowerName;

    private String borrowerMail;

    private List<BookEto> borrowedBooks;

}
