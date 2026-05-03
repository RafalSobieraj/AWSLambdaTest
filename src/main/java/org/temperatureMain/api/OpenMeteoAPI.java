package org.temperatureMain.api;

import org.temperatureMain.mapper.TemperatureMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoAPI {

    public Object getCoordinatesFromCity(String city) {

        final TemperatureMapper temperatureMapper = new TemperatureMapper();

        try {
            String cityUrl = "https://geocoding-api.open-meteo.com/v1/search?" +
                    "name=" + city +
                    "&count=10" +
                    "&language=en" +
                    "&format=json";

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(cityUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return temperatureMapper.mapTemperatureToJSON(response.body(), city);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e);
        }
        return " ";
    }

    public HttpResponse<String>  getWeatherFromCoordinates(double latitude, double longitude) {
        try {
            String weatherUrl = "https://api.open-meteo.com/v1/forecast?" +
                    "latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&current=temperature_2m";

            HttpRequest weatherHttpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(weatherUrl))
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();


            return httpClient.send(weatherHttpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
