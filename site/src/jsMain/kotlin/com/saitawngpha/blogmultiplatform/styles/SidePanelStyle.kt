package com.saitawngpha.blogmultiplatform.styles

import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.util.Id
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import org.jetbrains.compose.web.css.ms

/**
 * Created by တွင်ႉၾႃႉ on 03/11/2023.
 */

val NavigationItemStyle by ComponentStyle {
        cssRule("  > #${Id.svgParent} > #${Id.vectorIcon} ") {
                Modifier
                    .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
                    .styleModifier {
                         property("stroke", Theme.White.hex )
                }
        }

        cssRule(":hover > #${Id.svgParent} > #${Id.vectorIcon}") {
            Modifier
                .styleModifier {
                    property("stroke", Theme.Primary.hex )
                }
        }

        cssRule(":hover > #navigationText") {
             Modifier.color(Theme.Primary.rgb)
        }
        cssRule(" > #${Id.navigationText}") {
            Modifier
                .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
                .color(Theme.White.rgb)
        }
}