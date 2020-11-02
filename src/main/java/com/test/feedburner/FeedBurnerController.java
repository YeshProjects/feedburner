package com.test.feedburner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1/feedburners")
@Api(value = "FeedBurners")
public class FeedBurnerController {

	@Autowired
	private FeedBurnerService feedBurnerService;

	@ApiOperation(value = "View List of Feeds")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = FeedBurnerDTO.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FeedBurnerDTO>> getAllFeedBurners() {

		String url = "http://feeds.feedburner.com/PoorlyDrawnLines";

		return new ResponseEntity<>(feedBurnerService.getAll(url), HttpStatus.OK);
	}

}
