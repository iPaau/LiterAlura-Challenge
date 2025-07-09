package com.literalura.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.literalura.client.GutendexClient;
import com.literalura.literalura.model.GutendexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GutendexService {
    private final GutendexClient gutendexClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public GutendexService(GutendexClient gutendexClient) {
        this.gutendexClient = gutendexClient;
    }

    public GutendexResponse searchBooksByTitle(String title) {
        try {
            String json = gutendexClient.searchBooksByTitle(title);
            return objectMapper.readValue(json, GutendexResponse.class);
        } catch (Exception e) {
            System.out.println("[DEBUG] Error al consultar Gutendex: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al consultar Gutendex: " + e.getMessage(), e);
        }
    }
}
