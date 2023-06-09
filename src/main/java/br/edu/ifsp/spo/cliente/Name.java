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
public class Name {
	
	@Column(name = "name_title")
	private String title;
	
	@Column(name = "name_first")
	private String first;
	
	@Column(name = "name_last")
	private String last;
}
