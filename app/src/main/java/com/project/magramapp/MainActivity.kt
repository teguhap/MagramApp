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
    val listUser = ArrayList<UserData>()
    val listPostAdapter = ArrayList<ListPostAdapter>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.main_color)



        getPostData(listPostAdapter,listUser)

        binding.apply {
            //Toolbar
            toolbarMain.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.refresh -> {
                        getPostData(listPostAdapter,listUser)
                        rvPostMain.adapter?.notifyDataSetChanged()
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
                    val jsonObject = jsonarray.getJSONObject(i)
                    val id = jsonObject.get("id").toString()
                    val name = jsonObject.get("name").toString()
                    val email = jsonObject.get("email").toString()
                    val companyName =jsonObject.getJSONObject("company").get("name").toString()
                    val address = jsonObject.getJSONObject("address").toString()
                    listUser.add(UserData(id,name,email,companyName,address))
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

    override fun onResume() {
        super.onResume()
        binding.rvPostMain.adapter = AdapterViewPostMain(listPostAdapter)
        binding.rvPostMain.layoutManager = LinearLayoutManager(this@MainActivity)
    }


}