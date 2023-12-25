package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Produk;
import com.example.tokolia.R;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public class TransaksiProdukAdapter extends RecyclerView.Adapter<TransaksiProdukAdapter.TransaksiProdukHolder> {

    //TODO gimana caranya ngelimit plus sebatas stok

    private List<Produk> produks = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public TransaksiProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_produktransaksi,
                parent, false);
        return new TransaksiProdukHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiProdukHolder holder, int position) {
        Produk currentProduk = produks.get(position);
        holder.namaProduk.setText(currentProduk.getNama_produk());
        holder.hargaProduk.setText(String.format("%,d",currentProduk.getHarga_jual()));
        holder.qty.setText("" + 1);
    }

    @Override
    public int getItemCount() {
        return produks.size();
    }

    class TransaksiProdukHolder extends RecyclerView.ViewHolder{

        TextView namaProduk;
        TextView hargaProduk;
        EditText qty;
        Button buttonPlus;
        Button buttonMinus;

        int count = 0;

        int limit = produks.get(getAdapterPosition()).getStok();


        public TransaksiProdukHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.textViewTransaksiNamaProduk);
            hargaProduk = itemView.findViewById(R.id.textViewTransaksiHargaProduk);
            qty = itemView.findViewById(R.id.editJumlahProduk);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);

            /*
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count<=limit){
                        count++;
                    }
                    qty.setText("" + count);
                }
            });

            buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count>0){
                        count--;
                    }
                    qty.setText("" + count);
                }
            });*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(produks.get(position));
                    }
                }
            });


        }
    }

    public void addProduk(Produk produk){
        produks.add(produk);
        notifyDataSetChanged();
    }

    public void setProduks(List<Produk> produks) {
        this.produks = produks;
        notifyDataSetChanged();
    }

    public Produk getProduk(int position){
        return produks.get(position);
    }

    public interface OnItemClickListener{
        void OnItemClick(Produk produk);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}
