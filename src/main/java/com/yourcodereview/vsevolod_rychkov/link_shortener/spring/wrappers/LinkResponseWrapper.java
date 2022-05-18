package com.yourcodereview.vsevolod_rychkov.link_shortener.spring.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.Link;

public class LinkResponseWrapper {
  @JsonProperty("link")
  String shortLinkWithPrefix;

  public LinkResponseWrapper(String shortLink) {
    setLink(shortLink);
  }

  public void setLink(String shortLink) {
    this.shortLinkWithPrefix = Link.LINK_PREFIX + shortLink;
  }

  public String getLink() {
    return shortLinkWithPrefix;
  }
}
