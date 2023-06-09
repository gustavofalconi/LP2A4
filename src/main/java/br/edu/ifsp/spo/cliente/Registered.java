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
public class Registered {
	
	@Column(name = "registered_date")
	private String date;
	
	@Column(name = "registered_age")
	private Integer age;
}
