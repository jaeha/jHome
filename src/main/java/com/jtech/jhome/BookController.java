package com.jtech.jhome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value="/book")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository reposiotory;

    @Value("${jhome.book.path}")
    private String bookPath;

    @GetMapping("")
    public String getBook(Model model) {
        return "book";
    }

    @GetMapping("/edit")
    public String getView(Model model, @RequestParam(value = "id", defaultValue = "0") long id) {
        Book book = reposiotory.findById(id);

        if (book != null) {
            model.addAttribute("book", book);
        } else {
            model.addAttribute("book", "Book ID=" + id + " does not exist!");
        }

        return "edit";
    }


//// API
    @GetMapping("/api/list")
    public @ResponseBody
    List<Book> getList() {
        return reposiotory.findAll();
    }


}