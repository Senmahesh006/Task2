package com.example.ecconsol.Fagment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.ecconsol.Activities.AddressActivity
import com.example.ecconsol.Activities.CategoryActivity
import com.example.ecconsol.Adapter.CartAdapter
import com.example.ecconsol.R
import com.example.ecconsol.Roomdb.AppDatabase
import com.example.ecconsol.Roomdb.ProductModel
import com.example.ecconsol.databinding.FragmentCartBinding
import com.example.ecconsol.databinding.FragmentHomeBinding

class cartFragment : Fragment() {

        private  lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        val preferences =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getTnstance(requireContext()).productDao()
        dao.getallproducts().observe(requireActivity()) {
            binding.CartRecycler.adapter = CartAdapter(requireContext(), it)
            totalcost(it)

        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun totalcost(data :List<ProductModel>?) {
        var total = 0
        for (item in data!!){
            try {
                total += item.productSP!!.toInt()
            } catch (e: NumberFormatException) {

            }

        }

        binding.textView12.text="Total item in cart is ${data.size}"
        binding.textView13.text="Total Cost :  $total"

        binding.button2.setOnClickListener{
            val intent = Intent(context, AddressActivity::class.java)
            intent.putExtra("totalCost",total)
            startActivity(intent)
        }
    }


}