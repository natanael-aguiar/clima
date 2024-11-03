package com.clima.clima;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater; // Importar LayoutInflater
import android.view.View; // Importar View
import android.view.ViewGroup; // Importar ViewGroup

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";
    private GoogleMap mMap;
    private double latitude = -26.078190;
    private double longitude = -53.052929;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView chamado");
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Carregar o fragment do mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            Log.d(TAG, "mapFragment encontrado");
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "mapFragment não foi encontrado");
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady chamado");
        mMap = googleMap;

        // Verificar se o mapa foi inicializado corretamente
        if (mMap != null) {
            Log.d(TAG, "Mapa inicializado com sucesso");
            LatLng cityLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(cityLocation).title("Localização da Cidade"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 10));
        } else {
            Log.e(TAG, "Erro ao inicializar o mapa");
        }
    }

    // Método para atualizar a localização quando o QR Code fornecer uma nova cidade
    public void updateLocation(double newLatitude, double newLongitude) {
        Log.d(TAG, "updateLocation chamado com nova latitude: " + newLatitude + " e longitude: " + newLongitude);
        latitude = newLatitude;
        longitude = newLongitude;
        if (mMap != null) {
            Log.d(TAG, "Atualizando o mapa com a nova localização");
            mMap.clear();
            LatLng newLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(newLocation).title("Nova Localização"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 10));
        } else {
            Log.e(TAG, "Mapa não está inicializado para atualizar a localização");
        }
    }
}