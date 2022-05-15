package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Link {

  /**
   * web path prefix to links
   */
  public static final String LINK_PREFIX = "/l/";
  @Id
  @JsonIgnore
  private String shortLink;
  @JsonProperty("link")
  private String shortLinkWithPrefix;
  @JsonProperty("original")
  private String longLink;
  private long rank;
  private long count;

  public Link(String shortLink, String longLink) {
    this(shortLink, longLink, 0, 0);
  }

  public Link(String shortLink, String longLink, long rank, long count) {
    this.shortLink = shortLink;
    this.shortLinkWithPrefix = LINK_PREFIX + shortLink;
    this.longLink = longLink;
    this.count = count;
    this.rank = rank;
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

  public void bumpCount() {
    this.count += 1;
  }

  public long getRank() {
    return rank;
  }

  public void setRank(long rank) {
    this.rank = rank;
  }
}
