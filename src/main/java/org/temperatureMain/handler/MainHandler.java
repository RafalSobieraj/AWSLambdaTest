package org.temperatureMain.handler;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.temperatureMain.api.OpenMeteoAPI;

import java.util.Map;

public class MainHandler implements RequestHandler<Map<String, Object>, Object> {

    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {

        Map<String, Object> event = (Map<String, Object>) input.get("queryStringParameters");

        String city = event.get("city").toString();
        OpenMeteoAPI openMeteoAPI = new OpenMeteoAPI();

        return openMeteoAPI.getWeatherInCity(city);
    }
}
