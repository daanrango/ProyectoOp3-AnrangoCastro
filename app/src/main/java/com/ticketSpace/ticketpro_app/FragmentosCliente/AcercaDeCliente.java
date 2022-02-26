package com.ticketSpace.ticketpro_app.FragmentosCliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ticketSpace.ticketpro_app.FragmentosAdministrador.RegistrarAdmin;
import com.ticketSpace.ticketpro_app.InicioSesion;
import com.ticketSpace.ticketpro_app.R;
import com.ticketSpace.ticketpro_app.Registro;

public class AcercaDeCliente extends Fragment {

    Button Acceder, Registrate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acerca_de_cliente, container, false);
        Acceder = view.findViewById(R.id.Acceder);
        Registrate = view.findViewById(R.id.Registrate);

        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Forma1
                //startActivity(new Intent(getActivity(), InicioSesion.class));
                //Forma2
                Intent intent = new Intent(getActivity(),InicioSesion.class);
                startActivity(intent);
            }
        });

        Registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Registro.class);
                startActivity(intent);
            }
        });
        return view;
    }
}