package com.example.uzair.shopifychallenge

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.uzair.shopifychallenge.R.id.c_title
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_collection.view.*
import kotlinx.android.synthetic.main.productdetail.*
import kotlinx.android.synthetic.main.productdetail.view.*
import okhttp3.*
import java.io.IOException

class Product:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview_main.layoutManager=LinearLayoutManager(this)

        //recyclerview_main.adapter=ProductdetailAdapter()

        var bartitle=intent.getStringExtra("title")
        supportActionBar?.title=bartitle
        //c_title.text=bartitle
       val id:Long=intent.getLongExtra("ID",68424499256)
        var collecturl="https://shopicruit.myshopify.com/admin/collects.json?collection_id=${id}&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"
         println(collecturl)
          myjson(collecturl)
    }

    fun myjson(url:String) {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                var mystring:String=""
                var t=","
                var len=0
                println(body)
                val productid_list= arrayListOf<Long>()
                val gson = GsonBuilder().create()
                val new_collect = gson.fromJson(body, Data_Collects::class.java)
                for (i in new_collect.collects)
                {
                    productid_list.add(i.product_id)
                }
                len=productid_list.size
                for (i in productid_list) {
                     mystring=mystring.plus(i.toString())+t
                }

                Product_detail_json(mystring,len)




            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to request")
            }
        })

    }
    fun Product_detail_json(p_id:String,length:Int)
    {


        val url="https://shopicruit.myshopify.com/admin/products.json?ids=${p_id}&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()


                val gson = GsonBuilder().create()
                val P_details = gson.fromJson(body, Detail::class.java)
                  runOnUiThread {
                      recyclerview_main.adapter = ProductdetailAdapter(P_details)
                  }

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to request")
            }
        })

    }




    class ProductdetailAdapter(val P_D:com.example.uzair.shopifychallenge.Detail):RecyclerView.Adapter<Productdetailholder>(){

        override fun getItemCount(): Int {
               return P_D.products.count()
            }

        override fun onBindViewHolder(p0: Productdetailholder, p1: Int) {
            var total_inv=0
            val list_holder=P_D.products.get(p1)
            for(i in list_holder.variants)
            {
                total_inv+=i.inventory_quantity
            }
            p0?.customview?.c_title?.text=list_holder.title
                 p0?.customview?.vendor?.text="Vendor :- ${list_holder.vendor}"
                 p0?.customview?.Inventory?.text="Total Inventory ${total_inv.toString()}"
                 val myimage=p0?.customview?.image_product
                 Picasso.get().load(list_holder.image.src).into(myimage)
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Productdetailholder {

            val layoutInflater=LayoutInflater.from(p0?.context)
            val myview=layoutInflater.inflate(R.layout.productdetail,p0,false)


            return Productdetailholder(myview)
        }



    }

    class Productdetailholder(val customview:View):RecyclerView.ViewHolder(customview)
}

