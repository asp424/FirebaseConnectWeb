package dev.test.ru.ui.cells

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.test.ru.data.models.UserModel
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun UserCard(
    userModel: UserModel, width: CSSNumeric, onClick: () -> Unit
) {
    with(userModel) {

            Box(
                modifier = Modifier.fillMaxSize().background(White)
                    .border(1.px, LineStyle.Outset, Black)
                    .borderRadius(10.px).onClick {
                        onClick()
                    }
                    .width(width).height(((screenHeight - 37) / 11).px),
                contentAlignment = Alignment.CenterStart
            ) {
                Image(
                    iconUri, width = 35, height = 35,
                    modifier = Modifier.border(1.px, LineStyle.Outset, color = Black)
                        .borderRadius(30.px).margin(left = 7.px)
                )
                P({
                    style {
                        paddingBottom(15.px)
                        paddingLeft(50.px)
                    }
                }) {
                    B({

                    }) {
                        Box(modifier = Modifier.width(width)) {
                            Text(name.ifEmpty { id })
                        }
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
                        SpanText(lastMessage)
                    }
                }
            }
        }
    }
