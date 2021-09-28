package com.bookapp.gatewayservice.books;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookGatewayController {

    final BookService bookService;

    public BookGatewayController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public Book createPerson(@RequestBody Book person) {
        return bookService.save(person);
    }

    @PutMapping
    public Book putPerson(@RequestBody Book person) {
        return bookService.save(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        bookService.delete(id);
    }
}
