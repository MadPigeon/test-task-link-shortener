package com.yourcodereview.vsevolod_rychkov.link_shortener.spring.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkRequestWrapper {
  @JsonProperty("original")
  String longLink;

  public LinkRequestWrapper(String longLink) {
    this.longLink = longLink;
  }

  public void setLink(String longLink) {
    this.longLink = longLink;
  }

  public String getLink() {
    return longLink;
  }
}
