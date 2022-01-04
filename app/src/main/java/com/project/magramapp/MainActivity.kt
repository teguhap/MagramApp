package com.project.magramapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.magramapp.databinding.ActivityMainBinding
import org.json.JSONArray
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.main_color)

        val listUser = ArrayList<UserData>()
        val listPostAdapter = ArrayList<ListPostAdapter>()

        getPostData(listPostAdapter,listUser)

        binding.apply {
            //Toolbar
            toolbarMain.setTitleTextAppearance(this@MainActivity,R.style.MonsTextAppearance)
            toolbarMain.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.refresh -> {
                        getPostData(listPostAdapter,listUser)
                    }
                }
                true
            }

        }//Binding
    }

    private fun getPostData(listPost : ArrayList<ListPostAdapter>, listUser : ArrayList<UserData>){

        //stringRequest
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts"

        listUser.clear()
        listPost.clear()

        getUserData(listUser)

        val stringRequest = StringRequest(
            Request.Method.GET,url,
            { response ->

                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)

                for(i in 0 until 10){
                    val random = (0 until jsonarray.length()).random()
                    val jsonobject = jsonarray.getJSONObject(random)
                    val userId = jsonobject.get("userId").toString()
                    val id = jsonobject.get("id").toString()
                    val title = jsonobject.get("title").toString()
                    val body = jsonobject.get("body").toString()

                    var username = ""
                    var companyName = ""
                    listUser.forEach { user->
                        if(userId == user.id){
                            username = user.name
                            companyName = user.companyName
                        }
                    }
                    listPost.add(ListPostAdapter(userId,id,title,body,username,companyName))
                }

                //RecyclerView
                binding.rvPostMain.adapter = AdapterViewPostMain(listPost)
                binding.rvPostMain.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvPostMain.setHasFixedSize(true)
            }, {})
        queue.add(stringRequest)
    }

    private fun getUserData(listUserData: ArrayList<UserData>){
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/users"

        val stringRequest = StringRequest(
            Request.Method.GET,url,
            { response ->
                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)
                for(i in 0 until jsonarray.length()){
                    val jsonobject = jsonarray.getJSONObject(i)
                    val id = jsonobject.get("id").toString()
                    val name = jsonobject.get("name").toString()
                    val companyName =jsonobject.getJSONObject("company").get("name").toString()
                    listUserData.add(UserData(id,name,companyName))
                }
            }, {})

        queue.add(stringRequest)
    }

}