package com.example.ecconsol.Activities

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecconsol.MainActivity
import com.example.ecconsol.R
import com.example.ecconsol.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }




        binding.login.setOnClickListener {

        }

        binding.login.setOnClickListener{

            if (binding.phonenumber.text!!.isEmpty())
                Toast.makeText(this, "Please Provid Number", Toast.LENGTH_SHORT).show()
            else
                sendotp(binding.phonenumber.text.toString())

}
    }
private  lateinit var bulider:AlertDialog
    private fun sendotp(number: String) {
         bulider = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        bulider.show()
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
  val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {


        }

        override fun onVerificationFailed(e: FirebaseException) {


        }

        @SuppressLint("SuspiciousIndentation")
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {

        val intent = Intent(this@LoginActivity,OtpActivity::class.java)
            intent.putExtra("VerificationId",verificationId)
            //intent.putExtra("number",binding.phonenumber.text.toString())
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()

        }
    }

}
