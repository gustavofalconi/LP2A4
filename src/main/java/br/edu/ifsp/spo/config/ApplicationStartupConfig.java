package br.edu.ifsp.spo.config;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.ifsp.spo.service.ClienteDataService;

@Component
public class ApplicationStartupConfig {

    private final ClienteDataService dataService;

    public ApplicationStartupConfig(ClienteDataService dataService) {
        this.dataService = dataService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadDataOnStartup() {
        try {
            String csvUrl = "https://storage.googleapis.com/juntossomosmais-code-challenge/input-backend.csv";
            String jsonUrl = "https://storage.googleapis.com/juntossomosmais-code-challenge/input-backend.json";

            dataService.loadJsonData(jsonUrl);
            dataService.loadCsvData(csvUrl);
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados");
        }
    }
}

