package com.scoala_informala.final_project.final_project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoala_informala.final_project.final_project.model.CovidInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class CovidInfoService {

    private static final String COVID_API_URL = "https://coronavirus.m.pipedream.net/";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CovidInfo getCovidInfoForRomania() {
        ResponseEntity<String> response = restTemplate.getForEntity(COVID_API_URL, String.class);
        String apiResponse = response.getBody();

        try {
            JsonNode root = objectMapper.readTree(apiResponse);
            log.debug("Root node: {}", root);
            JsonNode dataArray = root.get("rawData");
            log.debug("Data array: {}", dataArray);

            if (dataArray != null && dataArray.isArray()) {
                for (JsonNode countryData : dataArray) {
                    String countryName = countryData.get("Country_Region").asText();
                    if ("Romania".equalsIgnoreCase(countryName)) {

                        String totalCases = countryData.get("Confirmed").asText();
                        String lastUpdate = countryData.get("Last_Update").asText();

                        CovidInfo covidInfo = new CovidInfo();
                        covidInfo.setTotalCases(totalCases);
                        covidInfo.setLastUpdate(lastUpdate);

                        return covidInfo;
                    }
                }
            }

            return null;
        } catch (IOException e) {
            log.info("Could not retrieve information from the external api.", e);
            return null;
        }
    }
}
