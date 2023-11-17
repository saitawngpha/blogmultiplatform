package com.saitawngpha.blogapp.utils

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */
sealed class RequestState<out T>{
    object Idel: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()
}
