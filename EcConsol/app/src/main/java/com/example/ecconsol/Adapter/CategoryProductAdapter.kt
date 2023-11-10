package com.example.ecconsol.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecconsol.Activities.ProductDetailsActivity
import com.example.ecconsol.Model.AddProductModel
import com.example.ecconsol.databinding.ItemCategoryProductLayoutBinding
import com.example.ecconsol.databinding.LayoutProductItemBinding

class CategoryProductAdapter(val context: Context, val list: ArrayList<AddProductModel>)
    :RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){

    inner class CategoryProductViewHolder(val binding: ItemCategoryProductLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding  = ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        Glide.with(context).load(list[position].ProductCoverImg).into(holder.binding.imageView3)

        holder.binding.textView.text=list[position].ProductName
        holder.binding.textView2.text=list[position].ProductSP

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].ProductId)
            context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
       return list.size
    }
}