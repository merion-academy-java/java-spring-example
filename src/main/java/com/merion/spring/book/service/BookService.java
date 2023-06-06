package com.merion.spring.book.service;

import com.merion.spring.base.exception.ResourceNotFoundException;
import com.merion.spring.book.entity.BookEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    static List<BookEntity> bookStorage = new ArrayList<>();

    public BookService() {
        this.fillStorage();
    }

    public void fillStorage() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            BookEntity book = new BookEntity();
            book.setId(i);
            book.setTitle("Book #" + random.nextInt(100, 999));
            book.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque dignissim mattis ex, vel ultrices augue congue quis. Nulla facilisi. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin lacinia urna id turpis tincidunt, vitae suscipit nunc tempor. Interdum et malesuada fames ac ante ipsum primis in faucibus. Suspendisse potenti. Suspendisse non iaculis odio, eget venenatis ipsum. Sed fermentum ultrices metus, tincidunt placerat enim tempor quis. Aliquam eu quam metus. Morbi arcu dui, dictum id felis et, maximus vestibulum enim. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.");
            bookStorage.add(book);
        }
    }

    public List<BookEntity> all() {
        return bookStorage;
    }

    public Optional<BookEntity> byId(Integer id) {
        return bookStorage.stream().filter((bookEntity -> bookEntity.getId().equals(id))).findFirst();
    }

    public BookEntity create(String title, String description) {
        BookEntity book = new BookEntity();
        book.setId(bookStorage.size());
        book.setTitle(title);
        book.setDescription(description);
        bookStorage.add(book);
        return book;
    }

    public Optional<BookEntity> edit(BookEntity book) {
        Optional<BookEntity> oldBookOptional = byId(book.getId());
        if(oldBookOptional.isEmpty()){
            return Optional.empty();
        }

        BookEntity oldBook = oldBookOptional.get();
        oldBook.setTitle(book.getTitle());
        oldBook.setDescription(book.getDescription());
        return Optional.of(oldBook);
    }

    public Boolean delete(Integer id) {
        Optional<BookEntity> book = byId(id);
        if(book.isEmpty()) return false;

        bookStorage.remove(book.get());
        return true;
    }

    public Optional<BookEntity> editPart(Integer id, Map<String, String> fields) {
        Optional<BookEntity> oldBookOptional = byId(id);
        if(oldBookOptional.isEmpty()){
            return Optional.empty();
        }

        BookEntity oldBook = oldBookOptional.get();

        for(String key : fields.keySet()){
            switch (key) {
                case "title" -> oldBook.setTitle(fields.get(key));
                case "description" -> oldBook.setDescription(fields.get(key));
            }
        }

        return Optional.of(oldBook);
    }

}
