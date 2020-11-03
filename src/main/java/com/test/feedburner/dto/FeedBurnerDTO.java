package com.test.feedburner.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate published;

}
