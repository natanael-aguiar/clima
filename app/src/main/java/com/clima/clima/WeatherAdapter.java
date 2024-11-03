package com.clima.clima;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final List<WeatherResponse.Results.Forecast> forecastList;

    // Construtor que recebe a lista de previsões do tempo
    public WeatherAdapter(List<WeatherResponse.Results.Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout personalizado para cada item da lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        // Vincula os dados do objeto de previsão do tempo ao ViewHolder
        WeatherResponse.Results.Forecast forecast = forecastList.get(position);
        holder.dateTextView.setText(forecast.date);
        holder.descriptionTextView.setText(forecast.description);
        holder.tempTextView.setText(String.format("Máx: %d°C, Mín: %d°C", forecast.max, forecast.min));
    }

    @Override
    public int getItemCount() {
        return forecastList.size(); // Retorna o número total de itens na lista
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView descriptionTextView;
        TextView tempTextView;

        // Inicializa os componentes do layout do item
        WeatherViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            tempTextView = itemView.findViewById(R.id.tempTextView);
        }
    }
}
