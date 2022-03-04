package com.ticketSpace.ticketpro_app.DetalleCliente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.ticketSpace.ticketpro_app.R;

public class DetalleCliente extends AppCompatActivity {

    ImageView ImagenDetalle;
    TextView NombreImagenDetalle,fechaTXT,direcccionTXT,precioTXT;

    FloatingActionButton fabEstablecer,fabCompartir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        ImagenDetalle = findViewById(R.id.ImagenDetalle);
        NombreImagenDetalle = findViewById(R.id.NombreImagenDetalle);
        fechaTXT = findViewById(R.id.fechaTXT);
        direcccionTXT = findViewById(R.id.direcccionTXT);
        precioTXT = findViewById(R.id.precioTXT);
        fabEstablecer = findViewById(R.id.fabEstablecer);
        fabCompartir = findViewById(R.id.fabCompartir);

        String imagen = getIntent().getStringExtra("Imagen");
        String Nombre = getIntent().getStringExtra("Nombre");
        String Direccion = getIntent().getStringExtra("Direccion");
        String Fecha = getIntent().getStringExtra("Fecha");
        String Precio = getIntent().getStringExtra("Precio");

        try {
            //SI LA IMAGEN FUE TRAIDA
            Picasso.get().load(imagen).placeholder(R.drawable.categoria).into(ImagenDetalle);
        }catch (Exception e){
            //SI LA IMAGEN NO FUE TRAIDA
            Picasso.get().load(R.drawable.categoria).into(ImagenDetalle);
        }

        NombreImagenDetalle.setText(Nombre);
        direcccionTXT.setText(Direccion);
        fechaTXT.setText(Fecha);
        precioTXT.setText(Precio);

        fabEstablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetalleCliente.this, "Aqui va tu parte SANTIAGO <3", Toast.LENGTH_SHORT).show();
            }
        });

        fabCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetalleCliente.this, "Comparte en tus redes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}