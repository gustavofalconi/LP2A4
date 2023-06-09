package br.edu.ifsp.spo.cliente;

public record DadosCadastroCliente(
		
		String gender,
		
		Name name,
		
		Location location,
		
		String email,
	    
	    Dob dob,
	    
	    Registered registered,
	    
	    String phone,
	    
	    String cell,
	    
	    Picture picture
		) {}
