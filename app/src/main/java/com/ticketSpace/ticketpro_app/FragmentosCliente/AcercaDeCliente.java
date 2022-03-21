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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acerca_de_cliente, container, false);

        return view;
    }
}