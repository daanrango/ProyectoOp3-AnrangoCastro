package com.ticketSpace.ticketpro_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ticketSpace.ticketpro_app.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //hasta aqui funciona perfecto
    private static final int LOCATION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    TextView NombreImagenDetalle;
    String s;
    String s1;
    String direccion,sniper;

    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        NombreImagenDetalle = findViewById(R.id.NombreImagenDetalle);

        this.s = intent.getStringExtra("Longuitud");
        this.s1 = intent.getStringExtra("Latitud");
        this.direccion = intent.getStringExtra("Etiqueta");
        NombreImagenDetalle.setText(direccion);
        this.sniper = intent.getStringExtra("sniper");
        System.out.println("Aquimijo" + s);
        System.out.println("Aquimijo" + s1);
        System.out.println("Aquimijo" + direccion);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double l = Double.parseDouble(this.s);
        double l2 = Double.parseDouble(this.s1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(l, l2);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .title(this.direccion)
                .snippet("Evento: "+this.sniper)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinprofinal)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(58.0f);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }
}