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

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukHolder>{

    private List<Produk> produks = new ArrayList<>();

    private List<Produk> produksCopy;

    private OnItemClickListener listener;



    @NonNull
    @Override
    public ProdukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_produk,parent,false);

        return new ProdukHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukHolder holder, int position) {
        //change produks
        Produk currentProduk = produks.get(position);
        holder.namaProduk.setText(currentProduk.getNama_produk());
        holder.hargaProduk.setText(String.format("%,d",currentProduk.getHarga_jual()));
        holder.stok.setText("" + currentProduk.getStok());

    }

    @Override
    public int getItemCount() {
        //change produks
        return produks.size();
    }

    public void setProduks(List<Produk> produks){

        this.produks = produks;
        produksCopy = new ArrayList<>(produks);
        notifyDataSetChanged();

    }

    public Produk getProduk(int position){
        return produks.get(position);
    }



    //------------------------------VIEW HOLDER---------------------------------------------------->
    class ProdukHolder extends RecyclerView.ViewHolder{

        TextView namaProduk;
        TextView hargaProduk;
        TextView stok;
        Button buttonEdit;


        public ProdukHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.textNamaProduk);
            hargaProduk = itemView.findViewById(R.id.textNominalHarga);
            stok = itemView.findViewById(R.id.textStok);
            buttonEdit = itemView.findViewById(R.id.buttonRestokProduk);
            buttonEdit.setOnClickListener(new View.OnClickListener() {
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
    //--------------------------------AKHIR VIEW HOLDER-------------------------------------------->





    /* TODO FILTER RECYCLER VIEW
    //-------------------------------FILTER METHODS------------------------------------------------>
    @Override
    public Filter getFilter() {
        return filtering;
    }

    private Filter filtering = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Produk> filteredProduk = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredProduk.addAll(produksCopy);
            } else {
                String filterKeyWords = constraint.toString().toLowerCase().trim();
                for (Produk item : produksCopy) {
                    if(item.nama_produk.toLowerCase().contains(filterKeyWords)){
                        filteredProduk.add(item);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filteredProduk;
            results.count = filteredProduk.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            produks.clear();
            produks.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    //----------------------------END FILTER METHODS----------------------------------------------->
    */

    public interface OnItemClickListener{
        void onItemClick(Produk produk);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}

