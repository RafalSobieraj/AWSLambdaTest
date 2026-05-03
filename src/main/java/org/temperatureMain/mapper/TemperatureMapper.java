package org.temperatureMain.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.temperatureMain.api.OpenMeteoAPI;
import org.temperatureMain.weatherEnum.TemperaturesEnum;

import java.net.http.HttpResponse;
import java.util.Objects;

public class TemperatureMapper {

    ObjectMapper objectMapper = new ObjectMapper();
    OpenMeteoAPI openMeteoAPI = new OpenMeteoAPI();

    public String mapTemperatureToJSON(String response, String city){

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode coordinatesData = jsonNode.get("results").get(0);

            double latitude = coordinatesData.get("latitude").asDouble();
            double longitude = coordinatesData.get("longitude").asDouble();

            HttpResponse<String> responseWeather = openMeteoAPI.getWeatherFromCoordinates(latitude, longitude);

            JsonNode weatherRoot = objectMapper.readTree(responseWeather.body());
            JsonNode weatherData = weatherRoot.get("current");

            ObjectNode result =objectMapper.createObjectNode();
            result.put("city", city);
            result.put("temperature", weatherData.get("temperature_2m").asInt());
            result.put("category",
                    Objects.requireNonNull(Objects.requireNonNull(TemperaturesEnum
                            .getTemperatureCategory(weatherData.get("temperature_2m").asInt())).toString()));

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e);
        }
        return " ";
    }
}
