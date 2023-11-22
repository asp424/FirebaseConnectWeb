package dev.test.ru.models

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingLeft
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.B
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Small
import org.jetbrains.compose.web.dom.Text

@Composable
fun UserCard(
    userModel: UserModel, onClick: () -> Unit
) {
    with(userModel) {
        Div({
            style {
                paddingBottom(5.px)
            }
        }) {
            Box(
                modifier = Modifier.fillMaxSize().background(White)
                    .border(1.px, LineStyle.Dashed, Black)
                    .borderRadius(10.px).onClick {
                        onClick()
                    }
                    .width(230.px).height(50.px).padding(left = 10.px), contentAlignment = Alignment.CenterStart
            ) {
                Image(iconUri, width = 35, height = 35,
                    modifier = Modifier.border(1.px, LineStyle.Outset, color = Black).borderRadius(30.px)
                )
                P({
                    style {
                        paddingBottom(15.px)
                        paddingLeft(50.px)
                    }
                }) {
                    B({

                    }) {
                        Text(name.ifEmpty { id })
                    }
                }
                P({
                    style {
                        paddingTop(15.px)
                        paddingLeft(50.px)

                    }
                }) {
                    Small({

                    }) {
                        SpanText(if (onLine != "0") "в сети" else "не в сети")
                    }

                }
            }
        }
    }
}