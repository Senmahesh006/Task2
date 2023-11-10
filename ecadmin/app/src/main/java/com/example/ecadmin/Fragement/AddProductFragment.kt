package com.example.ecadmin.Fragement

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.transition.Visibility
import com.example.ecadmin.Adapter.AddProductImageAdapter
import com.example.ecadmin.R
import com.example.ecadmin.databinding.FragmentAddProductBinding
import com.example.ecadmin.databinding.FragmentProductBinding
import com.example.ecadmin.databinding.ImageItemBinding
import com.example.ecadmin.model.AddProductModel
import com.example.ecadmin.model.CategoryModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddProductFragment : Fragment() {
    private lateinit var binding: FragmentAddProductBinding

    private lateinit var list: ArrayList<Uri>
    private lateinit var listImage: ArrayList<String>
    private lateinit var adapter: AddProductImageAdapter
    private  var coverImage:Uri?= null
            private lateinit var dialog: Dialog
            private  var coverImgUrl:String?=""
    private lateinit var categoryList:ArrayList<String>

    private  var launchproductActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
         if (it.resultCode == Activity.RESULT_OK){
        val imageUrl = it.data!!.data
             list.add(imageUrl!!)
             adapter.notifyDataSetChanged()
    }
    }
    private  var launchGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            coverImage = it.data!!.data
            binding.productCoverImg.setImageURI(coverImage)
            binding.productCoverImg.visibility = VISIBLE
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(layoutInflater)

        list = ArrayList()
        listImage = ArrayList()

        dialog =Dialog(requireContext())
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(false)

        binding.selectCoverImage.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            launchGalleryActivity.launch(intent)
        }

        binding.producrImgBtn.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            launchproductActivity.launch(intent)
        }



        setProductCategory()

        adapter = AddProductImageAdapter(list)
        binding.productRecyclerView.adapter=adapter

        binding.submit.setOnClickListener{
            validateData()
        }



        return binding.root
    }

    private fun validateData() {
        if (binding.produtNameEdt.text.toString().isEmpty()) {
            binding.produtNameEdt.requestFocus()
            binding.produtNameEdt.error = "Empty"
        } else if (binding.productSP.text.toString().isEmpty()) {
            binding.productSP.requestFocus()
            binding.productSP.error = "Empty"
        } else if (coverImage == null) {
            Toast.makeText(requireContext(), "please Select Cover Image", Toast.LENGTH_SHORT).show()
        }else if (list.size<1) {
            Toast.makeText(requireContext(), "please Select product Image", Toast.LENGTH_SHORT).show()
        }else {
            uploadImage()
        }
    }

    private fun uploadImage() {
        dialog.show()
        val  fileName = UUID.randomUUID().toString()+".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("products/$fileName")
        refStorage.putFile(coverImage!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image ->
                    coverImgUrl=image.toString()

                    uploadProductImage()
                }
            }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something went wrong with Storage", Toast.LENGTH_LONG).show()

            }
    }
private var  i = 0
    private fun uploadProductImage() {
        dialog.show()
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("products/$fileName")
        refStorage.putFile(list[i])
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image ->
                    listImage.add(image!!.toString())
                    if (list.size == listImage.size){
                        storeData()
                }else{
                i += 1
                uploadProductImage()
            }
                }

            }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(), "Something went wrong with Storage", Toast.LENGTH_LONG).show()

            }
    }

    private fun storeData() {
        val db = Firebase.firestore.collection("products")
        var key = db.document().id
        val data = AddProductModel(
            binding.produtNameEdt.text.toString(),
            binding.productDescription.text.toString(),
            coverImgUrl.toString(),
            categoryList[binding.productCategoryDropdown.selectedItemPosition],
            key,
            binding.productMRP.text.toString(),
            binding.productSP.text.toString(),
            listImage
        )

        db.document(key).set(data).addOnSuccessListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Product Added", Toast.LENGTH_SHORT).show()
            binding.produtNameEdt.text==null

        }.addOnFailureListener{
            Toast.makeText(requireContext(), "Smething went Wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setProductCategory(){
        categoryList = ArrayList()
        Firebase.firestore.collection("categories").get()
            .addOnSuccessListener {
                for (doc in it.documents){
                    val data= doc.toObject(CategoryModel::class.java)
                    categoryList.add(data!!.cate!!)
                }
                categoryList.add(0,"select Category")

                val arrayAdapter =ArrayAdapter(requireContext(),R.layout.dropdown_item_layout,categoryList)
                binding.productCategoryDropdown.adapter=arrayAdapter
            }
    }

}