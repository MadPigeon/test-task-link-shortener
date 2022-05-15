package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.interfaces.LinkRepository;
import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.wrappers.LinkRequestWrapper;
import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.wrappers.LinkResponseWrapper;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class LinkControllerRestTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private LinkRepository mockRepository;

  @Before
  public void init() {
    final String shortLink = "someShortName";
    final String longLink = "http://some-server.com/some/url";
    final int rank = 1;
    final int count = 100500;
    Link link = new Link(shortLink, longLink, rank, count);
    when(mockRepository.findById(shortLink)).thenReturn(Optional.of(link));
  }

  @Test
  public void getLinkByShortLink() {

    String shortLink = "someShortName";
    String expected = "http://some-server.com/some/url";

    ResponseEntity<String> response = restTemplate.getForEntity("/l/someShortName", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertEquals(expected, response.getBody());

    verify(mockRepository, times(1)).findById(shortLink);

  }

  @Test
  public void getStatsByShortLink() throws JSONException {
    String shortLink = "someShortName";
    String expected = "{link:\"/l/someShortName\",original:\"http://some-server.com/some/url\",rank:1,count:100500}";

    ResponseEntity<String> response = restTemplate.getForEntity("/stats/someShortName", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    JSONAssert.assertEquals(expected, response.getBody(), false);

    verify(mockRepository, times(1)).findById(shortLink);

  }

  @Test
  public void findAllLinks() throws Exception {

    List<Link> links = Arrays.asList(
        new Link("shortLink1", "givenLongLink1"),
        new Link("shortLink2", "givenLongLink2"));

    when(mockRepository.findAll()).thenReturn(links);

    String expected = objectMapper.writeValueAsString(links);

    ResponseEntity<String> response = restTemplate.getForEntity("/stats", String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    JSONAssert.assertEquals(expected, response.getBody(), false);

    verify(mockRepository, times(1)).findAll();
  }

  @Test
  public void failsToRedirectNonExistantLink() throws Exception {
    String expected = "{status:404,error:\"Not Found\",message:\"Link arbitraryLink not found\",path:\"/l/arbitraryLink\"}";

    ResponseEntity<String> response = restTemplate.getForEntity("/l/arbitraryLink", String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    JSONAssert.assertEquals(expected, response.getBody(), false);
  }

  @Test
  public void failsShowingNonExistantStats() throws Exception {
    String expected = "{status:404,error:\"Not Found\",message:\"Link arbitraryLink not found\",path:\"/stats/arbitraryLink\"}";

    ResponseEntity<String> response = restTemplate.getForEntity("/stats/arbitraryLink", String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    JSONAssert.assertEquals(expected, response.getBody(), false);
  }

  @Test
  public void generatesLink() throws Exception {
    final String longLink = "https://some-url.org/request-cookies";
    final String stubShortLink = "not400please";
    LinkRequestWrapper requestBody = new LinkRequestWrapper(longLink);
    Link stubGeneratedLink = new Link(stubShortLink, longLink);
    LinkResponseWrapper expectedResponse = new LinkResponseWrapper(stubShortLink);
    when(mockRepository.save(any(Link.class))).thenReturn(stubGeneratedLink);

    String expected = objectMapper.writeValueAsString(expectedResponse);

    ResponseEntity<String> response = restTemplate.postForEntity("/generate", requestBody.getLink(), String.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    JSONAssert.assertEquals(expected, response.getBody(), false);

    verify(mockRepository, times(1)).save(any(Link.class));

  }

}
