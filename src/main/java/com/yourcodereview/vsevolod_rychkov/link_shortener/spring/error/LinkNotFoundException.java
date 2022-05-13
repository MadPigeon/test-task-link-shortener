package com.yourcodereview.vsevolod_rychkov.link_shortener.spring.error;

public class LinkNotFoundException extends RuntimeException {

  public LinkNotFoundException(String shortLink) {
    super(String.format("Link %s not found"));
  }
}
