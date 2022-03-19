package com.ticketSpace.ticketpro_app.DetalleCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ticketSpace.ticketpro_app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PagoTransferencia extends AppCompatActivity {

    AppCompatTextView numCantidad,nombre,numTotal;
    Button confimarTransferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_transferencia);
        numCantidad=findViewById(R.id.numCantidad);
        numTotal=findViewById(R.id.numTotal);
        nombre=findViewById(R.id.nombreEvento);

        confimarTransferencia=findViewById(R.id.confirmarTransferencia);


        numCantidad.setText(transaccion().get(0));
        numTotal.setText(transaccion().get(5));
        nombre.setText(transaccion().get(6));

        confimarTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boletos=transaccion().get(0);
                String fecha=transaccion().get(1);
                String hora=transaccion().get(2);
                String id=transaccion().get(3);
                String userDB=transaccion().get(4);
                String valor=transaccion().get(5);

                registroVentas(boletos,fecha,hora,userDB,valor,id);
                Toast.makeText(PagoTransferencia.this, "Confirmacion realizada", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(PagoTransferencia.this,CodigoCompra.class);
                intent.putExtra("DetalleComprador",recibirDatos());
                startActivity(intent);
            }
        });


    }

    public String recibirDatos() {
        String boletos = transaccion().get(0);
        String fecha = transaccion().get(1);
        String hora=transaccion().get(2);
        String id=transaccion().get(3);
        String usuario=transaccion().get(4);
        String total=transaccion().get(5);
        String evento=transaccion().get(6);
        String datos="Evento: "+evento+"\n"
                +"Boletos Comprados: "+boletos+"\n"
                +"Fecha de Compra: "+fecha+"\n"
                +"Hora de la Compra: "+hora+"\n"
                +"Id del Comprador: "+id+"\n"
                +"Usuario del Comprador: "+usuario+"\n"
                +"Total Pagado: "+total+"\n";
        return datos;
    }

    public List<String> transaccion(){
        List<String>transaccion=new ArrayList<>();
        Bundle extras=getIntent().getExtras();
        String boletos=extras.getString("Boletos_Comprados");
        String fecha=extras.getString("Fecha_Compra");
        String hora=extras.getString("Hora_Compra");
        String id=extras.getString("idUsuario_Compra");
        String usuario=extras.getString("usuario_Compra");
        String total=extras.getString("Valor_Compra");
        String nombre=extras.getString("NombreEvento");

        transaccion.add(boletos);
        transaccion.add(fecha);
        transaccion.add(hora);
        transaccion.add(id);
        transaccion.add(usuario);
        transaccion.add(total);
        transaccion.add(nombre);

        return transaccion;
    }
    public void registroVentas(String boletos,String fecha,String hora,String user,String valorCompra,String id){
        HashMap<Object,Object> registroVentas=new HashMap<>();
        registroVentas.put("Boletos_Comprados",boletos);
        registroVentas.put("Fecha_Compra",fecha);
        registroVentas.put("Hora_Compra",hora);
        registroVentas.put("user_Compra",user);
        registroVentas.put("Valor_Compra",valorCompra);
        registroVentas.put("id_user",id);
        registroVentas.put("evento",transaccion().get(6));
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("BoletosComprados");
        reference.child(id.concat("_").concat(hora)).setValue(registroVentas);
        Toast.makeText(PagoTransferencia.this,"Register DB",Toast.LENGTH_SHORT).show();

    }
}