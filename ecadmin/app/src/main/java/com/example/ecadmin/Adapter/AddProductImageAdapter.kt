package com.example.ecadmin.Adapter

import android.net.Uri
import android.view.Choreographer.VsyncCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecadmin.databinding.FragmentProductBinding
import com.example.ecadmin.databinding.ImageItemBinding

class AddProductImageAdapter (val list: ArrayList<Uri>): RecyclerView.Adapter<AddProductImageAdapter.AddProductIamgeViewHolder>(){
    inner class AddProductIamgeViewHolder(val binding: ImageItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddProductIamgeViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddProductIamgeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddProductIamgeViewHolder, position: Int) {
        holder.binding.itemImg.setImageURI(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }



}