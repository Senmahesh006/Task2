package com.example.ecconsol.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecconsol.MainActivity
import com.example.ecconsol.R
import com.example.ecconsol.databinding.ActivityOtpBinding
import com.example.ecconsol.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)

        binding.button.setOnClickListener{
            if (binding.otp.text!!.isEmpty())
                Toast.makeText(this, "Please Provide OTP", Toast.LENGTH_SHORT).show()
            else{
                verifyUser(binding.otp.text.toString())
            }
        }

        setContentView(binding.root)
    }

    private fun verifyUser(otp: String) {

        val credential = PhoneAuthProvider.getCredential(
            intent.getStringExtra("VerificationId")!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
//
//                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
//                    val editor = preferences.edit()
//                    editor.putString("number",intent.getStringExtra("number")!!)
//                    editor.apply()

                   startActivity(Intent(this,MainActivity::class.java))
                    finish()


                    //val user = task.result?.user
                } else {
                    Toast.makeText(this, "Somethig Wwnt wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }


