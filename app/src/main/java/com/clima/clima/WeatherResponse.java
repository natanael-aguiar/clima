package com.clima.clima;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("results")
    public Results results;

    public static class Results {
        @SerializedName("city")
        public String city;

        @SerializedName("forecast")
        public List<Forecast> forecast;

        public static class Forecast {
            @SerializedName("date")
            public String date;

            @SerializedName("description")
            public String description;

            @SerializedName("max")
            public int max;

            @SerializedName("min")
            public int min;
        }
    }
}
