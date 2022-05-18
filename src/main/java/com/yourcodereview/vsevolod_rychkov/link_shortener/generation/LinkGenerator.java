package com.yourcodereview.vsevolod_rychkov.link_shortener.generation;

import org.apache.commons.lang3.RandomStringUtils;

public class LinkGenerator {

  /**
   * removed ambiguous Il10O
   */
  private static final String ALLOWED_CHARACTERS = "ABCDEFGHKMNPQRSTVXYZabcdefghkmnpqrstvxyz23456789";
  private static final int DEFAULT_LINK_LENGTH = 5;

  public static String createLink() {
    return createLink(DEFAULT_LINK_LENGTH);
  }

  public static String createLink(int length) {
    return RandomStringUtils.random(length, ALLOWED_CHARACTERS);
  }

}
