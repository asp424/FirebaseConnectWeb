package dev.test.ru.ui.cells

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.graphics.Colors.CadetBlue
import com.varabyte.kobweb.compose.ui.graphics.Colors.LightSeaGreen
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.test.ru.ui.states.UIStates
import dev.test.ru.ui.states.UIStates.screenHeight
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Small
import org.jetbrains.compose.web.dom.Text

@Composable
fun ChatBox(isExpandedMain: Boolean, onEndAnim: () -> Unit, onInvisibleChatBox: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
            .height(
                animateIntAsState(
                    if (isExpandedMain) screenHeight - 41 else 0, tween(500)
                ) {
                    if (it == 0) onInvisibleChatBox() else onEndAnim()
                }.value.px
            )
            .background(White)
            .border(1.px, LineStyle.Groove, Black)
            .borderRadius(bottomRight = 10.px, bottomLeft = 10.px),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(left = 1.px),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {

            Column(
                Modifier.width(600.px).height(500.px),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                UIStates.chatMessages.value.forEach {
                    Box(
                        Modifier.fillMaxWidth()
                            .padding(left = 30.px, right = 30.px).margin(bottom = 5.px),
                        contentAlignment = it.alignment
                    ) {
                        Row(
                            Modifier.background(CadetBlue).padding(
                                left = 10.px, right = 10.px, top = 5.px, bottom = 5.px
                            ).borderRadius(it.alignment, 20.px).maxWidth(350.px)
                        ) {
                            Text(it.text)
                            Box(Modifier.padding(left = 8.px).scale(0.8).margin(top = 8.px)) {
                                Small {
                                    Text("00:00")
                                }
                            }
                        }
                    }
                }
            }
            InputBar()
        }
    }
}

fun Modifier.borderRadius(alignment: Alignment, radius: CSSNumeric) =
    if (alignment == Alignment.CenterStart)
        borderRadius(topRight = radius, bottomRight = radius, bottomLeft = radius)
    else borderRadius(topRight = radius, bottomLeft = radius, topLeft = radius)

