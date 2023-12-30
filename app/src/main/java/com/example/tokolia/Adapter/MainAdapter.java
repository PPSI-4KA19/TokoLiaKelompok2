package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Pencatatan;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainActivityViewHolder> {

    private List<Produk> produkList = new ArrayList<>();
    private List<TransaksiProdukCrossRef> crossRefList = new ArrayList<>();
    private List<Transaksi> transaksiList = new ArrayList<>();
    private List<Pencatatan> index = new ArrayList<>();

    String tahun;
    String bulan;


    @NonNull
    @Override
    public MainActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_dashboard, parent, false);

        return new MainActivityViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityViewHolder holder, int position) {
        Pencatatan currentIndex = index.get(position);
        holder.index.setText(currentIndex.getJenis());

        int total = 0;
        if(currentIndex.getJenis().equals("Pemasukan")){
            for (TransaksiProdukCrossRef refs : crossRefList){
                for (Transaksi item : transaksiList){
                    if (item.getJenis_transaksi().equals("penjualan")){
                        /*if (refs.getId_transaksi().equals(item.getId_transaksi())
                                && refs.getId_transaksi().substring(0,4).equals(tahun)
                                && refs.getId_transaksi().substring(4,6).equals(bulan)){

                            total += refs.getTotal();

                        }*/
                        if(refs.getId_transaksi().equals(item.getId_transaksi())){
                            total += refs.getTotal();
                        }
                    }
                }
            }
        } else if (currentIndex.getJenis().equals("Hutang")){
            for (TransaksiProdukCrossRef refs : crossRefList){
                for(Transaksi item : transaksiList){
                    if(item.getJenis_transaksi().equals("hutang")){
                        if(refs.getId_transaksi().equals(item.getId_transaksi())){
                            total += refs.getTotal();
                        }
                    }
                }
            }
        } else if (currentIndex.getJenis().equals("Pengeluaran")){
            for (TransaksiProdukCrossRef refs : crossRefList){
                for(Produk item : produkList){
                    if(refs.getId_produk() == item.getId_produk()){
                        total += refs.getQuantity()*item.getHarga_modal();
                    }
                }
            }
        }
        holder.nominal.setText(String.format("Rp %,d",total));
    }

    @Override
    public int getItemCount() {
        return index.size();
    }

    public void setProdukList(List<Produk> produkList){
        this.produkList = produkList;
        notifyDataSetChanged();
    }

    public void setCrossRefList(List<TransaksiProdukCrossRef> crossRefList){
        this.crossRefList = crossRefList;
        notifyDataSetChanged();
    }

    public void setTransaksiList(List<Transaksi> transaksis){
        this.transaksiList = transaksis;
        notifyDataSetChanged();
    }

    public void setPencatatan(List<Pencatatan> index){
        this.index = index;
        notifyDataSetChanged();
    }

    public void setTahun(String tahun){
        this.tahun = tahun;
    }

    public void setBulan(String bulan){
        this.bulan = bulan;
    }

    public class MainActivityViewHolder extends RecyclerView.ViewHolder{

        TextView index, nominal;

        public MainActivityViewHolder(@NonNull View itemView) {
            super(itemView);

            index = itemView.findViewById(R.id.jenisPencatatan);
            nominal = itemView.findViewById(R.id.nominalPencatatan);

        }
    }

}
