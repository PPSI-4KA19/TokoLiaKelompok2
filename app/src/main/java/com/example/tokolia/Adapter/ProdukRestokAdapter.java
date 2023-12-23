package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Produk;
import com.example.tokolia.R;

import java.util.ArrayList;
import java.util.List;

public class ProdukRestokAdapter extends RecyclerView.Adapter<ProdukRestokAdapter.ProdukHolder> {


    private OnItemClickListener listener;
    private List<Produk> produks = new ArrayList<>();
    //TODO coba buat fitur search disini - lebih simple

    @NonNull
    @Override
    public ProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_restokproduk,parent,false);

        return new ProdukHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukHolder holder, int position) {

        Produk currentProduk = produks.get(position);
        holder.namaProduk.setText(currentProduk.getNama_produk());
        holder.hargaModal.setText(String.format("%,d",currentProduk.getHarga_modal()));
        holder.stok.setText(""+currentProduk.getStok());

    }

    @Override
    public int getItemCount() {
        return produks.size();
    }

    public void setProduks(List<Produk> produks){

        this.produks = produks;
        notifyDataSetChanged();

    }

    public Produk getProduk(int position){
        return produks.get(position);
    }


    class ProdukHolder extends RecyclerView.ViewHolder{
        TextView namaProduk;
        TextView hargaModal;
        TextView stok;
        Button buttonRestok;


        public ProdukHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.textRestokNamaProduk);
            hargaModal = itemView.findViewById(R.id.textRestokNominalHarga);
            stok = itemView.findViewById(R.id.textRestokStok);
            buttonRestok = itemView.findViewById(R.id.buttonRestokProduk);

            buttonRestok.setOnClickListener(new View.OnClickListener() {
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
