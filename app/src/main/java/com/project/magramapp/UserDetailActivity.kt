package com.project.magramapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.magramapp.adapter.AdapterViewAlbumPhotos
import com.project.magramapp.adapter.AdapterViewCommentPost
import com.project.magramapp.adapter.AdapterViewPostMain
import com.project.magramapp.databinding.ActivityUserDetailBinding
import com.project.magramapp.dataclass.*
import org.json.JSONArray
import java.util.ArrayList

class UserDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.main_color)

        val listUser = ArrayList<UserData>()
        val listAlbum = ArrayList<AlbumData>()
        val listPhotos = ArrayList<PhotosData>()



        val userId = intent.getStringExtra("userId")
        getUserData(listUser,listAlbum,userId!!)

        binding.spAlbumUseer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val albumTitle = parent?.selectedItem.toString()
                listAlbum.forEach {
                    if(albumTitle == it.title){
                        getPhotos(listPhotos,it.id)
                    }
                }
            }

        }

    }

    private fun getUserData(listUser : ArrayList<UserData>,listAlbum:ArrayList<AlbumData>,userId:String){

        //stringRequest
        val queue = Volley.newRequestQueue(this)
        val urlUser = "https://jsonplaceholder.typicode.com/users?id=$userId"
        val urlAlbum = "https://jsonplaceholder.typicode.com/albums?userId=$userId"

        listUser.clear()
        val stringRequestUser = StringRequest(
            Request.Method.GET,urlUser,
            { response ->
                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)
                    val jsonobject = jsonarray.getJSONObject(0)
                    val id = jsonobject.get("id").toString()
                    val name = jsonobject.get("name").toString()
                    val email = jsonobject.get("email").toString()
                    val companyName =jsonobject.getJSONObject("company").get("name").toString()
                    val addressStreet =jsonobject.getJSONObject("address").get("street")
                    val addressSuite =jsonobject.getJSONObject("address").get("suite")
                    val addressCity =jsonobject.getJSONObject("address").get("city")
                    val addressZipcode =jsonobject.getJSONObject("address").get("zipcode")
                    val addressGeo =jsonobject.getJSONObject("address").get("geo")
                    val address = "Street : $addressStreet\n" +
                            "Suite : $addressSuite\n" +
                            "City : $addressCity\n" +
                            "ZipCode : $addressZipcode\n" +
                            "Geo :  $addressGeo"
                     listUser.add(UserData(id,name,email,companyName,address))

                binding.apply {
                    tvUserNameDetailUser.text = name
                    tvEmailDetailUser.text = email
                    tvCompanyDetailUser.text = companyName
                    tvAddressDetailUser.text = address
                }

            }, {})


        val stringRequestAlbum = StringRequest(
            Request.Method.GET,urlAlbum,
            { response ->

                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)

                for(i in 0 until jsonarray.length()){
                    val jsonObject = jsonarray.getJSONObject(i)
                    val id = jsonObject.get("id").toString()
                    val title = jsonObject.get("title").toString()
                    listAlbum.add(AlbumData(id,title))
                }
                val listAlbumSpinner = ArrayList<String>()

                listAlbum.forEach{
                    listAlbumSpinner.add(it.title)
                }

                val adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listAlbumSpinner)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spAlbumUseer.adapter = adapter
            }, {})

        queue.add(stringRequestUser)
        queue.add(stringRequestAlbum)

    }


    private fun getPhotos(listPhotos : ArrayList<PhotosData>,albumId : String){

        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/photos?albumId=$albumId"

        val stringRequest = StringRequest(
            Request.Method.GET,url,
            { response ->
                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)

                listPhotos.clear()
                for(i in 0 until jsonarray.length()){
                    val jsonObject = jsonarray.getJSONObject(i)
                    val id = jsonObject.get("id").toString()
                    val title = jsonObject.get("title").toString()
                    val urlPhoto = jsonObject.get("url").toString()
                    val thumbnailUrl = jsonObject.get("thumbnailUrl").toString()
                    listPhotos.add(PhotosData(albumId, id, title, urlPhoto, thumbnailUrl))
                }

                //RecyclerView
                binding.rvAlbumPhoto.adapter = AdapterViewAlbumPhotos(listPhotos)
                binding.rvAlbumPhoto.layoutManager = GridLayoutManager(this,3)
                binding.rvAlbumPhoto.setHasFixedSize(true)
            }, {})
        queue.add(stringRequest)
    }



}