package com.yourcodereview.vsevolod_rychkov.link_shortener.generation;

import org.apache.commons.lang3.RandomStringUtils;

public class LinkGenerator {

  public static String createLink() {
    boolean hasAmbiguousCharacters = true;
    String link;
    do {
      link = RandomStringUtils.random(19, true, true);
      hasAmbiguousCharacters = false;
    } while (hasAmbiguousCharacters);
    return link;
  }

}
