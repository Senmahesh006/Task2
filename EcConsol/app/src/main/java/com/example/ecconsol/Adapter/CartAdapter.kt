package com.example.ecconsol.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecconsol.Activities.ProductDetailsActivity
import com.example.ecconsol.Roomdb.AppDatabase
import com.example.ecconsol.Roomdb.ProductModel
import com.example.ecconsol.databinding.LayoutCartItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CartAdapter(val context : Context, val list :List<ProductModel>):
RecyclerView.Adapter<CartAdapter.CratViewHolder>(){

    inner  class  CratViewHolder(val binding: LayoutCartItemBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CratViewHolder {
       var binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CratViewHolder(binding)

    }


    override fun onBindViewHolder(holder: CratViewHolder, position: Int) {
       Glide.with(context).load(list[position].productImage).into(holder.binding.imageView2)

        holder.binding.textView3.text = list[position].productName
        holder.binding.textView8.text = list[position].productSP

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }



        val dao = AppDatabase.getTnstance(context).productDao()
        holder.binding.imageView6.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteproduct(
                    ProductModel(
                        list[position].productId,
                        list[position].productName,
                        list[position].productImage,
                        list[position].productSP
                    )
                )

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}