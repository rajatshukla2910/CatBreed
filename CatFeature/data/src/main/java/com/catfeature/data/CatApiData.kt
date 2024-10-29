package com.catfeature.data

import java.net.URL


data class CatApiData(
    val id: String,
    val name: String,
    val description: String,
    val origin: String,
    val life_span: String,
    val image: ImageData?,
)

data class ImageData(val url: String)