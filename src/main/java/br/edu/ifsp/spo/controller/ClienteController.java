package br.edu.ifsp.spo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.cliente.Cliente;
import br.edu.ifsp.spo.service.ClienteDataService;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	
    
    private final ClienteDataService dataService;
    
    public ClienteController(ClienteDataService dataService) {
    	this.dataService = dataService;
    }
    
    @GetMapping
    public List<Cliente> getClientes(){
    	return dataService.getUsers();
    }
}

