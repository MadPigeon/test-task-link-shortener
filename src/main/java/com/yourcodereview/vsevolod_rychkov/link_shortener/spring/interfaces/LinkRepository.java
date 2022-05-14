package com.yourcodereview.vsevolod_rychkov.link_shortener.spring.interfaces;

import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.Link;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, String> {

}
