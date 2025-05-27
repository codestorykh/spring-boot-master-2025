package com.codestorykh.cache;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("books")
    public ResponseEntity<?> save(@RequestBody Book book) {
        bookRepository.saveAndFlush(book);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("books")
    public ResponseEntity<?> update(@RequestBody Book book) {
        Optional<Book> bookInDb = bookRepository.findFirstById(book.getId());
        if(bookInDb.isPresent()) {
            Book updatedBook = bookInDb.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setIsbn(book.getIsbn());
            updatedBook.setPublisher(book.getPublisher());
            updatedBook.setYear(book.getYear());
            updatedBook.setGenre(book.getGenre());

            bookRepository.save(updatedBook);
        }

        return ResponseEntity.accepted().build();
    }

    @PatchMapping("books")
    public ResponseEntity<?> patch(@RequestBody Book book) {
        int result = bookRepository.updateBookTitleAndYearById(book.getTitle(), book.getYear(), book.getId());
        System.out.println(result);
        return ResponseEntity.accepted().build();
    }
}
