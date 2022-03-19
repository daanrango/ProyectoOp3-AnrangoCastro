package com.ticketSpace.ticketpro_app.FragmentosAdministrador;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ticketSpace.ticketpro_app.Adaptador.Adaptador;
import com.ticketSpace.ticketpro_app.Modelo.Administrador;
import com.ticketSpace.ticketpro_app.R;

import java.util.ArrayList;
import java.util.List;

public class ListaAdmin extends Fragment {

    RecyclerView administradore_recyclerView;
    Adaptador adaptador;
    List<Administrador> administradoresList;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_admin, container, false);

        administradore_recyclerView = view.findViewById(R.id.administradore_recyclerView);
        administradore_recyclerView.setHasFixedSize(true);
        administradore_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        administradoresList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        ObtenerLista();
        return view;
    }

    private void ObtenerLista() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BoletosComprados");
        reference.orderByChild("evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                administradoresList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    Administrador administrador = ds.getValue(Administrador.class);

                    assert administrador != null;
                    assert user != null;
                    if (administrador.getId_user().equals(user.getUid())){
                        administradoresList.add(administrador);
                    }
                    adaptador = new Adaptador(getActivity(),administradoresList);
                    administradore_recyclerView.setAdapter(adaptador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}