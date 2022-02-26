package com.ticketSpace.ticketpro_app.Categorias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ticketSpace.ticketpro_app.CategoriasCliente.ConciertosCliente;
import com.ticketSpace.ticketpro_app.CategoriasCliente.DeportesCliente;
import com.ticketSpace.ticketpro_app.CategoriasCliente.FamiliaresCliente;
import com.ticketSpace.ticketpro_app.CategoriasCliente.FeriasCliente;
import com.ticketSpace.ticketpro_app.CategoriasCliente.FiestasCliente;
import com.ticketSpace.ticketpro_app.R;

public class ControladorCD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlador_cd);
        String cRecuperado = getIntent().getStringExtra("Categoria");

        if (cRecuperado.equals("Conciertos")){
            startActivity(new Intent(ControladorCD.this, ConciertosCliente.class));
            finish();
        }
        if (cRecuperado.equals("Deportes")){
            startActivity(new Intent(ControladorCD.this, DeportesCliente.class));
            finish();
        }
        if (cRecuperado.equals("Fiestas")){
            startActivity(new Intent(ControladorCD.this, FiestasCliente.class));
            finish();
        }
        if (cRecuperado.equals("Ferias")){
            startActivity(new Intent(ControladorCD.this, FeriasCliente.class));
            finish();
        }
        if (cRecuperado.equals("Familiares")){
            startActivity(new Intent(ControladorCD.this, FamiliaresCliente.class));
            finish();
        }
    }
}