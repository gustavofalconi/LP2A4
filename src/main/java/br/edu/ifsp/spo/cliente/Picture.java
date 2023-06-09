package br.edu.ifsp.spo.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
	
	@Column(name = "picture_large")
	private String large;
	@Column(name = "picture_medium")
	private String medium;
	@Column(name = "picture_thumbnail")
	private String thumbnail;
}
