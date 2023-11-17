package com.saitawngpha.blogmultiplatform.components

import androidx.compose.runtime.Composable
import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.navigation.Screen
import com.saitawngpha.blogmultiplatform.styles.CategoryItemStyle
import com.saitawngpha.blogmultiplatform.util.Constants
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.variable

/**
 * Created by တွင်ႉၾႃႉ on 11/11/2023.
 */

@Composable
fun CategoryNavigationItems(
    selectedCategory: Category? = null,
    vertical: Boolean = false
) {
    val context = rememberPageContext()
    Category.values().forEach { category ->
        Link(
            modifier = CategoryItemStyle.toModifier()
                .thenIf(
                    condition = vertical,
                    other = Modifier.margin(bottom = 24.px)
                )
                .thenIf(
                    condition = !vertical,
                    other = Modifier.margin(right = 24.px)
                )
                .thenIf(
                    condition = selectedCategory == category,
                    other = Modifier.color(Theme.Primary.rgb)
                )
                .fontFamily(Constants.FONT_FAMILY)
                .fontSize(16.px)
                .fontWeight(FontWeight.Medium)
                .textDecorationLine(TextDecorationLine.None)
                .onClick {
                         context.router.navigateTo(Screen.SearchPage.searchByCategory(category = category))
                },
            path = "",
            text = category.name
        )
    }
}

