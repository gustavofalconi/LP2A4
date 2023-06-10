package br.edu.ifsp.spo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.cliente.Cliente;
import br.edu.ifsp.spo.service.ClienteDataService;

@RestController
@RequestMapping("clientes")
@CrossOrigin(origins = "file:///C:/Users/supor/OneDrive/Documentos/JavaProjetos/lp2a4-front/index.html")
public class ClienteController {

	private final ClienteDataService dataService;

	public ClienteController(ClienteDataService dataService) {
		this.dataService = dataService;
	}

	@GetMapping
    public List<Cliente> getClientes(@RequestParam(required = false) String region, 
    		@RequestParam(required = false) String classificacao){
    	
    	List<Cliente> clientes = dataService.getUsers();
    	
    	if (region != null && classificacao != null) {
    	    clientes = filtrarPorRegiaoEClassificacao(clientes, region, classificacao);
    	}
    	if(region != null) {
    		clientes = filtrarPorRegiao(clientes, region);
    	}
    	
    	if(classificacao != null) {
    		clientes = filtrarPorClassificacao(clientes, classificacao);
    	}
    	
    	return clientes;
    }

	private List<Cliente> filtrarPorRegiaoEClassificacao(List<Cliente> clientes, String region, String classificacao) {

		return clientes.stream().filter(cliente -> cliente.getLocation().getRegion().equals(region)
				&& cliente.getClassificacao().equals(classificacao)).toList();
	}

	private List<Cliente> filtrarPorClassificacao(List<Cliente> clientes, String classificacao) {

		return clientes.stream().filter(cliente -> cliente.getClassificacao().equals(classificacao)).toList();
	}

	private List<Cliente> filtrarPorRegiao(List<Cliente> clientes, String region) {

		return clientes.stream().filter(cliente -> cliente.getLocation().getRegion().equals(region)).toList();
	}

}
