package com.ticketSpace.ticketpro_app.FragmentosAdministrador;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import static com.google.firebase.storage.FirebaseStorage.getInstance;

import com.ticketSpace.ticketpro_app.MainActivityAdministrador;
import com.ticketSpace.ticketpro_app.R;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class PerfilAdmin extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BASE_DE_DATOS_ADMINISTRADORES;

    StorageReference storageReference;
    String RutaDeAlmacenamiento = "Fotos_Perfil_Administradores/*";

    private static final int CODIGO_DE_SOLICITUD_DE_CAMARA = 100;
    private static final int CODIGO_DE_GALERIA_DE_SELECCION = 200;

    private String [] permisos_de_la_camara;
    private Uri imagen_uri;
    private String imagen_perfil;
    private ProgressDialog progressDialog;

    ImageView FOTOPERFILIMG;
    TextView UIDPERFIL, NOMBRESPERFIL, APELLIDOSPERFIL, CORREOPERFIL, PASSWORDPERFIL, CEDULAPERFIL;
    Button ACTUALIZARPASS,ACTUALIZARDATOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_admin, container, false);

        FOTOPERFILIMG = view.findViewById(R.id.FOTOPERFILIMG);
        UIDPERFIL = view.findViewById(R.id.UIDPERFIL);
        NOMBRESPERFIL = view.findViewById(R.id.NOMBRESPERFIL);
        APELLIDOSPERFIL = view.findViewById(R.id.APELLIDOSPERFIL);
        CORREOPERFIL = view.findViewById(R.id.CORREOPERFIL);
        PASSWORDPERFIL = view.findViewById(R.id.PASSWORDPERFIL);
        CEDULAPERFIL = view.findViewById(R.id.CEDULAPERFIL);

        ACTUALIZARPASS = view.findViewById(R.id.ACTUALIZARPASS);
        ACTUALIZARDATOS = view.findViewById(R.id.ACTUALIZARDATOS);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        storageReference = getInstance().getReference();

        permisos_de_la_camara = new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        progressDialog = new ProgressDialog(getActivity());

        BASE_DE_DATOS_ADMINISTRADORES = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");

        BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String uid = ""+snapshot.child("UID").getValue();
                    String nombres = ""+snapshot.child("NOMBRES").getValue();
                    String apellidos = ""+snapshot.child("APELLIDOS").getValue();
                    String correo = ""+snapshot.child("CORREO").getValue();
                    String pass = ""+snapshot.child("PASSWORD").getValue();
                    String cedula = ""+snapshot.child("CEDULA").getValue();
                    String imagen = ""+snapshot.child("IMAGEN").getValue();

                    UIDPERFIL.setText(uid);
                    NOMBRESPERFIL.setText(nombres);
                    APELLIDOSPERFIL.setText(apellidos);
                    CORREOPERFIL.setText(correo);
                    PASSWORDPERFIL.setText(pass);
                    CEDULAPERFIL.setText(cedula);

                    try {
                        Picasso.get().load(imagen).placeholder(R.drawable.perfil).into(FOTOPERFILIMG);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.perfil).into(FOTOPERFILIMG);
                    }
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FOTOPERFILIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CambiarImagenPerfil();
            }
        });
        return view;
    }

    private void CambiarImagenPerfil() {
        String [] opcion = {"Cambiar Foto de Perfil"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elija un opcion");
        builder.setItems(opcion, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    imagen_perfil = "IMAGEN";
                    ElegiFoto();
                }
            }
        });
        builder.create().show();;
        
    }

    private boolean comprobar_permisos_camara(){
        boolean resultado_uno = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean resultado_dos = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return resultado_dos && resultado_uno;
    }

    private void Solicitar_permisos(){
        requestPermissions(permisos_de_la_camara,CODIGO_DE_SOLICITUD_DE_CAMARA);
    }

    private void ElegiFoto() {
        String [] opciones ={"Camara","Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccionar Imagen de:");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    if (!comprobar_permisos_camara()){
                        Solicitar_permisos();
                    }else {
                        Elegir_De_Camara();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void Elegir_De_Camara() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Foto Temporal");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Descripcion Temporal");
        imagen_uri = (Objects.requireNonNull(getActivity())).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagen_uri);
        startActivityForResult(camaraIntent,CODIGO_DE_GALERIA_DE_SELECCION);

    }

    private void ActulizarImagenEnDB(Uri uri){
        String Ruta_de_archivo_y_nombre = RutaDeAlmacenamiento +""+imagen_perfil+"_"+user.getUid();
        StorageReference storageReference2 = storageReference.child(Ruta_de_archivo_y_nombre);
        storageReference2.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task <Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()){
                    Uri downloadUri = uriTask.getResult();
                    if (uriTask.isSuccessful()){
                        HashMap<String,Object> results = new HashMap<>();
                        results.put(imagen_perfil,downloadUri.toString());
                        BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(results)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        startActivity(new Intent(getActivity(), MainActivityAdministrador.class));
                                        getActivity().finish();
                                        Toast.makeText(getActivity(), "Imagen cambiada con exito", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK ){
            if(requestCode == CODIGO_DE_GALERIA_DE_SELECCION){
                ActulizarImagenEnDB(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta actulizando, espere profavor");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CODIGO_DE_SOLICITUD_DE_CAMARA:{
                if (grantResults.length>0){
                    boolean Camara_Aceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean EscribirAlmacenamiento_Aceptada = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (Camara_Aceptada && EscribirAlmacenamiento_Aceptada){
                        Elegir_De_Camara();
                    }else {
                        Toast.makeText(getActivity(), "Por favor acepte los permisos de camara y almaenamiento", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}