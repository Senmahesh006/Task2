package com.example.ecconsol.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ecconsol.Model.UserModel
import com.example.ecconsol.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lo.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

            binding.si.setOnClickListener{
                validatUSer()
            }


    }

    private fun validatUSer() {
        if (binding.name.text!!.isEmpty() || binding.PhoneNumber.text!!.isEmpty())
            Toast.makeText(this, "Please fill All Fields", Toast.LENGTH_SHORT).show()
        else
            storedata()

    }

    private fun storedata() {
        val bulider = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        bulider.show()
//
//        val preferences = this.getSharedPreferences("User", MODE_PRIVATE)
//        val editor = preferences.edit()
//
//        editor.putString("number",binding.PhoneNumber.text.toString())
//        editor.putString("name",binding.name.text.toString())
//        editor.apply()

        val data = UserModel(UserName =binding.name.text.toString(), UserPhoneNumber  =binding.PhoneNumber.text.toString())

        Firebase.firestore.collection("Users").document(binding.PhoneNumber.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this, "User Register", Toast.LENGTH_SHORT).show()
                bulider.dismiss()
                openLogin()

            }.addOnFailureListener{
                bulider.dismiss()
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()

            }
    }

    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}