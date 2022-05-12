package com.yourcodereview.vsevolod_rychkov.link_shortener.generation;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
}
