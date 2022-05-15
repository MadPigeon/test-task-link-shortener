package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
