package com.example.uzair.shopifychallenge

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_collection.view.*

class Mainadapter(val collection:Collect):RecyclerView.Adapter<Customviewholder>()
{
    override fun getItemCount(): Int {
        return collection.custom_collections.count()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int):Customviewholder {
             val layoutInflater=LayoutInflater.from(p0?.context)
             val cellforRow=layoutInflater.inflate(R.layout.custom_collection,p0,false)
             return Customviewholder(cellforRow)
        }

    override fun onBindViewHolder(p0: Customviewholder, p1: Int) {
        val custcollect=collection.custom_collections.get(p1)
         p0?.view?.title?.text=custcollect.title
         p0?.view?.textView_modified?.text="Modified at ${custcollect.updated_at}"
         val image=p0?.view?.imageView
          Picasso.get().load(custcollect.image.src).into(image)
          p0?.custtitle=custcollect
    }


}

class Customviewholder(val view:View,var custtitle:CustomCollection?=null):RecyclerView.ViewHolder(view)
{


    init {
        view.setOnClickListener {
            val intent=Intent(view.context,Product::class.java)
            println("this is title${custtitle?.title}")
            intent.putExtra("title",custtitle?.title)
            println("this is id ${custtitle?.id}")
            intent.putExtra("ID",custtitle?.id)

            view.context.startActivity(intent)

        }
    }
}