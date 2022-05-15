package com.yourcodereview.vsevolod_rychkov.link_shortener.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourcodereview.vsevolod_rychkov.link_shortener.spring.interfaces.LinkRepository;

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

import java.math.BigDecimal;
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
    // TODO: make it return redirect

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

  /*
   * 
   * @Test
   * public void save_book_OK() throws Exception {
   * 
   * Book newBook = new Book(1L, "Spring Boot Guide", "mkyong", new
   * BigDecimal("2.99"));
   * when(mockRepository.save(any(Book.class))).thenReturn(newBook);
   * 
   * String expected = om.writeValueAsString(newBook);
   * 
   * ResponseEntity<String> response = restTemplate.postForEntity("/books",
   * newBook, String.class);
   * 
   * assertEquals(HttpStatus.CREATED, response.getStatusCode());
   * JSONAssert.assertEquals(expected, response.getBody(), false);
   * 
   * verify(mockRepository, times(1)).save(any(Book.class));
   * 
   * }
   * 
   * @Test
   * public void update_book_OK() throws Exception {
   * 
   * Book updateBook = new Book(1L, "ABC", "mkyong", new BigDecimal("19.99"));
   * when(mockRepository.save(any(Book.class))).thenReturn(updateBook);
   * 
   * HttpHeaders headers = new HttpHeaders();
   * headers.setContentType(MediaType.APPLICATION_JSON);
   * HttpEntity<String> entity = new
   * HttpEntity<>(om.writeValueAsString(updateBook), headers);
   * 
   * ResponseEntity<String> response = restTemplate.exchange("/books/1",
   * HttpMethod.PUT, entity, String.class);
   * 
   * assertEquals(HttpStatus.OK, response.getStatusCode());
   * JSONAssert.assertEquals(om.writeValueAsString(updateBook),
   * response.getBody(), false);
   * 
   * verify(mockRepository, times(1)).findById(1L);
   * verify(mockRepository, times(1)).save(any(Book.class));
   * 
   * }
   * 
   * @Test
   * public void patch_bookAuthor_OK() {
   * 
   * when(mockRepository.save(any(Book.class))).thenReturn(new Book());
   * String patchInJson = "{\"author\":\"ultraman\"}";
   * 
   * HttpHeaders headers = new HttpHeaders();
   * headers.setContentType(MediaType.APPLICATION_JSON);
   * HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);
   * 
   * ResponseEntity<String> response = restTemplate.exchange("/books/1",
   * HttpMethod.PUT, entity, String.class);
   * 
   * assertEquals(HttpStatus.OK, response.getStatusCode());
   * 
   * verify(mockRepository, times(1)).findById(1L);
   * verify(mockRepository, times(1)).save(any(Book.class));
   * 
   * }
   * 
   * @Test
   * public void patch_bookPrice_405() throws JSONException {
   * 
   * String expected =
   * "{status:405,error:\"Method Not Allowed\",message:\"Field [price] update is not allow.\"}"
   * ;
   * 
   * String patchInJson = "{\"price\":\"99.99\"}";
   * 
   * HttpHeaders headers = new HttpHeaders();
   * headers.setContentType(MediaType.APPLICATION_JSON);
   * HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);
   * 
   * ResponseEntity<String> response = restTemplate.exchange("/books/1",
   * HttpMethod.PATCH, entity, String.class);
   * 
   * assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
   * JSONAssert.assertEquals(expected, response.getBody(), false);
   * 
   * verify(mockRepository, times(1)).findById(1L);
   * verify(mockRepository, times(0)).save(any(Book.class));
   * }
   * 
   * @Test
   * public void delete_book_OK() {
   * 
   * doNothing().when(mockRepository).deleteById(1L);
   * 
   * HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
   * ResponseEntity<String> response = restTemplate.exchange("/books/1",
   * HttpMethod.DELETE, entity, String.class);
   * 
   * assertEquals(HttpStatus.OK, response.getStatusCode());
   * 
   * verify(mockRepository, times(1)).deleteById(1L);
   * }
   */
  private static void printJSON(Object object) {
    String result;
    try {
      result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
      System.out.println(result);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

}
