package com.yourcodereview.vsevolod_rychkov.link_shortener.storage;

import java.util.NoSuchElementException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.yourcodereview.vsevolod_rychkov.link_shortener.generation.LinkGenerator;

public class LinkStorage {

  private static BiMap<String, String> storage = HashBiMap.create();

  public static String shorten(String longLink) {
    if (!storage.containsValue(longLink)) {
      final String shortLink = LinkGenerator.createLink();
      storage.put(shortLink, longLink);
      return shortLink;
    } else {
      return storage.inverse().get(longLink);
    }

  }

  public static String get(String shortLink) {
    if (!storage.containsKey(shortLink)) {
      throw new NoSuchElementException(shortLink);
    }
    return storage.get(shortLink);
  }

}
