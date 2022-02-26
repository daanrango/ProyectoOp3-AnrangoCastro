package com.ticketSpace.ticketpro_app.CategoriasClienteFirebase;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ticketSpace.ticketpro_app.Categorias.Cat_Firebase.ViewHolderCF;
import com.ticketSpace.ticketpro_app.R;

public class ViewHolderImgCatFElegida  extends RecyclerView.ViewHolder {
    View mView;

    private ViewHolderImgCatFElegida.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderImgCatFElegida.ClickListener clickListener){
        mClickListener=clickListener;

    }

    public ViewHolderImgCatFElegida(@NonNull View itemView) {
        super(itemView);
        mView=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });
    }

    public void SeteoCategoriaFElegida (Context context, String imagen, String nombre, String precio, String direccion, String fecha){
        ImageView ImgCatFElegida;
        TextView NombreEvento,PrecioEvento;

        ImgCatFElegida = mView.findViewById(R.id.ImgCatFElegida);
        NombreEvento = mView.findViewById(R.id.NombreEvento);
        PrecioEvento = mView.findViewById(R.id.PrecioEvento);

        NombreEvento.setText(nombre);
        PrecioEvento.setText(precio);
        try {
            Picasso.get().load(imagen).placeholder(R.drawable.categoria).into(ImgCatFElegida);
        }catch (Exception e){
            Picasso.get().load(R.drawable.categoria).into(ImgCatFElegida);
        }

    }
}
