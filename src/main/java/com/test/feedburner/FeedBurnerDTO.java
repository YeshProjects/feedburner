package com.test.feedburner;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedBurnerDTO {

	@JsonIgnore
	private Long id;

	@NotNull
	private String title;

	@NotNull
	private String img;

	@NotNull
	private String uri;

	@NotNull
	private String link;

	@JsonFormat(pattern = "dd.MM.yyyy")
	private LocalDate published;

}
