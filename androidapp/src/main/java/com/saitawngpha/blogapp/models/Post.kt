package com.saitawngpha.blogapp.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */
open class Post: RealmObject {
    @PrimaryKey
    var _id: String = ""
    var author: String = ""
    var date: Long = 0L
    var title: String = ""
    var subtitle: String = ""
    var thumbnail: String = ""
    var category: String = Category.Programming.name
}
