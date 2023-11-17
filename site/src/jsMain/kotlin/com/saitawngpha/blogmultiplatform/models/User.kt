package com.saitawngpha.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by တွင်ႉၾႃႉ on 02/11/2023.
 */
@Serializable
actual data class User(
    @SerialName(value = "_id")
    actual val id: String = "",
    actual val username: String = "",
    actual val password: String = ""
)

@Serializable
actual data class UserWithoutPass(
    @SerialName(value = "_id")
    actual val id: String = "",
    actual val username: String = ""
)