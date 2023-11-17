package com.saitawngpha.blogmultiplatform.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

/**
 * Created by တွင်ႉၾႃႉ on 03/11/2023.
 */

@Composable
fun LoadingIndicator( modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .height(100.vh)
            .padding(top = 50.px),
        contentAlignment = Alignment.Center
    ){
        Div(
            attrs = Modifier
                .classNames("spinner-border", "text-primary")
                .toAttrs()
        ){
            Span(
                 attrs = Modifier.classNames("visually-hidden").toAttrs ()
            ) {
                Text("Loading...")
            }
        }
    }
}