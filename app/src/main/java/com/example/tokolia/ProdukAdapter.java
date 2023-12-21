package com.example.tokolia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukHolder> {

    private List<Produk> produks = new ArrayList<>();


    @NonNull
    @Override
    public ProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_produk,parent,false);

        return new ProdukHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukHolder holder, int position) {

        Produk currentProduk = produks.get(position);
        holder.namaProduk.setText(currentProduk.getNama_produk());
        holder.hargaProduk.setText("" + currentProduk.getHarga_jual());
        holder.stok.setText("" + currentProduk.getStok());

    }

    @Override
    public int getItemCount() {
        return produks.size();
    }

    public void setProduks(List<Produk> produks){

        this.produks = produks;
        notifyDataSetChanged();

    }

    class ProdukHolder extends RecyclerView.ViewHolder{

        TextView namaProduk;
        TextView hargaProduk;
        TextView stok;


        public ProdukHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.textNamaProduk);
            hargaProduk = itemView.findViewById(R.id.textNominalHarga);
            stok = itemView.findViewById(R.id.textStok);

        }
    }

}

