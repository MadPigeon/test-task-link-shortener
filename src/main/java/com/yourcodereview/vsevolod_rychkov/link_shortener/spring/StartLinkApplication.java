package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import org.springframework.boot.SpringApplication;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class StartLinkApplication {
  public static void main(String[] args) {
    SpringApplication.run(StartLinkApplication.class, args);
  }

  @Profile("demo")
  @Bean
  CommandLineRunner initDatabase(BookRepository repository) {
    return args -> {
      repository.save(new Book("A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41")));
      repository.save(new Book("The Life-Changing Magic of Tidying Up", "Marie Kondo", new BigDecimal("9.69")));
      repository.save(
          new Book("Refactoring: Improving the Design of Existing Code", "Martin Fowler", new BigDecimal("47.99")));
    };
  }

}
