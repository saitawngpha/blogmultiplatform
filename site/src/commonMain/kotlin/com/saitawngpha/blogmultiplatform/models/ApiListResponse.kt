package com.saitawngpha.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

/**
 * Created by တွင်ႉၾႃႉ on 08/11/2023.
 */

@Serializable(ApiListResponseSerializer::class)
 sealed class ApiListResponse {
    @Serializable
    @SerialName("idel")
     object Idel: ApiListResponse()
    @Serializable
    @SerialName("success")
     class Success( val data: List<PostWithoutDetails>): ApiListResponse()
    @Serializable
    @SerialName("error")
     class Error(val message: String): ApiListResponse()
}

@Serializable(ApiResponseSerializer::class)
 sealed class ApiResponse {
    @Serializable
    @SerialName("idel")
     object Idel: ApiResponse()
    @Serializable
    @SerialName("success")
     class Success( val data: Post): ApiResponse()
    @Serializable
    @SerialName("error")
     class Error(val message: String): ApiResponse()
}

object ApiListResponseSerializer: JsonContentPolymorphicSerializer<ApiListResponse>(ApiListResponse::class){
    override fun selectDeserializer(element: JsonElement) = when {
        "data" in element.jsonObject -> ApiListResponse.Success.serializer()
        "message" in element.jsonObject -> ApiListResponse.Error.serializer()
        else -> ApiListResponse.Idel.serializer()
    }
}

object ApiResponseSerializer: JsonContentPolymorphicSerializer<ApiResponse>(ApiResponse::class){
    override fun selectDeserializer(element: JsonElement) = when {
        "data" in element.jsonObject -> ApiResponse.Success.serializer()
        "message" in element.jsonObject -> ApiResponse.Error.serializer()
        else -> ApiResponse.Idel.serializer()
    }
}