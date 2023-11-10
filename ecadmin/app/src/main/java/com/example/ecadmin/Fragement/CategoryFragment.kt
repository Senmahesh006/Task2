package com.example.ecadmin.Fragement

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ecadmin.Adapter.CategoryAdapter
import com.example.ecadmin.R
import com.example.ecadmin.databinding.FragmentCategoryBinding
import com.example.ecadmin.model.CategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class CategoryFragment : Fragment() {


    private  lateinit var  binding: FragmentCategoryBinding
    private  var imageUrl : Uri?= null
    private  lateinit var dialog: Dialog

    private  var launchGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            imageUrl = it.data!!.data
            binding.imageView.setImageURI(imageUrl)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater)

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(false)

        getData()

        binding.apply {
            imageView.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                launchGalleryActivity.launch(intent)

            }
            button6.setOnClickListener{
                validateData(binding.categoryname.text.toString())

            }
        }

        return binding.root
    }

    private fun getData() {
        val list =ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryrecyclerView.adapter = CategoryAdapter(requireContext(),list)
            }
    }

    private fun validateData(categoryname: String) {
        if (categoryname.isEmpty()){
            Toast.makeText(requireContext(),"Please Provide category Name",Toast.LENGTH_LONG).show()
        }else if (imageUrl == null){
            Toast.makeText(requireContext(),"Please Select Image",Toast.LENGTH_LONG).show()
        }
        else{
            uploadImage(categoryname)
        }
    }

    private fun uploadImage(categoryname: String) {
        dialog.show()
        val  fileName = UUID.randomUUID().toString()+".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("category/$fileName")
        refStorage.putFile(imageUrl!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image ->
                    storeData(categoryname,image.toString())
                }
            }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something went wrong with Storage", Toast.LENGTH_LONG).show()

            }
    }

    private fun storeData(categoryname: String, url: String) {
        val db = Firebase.firestore

        val data = hashMapOf<String, Any>(
            "cate" to categoryname,
            "img" to url
        )
        db.collection("categories").add(data)
            .addOnSuccessListener {
                dialog.dismiss()
                binding.imageView.setImageDrawable(resources.getDrawable(R.drawable.slider))
                binding.categoryname.text = null
                getData()
                Toast.makeText(requireContext(), "Category Add", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Slider not Update ", Toast.LENGTH_LONG).show()
            }

    }
}