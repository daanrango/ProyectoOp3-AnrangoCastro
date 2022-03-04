package com.ticketSpace.ticketpro_app.FragmentosAdministrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ticketSpace.ticketpro_app.InicioSesion;
import com.ticketSpace.ticketpro_app.MainActivityAdministrador;
import com.ticketSpace.ticketpro_app.R;

public class InicioAdmin extends Fragment {

    TextView clienteS;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference BASE_DE_DATOS_ADMINISTRADORES;

    //Hasta aqui funviona



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio_admin, container, false);
        clienteS= view.findViewById(R.id.clienteS);

        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        BASE_DE_DATOS_ADMINISTRADORES = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ComprobarUsuarioActivo();

    }

    private void ComprobarUsuarioActivo(){
        if (user!=null){
            CargaDeDatos();
        }
    }

    private void CargaDeDatos(){
        BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //si usuario admin existe
                if (snapshot.exists()){
                    //OBTENER EL DATO NOMBRE
                    String nombre = ""+snapshot.child("NOMBRES").getValue();
                    String [] strings = nombre.split(" ");
                    clienteS.setText("HOLA "+strings[0]+"!\n¿Que vamos a hacer hoy?");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}