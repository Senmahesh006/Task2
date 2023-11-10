package com.example.ecconsol.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecconsol.Activities.ProductDetailsActivity
import com.example.ecconsol.Model.AddProductModel
import com.example.ecconsol.databinding.LayoutProductItemBinding

class ProductAdaoter(val context: Context,val list: ArrayList<AddProductModel>)
    :RecyclerView.Adapter<ProductAdaoter.ProductViewHolder>(){
    inner class ProductViewHolder (val binding: LayoutProductItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=LayoutProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       val data = list[position]

        Glide.with(context).load(data.ProductCoverImg).into(holder.binding.imageView4)
        holder.binding.productName.text=data.ProductName
        holder.binding.productprice.text=data.ProductCategory
        holder.binding.textView13.text=data.ProductMRP

        holder.binding.button4.text = data.ProductSP

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].ProductId)
            context.startActivity(intent)
        }



    }
    override fun getItemCount(): Int {
        return  list.size
    }
}