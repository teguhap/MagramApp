package com.project.magramapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.magramapp.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listPost = ArrayList<ListPostAdapter>()
        val listUser = ArrayList<UserData>()
        val listPostAdapter = ArrayList<ListPost>()

        Log.d("loopBefore","Sebelum Terlooping")
        getPostData(listPost)


        listPost.forEach {
            Log.d("loop","Terlooping")
            var username = ""
            var companyName = ""
            listUser.forEach { user->
                if(it.userId == user.id){
                    username = user.name
                    companyName = user.companyName
                }
            }

            listPostAdapter.add(ListPost(it.userId,it.idPost,it.title,it.body,username,companyName))
            Log.d("dataPost",listPostAdapter.toString())
        }

        binding.apply {
            //Toolbar
            toolbarMain.setTitleTextAppearance(this@MainActivity,R.style.MonsTextAppearance)

            //ArrayPost


            rvPostMain.adapter = AdapterViewPostMain(listPostAdapter)
            rvPostMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvPostMain.setHasFixedSize(true)
        }
    }

    fun getPostData(listPost : ArrayList<ListPostAdapter>){
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts"

        val stringRequest = StringRequest(
            Request.Method.GET,url,
            {
                    response ->

                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)
                for(i in 0 until jsonarray.length()){
                    val jsonobject = jsonarray.getJSONObject(i)
                    val userId = jsonobject.get("userId").toString()
                    val id = jsonobject.get("id").toString()
                    val title = jsonobject.get("title").toString()
                    val body = jsonobject.get("body").toString()
                    listPost.add(ListPostAdapter(userId,id,title,body))
                }
            }, {})
        queue.add(stringRequest)
    }

    fun getUserData(listUserData: ArrayList<UserData>){
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/users"

        val stringRequest = StringRequest(
            Request.Method.GET,url,
            {
                    response ->

                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)
                for(i in 0 until jsonarray.length()){
                    val jsonobject = jsonarray.getJSONObject(i)
                    val id = jsonobject.get("id").toString()
                    val name = jsonobject.get("name").toString()
                    val companyName =jsonobject.getJSONObject("company").get("name").toString()
                    listUserData.add(UserData(id,name,companyName))
                    Log.d("listUser",listUserData.toString())
                }
            }, {})
        queue.add(stringRequest)
    }

}