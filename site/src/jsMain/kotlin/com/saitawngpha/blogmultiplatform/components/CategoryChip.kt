package com.saitawngpha.blogmultiplatform.components

import androidx.compose.runtime.Composable
import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

/**
 * Created by တွင်ႉၾႃႉ on 08/11/2023.
 */

@Composable
fun CategoryChip(
    category: Category,
    darkTheme: Boolean = false
) {
    Box (
        modifier = Modifier
            .height(32.px)
            .padding(leftRight = 14.px)
            .borderRadius(r = 100 .px)
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = if(darkTheme) Theme.values().find { it.hex == category.color }?.rgb else Theme.HalfBlack.rgb
            ),
        contentAlignment = Alignment.Center
    ){
        SpanText(
            modifier = Modifier
                .fontFamily(FONT_FAMILY)
                .fontSize(12.px)
                .color(if(darkTheme) Theme.values().find { it.hex == category.color }?.rgb ?: Theme.HalfBlack.rgb
                else Theme.HalfBlack.rgb)
                    ,
            text = category.name
        )
    }
}