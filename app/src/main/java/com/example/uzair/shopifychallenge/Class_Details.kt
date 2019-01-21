package com.example.uzair.shopifychallenge

class Collect(val custom_collections:List<CustomCollection>)

class CustomCollection(val id:Long,val title:String,val updated_at:String, val image:Image)

class Image(val src:String)

class Data_Collects(val collects:List<NewCollect>)
class NewCollect(val collection_id:Long,val product_id:Long)

class Detail(var products:List<Detail_Product>)
class Detail_Product(var title:String,var vendor:String,var variants:List<Variants>,var image:Img)
class Variants(var inventory_quantity:Int)

class Img(var src:String)