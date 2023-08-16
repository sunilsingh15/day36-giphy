package sg.edu.nus.iss.day36giphy.service;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class AppService {

    @Value("${GIPHY_API_KEY}")
    private String apiKey;

    private final String apiURL = "https://api.giphy.com/v1/gifs/search";
    RestTemplate template = new RestTemplate();

    public List<String> getGifsFromAPI(String query) {

        String callURL = UriComponentsBuilder
                .fromUriString(apiURL)
                .queryParam("api_key", apiKey)
                .queryParam("q", query)
                .queryParam("limit", 5)
                .toUriString();

        RequestEntity<Void> request = RequestEntity.get(callURL).build();

        ResponseEntity<String> response = template.exchange(request, String.class);

        JsonReader reader = Json.createReader(new StringReader(response.getBody()));
        JsonObject result = reader.readObject();
        JsonArray data = result.getJsonArray("data");

        return data.stream()
                .map(v -> v.asJsonObject())
                .map(v -> v.getJsonObject("images").getJsonObject("fixed_height").getString("url"))
                .toList();

    }

}
