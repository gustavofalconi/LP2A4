package br.edu.ifsp.spo.cliente;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "clientes")
@Entity(name = "Cliente")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String gender;

	@Embedded
	private Name name;

	@Embedded
	private Location location;

	private String email;

	@Embedded
	private Dob dob;

	@Embedded
	private Registered registered;

	@ElementCollection
	private List<String> telephoneNumbers = new ArrayList<>();

	@ElementCollection
	private List<String> mobileNumbers = new ArrayList<>();

	private String nationality;

	@Embedded
	private Picture picture;

	public Cliente(DadosCadastroCliente dados) {
		
		this.gender = dados.gender();
		this.name = dados.name();
		this.location = dados.location();
		this.email = dados.email();
		this.dob = dados.dob();
		this.registered = dados.registered();
		this.telephoneNumbers.add(dados.phone());
		this.mobileNumbers.add(dados.cell());
		this.nationality = null;
		this.picture = dados.picture();
	}
	
public Cliente(DadosCadastroClienteCsv dados) {
		
		this.gender = dados.gender();
		this.name = new Name();
		this.name.setFirst(dados.firstName());
		this.name.setLast(dados.lastName());
		this.name.setTitle(dados.title());
		this.location = new Location();
		this.location.setCoordinates(new Coordinates());
		this.location.setTimezone(new TimeZone());
		this.location.setStreet(dados.street());
		this.location.setCity(dados.city());
		this.location.getCoordinates().setLatitude(Double.parseDouble(dados.latitude()));
		this.location.getCoordinates().setLongitude(Double.parseDouble(dados.longitude()));
		this.location.setPostcode(Integer.parseInt(dados.postcode()));
		this.location.setState(dados.state());
		this.location.getTimezone().setDescription(dados.timezoneDescription());
		this.location.getTimezone().setOffset(dados.timezoneOffset());;
		this.email = dados.email();
		this.dob = new Dob();
		this.dob.setAge(Integer.parseInt(dados.dobAge()));
		this.dob.setDate(dados.dobDate());
		this.registered = new Registered();
		this.registered.setDate(dados.registeredDate());
		this.registered.setAge(Integer.parseInt(dados.registeredAge()));
		this.telephoneNumbers.add(dados.phone());
		this.mobileNumbers.add(dados.cell());
		this.nationality = null;
		this.picture = new Picture();
		this.picture.setLarge(dados.largePicture());
		this.picture.setMedium(dados.mediumPicture());
		this.picture.setThumbnail(dados.thumbnailPicture());
	}

}
