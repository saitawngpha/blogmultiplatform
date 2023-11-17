package com.saitawngpha.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.saitawngpha.blogmultiplatform.models.Category

/**
 * Created by တွင်ႉၾႃႉ on 04/11/2023.
 */

@Serializable
 data class Post(
    @SerialName("_id")
     val id: String = "",
     val author: String = "",
//     val date: Double = 0.0,
     val date: Long = 0L,
     val title: String ,
     val subtitle: String ,
     val thumbnail: String ,
     val content: String,
     val category: Category,
     val popular: Boolean = false,
     val main: Boolean = false,
     val sponsored: Boolean = false
)

@Serializable
 data class PostWithoutDetails(
    @SerialName("_id")
     val id: String = "",
     val author: String,
     val date: Double ,
//     val date: Long ,
     val title: String ,
     val subtitle: String ,
     val thumbnail: String ,
     val category: Category,
    val popular: Boolean = false,
    val main: Boolean = false,
    val sponsored: Boolean = false
)
