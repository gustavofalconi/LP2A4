package br.edu.ifsp.spo.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DadosCadastroClienteCsv(

		@JsonProperty("﻿\"gender\"") String gender,

		@JsonProperty("name__title") String title,

		@JsonProperty("name__first") String firstName,

		@JsonProperty("name__last") String lastName,

		@JsonProperty("location__street") String street,

		@JsonProperty("location__city") String city,

		@JsonProperty("location__state") String state,

		@JsonProperty("location__postcode") String postcode,

		@JsonProperty("location__coordinates__latitude") String latitude,

		@JsonProperty("location__coordinates__longitude") String longitude,

		@JsonProperty("location__timezone__offset") String timezoneOffset,

		@JsonProperty("location__timezone__description") String timezoneDescription,

		@JsonProperty("email") String email,

		@JsonProperty("dob__date") String dobDate,

		@JsonProperty("dob__age") String dobAge,

		@JsonProperty("registered__date") String registeredDate,

		@JsonProperty("registered__age") String registeredAge,

		@JsonProperty("phone") String phone,

		@JsonProperty("cell") String cell,

		@JsonProperty("picture__large") String largePicture,

		@JsonProperty("picture__medium") String mediumPicture,

		@JsonProperty("picture__thumbnail") String thumbnailPicture

) {
}
