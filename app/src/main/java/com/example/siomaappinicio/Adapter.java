package com.example.siomaappinicio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Items> items;
    LayoutInflater inflater;

    public Adapter(List<Items> items, Context context){
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.items_view, null);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter.ViewHolder holder, final int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Items> newItems){items = newItems;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameView, incrementoView, unidadView;

        ViewHolder(View itemView){
            super(itemView);
            nameView = itemView.findViewById(R.id.name_view);
            incrementoView = itemView.findViewById(R.id.incremento_view);
            unidadView = itemView.findViewById(R.id.unidad_view);
        }

        void bindData(final Items item){
            nameView.setText(item.getNombre());
            incrementoView.setText(item.getIncremento());
            unidadView.setText(item.getUnidad());
        }

    }
}
