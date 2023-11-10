package com.example.ecconsol.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat.getCategory
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.ecconsol.Adapter.CategoryProductAdapter
import com.example.ecconsol.Adapter.ProductAdaoter
import com.example.ecconsol.Model.AddProductModel
import com.example.ecconsol.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CategoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProducts(intent.getStringExtra("cate"))
    }

    private fun getProducts(category: String?) {

        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView=findViewById<RecyclerView>(R.id.cRecycler)
                recyclerView.adapter = CategoryProductAdapter(this, list)
            }
    }

    }