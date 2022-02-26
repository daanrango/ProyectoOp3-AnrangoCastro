package com.ticketSpace.ticketpro_app.CategoriasClienteFirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ticketSpace.ticketpro_app.R;

public class ListaCategoriaFirebase extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewCat_Elegida;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView filtrado;

    FirebaseRecyclerAdapter<ImgCatFirebaseElegida, ViewHolderImgCatFElegida> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<ImgCatFirebaseElegida> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria_firebase);

        String BD_CAT_FIREBASE = getIntent().getStringExtra("Categoria");

        recyclerViewCat_Elegida = findViewById(R.id.recyclerViewCat_Elegida);
        filtrado = findViewById(R.id.filtrado);
        filtrado.setText(BD_CAT_FIREBASE);
        recyclerViewCat_Elegida.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EVENTOS_FIREBAS").child(BD_CAT_FIREBASE);
        
        ListarCategoriaSeleccionada();

    }

    private void ListarCategoriaSeleccionada() {

        options = new FirebaseRecyclerOptions.Builder<ImgCatFirebaseElegida>().setQuery(databaseReference,ImgCatFirebaseElegida.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ImgCatFirebaseElegida, ViewHolderImgCatFElegida>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderImgCatFElegida viewHolderImgCatFElegida, int i, @NonNull ImgCatFirebaseElegida imgCatFirebaseElegida) {
                viewHolderImgCatFElegida.SeteoCategoriaFElegida(
                        getApplicationContext(),
                        imgCatFirebaseElegida.getImagen(),
                        imgCatFirebaseElegida.getNombre(),
                        imgCatFirebaseElegida.getPrecio(),
                        imgCatFirebaseElegida.getDireccion(),
                        imgCatFirebaseElegida.getFecha()
                );
            }

            @NonNull
            @Override
            public ViewHolderImgCatFElegida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //INFLAR EN ITEM
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_cat_f_elegida,parent,false);

                ViewHolderImgCatFElegida viewHolderImgCatFElegida = new ViewHolderImgCatFElegida(itemView);

                viewHolderImgCatFElegida.setOnClickListener(new ViewHolderImgCatFElegida.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //OBTENER LOS DATOS DE LA IMAGEN
                        //final String Id = getItem(position).getId();
                        String Imagen = getItem(position).getImagen();
                        String Nombres = getItem(position).getNombre();
                        String Precio = getItem(position).getPrecio();
                        //final int Vistas = getItem(position).getVistas();
                        //CONVERTIR A STRING LA VISTA
                        //String VistaString = String.valueOf(Vistas);

                        /*
                        valueEventListener = mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    //CREAR UN OBJETO DE LA CLASE MÃšSICA
                                    Musica musica = ds.getValue(Musica.class);

                                    if (musica.getId().equals(Id)){
                                        int i = 1;
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        //"el valor en la BD que vamos a actualizar", INCREMENTE DE 1 EN 1
                                        hashMap.put("vistas", Vistas+i);
                                        ds.getRef().updateChildren(hashMap);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //PASAMOS A LA ACTIVIDAD DETALLE CLIENTE
                        //Intent intent = new Intent(ListaCategoriaFirebase.this, DetalleImagen.class);

                        //DATOS A PASAR
                        intent.putExtra("Imagen",Imagen );
                        intent.putExtra("Nombre",Nombres);
                        intent.putExtra("Vista",VistaString);

                        startActivity(intent);*/

                    }

                    public void OnItemLongClick(View view, int position) {

                    }
                });

                return viewHolderImgCatFElegida;
            }
        };

        recyclerViewCat_Elegida.setLayoutManager(new GridLayoutManager(ListaCategoriaFirebase.this,2));
        firebaseRecyclerAdapter.startListening();
        recyclerViewCat_Elegida.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onStart() {

        if (firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}