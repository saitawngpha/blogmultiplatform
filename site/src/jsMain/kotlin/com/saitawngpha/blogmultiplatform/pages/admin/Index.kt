package com.saitawngpha.blogmultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.saitawngpha.blogmultiplatform.components.AdminPageLayout
import com.saitawngpha.blogmultiplatform.components.LoadingIndicator
import com.saitawngpha.blogmultiplatform.models.RandomJoke
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.navigation.Screen
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Constants.HUMOR_API_URL
import com.saitawngpha.blogmultiplatform.util.Constants.PAGE_WIDTH
import com.saitawngpha.blogmultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.saitawngpha.blogmultiplatform.util.Res
import com.saitawngpha.blogmultiplatform.util.fetchRandomJoke
import com.saitawngpha.blogmultiplatform.util.isUserLoggedIn
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.http.http
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaPlus
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

/**
 * Created by တွင်ႉၾႃႉ on 03/11/2023.
 */

@Page
@Composable
fun HomePage() {
  isUserLoggedIn {
      HomeScreen()
  }
}

@Composable
fun HomeScreen() { 
    var randomJoke: RandomJoke? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit){
        fetchRandomJoke {
            randomJoke = it 
        }
    }
     
    AdminPageLayout {
        AddButton()
        HomeContent(randomJoke = randomJoke)
    }
}

@Composable
fun HomeContent(randomJoke: RandomJoke? ) {
    var breakpoint = rememberBreakpoint()
    Box(
        modifier = Modifier
            .fillMaxSize()
            // .margin(top = if (breakpoint > Breakpoint.MD)10.percent else 0.percent)
            .padding(left = if(breakpoint > Breakpoint.MD)SIDE_PANEL_WIDTH.px else 0.px),
        contentAlignment = Alignment.Center
    ){
        if(randomJoke != null){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(topBottom = 50.px),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(randomJoke.id != -1){
                    Image(
                         modifier = Modifier
                             .size(150.px)
                             .margin(bottom = 50.px),
                        src = Res.Image.laugh,
                        desc = "Image Laugh"
                    )
                }

                if(randomJoke.joke.contains("Q:")){
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 14.px)
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .color(Theme.Secondary.rgb)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(28.px)
                            .fontWeight(FontWeight.Bold ),
                        text = randomJoke.joke.split(":")[1].dropLast(1)
                    )
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 14.px)
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .fontFamily(FONT_FAMILY)
                            .color(Theme.HalfBlack .rgb)
                            .fontSize(20.px)
                            .fontWeight(FontWeight.Normal )
                           ,
                        text = randomJoke.joke.split(":").last()
                    )
                }else{
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 14.px)
                            .fillMaxWidth(40.percent)
                            .textAlign(TextAlign.Center)
                            .fontFamily(FONT_FAMILY)
                            .color(Theme.Secondary.rgb)
                            .fontSize(28.px)
                            .fontWeight(FontWeight.Bold ),
                        text = randomJoke.joke
                    )
                }
            }
        }else{
            LoadingIndicator( )
        }
    }
}

@Composable
fun AddButton() {
    val breakpoint = rememberBreakpoint()
    val context = rememberPageContext()
     Box (
         modifier = Modifier
             .height(100.vh) 
             .fillMaxSize()
             .maxWidth(PAGE_WIDTH.px)
             .position(Position.Fixed)
             .styleModifier {
                            property("pointer-events", "none")
             },
         contentAlignment = Alignment.BottomEnd
     ){
         Box(
             modifier = Modifier
                 .margin(
                     right = if(breakpoint > Breakpoint.MD) 40.px else 20.px,
                     bottom = if(breakpoint > Breakpoint.MD) 40.px else 20.px
                 )
                 .backgroundColor(Theme.Primary.rgb )
                 .size(if(breakpoint > Breakpoint.MD) 80.px else 50.px)
                 .borderRadius(r = 14.px)
                 .cursor(Cursor.Pointer)
                 .onClick {
                     context.router.navigateTo(Screen.AdminCreate.route )
                 }
                 .styleModifier {
                     property("pointer-events", "auto")
                 },
             contentAlignment = Alignment.Center
         ) {
             FaPlus(
                 modifier = Modifier
                     .color(Colors.White )
                 ,
                 size = IconSize.LG
             )
         }
     }
}