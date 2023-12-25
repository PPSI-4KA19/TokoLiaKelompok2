package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Cart;
import com.example.tokolia.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<Cart> cartList = new ArrayList<>();

    CartListener listener;

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_produktransaksi,
                parent,false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart currentCart = cartList.get(position);
        holder.namaProduk.setText(currentCart.getNamaProduk());
        holder.hargaProduk.setText(String.format("%,d",currentCart.getHargaProduk()));
        holder.qty.setText("" + currentCart.getQty());


        int limit = currentCart.getStok();

        if(currentCart.getQty() <= 0){
            holder.buttonMinus.setEnabled(false);
        } else {
            holder.buttonMinus.setEnabled(true);
        }

        if(currentCart.getQty() >= limit){
            holder.buttonPlus.setEnabled(false);
            holder.stokWarning.setVisibility(View.VISIBLE);
        } else {
            holder.buttonPlus.setEnabled(true);
            holder.stokWarning.setVisibility(View.INVISIBLE);
        }

        holder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCart.setQty(currentCart.getQty()+1);
                notifyDataSetChanged();
            }
        });

        holder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCart.setQty(currentCart.getQty()-1);

                if(currentCart.getQty() == 0){
                    cartList.remove(currentCart);
                }

                notifyDataSetChanged();
            }
        });

    }

    public void setCartList(List<Cart> cartList){
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int size = cartList.size();
        if(cartList.isEmpty()){
            size = 0;
        }
        return size;
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        private TextView namaProduk, hargaProduk, stokWarning;
        private EditText qty;
        private Button buttonPlus, buttonMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            namaProduk = itemView.findViewById(R.id.textViewTransaksiNamaProduk);
            hargaProduk = itemView.findViewById(R.id.textViewTransaksiHargaProduk);
            stokWarning = itemView.findViewById(R.id.textWarningStok);
            qty = itemView.findViewById(R.id.editJumlahProduk);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);

            stokWarning.setVisibility(View.INVISIBLE);

        }
    }

    public void setCartListener(CartListener listener){
        this.listener = listener;
    }

    public interface CartListener{
        public void onRemove(Cart cart);
        public void onChange(Cart cart);
    }
}
