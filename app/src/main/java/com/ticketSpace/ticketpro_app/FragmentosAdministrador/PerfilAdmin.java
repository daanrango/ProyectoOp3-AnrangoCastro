package com.ticketSpace.ticketpro_app.FragmentosAdministrador;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import com.ticketSpace.ticketpro_app.InicioSesion;
import com.ticketSpace.ticketpro_app.MainActivityAdministrador;
import com.ticketSpace.ticketpro_app.R;
import com.ticketSpace.ticketpro_app.Registro;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class PerfilAdmin extends Fragment {
    //FIREBASE
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference BASE_DE_DATOS_ADMINISTRADORES;

    StorageReference storageReference;
    String RutaDeAlmacenamiento = "Fotos_Perfil_Administradores/*";

    //SOLICITUDES DE CAMARA
    private static final int CODIGO_DE_SOLICITUD_DE_CAMARA = 100;
    private static final int CODIGO_DE_CAMARA_DE_SELECCION_DE_IMAGENES = 200;

    //SOLICITUDES DE GALERIA
    private static final int CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO = 300;
    private static final int CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN = 400;

    //PERMISOS A SOLICITAR
    private String [] permisos_de_la_camara;
    private String [] permisos_de_almacenamiento;

    private Uri imagen_uri;
    private String imagen_perfil;
    private ProgressDialog progressDialog;

    //VISTAS
    ImageView FOTOPERFILIMG;
    TextView UIDPERFIL,NOMBRESPERFIL,APELLIDOSPERFIL,CORREOPERFIL,PASSWORDPERFIL,CEDULAPERFIL;
    Button ACTUALIZARPASS,ACTUALIZARDATOS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        //INICIALIZAR LOS PERMISOS
        permisos_de_la_camara = new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permisos_de_almacenamiento = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(getActivity());

        BASE_DE_DATOS_ADMINISTRADORES = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");
        //VISUALIZAR LOS DATOS EN FRAGMENTO
        BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    //OBTENER LOS DATOS

                    String uid = ""+snapshot.child("UID").getValue();
                    String nombre = ""+snapshot.child("NOMBRES").getValue();
                    String apellidos = ""+snapshot.child("APELLIDOS").getValue();
                    String correo = ""+snapshot.child("CORREO").getValue();
                    String pass = ""+snapshot.child("PASSWORD").getValue();
                    String cedula = ""+snapshot.child("CEDULA").getValue();
                    String imagen = ""+snapshot.child("IMAGEN").getValue();

                    UIDPERFIL.setText(uid);
                    NOMBRESPERFIL.setText(nombre);
                    APELLIDOSPERFIL.setText(apellidos);
                    CORREOPERFIL.setText(correo);
                    PASSWORDPERFIL.setText(pass);
                    CEDULAPERFIL.setText(cedula);

                    try {
                        //SI EXISTE LA IMAGEN EN LA BD ADMINISTRADOR
                        Picasso.get().load(imagen).placeholder(R.drawable.perfil).into(FOTOPERFILIMG);
                    }catch (Exception e){
                        // NO EXISTE IMAGEN EN LA BD DEL ADMINISTRADOR
                        Picasso.get().load(R.drawable.perfil).into(FOTOPERFILIMG);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //NOS DIRIGE A LA ACTIVIDAD CAMBIAR CONTRASEñA
        ACTUALIZARPASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Cambio_Pass.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ACTUALIZARDATOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarDatos();
            }
        });

        FOTOPERFILIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CambiarImagenPerfilAdministrador();
            }
        });


        return view;
    }



    //MODO PARA ABRIR CAUDRO DE DialoLOGO "EDITAR DATOS"
    private void EditarDatos() {
        //MOSTRAR UN DIÃLOGO QUE CONTIENE OPCIONES
        //0.- EDITAR NOMBRES
        //1.- EDITAR APELLIDOS
        //2.- EDITAR CEDULA
        String [] opciones = {"Editar nombres","Editar apellidos","Editar Cedula"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elegia opcion: ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if ( i == 0){
                    //EDITAR NOMBRES
                    EditarNombres();
                }
                else if ( i == 1){
                    //EDITAR APELLIDOS
                    EditarApellidos();
                }
                else if ( i == 2){
                    //EDITAR EDAD
                    EditarEdad();
                }
            }
        });
        builder.create().show(); //SIRVE PARA MOSTRAR EL CUADRO DE DIÃLOGO
    }

    //MTODO EDITAR NOMBRES DEL ADMINISTRADOR
    private void EditarNombres() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizar informacion: ");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getActivity());
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Ingrese el nuevo dato");
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS| InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nuevoDato = editText.getText().toString().trim();
                if (!nuevoDato.equals("")){
                    HashMap<String , Object> resultado = new HashMap<>();
                    resultado.put("NOMBRES",nuevoDato);
                    BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(resultado)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Dato actualizado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Campo vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "No se realizao ningun cambio", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();

    }

    //MTODO EDITAR APELLIDOS DEL ADMINISTRADOR
    private void EditarApellidos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizar informacion: ");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getActivity());
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Ingrese nuevo dato");
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS|InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nuevoDato = editText.getText().toString().trim();
                if (!nuevoDato.equals("")){
                    HashMap<String , Object> resultado = new HashMap<>();
                    resultado.put("APELLIDOS",nuevoDato);
                    BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(resultado)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Dato actualizado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Campo Vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "No se realizo ningun cambio", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    //MTODO EDITAR EDAD DEL ADMINISTRADOR
    private void EditarEdad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizar informacion: ");
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getActivity());
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setPadding(10,10,10,10);
        final EditText editText = new EditText(getActivity());
        editText.setHint("Ingrese nuevo dato");
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS|InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        linearLayoutCompat.addView(editText);
        builder.setView(linearLayoutCompat);
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nuevoDato = editText.getText().toString().trim();
                if (!nuevoDato.equals("")){
                    HashMap<String , Object> resultado = new HashMap<>();
                    resultado.put("CEDULA",nuevoDato);
                    BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(resultado)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Dato actualizado", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Campo Vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "No se realizo ningun cambio", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    //ALERTDIALOG PARA EDITAR IMAGEN
    private void CambiarImagenPerfilAdministrador() {
        String [] opcion = {"Cambiar foto de perfil"};
        //crear el alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //vamos asignar titulo
        builder.setTitle("Elegir una opcion");
        builder.setItems(opcion, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    imagen_perfil = "IMAGEN";
                    ElegirFoto();
                }
            }
        });

        builder.create().show();


    }

    /*COMPRUEBA SI LOS PERMISOS DE ALMACENAMIENTO ESTÃN HABILITADOS O NO*/
    private boolean Comprobar_los_permisos_de_almacenamiento(){
        boolean resultado_uno = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resultado_uno;
    }

    /*SOLICITAR PERMISOS DE ALMACENAMIENTO EN TIEMPO DE EJECUCIÃ“N*/
    private void Solicitar_los_permisos_de_almacenamiento(){
        requestPermissions(permisos_de_almacenamiento, CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO);
    }

    /*COMPRUEBA SI LOS PERMISOS DE LA CÃMARA ESTÃN HABILITADOS O NO*/
    private boolean Comprobar_los_permisos_de_la_camara(){
        //DEVUELVE TRUE SI ESTÃ HABILITADO // DEVUELVE FALSE SI NO ESTÃ HABILITADO
        boolean resultado_uno = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean resultado_dos = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return  resultado_uno && resultado_dos;
    }

    /*SOLICITAR PERMISOS DE CÃMARA EN TIEMPO DE EJECUCIÃ“N*/
    private void Solicitar_permisos_de_camara(){
        requestPermissions(permisos_de_la_camara,CODIGO_DE_SOLICITUD_DE_CAMARA);
    }

    //ELEGIR DE DONDE PROCEDE LA IMAGEN (DE GALERIA O CÃMARA)
    private void ElegirFoto() {
        String [] opciones = {"Camara","Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccionar imagen de : ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //SELECCIONAR DE CaMARA
                if ( i == 0 ){
                    //VAMOS A VERIFICAR SI EL PERMISO YA ESTÃ CONCEDIDO
                    if (!Comprobar_los_permisos_de_la_camara()){
                        //SI NO ESTÃ CONCEDIDO EL PERMISO
                        Solicitar_permisos_de_camara();
                    }else {
                        Elegir_De_Camara();
                    }
                }
                //SELECCIONAR DE GALERÃA
                else if ( i == 1){
                    if (!Comprobar_los_permisos_de_almacenamiento()){
                        Solicitar_los_permisos_de_almacenamiento();
                    }else {
                        Elegir_De_Galeria();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void Elegir_De_Galeria() {
        Intent GaleriaIntent = new Intent(Intent.ACTION_PICK);
        GaleriaIntent.setType("image/*");
        //SDK 30
        //startActivityForResult(GaleriaIntent,CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN);
        //SDK 31
        ObtenerImagenGaleria.launch(GaleriaIntent);
    }

    //MTODO PARA ABRIR LA CÃMARA
    private void Elegir_De_Camara() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Foto Temporal");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Descripcion Temporal");
        imagen_uri = (getActivity()).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //ACTIVIDAD PARA ABRIR LA CAMARA
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagen_uri);
        //SDK 30
        //startActivityForResult(camaraIntent,CODIGO_DE_CAMARA_DE_SELECCION_DE_IMAGENES);
        //SDK 31
        ObtenerImagenCamara.launch(camaraIntent);
    }

    //MÃ‰TODO PARA ACTUALIZAR IMAGEN
    private void ActualizarImagenEnBD(final Uri uri){
        String Ruta_de_archivo_y_nombre = RutaDeAlmacenamiento + "" + imagen_perfil + "_" + user.getUid();
        StorageReference storageReference2 = storageReference.child(Ruta_de_archivo_y_nombre);
        storageReference2.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task <Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        if (uriTask.isSuccessful()){
                            HashMap<String , Object> results = new HashMap<>();
                            results.put(imagen_perfil,downloadUri.toString());
                            BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(getActivity(),MainActivityAdministrador.class));
                                            getActivity().finish();
                                            Toast.makeText(getActivity(), "Imagen cambiada con Exito", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else {
                            Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Ã‰STE MTODO SE LLAMARÃ DESPUÃ‰S DE ELEGIR LA IMAGEN DE LA CÃMARA DE LA GALERÃA
    //SDK 30
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == CODIGO_DE_CAMARA_DE_SELECCION_DE_IMAGENES){
                ActualizarImagenEnBD(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta cambiando, espere por favor ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            if (requestCode == CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN){
                //OBTENER URI DE LA IMAGEN SELECCIONADA DE GALERIA
                imagen_uri = data.getData();
                ActualizarImagenEnBD(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta cambiando, espere por favor ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //SDK 31
    private ActivityResultLauncher<Intent> ObtenerImagenCamara = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //Manejar el resultado del intent
                    if (result.getResultCode() == Activity.RESULT_OK){
                        ActualizarImagenEnBD(imagen_uri);
                        progressDialog.setTitle("Procesando");
                        progressDialog.setMessage("La imagen se esta cambiando, espere por favor ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    //SDK 31
    private ActivityResultLauncher<Intent> ObtenerImagenGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        //Seleccionar la imagen
                        Intent data = result.getData();
                        //Obtener el uri de la imagen
                        Uri RutaArchivoUri = data.getData();
                        //Pasamos el uri al mtodo como parÃ¡metro
                        ActualizarImagenEnBD(RutaArchivoUri);
                        progressDialog.setTitle("Procesando");
                        progressDialog.setMessage("La imagen se esta¡ cambiando, espere por favor ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    //RESULTADOS DE LOS PERMISOS DE SOLICITUD
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //ESTE MÃODO SE LLAMA CUANDO EL USUARIO ADMINISTRADOR PRESIONA EN PERMITIR O DENEGAR EL CUADRO DE DIALOGO.
        switch (requestCode){
            case CODIGO_DE_SOLICITUD_DE_CAMARA:{
                //VERIFICA SI LOS PERMISOS DE CÃMARA Y ALMACENAMIENTO ESTÃN CONCEDIDOS O NO
                if (grantResults.length >0){
                    boolean Camara_Aceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean EscribirAlmacenamiento_Aceptada = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (Camara_Aceptada && EscribirAlmacenamiento_Aceptada){
                        Elegir_De_Camara();
                    }else {
                        Toast.makeText(getActivity(), "Por favor acepte los permisos de camara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO:{
                if (grantResults.length > 0){
                    boolean EscribirAlmacenamiento_Aceptada = grantResults [0] == PackageManager.PERMISSION_GRANTED;

                    if (EscribirAlmacenamiento_Aceptada){
                        Elegir_De_Galeria();
                    }else {
                        Toast.makeText(getActivity(), "Por favor acepte los permisos de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}