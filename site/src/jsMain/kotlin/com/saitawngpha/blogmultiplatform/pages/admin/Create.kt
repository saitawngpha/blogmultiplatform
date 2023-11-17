package com.saitawngpha.blogmultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.saitawngpha.blogmultiplatform.components.AdminPageLayout
import com.saitawngpha.blogmultiplatform.components.LinkPopup
import com.saitawngpha.blogmultiplatform.components.MessagePopup
import com.saitawngpha.blogmultiplatform.models.ApiResponse
import com.saitawngpha.blogmultiplatform.models.Category
import com.saitawngpha.blogmultiplatform.models.Constants.POST_ID_PARAM
import com.saitawngpha.blogmultiplatform.models.ControlStyle
import com.saitawngpha.blogmultiplatform.models.EditorControl
import com.saitawngpha.blogmultiplatform.models.Post
import com.saitawngpha.blogmultiplatform.models.Theme
import com.saitawngpha.blogmultiplatform.navigation.Screen
import com.saitawngpha.blogmultiplatform.styles.EditorKeyStyle
import com.saitawngpha.blogmultiplatform.util.Constants.FONT_FAMILY
import com.saitawngpha.blogmultiplatform.util.Constants.SIDE_PANEL_WIDTH
import com.saitawngpha.blogmultiplatform.util.Id
import com.saitawngpha.blogmultiplatform.util.addPost
import com.saitawngpha.blogmultiplatform.util.applyControlStyle
import com.saitawngpha.blogmultiplatform.util.applyStyle
import com.saitawngpha.blogmultiplatform.util.fetchSelectedPost
import com.saitawngpha.blogmultiplatform.util.getEditor
import com.saitawngpha.blogmultiplatform.util.getSelectedText
import com.saitawngpha.blogmultiplatform.util.isUserLoggedIn
import com.saitawngpha.blogmultiplatform.util.noBorder
import com.saitawngpha.blogmultiplatform.util.updatePost
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.file.loadDataUrlFromDisk
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.disabled
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onKeyDown
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.Ul
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import kotlin.js.Date

/**
 * Created by တွင်ႉၾႃႉ on 03/11/2023.
 */

data class CreatePageUiState(
    var id: String = "",
    var title: String = "",
    var subtitle: String = "",
    var thumbnail: String = "",
    var thumbnialInputDisabled: Boolean = true,
    var content: String = "",
    var category: Category = Category.Programming,
    var buttonText: String = "Create",
    var popular: Boolean = false,
    var main: Boolean = false,
    var sponsored: Boolean = false,
    var editorVisibility: Boolean = true,
    var messagePopup: Boolean = false,
    var linkPopup: Boolean = false,
    var imagePopup: Boolean = false
){
    fun reset() = this.copy(
        id = "",
        title = "",
        subtitle = "",
        thumbnail = "",
        content = "",
        category = Category.Programming,
        buttonText = "Create",
        main = false,
        popular = false,
        sponsored = false,
        editorVisibility = true,
        messagePopup = false,
        linkPopup = false,
        imagePopup = false
    )
}

@Page
@Composable
fun CreatePage() {
    isUserLoggedIn {
        CreateScreen()
    }
}

@Composable
fun CreateScreen() {
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    val context = rememberPageContext()
    var uiState by remember { mutableStateOf(CreatePageUiState()) }

    var hasPostIdParam = remember(key1 = context.route) {
        context.route.params.containsKey(POST_ID_PARAM)
    }

    LaunchedEffect(hasPostIdParam){
        if(hasPostIdParam){
            val postId = context.route.params[POST_ID_PARAM] ?: ""
            val response = fetchSelectedPost(id = postId)
            if(response is ApiResponse.Success){
                (document.getElementById(Id.editor) as HTMLTextAreaElement).value = response.data.content
                uiState = uiState.copy(
                    id = response.data.id,
                    title = response.data.title,
                    subtitle = response.data.subtitle,
                    thumbnail = response.data.thumbnail,
                    content = response.data.content,
                    buttonText = "Update",
                    main = response.data.main,
                    popular = response.data.popular,
                    sponsored = response.data.sponsored,
                    category = response.data.category
                )
            }
        }else {
            (document.getElementById(Id.editor) as HTMLTextAreaElement).value = ""
            uiState = uiState.reset()
        }
    }

    AdminPageLayout {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .margin(topBottom = 50.px)
                .padding(if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .maxWidth(700.px),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleGrid(
                    numColumns = numColumns(base = 1, sm = 3)
                ) {
                    Row(
                        modifier = Modifier
                            .margin(
                                right = if (breakpoint < Breakpoint.SM) 0.px else 24.px,
                                bottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = uiState.popular,
                            onCheckedChange = { uiState = uiState.copy(popular = it) },
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontSize(14.px)
                                .color(Theme.HalfBlack.rgb),
                            text = "Popular"
                        )
                    }

                    Row(
                        modifier = Modifier
                            .margin(
                                right = if (breakpoint < Breakpoint.SM) 0.px else 24.px,
                                bottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = uiState.main,
                            onCheckedChange = { uiState = uiState.copy(main = it) },
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontSize(14.px)
                                .color(Theme.HalfBlack.rgb),
                            text = "Main"
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = uiState.sponsored,
                            onCheckedChange = { uiState = uiState.copy(sponsored = it) },
                            size = SwitchSize.LG
                        )
                        SpanText(
                            modifier = Modifier
                                .fontFamily(FONT_FAMILY)
                                .fontSize(14.px)
                                .color(Theme.HalfBlack.rgb),
                            text = "Sponsored"
                        )
                    }
                }

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(Id.titleInput)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(topBottom = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Theme.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .noBorder()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .toAttrs {
                            attr("placeholder", "Title ")
                            attr("value", uiState.title)
                        }
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(Id.subtitleInput)
                        .fillMaxWidth()
                        .height(54.px)
                        //.margin(bottom  = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Theme.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .noBorder()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .toAttrs {
                            attr("placeholder", "Subtitle")
                            attr("value", uiState.subtitle)
                        }
                )

                //drop category
                CategoryDropdown(
                    selectedCategory = uiState.category,
                    onCategorySelected = { uiState = uiState.copy(category = it) }
                )

                //switch url
                Row(
                    modifier = Modifier.fillMaxWidth().margin(topBottom = 12.px),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Switch(
                        modifier = Modifier.margin(right = 8.px),
                        checked = !uiState.thumbnialInputDisabled,
                        onCheckedChange = { uiState = uiState.copy(thumbnialInputDisabled = !it) },
                        size = SwitchSize.MD
                    )
                    SpanText(
                        modifier = Modifier
                            .fontFamily(FONT_FAMILY)
                            .fontSize(14.px)
                            .color(Theme.HalfBlack.rgb),
                        text = "Paste an Image URL instead"
                    )
                }

                //thumbnail
                ThumbnailUploader(
                    thumbnail = uiState.thumbnail,
                    thumbnailInputDisabled = uiState.thumbnialInputDisabled,
                    onThumbnailSelect = { filename, file ->
                        (document.getElementById(Id.thumbnailInput) as HTMLInputElement).value =
                            filename
                        uiState = uiState.copy(thumbnail = file)
//                         println(filename)
//                         println(file)
                    }
                )

                //editor controls
                EditorControls(
                    breakpoint = breakpoint,
                    editorVisibility = uiState.editorVisibility,
                    onEditorVisibilityChange = {
                        uiState = uiState.copy(
                            editorVisibility = !uiState.editorVisibility
                        )
                    },
                    onLinkClicked = {
                        uiState = uiState.copy(linkPopup = true)
                    },
                    onImageClicked = {
                        uiState = uiState.copy(imagePopup = true)
                    }
                )
                Editor(editorVisibility = uiState.editorVisibility)

                //create button
                CreateButton(
                    text = uiState.buttonText,
                    onClick = {
                    //check input value
                    uiState =
                        uiState.copy(title = (document.getElementById(Id.titleInput) as HTMLInputElement).value)
                    uiState =
                        uiState.copy(subtitle = (document.getElementById(Id.subtitleInput) as HTMLInputElement).value)
                    uiState =
                        uiState.copy(content = (document.getElementById(Id.editor) as HTMLTextAreaElement).value)
                    if(!uiState.thumbnialInputDisabled){
                        uiState =
                            uiState.copy(thumbnail = (document.getElementById(Id.thumbnailInput) as HTMLInputElement).value)
                    }
                    if (
                        uiState.title.isNotEmpty() &&
                        uiState.subtitle.isNotEmpty() &&
                        uiState.thumbnail.isNotEmpty() &&
                        uiState.content.isNotEmpty()
                    ) {
                        scope.launch {
                            if (hasPostIdParam){
                                val result = updatePost(
                                    Post(
                                        id = uiState.id,
                                        title = uiState.title,
                                        subtitle = uiState.subtitle,
                                        thumbnail = uiState.thumbnail,
                                        content = uiState.content,
                                        category = uiState.category,
                                        popular = uiState.popular,
                                        main = uiState.main,
                                        sponsored = uiState.sponsored
                                    )
                                )
                                if (result){
                                    context.router.navigateTo(Screen.AdminSuccess.postUpdated())
                                }
                            }else{
                                val result = addPost(
                                    Post(
                                        author = localStorage.getItem("username").toString(),
                                        title = uiState.title,
                                        subtitle = uiState.subtitle,
                                        date = Date.now(),
                                        thumbnail = uiState.thumbnail,
                                        content = uiState.content,
                                        category = uiState.category,
                                        popular = uiState.popular,
                                        main = uiState.main,
                                        sponsored = uiState.sponsored
                                    )
                                )
                                if (result){
                                    context.router.navigateTo(Screen.AdminSuccess.route)
                                }
                            }
                        }
                    } else {
                        scope.launch {
                            uiState = uiState.copy(messagePopup = true)
                            delay(2000)
                            uiState = uiState.copy(messagePopup = false)
                        }
                    }
                })
            }
        }
    }

    if(uiState.messagePopup){
        MessagePopup(
            message = "Please fill all fields",
            onDialogDissmis = {uiState = uiState.copy(messagePopup = false)}
        )
    }

    if(uiState.linkPopup){
        LinkPopup(
            editorControl = EditorControl.Link,
            onDialogDismiss = {uiState = uiState.copy(linkPopup = false)},
            onAddClick = { href, title ->
                applyStyle(
                    ControlStyle.Link(
                        selectedText =  getSelectedText(),
                        href = href,
                        title = title
                    )
                )
            }
        )
    }

    if(uiState.imagePopup){
        LinkPopup(
            editorControl = EditorControl.Image,
            onDialogDismiss = {uiState = uiState.copy(imagePopup = false)},
            onAddClick = { imageUrl, desc ->
                applyStyle(
                    ControlStyle.Image(
                        selectedText =  getSelectedText(),
                        imageUrl = imageUrl,
                        desc = desc
                    )
                )
            }
        )
    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    Box(
        modifier = Modifier
            .classNames("dropdown")
            .fillMaxWidth()
            .margin(topBottom = 12.px)
            .height(54.px)
            .backgroundColor(Theme.LightGray.rgb)
            .cursor(Cursor.Pointer)
            .attrsModifier {
                attr("data-bs-toggle", "dropdown")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(leftRight = 20.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .fontSize(16.px)
                    .fontFamily(FONT_FAMILY),
                text = selectedCategory.name
            )
            Box(
                modifier = Modifier
                    .classNames("dropdown-toggle")
            )
        }

        Ul(
            attrs = Modifier
                .fillMaxWidth()
                .classNames("dropdown-menu")
                .toAttrs()
        ) {
            Category.values().forEach { category ->
                Li {
                    A(
                        attrs = Modifier
                            .classNames("dropdown-item")
                            .color(Colors.Black)
                            .fontFamily(FONT_FAMILY)
                            .fontSize(16.px)
                            .onClick { onCategorySelected(category) }
                            .toAttrs()
                    ) {
                        Text(value = category.name)
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailUploader(
    thumbnail: String,
    thumbnailInputDisabled: Boolean,
    onThumbnailSelect: (String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .margin(bottom = 20.px)
            .height(54.px)
    ) {
        Input(
            type = InputType.Text,
            attrs = Modifier
                .id(Id.thumbnailInput)
                .fillMaxSize()
                .margin(right = 12.px)
                .padding(leftRight = 20.px)
                .borderRadius(r = 4.px)
                .backgroundColor(Theme.LightGray.rgb)
                .noBorder()
                .fontSize(16.px)
                .fontFamily(FONT_FAMILY)
                .thenIf(
                    condition = thumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs {
                    attr("placeholder", "Thumbnail")
                    attr("value", thumbnail)
                }
        )
        //button
        Button(
            attrs = Modifier
                .onClick {
                    document.loadDataUrlFromDisk(
                        accept = "image/png, image/jpg",
                        onLoaded = {
                            onThumbnailSelect(filename, it)
                        }
                    )
                }
                .fillMaxHeight()
                .padding(leftRight = 24.px)
                .backgroundColor(if (!thumbnailInputDisabled) Theme.Gray.rgb else Theme.Primary.rgb)
                .color(if (!thumbnailInputDisabled) Theme.DarkGray.rgb else Colors.White)
                .noBorder()
                .borderRadius(r = 4.px)
                .fontSize(14.px)
                .fontFamily(FONT_FAMILY)
                .fontWeight(FontWeight.Medium)
                .thenIf(
                    condition = !thumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs()
        ) {
            SpanText(
                text = "Upload"
            )
        }
    }
}

@Composable
fun EditorControls(
    breakpoint: Breakpoint,
    editorVisibility: Boolean,
    onLinkClicked: () -> Unit,
    onImageClicked: () -> Unit,
    onEditorVisibilityChange: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SimpleGrid(
            numColumns = numColumns(base = 1, sm = 2)
        ) {
            Row(
                modifier = Modifier
                    .backgroundColor(Theme.LightGray.rgb)
                    .borderRadius(r = 4.px)
                    .height(54.px)
            ) {
                EditorControl.values().forEach {
                    EditorControlView(
                        control = it,
                        onClick = {
                            applyControlStyle(
                                editorControl = it,
                                onLinkClicked = onLinkClicked,
                                onImageClicked = onImageClicked
                            )
                        })
                }
            }

            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    attrs = Modifier
                        .height(54.px)
                        .thenIf(
                            condition = breakpoint < Breakpoint.SM,
                            other = Modifier.fillMaxWidth()
                        )
                        .margin(topBottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px)
                        .padding(leftRight = 24.px)
                        .borderRadius(r = 4.px)
                        .backgroundColor(
                            if (editorVisibility) Theme.LightGray.rgb
                            else Theme.Primary.rgb
                        )
                        .color(
                            if (editorVisibility) Theme.DarkGray.rgb
                            else Colors.White
                        )
                        .noBorder()
                        .onClick {
                            onEditorVisibilityChange()
                            document.getElementById(Id.editorPreview)?.innerHTML = getEditor().value
                            js("hljs.highlightAll()") as Unit
                        }
                        .toAttrs()
                ) {
                    SpanText(
                        modifier = Modifier
                            .fontSize(14.px)
                            .fontWeight(FontWeight.Medium)
                            .fontFamily(FONT_FAMILY),
                        text = "Preview"
                    )
                }
            }
        }
    }
}

@Composable
fun EditorControlView(
    control: EditorControl,
    onClick: () -> Unit
) {
    Box(
        modifier = EditorKeyStyle.toModifier()
            .fillMaxHeight()
            .padding(leftRight = 12.px)
            .borderRadius(r = 4.px)
            .cursor(Cursor.Pointer)
            .onClick { onClick()},
        contentAlignment = Alignment.Center
    ) {
        Image(
            src = control.icon,
            desc = "${control.icon} Icon "
        )
    }
}

@Composable
fun Editor(editorVisibility: Boolean) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextArea(
            attrs = Modifier
                .id(Id.editor)
                .fillMaxWidth()
                .height(400.px)
                .maxHeight(400.px)
                .resize(Resize.None)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(Theme.LightGray.rgb)
                .borderRadius(r = 4.px)
                .onKeyDown {
                    if(it.code == "Enter" && it.shiftKey){
                        applyStyle(
                            controlStyle = ControlStyle.Break(
                                selectedText = getSelectedText()
                            )
                        )
                    }
                }
                .noBorder()
                .visibility(
                    if (editorVisibility) Visibility.Visible else Visibility.Hidden
                )
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .toAttrs {
                    attr("placeholder", "Type here ...")
                }
        )

        Div(
            attrs = Modifier
                .id(Id.editorPreview)
                .fillMaxWidth()
                .maxHeight(400.px)
                .height(400.px)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(Theme.LightGray.rgb)
                .borderRadius(r = 4.px)
                .visibility(
                    if (editorVisibility) Visibility.Hidden
                    else Visibility.Visible
                )
                .noBorder()
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .toAttrs()
        ) {

        }
    }
}

@Composable
fun CreateButton(
    text: String,
    onClick: () -> Unit) {
    Button(
        attrs = Modifier
            .fillMaxWidth()
            .height(54.px)
            .margin(top = 12.px)
            .backgroundColor(Theme.Primary.rgb)
            .color(Colors.White)
            .fontSize(16.px)
            .fontFamily(FONT_FAMILY)
            .borderRadius(r = 4.px)
            .onClick { onClick() }
            .noBorder()
            .toAttrs()
    ) {
        SpanText(
            text = text
        )
    }
}