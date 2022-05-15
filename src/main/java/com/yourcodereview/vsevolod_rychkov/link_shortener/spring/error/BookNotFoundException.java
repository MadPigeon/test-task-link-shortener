package com.yourcodereview.vsevolod_rychkov.link_shortener.spring.error;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book id not found : " + id);
    }

}
