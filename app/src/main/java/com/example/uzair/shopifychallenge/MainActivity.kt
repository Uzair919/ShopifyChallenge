package com.example.uzair.shopifychallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_main.layoutManager=LinearLayoutManager(this)


        fetchjson()
    }

    fun fetchjson()
    {
        val url="https://shopicruit.myshopify.com/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"
        val request=Request.Builder().url(url).build()
        val client=OkHttpClient()
        client.newCall(request).enqueue(object :Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body=response?.body()?.string()
                println(body)
                val gson=GsonBuilder().create()
                val customcollect=gson.fromJson(body,Collect::class.java)
                runOnUiThread {
                    recyclerview_main.adapter=Mainadapter(customcollect)
                }

            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to request")
            }
        })



    }

}



