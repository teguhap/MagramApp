package com.project.magramapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.magramapp.adapter.AdapterViewPostMain
import com.project.magramapp.databinding.ActivityMainBinding
import com.project.magramapp.dataclass.ListPostAdapter
import com.project.magramapp.dataclass.UserData
import org.json.JSONArray
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    @SuppressLint("NotifyDataSetChanged")
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


    @SuppressLint("NotifyDataSetChanged")
    private fun getPostData(listPost : ArrayList<ListPostAdapter>, listUser : ArrayList<UserData>){

        //stringRequest
        val queue = Volley.newRequestQueue(this)
        val urlPost = "https://jsonplaceholder.typicode.com/posts"
        val urlUser = "https://jsonplaceholder.typicode.com/users"

        listUser.clear()
        listPost.clear()

        val stringRequestUser = StringRequest(
            Request.Method.GET,urlUser,
            { response ->
                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)
                for(i in 0 until jsonarray.length()){
                    val jsonobject = jsonarray.getJSONObject(i)
                    val id = jsonobject.get("id").toString()
                    val name = jsonobject.get("name").toString()
                    val companyName =jsonobject.getJSONObject("company").get("name").toString()
                    listUser.add(UserData(id,name,companyName))
                }
            }, {})


        val stringRequest = StringRequest(
            Request.Method.GET,urlPost,
            { response ->

                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)

                for(i in 0 until 15){
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
                binding.rvPostMain.itemAnimator = null


            }, {})

        queue.add(stringRequestUser)
        queue.add(stringRequest)

    }


}