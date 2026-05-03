package org.temperatureMain.api;

import org.json.JSONObject;
import org.temperatureMain.weatherEnum.TemperaturesEnum;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenMeteoAPI {

    public Object getWeatherInCity(String city){

        try{
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

            JSONObject jsonObject = new JSONObject(response.body());
            JSONObject coordinatesData = jsonObject.getJSONArray("results").getJSONObject(0);
            double latitude = coordinatesData.getDouble("latitude");
            double longitude = coordinatesData.getDouble("longitude");

            String weatherUrl = "https://api.open-meteo.com/v1/forecast?" +
                    "latitude=" + latitude +
                    "&longitude=" + longitude +
                    "&current=temperature_2m";

            HttpRequest weatherHttpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(weatherUrl))
                    .GET()
                    .build();

            HttpResponse<String> responseWeather = httpClient.send(weatherHttpRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject weatherObject = new JSONObject(responseWeather.body());
            JSONObject weatherData = weatherObject.getJSONObject("current");

            JSONObject jsonWeather = new JSONObject();
            jsonWeather.put("city", city);
            jsonWeather.put("temperature", weatherData.getDouble("temperature_2m"));
            jsonWeather.put("category", TemperaturesEnum.getTemperatureCategory(weatherData.getInt("temperature_2m")));

            return jsonWeather.toMap();

        }catch (Exception e) {
            System.out.println("An error occurred: " + e);
        }
        return " ";
    }
}
