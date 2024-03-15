package com.example.siomaappinicio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mData = itemList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount(){return mData.size();}

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.items_view, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.binData(mData.get(position));
    }

    public void setItems(List<ListElement> items){mData= items;}

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, incremento, unidad;

        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name_view);
            incremento = itemView.findViewById(R.id.incremento_view);
            unidad = itemView.findViewById(R.id.unidad_view);
        }

        void binData(final ListElement item){
            name.setText(item.getName());
            incremento.setText(item.getIncremento());
            incremento.setTextColor(item.getColor());
            unidad.setText(item.getUnidad());
        }
    }
}
