package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Produk;
import com.example.tokolia.R;

import java.util.List;

public class SelectProdukAdapter extends RecyclerView.Adapter<SelectProdukAdapter.ProdukHolder> {

    private List<Produk> produks;

    private OnItemClickListener listener;

    @NonNull
    @Override
    public ProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_selectproduk, parent, false);
        return new ProdukHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProdukHolder holder, int position) {

        Produk currentProduk = produks.get(position);
        holder.namaProduk.setText(currentProduk.getNama_produk());
        holder.hargaProduk.setText(String.format("%,d", currentProduk.getHarga_jual()));
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

    public Produk getProduks(int position){
        return produks.get(position);
    }

    class ProdukHolder extends RecyclerView.ViewHolder{

        TextView namaProduk;
        TextView hargaProduk;
        TextView stok;

        public ProdukHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.textViewNamaSelectProduk);
            hargaProduk = itemView.findViewById(R.id.textViewHargaSelectProduk);
            stok = itemView.findViewById(R.id.textViewStokSelectProduk);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(produks.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Produk produk);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
