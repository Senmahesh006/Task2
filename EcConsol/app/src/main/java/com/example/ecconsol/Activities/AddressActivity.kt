package com.example.ecconsol.Activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.ecconsol.R
import com.example.ecconsol.databinding.ActivityAddressBinding
import com.example.ecconsol.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
   private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
      preferences= this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInfo()

        binding.proced.setOnClickListener {
            validateData(
                binding.name.text.toString(),
                binding.number.text.toString(),
                binding.pincode.text.toString(),
                binding.village.text.toString(),
                binding.city.text.toString(),
                binding.state.text.toString()
            )
        }

    }


    private fun validateData(
        name: String,
        number: String,
        pin: String,
        village: String,
        city: String,
        state: String
    ) {
        if (number.isEmpty() || state.isEmpty() || pin.isEmpty() || name.isEmpty() || village.isEmpty() || city.isEmpty()) {
            Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
        } else {
            storeData(village, pin, state, city,name,number)
        }
    }

    private fun storeData(name: String,number: String,village: String, pin: String, state: String, city: String) {

        val map = hashMapOf<String, Any>()
        map["UserName"] = name
        map["UserPhoneNumber"] = number
        map["village"] = village
        map["state"] = state
        map["pinCode"] = pin
        map["city"] = city

        Firebase.firestore.collection("users")
            //.document(preferences.getString("number", "")!!)
            .document()
            .update(map).addOnSuccessListener {

                startActivity(Intent(this, CheckOutActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Some Thing went Wrong", Toast.LENGTH_SHORT).show()
            }


    }


    private fun loadUserInfo() {

        Firebase.firestore.collection("Users")
            //.document(preferences.getString("number","9571168399")!!)
            .document()
            .get().addOnSuccessListener {
                binding.name.setText(it.getString("UserName"))
                binding.village.setText(it.getString("village"))
                binding.state.setText(it.getString("state"))
                binding.city.setText(it.getString("city"))
                binding.pincode.setText(it.getString("pinCode"))
            }

    }
    }

