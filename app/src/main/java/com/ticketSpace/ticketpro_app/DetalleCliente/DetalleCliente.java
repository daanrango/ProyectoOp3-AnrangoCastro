package com.ticketSpace.ticketpro_app.DetalleCliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.ticketSpace.ticketpro_app.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class DetalleCliente extends AppCompatActivity {

    ImageView ImagenDetalle;
    TextView NombreImagenDetalle,fechaTXT,direcccionTXT,precioTXT,fechaTXT2;

    FloatingActionButton fabEstablecer,fabCompartir;
    String precio,Nombre, imagen,Fecha;

    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        ImagenDetalle = findViewById(R.id.ImagenDetalle);
        NombreImagenDetalle = findViewById(R.id.NombreImagenDetalle);
        fechaTXT = findViewById(R.id.fechaTXT);
        direcccionTXT = findViewById(R.id.direcccionTXT);
        precioTXT = findViewById(R.id.precioTXT);
        fabEstablecer = findViewById(R.id.fabEstablecer);
        fabCompartir = findViewById(R.id.fabCompartir);
        fechaTXT2 = findViewById(R.id.fechaTXT2);

        this. imagen = getIntent().getStringExtra("Imagen");
        this.Nombre = getIntent().getStringExtra("Nombre");
        String Direccion = getIntent().getStringExtra("Direccion");
        this. Fecha = getIntent().getStringExtra("Fecha");
        this.precio = getIntent().getStringExtra("Precio");

        try {
            //SI LA IMAGEN FUE TRAIDA
            Picasso.get().load(imagen).placeholder(R.drawable.categoria).into(ImagenDetalle);
        }catch (Exception e){
            //SI LA IMAGEN NO FUE TRAIDA
            Picasso.get().load(R.drawable.categoria).into(ImagenDetalle);
        }

        NombreImagenDetalle.setText(Nombre.toUpperCase());
        direcccionTXT.setText("En el: "+Direccion+".");
        fechaTXT.setText(infoFecha(Fecha));
        fechaTXT2.setText(calcularDias(Fecha));
        precioTXT.setText("Costo: "+precio+" USD");


        fabEstablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(DetalleCliente.this, "Aqui va tu parte SANTIAGO <3", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DetalleCliente.this,CompraEvento.class);
                intent.putExtra("PrecioEntrada",precio);
                intent.putExtra("NombreEvento",Nombre);
                intent.putExtra("ImagenEvento",imagen);
                intent.putExtra("FechaEvento",Fecha);
                startActivity(intent);
            }
        });

        fabCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    bitmap = ((BitmapDrawable) ImagenDetalle.getDrawable()).getBitmap();
                    String nombreIma = NombreImagenDetalle.getText().toString();
                    File file = new File(getExternalCacheDir(),nombreIma+".JPEG");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    file.setReadable(true,true);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/jpeg");
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,"Hola! me quieres acompañar a "+Nombre
                            +" este evento se llevara acabo en el "+Direccion
                            +" "+infoFecha(Fecha)+""
                            +" Oh si lo prefieres Descaragte TicketProAPP y compra tus entradas ya!");
                    startActivity(Intent.createChooser(intent,"Share With:"));

                }catch (Exception e){
                    Toast.makeText(DetalleCliente.this , e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    private String infoFecha(String fecha) {
        String MES[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String DIA[] = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

        String [] splitEvento = fecha.split("/");
        int de = Integer.parseInt(splitEvento[0]);
        int me = Integer.parseInt(splitEvento[1]);
        int ae = Integer.parseInt(splitEvento[2]);

        Calendar inicio = Calendar.getInstance();
        inicio.set(ae, me-1,de);
        String fechaTXt = DIA[inicio.get(Calendar.DAY_OF_WEEK) - 1] + " " + inicio.get(Calendar.DAY_OF_MONTH)+ " de "
                + MES[inicio.get(Calendar.MONTH)] + " del " + inicio.get(Calendar.YEAR)+".";

        return "Programado para el:\n"+fechaTXt;
    }

    private String calcularDias(String fecha) {
        String [] splitEvento = fecha.split("/");
        int de = Integer.parseInt(splitEvento[0]);
        int me = Integer.parseInt(splitEvento[1]);
        int ae = Integer.parseInt(splitEvento[2]);

        Calendar inicio = Calendar.getInstance();
        inicio.set(ae, me-1,de);
        inicio.set(Calendar.HOUR,0);
        inicio.set(Calendar.HOUR_OF_DAY,0);
        inicio.set(Calendar.MINUTE,0);
        inicio.set(Calendar.SECOND,0);

        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR,0);
        hoy.set(Calendar.HOUR_OF_DAY,0);
        hoy.set(Calendar.MINUTE,0);
        hoy.set(Calendar.SECOND,0);

        long finMS = hoy.getTimeInMillis();
        long incioMS = inicio.getTimeInMillis();

        int dias =(int) ((Math.abs(finMS - incioMS))/(1000*60*60*24));
        if (dias!=0){
            return "Faltan: " +String.valueOf(dias)+" dias para el evento.";
        }
        return "No falta nada es hoy el evento.";
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}