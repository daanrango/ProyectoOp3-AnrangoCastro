package com.ticketSpace.ticketpro_app.DetalleCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ticketSpace.ticketpro_app.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CompraEvento extends AppCompatActivity {

    Button Adicion,Sustraccion,Compra;
    EditText NumBoletos;
    Integer variable=0;
    FirebaseAuth auth;
    AppCompatTextView numUnitario,numCantidad,numSubtotal,numTotal,nombreEvento;
    Spinner spiner;
    float numBoletos,valUnitario,subtotal,total;
    String nombre,imagenEvento,fechaEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_evento);
        //Precio&Nombre
        String precio=getIntent().getStringExtra("PrecioEntrada");
        this.nombre=getIntent().getStringExtra("NombreEvento");
        this.imagenEvento=getIntent().getStringExtra("ImagenEvento");
        this.fechaEvento=getIntent().getStringExtra("FechaEvento");

        Adicion=findViewById(R.id.Adicion);
        Sustraccion=findViewById(R.id.Sustraccion);
        NumBoletos=findViewById(R.id.NumBoletos);
        Compra=findViewById(R.id.Compra);
        auth = FirebaseAuth.getInstance();
        numUnitario=findViewById(R.id.numUnitario);
        numCantidad=findViewById(R.id.numCantidad);
        numSubtotal=findViewById(R.id.numSubtotal);
        numTotal=findViewById(R.id.numTotal);
        numUnitario.setText("$ "+precio+".0");
        spiner=findViewById(R.id.pago);
        nombreEvento=findViewById(R.id.nombreEvento);
        nombreEvento.setText(nombre);
        total=0f;

        String [] datosPago={"Transferencia Bancaria","Tarjeta de Crédito"};

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,datosPago);
        spiner.setAdapter(adapter);

        Adicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     if(variable>=10){
                         Toast.makeText(CompraEvento.this,"No puede comprar mas de 10 boletos",Toast.LENGTH_SHORT).show();
                     }else{
                         variable=variable+1;
                         NumBoletos.setText(variable.toString());
                     }
            }
        });

        Sustraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(variable<=0){
                    Toast.makeText(CompraEvento.this,"Numero de Boletos invalido",Toast.LENGTH_SHORT).show();
                }else{
                    variable=variable-1;
                    NumBoletos.setText(variable.toString());
                }
            }
        });

        NumBoletos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(NumBoletos.equals("")){
                    NumBoletos.setText("0");
                }else {
                    numBoletos = Float.parseFloat(NumBoletos.getText().toString());
                    numCantidad.setText(NumBoletos.getText().toString());
                    valUnitario = Float.parseFloat(precio);
                    subtotal = (valUnitario * numBoletos) - ((0.12f) * (valUnitario * numBoletos));
                    total = (valUnitario * numBoletos);
                    numUnitario.setText("$ "+String.valueOf(valUnitario));
                    numSubtotal.setText("$ "+String.valueOf(subtotal));
                    numTotal.setText("$ "+String.valueOf(total));
                }
            }
        });

        Compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int boletos=Integer.valueOf(NumBoletos.getText().toString());
                if(boletos>10 ||boletos<=0 ){
                    Toast.makeText(CompraEvento.this,"Numero de Boletos invalido",Toast.LENGTH_SHORT).show();
                }else{
                    //numero de boletos
                    String boletosComprados=String.valueOf(boletos);
                    //fecha de la compra
                    Date fecha = new Date();
                    CharSequence s = DateFormat.format("MMMM d, yyyy ", fecha.getTime());
                    String fechaCompra=String.valueOf(s);
                    //hora de la compra
                    SimpleDateFormat formatter   =   new   SimpleDateFormat   ("HH:mm:ss");
                    Date curDate =  new Date(System.currentTimeMillis());
                    String   horaCompra   =   formatter.format(curDate);
                    //Datos del comprador
                    FirebaseUser user=auth.getCurrentUser();
                    String idUsuarioCompra=user.getUid();
                    String usuarioCompra=user.getEmail();

                    //MetodoPago
                    String seleccion=spiner.getSelectedItem().toString();
                    if(seleccion.equals("Transferencia Bancaria")){
                        Intent intent=new Intent(CompraEvento.this,PagoTransferencia.class);
                        intent.putExtra("Boletos_Comprados",boletosComprados);
                        intent.putExtra("Fecha_Compra",fechaCompra);
                        intent.putExtra("Hora_Compra",horaCompra);
                        intent.putExtra("idUsuario_Compra",idUsuarioCompra);
                        intent.putExtra("usuario_Compra",usuarioCompra);
                        intent.putExtra("Valor_Compra",String.valueOf(total));
                        intent.putExtra("NombreEvento",nombre);
                        intent.putExtra("ImagenEvento",imagenEvento);
                        intent.putExtra("FechaEvento",fechaEvento);
                        startActivity(intent);
                    }else{
                        Toast.makeText(CompraEvento.this, "Hacia Tarjeta", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CompraEvento.this,PagoTarjeta.class);
                        intent.putExtra("Boletos_Comprados",boletosComprados);
                        intent.putExtra("Fecha_Compra",fechaCompra);
                        intent.putExtra("Hora_Compra",horaCompra);
                        intent.putExtra("idUsuario_Compra",idUsuarioCompra);
                        intent.putExtra("usuario_Compra",usuarioCompra);
                        intent.putExtra("Valor_Compra",String.valueOf(total));
                        intent.putExtra("NombreEvento",nombre);
                        intent.putExtra("ImagenEvento",imagenEvento);
                        intent.putExtra("FechaEvento",fechaEvento);
                        startActivity(intent);
                    }

                }
            }
        });



    }
    public void registroVentas(String boletosComprados,String fechaCompra,String horaCompra,String idUser,String user){

        HashMap<Object,Object> registroVentas=new HashMap<>();
        registroVentas.put("Boletos_Comprados",boletosComprados);
        registroVentas.put("Fecha_Compra",fechaCompra);
        registroVentas.put("Hora_Compra",horaCompra);
        registroVentas.put("idUser_Compra",idUser);
        registroVentas.put("user_Compra",user);

    }


}