package com.saitawngpha.blogapp.models

import com.saitawngpha.blogmultiplatform.models.CategoryCommon

/**
 * Created by တွင်ႉၾႃႉ on 16/11/2023.
 */

enum class Category(override val color: String): CategoryCommon {
    Programming(color = ""),
    Technology(color = ""),
    Design(color = "")
}
