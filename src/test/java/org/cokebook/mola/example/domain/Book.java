package org.cokebook.mola.example.domain;

import lombok.Data;
import org.cokebook.mola.Model;
import org.cokebook.mola.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wuming
 * @date 2019/7/7 16:45
 */
@Data
@Model
public class Book {

    @Autowired
    private BookRepository bookRepository;

    private int id;
    private String name;

}
