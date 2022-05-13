package com.yourcodereview.vsevolod_rychkov.link_shortener.spring.interfaces;

import java.util.List;
import java.util.Optional;

import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.Link;

public interface LinkRepository {

  List<Link> getStatsFull();

  Link generateShortLink(Link newLink);

  Optional<Link> findLongLink(String shortLink);

  Optional<Link> getStats(String shortLink);

}
