package com.example.ecadmin.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.ecadmin.Adapter.AllOrderAdapter
import com.example.ecadmin.R
import com.example.ecadmin.model.AllOrderModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class allOrderActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<AllOrderModel>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_order)

        list = ArrayList()

        val recyclerview  = findViewById<RecyclerView>(R.id.recyclerView)

        Firebase.firestore.collection("allOrders").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it){
                    val data = doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }
                recyclerview.adapter = AllOrderAdapter(list,this)
            }


    }
}