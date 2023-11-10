package com.example.ecconsol.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecconsol.Activities.CategoryActivity
import com.example.ecconsol.Model.CategoryModel
import com.example.ecconsol.R
import com.example.ecconsol.databinding.LayoutCategoryItemBinding


class CategoryAdapter(var context : Context, var list: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view : View):RecyclerView.ViewHolder(view){
        var binding = LayoutCategoryItemBinding.bind(view)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
      return  CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.binding.eccatname.text = list[position].cate
        Glide.with(context).load(list[position].img).into(holder.binding.imageView)

        holder.itemView.setOnClickListener{
            val intent = Intent(context,CategoryActivity::class.java)
            intent.putExtra("cate",list[position].cate)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }

}