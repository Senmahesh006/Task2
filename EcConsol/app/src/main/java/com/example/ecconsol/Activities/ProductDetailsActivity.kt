package com.example.ecconsol.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ecconsol.MainActivity
import com.example.ecconsol.R
import com.example.ecconsol.Roomdb.AppDatabase
import com.example.ecconsol.Roomdb.ProductDao
import com.example.ecconsol.Roomdb.ProductModel
import com.example.ecconsol.databinding.ActivityProductDetailsBinding
import com.example.ecconsol.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {

    private  lateinit var  binding: ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)

        getProductDetail(intent.getStringExtra("id"))

        setContentView(binding.root)
    }

    private fun getProductDetail(productid: String?) {

        Firebase.firestore.collection("products")
            .document(productid!!).get().addOnSuccessListener {
                val list = it.get("productImage")as ArrayList<String>
                val name = it.getString("productName")
                val productSP = it.getString("productSP")
                val productDescripton = it.getString("productDescripton")

                binding.textView4.text=name
                binding.textView5.text=productSP
                binding.textView6.text= productDescripton



                val Slidelist = ArrayList<SlideModel>()
                for(data in list){
                    Slidelist.add(SlideModel(data,ScaleTypes.CENTER_CROP))
                }


                cartAction(productid,name,productSP, it.getString("productCoverImg"))

                binding.imageSlider.setImageList(Slidelist)
            }.addOnFailureListener{
                Toast.makeText(this, "Something WEnt Wrong", Toast.LENGTH_SHORT).show()

            }

    }

    private fun cartAction(productid: String, name: String?, productSP: String?, coverImage: String?) {
        val productDao = AppDatabase.getTnstance(this).productDao()

        if (productDao.isExit(productid)!=null){
            binding.textView7.text = "Go to Cart"
        }else{
            binding.textView7.text = "Add to Cart"
        }

        binding.textView7.setOnClickListener{
            if (productDao.isExit(productid)!=null){
                openCart()
            }else{
                addTOCart(productDao,productid,name,productSP,coverImage)
            }
        }

    }

    private fun addTOCart(productDao: ProductDao,
                          productid: String,
                          name: String?,
                          productSP: String?,
                          coverImage: String?) {
        val data = ProductModel(productid,name,coverImage,productSP)

        lifecycleScope.launch (Dispatchers.IO){
            productDao.inserrtProduct(data)
            binding.textView7.text = "Go to Cart"

        }

    }

    private fun openCart() {
        val preferences = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart",true)
        editor.apply()


        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }


}