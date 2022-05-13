package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import java.util.List;

import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.error.LinkNotFoundException;
import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.interfaces.LinkRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkController {
  
  @Autowired
  private LinkRepository repository;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/generate")
  Link generateShortLink(@RequestBody Link longLink) {
    return repository.generateShortLink(longLink);
  }

  @GetMapping("/l/{shortLink}")
  Link findOne(@PathVariable String shortLink) {
    return repository.findLongLink(shortLink)
        .orElseThrow(() -> new LinkNotFoundException(shortLink));
  }

  @GetMapping("/stats")
  List<Link> getStatsFull() {
    return repository.getStatsFull();
  }

  @GetMapping("/stats/{shortLink}")
  Link getStatsForOneLink(@PathVariable String shortLink) {
    return repository.getStats(shortLink)
        .orElseThrow(() -> new LinkNotFoundException(shortLink));
  }

}
