package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Link{

  @Id
  private String shortLink;
  private String longLink;
  private long rank;
  private long count;

  public Link(String shortLink, String longLink) {
    this.shortLink = shortLink;
    this.longLink = longLink;
    this.count = 0;
  }

  public String getShortLink() {
    return shortLink;
  }

  public String getLongLink() {
    return longLink;
  }

  public long getCount() {
    return count;
  }

  public long getRank() {
    return rank;
  }
}
