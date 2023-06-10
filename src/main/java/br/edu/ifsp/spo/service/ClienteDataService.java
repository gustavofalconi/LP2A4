package br.edu.ifsp.spo.service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;

import br.edu.ifsp.spo.cliente.Cliente;
import br.edu.ifsp.spo.cliente.ClienteRepository;
import br.edu.ifsp.spo.cliente.DadosCadastroCliente;
import br.edu.ifsp.spo.cliente.DadosCadastroClienteCsv;
import br.edu.ifsp.spo.cliente.DadosCadastroClienteWrapper;
import br.edu.ifsp.spo.processor.ClienteProcessor;

@Service
@Transactional
public class ClienteDataService {

	@Autowired
	private ClienteRepository repository;

	private ClienteProcessor processor = new ClienteProcessor(this.repository);

	public void loadCsvData(String csvUrl) throws IOException {
		try {

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(csvUrl)).build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			String csvContent = response.body();
			String jsonFilePath = "clientesCsv.json";
			
			
			List<Map<String, Object>> data = parseCsv(csvContent);
			convertToJson(data, jsonFilePath);
			System.out.println("Conversão concluida");
			
			try(FileReader fileReader = new FileReader("clientesCsv.json")){
				
				StringBuilder csvContentBuilder = new StringBuilder();
				int character;
				
				// Ler caractere por caractere do arquivo
		        while ((character = fileReader.read()) != -1) {
		            csvContentBuilder.append((char) character);
		        }
		        
		        // Realizar a substituição das aspas extras no campo "gender"
		        String jsonContent = csvContentBuilder.toString();
		        jsonContent = jsonContent.replaceAll("﻿\"gender\"", "gender");				
				
				//Gson gson = new Gson();
				
		        
				ObjectMapper objectMapper = new ObjectMapper();
				
				DadosCadastroClienteCsv[] dados = objectMapper.readValue(jsonContent, DadosCadastroClienteCsv[].class);
				//List<DadosCadastroClienteCsv> users = wrapper.getData();
				List<Cliente> clientes = new ArrayList<>();
				
				for (DadosCadastroClienteCsv dado : dados) {
					Cliente cliente = new Cliente(dado);
					clientes.add(cliente);
				}
				repository.saveAll(processor.processUsers(clientes));
				System.out.println("Salvei o csv no banco de dados");
			}			
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void loadJsonData(String jsonUrl) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(jsonUrl)).build();
			HttpResponse<String> response;
			response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Gson gson = new Gson();
			DadosCadastroClienteWrapper wrapper = gson.fromJson(response.body(), DadosCadastroClienteWrapper.class);
			List<DadosCadastroCliente> jsonUsers = wrapper.getResults();
			List<Cliente> clientes = new ArrayList<>();
			for (DadosCadastroCliente dado : jsonUsers) {
				Cliente cliente = new Cliente(dado);
				clientes.add(cliente);
			}
			repository.saveAll(processor.processUsers(clientes));
			System.out.println("Salvei o json no banco de dados");
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}

	}

	private static List<Map<String, Object>> parseCsv(String csvContent) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        List<Map<String, Object>> data = new ArrayList<>();
        MappingIterator<Map<String, Object>> rows = csvMapper.readerWithTypedSchemaFor(Map.class).with(schema).readValues(csvContent);
        while (rows.hasNext()) {
        	Map<String, Object> rowData = rows.next();
            data.add(rowData);
        }
        return data;
    }

    private static void convertToJson(List<Map<String, Object>> data, String jsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(jsonFilePath);
        objectMapper.writeValue(file, data);
    }
    
    public List<Cliente> getUsers() {
    	return this.repository.findAll();
    }
}