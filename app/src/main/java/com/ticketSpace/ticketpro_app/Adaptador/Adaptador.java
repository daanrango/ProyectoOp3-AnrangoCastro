package com.ticketSpace.ticketpro_app.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ticketSpace.ticketpro_app.DetalleEventoComprado.Detalle_Evento_Comprado;
import com.ticketSpace.ticketpro_app.Modelo.Administrador;
import com.ticketSpace.ticketpro_app.R;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder>{

    private Context context;
    private List<Administrador> administradores;

    public Adaptador(Context context, List<Administrador> administradores) {
        this.context = context;
        this.administradores = administradores;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String Boletos_Comprados = administradores.get(position).getBoletos_Comprados();
        String Hora_Compra= administradores.get(position).getHora_Compra();
        String evento= administradores.get(position).getEvento();
        String user_Compra= administradores.get(position).getUser_Compra();
        String Fecha_Compra= administradores.get(position).getFecha_Compra();
        String Valor_Compra= administradores.get(position).getValor_Compra();
        String id_user= administradores.get(position).getId_user();
        String imagenEvento = administradores.get(position).getImagenEvento();
        String fechaEvento = administradores.get(position).getFechaEvento();

        holder.NombresEVENTO.setText(evento);
        holder.TiempoRestante.setText(calcularDias2(fechaEvento));

        try {
            //SI EXISTE LA IMAGEN EN LA BD ADMINISTRADOR
            Picasso.get().load(imagenEvento).placeholder(R.drawable.perfil).into(holder.imgMisEventos);
        }catch (Exception e){
            // NO EXISTE IMAGEN EN LA BD DEL ADMINISTRADOR
            Picasso.get().load(R.drawable.perfil).into(holder.imgMisEventos);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,Detalle_Evento_Comprado.class);
                intent.putExtra("Boletos_Comprados",Boletos_Comprados);
                intent.putExtra("Hora_Compra",Hora_Compra);
                intent.putExtra("evento",evento);
                intent.putExtra("user_Compra",user_Compra);
                intent.putExtra("Fecha_Compra",Fecha_Compra);
                intent.putExtra("Valor_Compra",Valor_Compra);
                intent.putExtra("id_user",id_user);
                intent.putExtra("imagenEvento",imagenEvento);
                intent.putExtra("fechaEvento",fechaEvento);
                context.startActivity(intent);
            }
        });

    }

    private String calcularDias2(String fecha) {
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
    public int getItemCount() {
        return administradores.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        CircleImageView imgMisEventos;
        TextView NombresEVENTO,TiempoRestante;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgMisEventos = itemView.findViewById(R.id.imgMisEventos);
            NombresEVENTO = itemView.findViewById(R.id.NombresEVENTO);
            TiempoRestante = itemView.findViewById(R.id.TiempoRestante);

        }
    }
}
