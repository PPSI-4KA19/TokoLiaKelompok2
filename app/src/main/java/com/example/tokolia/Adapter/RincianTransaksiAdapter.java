package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;

import java.util.ArrayList;
import java.util.List;

public class RincianTransaksiAdapter extends RecyclerView.Adapter<RincianTransaksiAdapter.RincianViewHolder>{

    private List<Produk> produks = new ArrayList<>();
    private List<TransaksiProdukCrossRef> crossRefs = new ArrayList<>();



    @NonNull
    @Override
    public RincianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_rinciantransaksi,
                parent, false);

        return new RincianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RincianViewHolder holder, int position) {
        TransaksiProdukCrossRef currentCrossRef = crossRefs.get(position);
        String produkName = "";
        int unitPrice = 0;
        for(Produk item : produks) {
            if(currentCrossRef.getId_produk() == item.getId_produk()){
                produkName = item.getNama_produk();
                unitPrice = item.getHarga_jual();
            }
        }
        holder.namaProduk.setText(produkName);
        holder.qty.setText("" + currentCrossRef.getQuantity());
        holder.hargaSatuan.setText(String.format("Rp %,d",unitPrice));
        holder.hargaTotal.setText(String.format("Rp %,d",currentCrossRef.getTotal()));
    }

    @Override
    public int getItemCount() {
        return crossRefs.size();
    }

    public void setCrossRefs(List<TransaksiProdukCrossRef> crossRefs){
        this.crossRefs = crossRefs;
        notifyDataSetChanged();
    }

    public void setProduks(List<Produk> produks){
        this.produks = produks;
        notifyDataSetChanged();
    }

    public List<TransaksiProdukCrossRef> getCrossRefs(){
        return crossRefs;
    }

    class RincianViewHolder extends RecyclerView.ViewHolder{

        TextView namaProduk, qty, hargaSatuan, hargaTotal;
        public RincianViewHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.textRincianTransaksiNamaProduk);
            qty = itemView.findViewById(R.id.textRincianTransaksiQty);
            hargaSatuan = itemView.findViewById(R.id.textRincianTransaksiHargaSatuan);
            hargaTotal = itemView.findViewById(R.id.textRincianTransaksiHargaTotal);
        }
    }

}
