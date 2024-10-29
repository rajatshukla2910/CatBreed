package com.catfeature.data



 interface  ListViewData {
     val id: String
     val name: String
     val description: String
     val origin: String
     val life_span: String
     val image: ImageData?
 }

data class  CatApiData(
    override val id: String,
    override val name: String,
    override val description: String,
    override val origin: String,
    override val life_span: String,
    override val image: ImageData?
) : ListViewData

data class ImageData(val url: String)