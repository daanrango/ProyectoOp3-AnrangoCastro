package com.ticketSpace.ticketpro_app.FragmentosCliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.ticketSpace.ticketpro_app.Categorias.Cat_Firebase.CategoriasF;
import com.ticketSpace.ticketpro_app.Categorias.Cat_Firebase.ViewHolderCF;
import com.ticketSpace.ticketpro_app.Categorias.ControladorCD;
import com.ticketSpace.ticketpro_app.CategoriasClienteFirebase.ListaCategoriaFirebase;
import com.ticketSpace.ticketpro_app.R;

public class InicioCliente extends Fragment implements Connectable, Disconnectable, Bindable {

    RecyclerView recyclerViewCategoriasF;
    FirebaseDatabase firebaseDatabaseF;
    DatabaseReference referenceF;
    LinearLayoutManager linearLayoutManager;
    SearchView searchView;

    FirebaseRecyclerAdapter<CategoriasF, ViewHolderCF> firebaseRecyclerAdapterF;
    FirebaseRecyclerOptions<CategoriasF> optionsD;
    FirebaseRecyclerOptions<CategoriasF> optionsD2;

    TextView clienteS2;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference BASE_DE_DATOS_ADMINISTRADORES;

    Merlin merlin;

    LinearLayoutCompat conConexion,sinConexion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_cliente, container, false);

        conConexion = view.findViewById(R.id.conConexion);
        sinConexion = view.findViewById(R.id.sinConexion);

        firebaseDatabaseF = FirebaseDatabase.getInstance();
        referenceF = firebaseDatabaseF.getReference("CATEGORIAS_F");
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewCategoriasF = view.findViewById(R.id.recyclerViewCategoriasF);
        //searchView = view.findViewById(R.id.txtbuscar);
        recyclerViewCategoriasF.setHasFixedSize(true);
        recyclerViewCategoriasF.setLayoutManager(linearLayoutManager);

        clienteS2= view.findViewById(R.id.clienteS2);

        firebaseAuth= FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        BASE_DE_DATOS_ADMINISTRADORES = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");

        VerCategoriasD();
        inicializarMerlin();

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return false;
            }
        });*/

        return view;
    }

    private void inicializarMerlin(){
        merlin = new Merlin.Builder().withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(getActivity());

        merlin.registerBindable(this);
        merlin.registerConnectable(this);
        merlin.registerDisconnectable(this);
    }

    private void buscar(String s) {
        optionsD = new FirebaseRecyclerOptions.Builder<CategoriasF>().setQuery(referenceF
                .orderByChild("categoria").startAt(s).endAt(s+"uf8ff"),CategoriasF.class).build();
        firebaseRecyclerAdapterF = new FirebaseRecyclerAdapter<CategoriasF, ViewHolderCF>(optionsD) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderCF viewHolderCF, int position, @NonNull CategoriasF categoriasF) {
                viewHolderCF.SeteoCategoriaF(getActivity(),categoriasF.getCategoria(),categoriasF.getImagen());
            }

            @NonNull
            @Override
            public ViewHolderCF onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorias_firebase,parent,false);
                ViewHolderCF viewHolderCF = new ViewHolderCF(itemView);

                viewHolderCF.setOnClickListener(new ViewHolderCF.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolderCF;
            }
        };
        recyclerViewCategoriasF.setAdapter(firebaseRecyclerAdapterF);
    }

    private void VerCategoriasD(){
        optionsD = new FirebaseRecyclerOptions.Builder<CategoriasF>().setQuery(referenceF,CategoriasF.class).build();
        firebaseRecyclerAdapterF = new FirebaseRecyclerAdapter<CategoriasF, ViewHolderCF>(optionsD) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderCF viewHolderCF, int position, @NonNull CategoriasF categoriasF) {
                viewHolderCF.SeteoCategoriaF(getActivity(),categoriasF.getCategoria(),categoriasF.getImagen());
            }

            @NonNull
            @Override
            public ViewHolderCF onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorias_firebase,parent,false);
                ViewHolderCF viewHolderCF = new ViewHolderCF(itemView);
                
                viewHolderCF.setOnClickListener(new ViewHolderCF.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String c = getItem(position).getCategoria();
                        //Intent intent = new Intent(view.getContext(), ControladorCD.class);
                        //intent.putExtra("Categoria",c);
                        //startActivity(intent);
                        Intent intent = new Intent(view.getContext(), ListaCategoriaFirebase.class);
                        intent.putExtra("Categoria",c);
                        Toast.makeText(view.getContext(), "Categoria Selecciona: "+c, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
                return viewHolderCF;
            }
        };
        recyclerViewCategoriasF.setAdapter(firebaseRecyclerAdapterF);
    }

    @Override
    public void onStart() {
        super.onStart();
        ComprobarUsuarioActivo();
        if (firebaseRecyclerAdapterF!=null){
            firebaseRecyclerAdapterF.startListening();
        }
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
                    clienteS2.setText("HOLA "+strings[0].toUpperCase()+" !");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (merlin!=null){
            merlin.bind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (merlin!=null){
            merlin.unbind();
        }
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {

        if (!networkStatus.isAvailable()){
            onDisconnect();
        }else{
            onConnect();
        }
    }

    @Override
    public void onConnect() {
        //Toast.makeText(getActivity(), "Si hay conexion", Toast.LENGTH_SHORT).show();
        conConexion.setVisibility(View.VISIBLE);
        sinConexion.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDisconnect() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), "No hay conexion", Toast.LENGTH_SHORT).show();
                sinConexion.setVisibility(View.VISIBLE);
                conConexion.setVisibility(View.INVISIBLE);
            }
        });
    }
}
