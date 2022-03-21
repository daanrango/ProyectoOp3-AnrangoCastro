package com.ticketSpace.ticketpro_app.DetalleEventoComprado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ticketSpace.ticketpro_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detalle_Evento_Comprado extends AppCompatActivity {

    CircleImageView imagenDetalleEventoComprado;
    TextView detalleNombreEvento,fechaTXT,fechaTXT2,numBoeltosDetalle,horaDetalle,fechaDetalle,valorDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento_comprado);

        imagenDetalleEventoComprado = findViewById(R.id.imagenDetalleEventoComprado);
        detalleNombreEvento = findViewById(R.id.detalleNombreEvento);
        fechaTXT = findViewById(R.id.fechaTXT);
        fechaTXT2 = findViewById(R.id.fechaTXT2);
        numBoeltosDetalle = findViewById(R.id.numBoeltosDetalle);
        horaDetalle = findViewById(R.id.horaDetalle);
        fechaDetalle = findViewById(R.id.fechaDetalle);
        valorDetalle = findViewById(R.id.valorDetalle);

        String imagenEventoDetalle = getIntent().getStringExtra("imagenEvento");
        String eventoDetalle = getIntent().getStringExtra("evento");
        String fechaEvento = getIntent().getStringExtra("fechaEvento");
        String Boletos_Comprados = getIntent().getStringExtra("Boletos_Comprados");
        String Hora_Compra = getIntent().getStringExtra("Hora_Compra");
        String Fecha_Compra = getIntent().getStringExtra("Fecha_Compra");
        String Valor_Compra = getIntent().getStringExtra("Valor_Compra");

        detalleNombreEvento.setText(eventoDetalle.toUpperCase());
        fechaTXT.setText(infoFecha(fechaEvento));
        fechaTXT2.setText(calcularDias(fechaEvento));
        numBoeltosDetalle.setText(tipoBoleto(Boletos_Comprados));
        horaDetalle.setText("A las: "+Hora_Compra);
        try {
            fechaDetalle.setText(nuevaFecha(Fecha_Compra));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        valorDetalle.setText("Por un valor de: $ "+Valor_Compra+"");

        try {
            //SI LA IMAGEN FUE TRAIDA
            Picasso.get().load(imagenEventoDetalle).placeholder(R.drawable.categoria).into(imagenDetalleEventoComprado);
        }catch (Exception e){
            //SI LA IMAGEN NO FUE TRAIDA
            Picasso.get().load(R.drawable.categoria).into(imagenDetalleEventoComprado);
        }

    }

    private String tipoBoleto(String boletos_comprados) {
        int i = Integer.parseInt(boletos_comprados);
        String boletos;
        if (i==1){
            boletos="Compraste: "+i+" boleto";
        }else {
            boletos="Compraste: "+i+" boletos";
        }
        return boletos;
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
    private String infoFechaDetalle(String fecha) {
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

        return "El dia:\n"+fechaTXt;
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
    private String nuevaFecha(String fecha) throws ParseException {
        SimpleDateFormat formatoOri = new SimpleDateFormat("MMMM d, yyyy ");
        Date fechaOrigen = formatoOri.parse(fecha);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String otro = formatter.format(fechaOrigen);
        String s = infoFechaDetalle(otro);
        return s;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}