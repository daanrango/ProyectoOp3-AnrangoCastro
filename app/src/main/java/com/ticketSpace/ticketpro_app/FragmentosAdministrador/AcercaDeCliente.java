package com.ticketSpace.ticketpro_app.FragmentosAdministrador;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ticketSpace.ticketpro_app.InicioSesion;
import com.ticketSpace.ticketpro_app.R;
import com.ticketSpace.ticketpro_app.Registro;

public class AcercaDeCliente extends Fragment {

    TextView ir_facebook,ir_what,ir_facebookS,ir_whatS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acerca_de_cliente, container, false);

        ir_facebook = view.findViewById(R.id.ir_facebook);
        ir_what = view.findViewById(R.id.ir_what);
        ir_facebookS = view.findViewById(R.id.ir_facebookS);
        ir_whatS = view.findViewById(R.id.ir_whatS);

        ir_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/davicho.anrango/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        ir_what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send/?phone=+593998127022&text&app_absent=0");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        ir_facebookS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/santiago.castro.1671897");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        ir_whatS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send/?phone=+593984597425&text&app_absent=0");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        return view;
    }
}