package com.saitawngpha.blogmultiplatform.api

import com.saitawngpha.blogmultiplatform.data.MongoDB
import com.saitawngpha.blogmultiplatform.models.Newsletter
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue

/**
 * Created by တွင်ႉၾႃႉ on 14/11/2023.
 */
@Api(routeOverride = "subscribe")
suspend fun subscribeNewsletter(context: ApiContext){
    try {
        val newsletter = context.req.getBody<Newsletter>()
        context.res.setBody(newsletter?.let {
            context.data.getValue<MongoDB>().subscribe(newsletter = it)
        })
    }catch (e: Exception){
        context.res.setBody(e.message)
    }
}