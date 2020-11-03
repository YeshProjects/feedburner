package com.test.feedburner.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.test.feedburner.dto.FeedBurnerDTO;
import com.test.feedburner.service.FeedBurnerService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FeedBurnerControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@InjectMocks
	private FeedBurnerController feedBurnerController;

	@Mock
	private FeedBurnerService feedBurnerService;

	@Test
	void testGetAllFeedBurners() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		String baseUrl = "http://localhost:" + port + "/api/v1/feedburners/";
		URI uri = new URI(baseUrl);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<FeedBurnerDTO> requestEntity = new HttpEntity<>(null, requestHeaders);

		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		List<FeedBurnerDTO> participantJsonList = mapper.readValue(responseEntity.getBody(),
				new TypeReference<List<FeedBurnerDTO>>() {
				});
		assertThat(responseEntity.getStatusCode().is2xxSuccessful());

	}
	
	@Test
	void testGetAllFeedBurners_404() throws URISyntaxException, JsonMappingException, JsonProcessingException {
		String baseUrl = "http://localhost:" + port + "/api/v1/feedburne/";
		URI uri = new URI(baseUrl);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<FeedBurnerDTO> requestEntity = new HttpEntity<>(null, requestHeaders);

		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		Assertions.assertThrows(MismatchedInputException.class, () -> {
		List<FeedBurnerDTO> participantJsonList = mapper.readValue(responseEntity.getBody(),
				new TypeReference<List<FeedBurnerDTO>>() {
				});
		});
		assertThat(responseEntity.getStatusCode().is4xxClientError());

	}

}
