package org.temperatureMain.weatherEnum;

public enum TemperaturesEnum {

    FREEZING(-50, 0),
    COLD(0, 10),
    MILD(10, 20),
    WARM(20, 30),
    HOT(30, 50);

    public final int min;
    public final int max;

    TemperaturesEnum(int min, int max) {
        this.max = max;
        this.min = min;
    }

    public boolean compare(int temperature){
        return temperature >= min && temperature < max;
    }

    public static TemperaturesEnum getTemperatureCategory(int temperature){
        for(TemperaturesEnum category : values()){
            if (category.compare(temperature))
                return category;
        }
        return null;
    }
}
