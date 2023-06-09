package br.edu.ifsp.spo.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.spo.service.ClienteDataService;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	
    
    private final ClienteDataService dataService;
    
    public ClienteController(ClienteDataService dataService) {
    	this.dataService = dataService;
    }
}

