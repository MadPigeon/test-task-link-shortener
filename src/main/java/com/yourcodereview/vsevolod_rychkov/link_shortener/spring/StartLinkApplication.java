package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import org.springframework.boot.SpringApplication;

import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.interfaces.LinkRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StartLinkApplication {
  public static void main(String[] args) {
    SpringApplication.run(StartLinkApplication.class, args);
  }

  @Bean
  CommandLineRunner initDatabase(LinkRepository repository) {
    return args -> {
      repository.save(new Link("ya", "https://yandex.ru"));
      repository.save(new Link("duck", "https://duckduckgo.com"));
    };
  }

}
