package com.ticketSpace.ticketpro_app.FragmentosAdministrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.ticketSpace.ticketpro_app.R;

public class CompartirCliente extends Fragment {

    Button BotonCompartir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compartir_cliente, container, false);


        BotonCompartir = view.findViewById(R.id.BotonCompartir);

        BotonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartirApp();
            }
        });
        return view;


    }

    private void compartirApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
            String mensaje = "Hola :D, Te invito a que te descarges esta app. te encantara\n";
            mensaje = mensaje + "https://play.google.com/store/apps/details?id=com.rtb.partyfinder&hl=es_EC&gl=US";

            intent.putExtra(Intent.EXTRA_TEXT,mensaje);
            startActivity(intent);
        }catch (Exception e){

        }
    }
}