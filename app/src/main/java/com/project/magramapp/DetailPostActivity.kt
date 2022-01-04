package com.project.magramapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.project.magramapp.adapter.AdapterViewCommentPost
import com.project.magramapp.databinding.ActivityDetailPostBinding
import com.project.magramapp.dataclass.CommentData
import org.json.JSONArray
import java.util.ArrayList

class DetailPostActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailPostBinding
    val listComment = ArrayList<CommentData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = getColor(R.color.main_color)


        val postId = intent.getStringExtra("postId")
        val userId = intent.getStringExtra("userId")
        val username = intent.getStringExtra("username")
        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")


        getPostData(listComment,postId!!)

        binding.apply {
            tvUserNameDetailPost.text = username
            tvTitleDetailPost.text = title
            tvBodyDetailPost.text = body


            tvUserNameDetailPost.setOnClickListener {
                Intent(this@DetailPostActivity,UserDetailActivity::class.java).also {
                    it.putExtra("userId",userId)
                    startActivity(it)
                }
            }
        }



    }


    private fun getPostData(listComment : ArrayList<CommentData>,postId : String){

        //stringRequest
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/comments?postId=$postId"


        val stringRequest = StringRequest(
            Request.Method.GET,url,
            { response ->

                val strResponse = response.toString()
                val jsonarray = JSONArray(strResponse)

                listComment.clear()

                for(i in 0 until jsonarray.length()){
                    val jsonObject = jsonarray.getJSONObject(i)
                    val post = jsonObject.get("postId").toString()
                    val id = jsonObject.get("id").toString()
                    val name = jsonObject.get("name").toString()
                    val body = jsonObject.get("body").toString()
                    val email = jsonObject.get("email").toString()
                    listComment.add(CommentData(post,id,name,email,body))
                }

                //RecyclerView
                binding.rvCommentDetailPost.adapter = AdapterViewCommentPost(listComment)
                binding.rvCommentDetailPost.layoutManager = LinearLayoutManager(this)
                binding.rvCommentDetailPost.setHasFixedSize(true)
            }, {})
        queue.add(stringRequest)
    }

    override fun onResume() {
        super.onResume()
        binding.rvCommentDetailPost.adapter = AdapterViewCommentPost(listComment)
        binding.rvCommentDetailPost.layoutManager = LinearLayoutManager(this)
        binding.rvCommentDetailPost.setHasFixedSize(true)
    }

}