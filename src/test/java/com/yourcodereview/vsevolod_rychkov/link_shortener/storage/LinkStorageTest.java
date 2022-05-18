package com.yourcodereview.vsevolod_rychkov.link_shortener.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Test;

public class LinkStorageTest {

  @Test
  public void givesLink() {
    final String longLink = "https://bitly.com/";
    final String shortLink = LinkStorage.shorten(longLink);
    assertNotNull(shortLink);
    assertNotEquals(shortLink, longLink);
  }

  @Test
  public void givesSameShortLinkToSameLongLinks() {
    final String longLink = "https://bit.ly/";
    final String shortLink1 = LinkStorage.shorten(longLink);
    final String shortLink2 = LinkStorage.shorten(longLink);
    assertEquals(shortLink1, shortLink2);
  }

  @Test
  public void givesDifferentShortLinksToDifferentLongLinks() {
    final String longLink1 = "https://rb.gy/";
    final String longLink2 = "https://www.rebrandly.com/";
    final String shortLink1 = LinkStorage.shorten(longLink1);
    final String shortLink2 = LinkStorage.shorten(longLink2);
    assertNotEquals(shortLink1, shortLink2);
  }

  @Test
  public void givesLongLinkForShortLink() {
    final String longLinkBefore = "https://tinyurl.com/";
    final String shortLink = LinkStorage.shorten(longLinkBefore);
    final String longLinkAfter = LinkStorage.get(shortLink);
    assertEquals(longLinkBefore, longLinkAfter);
  }

  @Test(expected = NoSuchElementException.class)
  public void throwsExceptionWhenTryingToGetNonExistentLink() {
    final String shortLink = "linkThatDoesntExist";
    LinkStorage.get(shortLink);
    fail();
  }

}
