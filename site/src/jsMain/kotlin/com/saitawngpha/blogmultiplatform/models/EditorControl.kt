package com.saitawngpha.blogmultiplatform.models

import com.saitawngpha.blogmultiplatform.util.Res

/**
 * Created by တွင်ႉၾႃႉ on 04/11/2023.
 */
enum class EditorControl (
     val icon: String,
) {
    Bold(icon = Res.Icon.bold),
    Italic(icon = Res.Icon.italic),
    Link(icon = Res.Icon.link),
    Title(icon = Res.Icon.title),
    Subtitle(icon = Res.Icon.subtitle),
    Quote(icon = Res.Icon.quote),
    Code(icon = Res.Icon.code),
    Image(icon = Res.Icon.image)
}