package com.yourcodereview.vsevolod_rychkov.link_shortener.generation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

public class LinkGeneratorTest {

  @Test
  public void createsLink() {
    String createdLink = LinkGenerator.createLink();
    assertTrue(createdLink != null);
    assertTrue(createdLink.length() > 0);
  }

  @Test
  public void createsDifferentLinks() {
    String link1 = LinkGenerator.createLink();
    String link2 = LinkGenerator.createLink();
    assertNotEquals(link1, link2);
  }

  @Test
  public void generatesOnlyUnambiguousCharacters() {
    final Pattern confusingCharacters = Pattern.compile("[Il1O0]", Pattern.CASE_INSENSITIVE);
    for (int i = 0; i < 100000; i++) {
      final String link = LinkGenerator.createLink(500);
      assertFalse(confusingCharacters.matcher(link).find());
    }
  }
}
