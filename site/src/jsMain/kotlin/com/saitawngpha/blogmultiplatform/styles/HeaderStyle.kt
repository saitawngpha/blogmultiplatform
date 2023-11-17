package com.saitawngpha.blogmultiplatform.styles

import com.saitawngpha.blogmultiplatform.models.Theme
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.anyLink
import com.varabyte.kobweb.silk.components.style.hover
import org.jetbrains.compose.web.css.ms

/**
 * Created by တွင်ႉၾႃႉ on 11/11/2023.
 */

val CategoryItemStyle by ComponentStyle {
    base {
        Modifier
            .color(Colors.White)
            .transition(CSSTransition(property = "color", duration = 200.ms))
    }

    anyLink {
        Modifier
            .color(Colors.White)
    }

    hover {
        Modifier
            .color(Theme.Primary.rgb)

    }
}