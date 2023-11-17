package com.saitawngpha.blogmultiplatform.components

import androidx.compose.runtime.Composable
import com.saitawngpha.blogmultiplatform.models.EditorControl
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Id
import com.saitawngpha.blogmultiplatform.util.noBorder
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleCheck
import com.varabyte.kobweb.silk.components.icons.fa.FaCircleXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement

/**
 * Created by တွင်ႉၾႃႉ on 04/11/2023.
 */

@Composable
fun MessagePopup(
    checkOnSuccess: Boolean = false,
    message: String,
    onDialogDissmis: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(Theme.HalfBlack.rgb)
                .onClick { onDialogDissmis() },
            contentAlignment = Alignment.Center
        )
        Column (
            modifier = Modifier
                .padding(all = 24.px)
                .backgroundColor(Colors.White)
                .borderRadius(r = 4.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (checkOnSuccess){
                FaCircleCheck(
                    modifier = Modifier
                        .margin(bottom = 12.px)
                        .color(Colors.Green),
                    size = IconSize.X2
                )
            }else {
                FaCircleXmark(
                    modifier = Modifier
                        .margin(bottom = 12.px)
                        .color(Colors.Red),
                    size = IconSize.X2
                )
            }
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .textAlign(TextAlign.Center)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(16.px)
                ,
                text = message)
        }
    }
}

@Composable
fun LinkPopup(
    editorControl: EditorControl,
    onDialogDismiss: () -> Unit,
    onAddClick: (String, String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.vw)
            .height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(Theme.HalfBlack.rgb)
                .onClick { onDialogDismiss() },
            contentAlignment = Alignment.Center
        )
        Column (
            modifier = Modifier
                .width(500.px)
                .padding(all = 24.px)
                .backgroundColor(Colors.White)
                .borderRadius(r = 4.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           Input(
               type = InputType.Text,
               attrs = Modifier
                   .id(Id.linkHrefInput)
                   .fillMaxWidth()
                   .height(54.px)
                   .margin(bottom = 12.px)
                   .padding(leftRight = 20.px)
                   .fontFamily(FONT_FAMILY)
                   .fontSize(14.px)
                   .borderRadius(r = 4.px)
                   .noBorder()
                   .backgroundColor(Theme.LightGray.rgb)
                   .toAttrs{
                       attr("placeholder", if(editorControl == EditorControl.Link) "Link" else "Image URL")
                   }
           )

            Input(
                type = InputType.Text,
                attrs = Modifier
                    .id(Id.linkTitleInput)
                    .fillMaxWidth()
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .margin(bottom = 20.px )
                    .fontFamily(FONT_FAMILY)
                    .borderRadius(r = 4.px)
                    .fontSize(14.px)
                    .noBorder()
                    .backgroundColor(Theme.LightGray.rgb)
                    .toAttrs{
                        attr("placeholder", if(editorControl == EditorControl.Link) "Title" else "Description")
                    }
            )

            Button(
                attrs = Modifier
                    .onClick {
                        val href = (document.getElementById(Id.linkHrefInput) as HTMLInputElement).value
                        val title = (document.getElementById(Id.linkTitleInput) as HTMLInputElement).value
                        onAddClick(href, title)
                        onDialogDismiss()
                    }
                    .fillMaxWidth()
                    .height(54.px)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .borderRadius(r = 4.px)
                    .backgroundColor(Theme.Primary.rgb)
                    .color(Colors.White)
                    .noBorder()
                    .toAttrs()
            ){
                SpanText(text = "Add")
            }
        }
    }
}

