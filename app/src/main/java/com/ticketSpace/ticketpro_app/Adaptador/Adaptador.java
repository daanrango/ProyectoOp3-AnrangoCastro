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

        holder.NombresEVENTO.setText(evento);
        holder.TiempoRestante.setText(Fecha_Compra);

        try {
            Picasso.get().load(R.drawable.perfil).into(holder.imgMisEventos);
        }catch (Exception e){
            Picasso.get().load(R.drawable.perfil).into(holder.imgMisEventos);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Aqui me Quede", Toast.LENGTH_SHORT).show();
            }
        });

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
