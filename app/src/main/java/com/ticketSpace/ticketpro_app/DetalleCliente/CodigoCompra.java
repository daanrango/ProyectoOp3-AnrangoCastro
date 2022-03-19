package com.ticketSpace.ticketpro_app.DetalleCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.ticketSpace.ticketpro_app.MainActivityAdministrador;
import com.ticketSpace.ticketpro_app.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class CodigoCompra extends AppCompatActivity {
    Button MenuPrincipal,GuardarImagen;
    ImageView codigo;
    String rutaImagen;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_compra);

        MenuPrincipal =findViewById(R.id.MenuPrincipal);
        MenuPrincipal.setEnabled(false);
        GuardarImagen=findViewById(R.id.GuardarImagen);
        codigo=findViewById(R.id.Codigo);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        ActivityCompat.requestPermissions(CodigoCompra.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(CodigoCompra.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        codigoCompra();

        GuardarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarImagen();
                Toast.makeText(CodigoCompra.this, "Codigo Guardado Correctamente", Toast.LENGTH_SHORT).show();
                GuardarImagen.setEnabled(false);
                MenuPrincipal.setEnabled(true);
            }
        });

        MenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CodigoCompra.this, MainActivityAdministrador.class);
                startActivity(intent);
                finish();

            }
        });
    }
    private void guardarImagen(){
        Drawable drawable=codigo.getDrawable();
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
        //save image to gallery
        String savedImage= MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Codigo",
                "Image of bird"
        );
        Uri savedImageURI= Uri.parse(savedImage);

    }
    public void codigoCompra(){
        try{
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            //contenido dentro del QR
            Bitmap bitmap=barcodeEncoder.encodeBitmap(recibirDatos(), BarcodeFormat.QR_CODE,750,750);
            codigo.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String recibirDatos(){
        Bundle extras=getIntent().getExtras();
        /*
        String boletos=extras.getString("Boletos_Comprados");
        String fecha=extras.getString("Fecha_Compra");
        String hora=extras.getString("Hora_Compra");
        String id=extras.getString("idUsuario_Compra");
        String usuario=extras.getString("usuario_Compra");
        String datos="Boletos Comprados: "+boletos+"\n"
                +"Fecha de Compra: "+fecha+"\n"
                +"Hora de la Compra: "+hora+"\n"
                +"Id del Comprador: "+id+"\n"
                +"Usuario del Comprador: "+usuario+"\n";

         */
        String datos=extras.getString("DetalleComprador");
        return datos;
    }

}