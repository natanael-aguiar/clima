package com.clima.clima;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView cityNameTextView;
    private WeatherAdapter adapter;
    private static final String BASE_URL = "https://api.hgbrasil.com/";
    private static final int DEFAULT_WOEID = 457197; // Código fixo da cidade
    private WeatherService weatherService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        cityNameTextView = view.findViewById(R.id.cityNameTextView);
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);

        // Chamar a API com o `woeid` fixo
        fetchWeatherData(DEFAULT_WOEID);

        return view;
    }

    public  void fetchWeatherData(int woeid) {
        Call<WeatherResponse> call = weatherService.getWeather(woeid);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse.Results results = response.body().results;
                    cityNameTextView.setText("Cidade: " + results.city);
                    List<WeatherResponse.Results.Forecast> forecastList = results.forecast;
                    adapter = new WeatherAdapter(forecastList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("WeatherFragment", "Erro na resposta da API");
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Log.e("WeatherFragment", "Erro ao carregar dados", t);
            }
        });
    }
}
