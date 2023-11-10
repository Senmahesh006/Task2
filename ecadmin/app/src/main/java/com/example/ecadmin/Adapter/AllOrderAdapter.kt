package com.example.ecadmin.Adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecadmin.activity.allOrderActivity
import com.example.ecadmin.databinding.AllOrderItemLayoutBinding
import com.example.ecadmin.model.AllOrderModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AllOrderAdapter(val list: ArrayList<AllOrderModel>, var context: allOrderActivity) :
RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(val binding: AllOrderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }


    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTittle.text = list[position].productName
        holder.binding.productprice.text = list[position].productSP

        holder.binding.cancle.setOnClickListener {
               holder.binding.proceed.text = "Cancel"
            holder.binding.proceed.visibility = GONE
            updateStstus("Canceled", list[position].orderId!!)
        }

        when (list[position].status) {
            "ordered" -> {
                holder.binding.proceed.text = "ordered"

                holder.binding.proceed.setOnClickListener {
                    updateStstus("Dispatcher", list[position].orderId!!)
                }
            }

            "Dispatcher" -> {
                holder.binding.proceed.text = "Dispatcher"
                holder.binding.proceed.setOnClickListener {
                    updateStstus("Delivered", list[position].orderId!!)
                }
            }

            "Delivered" -> {
                holder.binding.proceed.isEnabled = false
                holder.binding.cancle.visibility = GONE
                holder.binding.proceed.text = "Already Delivered"
//                holder.binding.proceed.setOnClickListener {
//                    updateStstus("Delivered", list[position].orderId!!)
//                }
            }

            "Canceled" -> {
                holder.binding.proceed.visibility = GONE
                holder.binding.cancle.isEnabled = false
            }
        }

    }

    fun updateStstus(str: String, doc: String) {
        val data = hashMapOf<String, Any>()
        data["status"] = str
        Firebase.firestore.collection("allOrders")
            .document(doc).update(data)
            .addOnSuccessListener {
               // Toast.makeText(context, "Status Updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                //Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
       return  list.size
    }

}