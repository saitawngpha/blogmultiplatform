package com.saitawngpha.blogmultiplatform.models

import kotlinx.serialization.Serializable

/**
 * Created by တွင်ႉၾႃႉ on 04/11/2023.
 */
@Serializable
enum class Category(override val color: String) : CategoryCommon{
        Technology(color = Theme.Green.hex),
        Programming(color = Theme.Yellow.hex),
        Design(color = Theme.Purple .hex )
}