package com.saitawngpha.blogmultiplatform.styles

import com.saitawngpha.blogmultiplatform.models.Theme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.hover
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.ms


/**
 * Created by တွင်ႉၾႃႉ on 04/11/2023.
 */

val EditorKeyStyle by ComponentStyle {
     base {
         Modifier
             .backgroundColor(Colors.Transparent)
             .transition(CSSTransition(property = "background", duration = 300.ms))
     }

    hover{
        Modifier
            .backgroundColor(Theme.Primary.rgb)
            //.transition(CSSTransition(property = "background", duration = 300.ms))
    }
}