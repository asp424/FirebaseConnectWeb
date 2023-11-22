import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors.Black
import com.varabyte.kobweb.compose.ui.graphics.Colors.CadetBlue
import com.varabyte.kobweb.compose.ui.graphics.Colors.LightGrey
import com.varabyte.kobweb.compose.ui.graphics.Colors.White
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.icons.ArrowForwardIcon
import dev.test.ru.models.UserCard
import dev.test.ru.states.UIStates.firebaseData
import org.jetbrains.compose.web.css.LineStyle.Companion.Groove
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput

@Composable
fun MainScreen(onClick: () -> Unit = {}) {
    var textMessage by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize().background(LightGrey).padding(left = 290.px))

    Box(
        modifier = Modifier.fillMaxWidth().height(33.px).background(CadetBlue),
        contentAlignment = Alignment.Center
    ) { H1({ style { color(White) } }) { Text("Kilogram") } }

    Column(modifier = Modifier.padding(top = 40.px, left = 10.px)) {
        firebaseData.value.forEach { if (it.id != "chats") UserCard(it, onClick) }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(top = 40.px, left = 250.px),
        contentAlignment = Alignment.TopStart
    ) {

        Box(
            modifier = Modifier.width(850.px)
                .height(570.px)
                .background(White)
                .border(1.px, Groove, Black)
                .borderRadius(10.px), contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier.height(40.px).fillMaxWidth()
                    .borderRadius(topLeft = 9.px, topRight = 9.px)
                    .background(CadetBlue),
                contentAlignment = Alignment.Center
            )
            Box(modifier = Modifier.fillMaxSize().padding(bottom = 30.px), contentAlignment = Alignment.BottomCenter) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextInput(textMessage) {
                        style {
                            width(425.px)
                            height(30.px)
                        }
                        onInput { textMessage = it.value }
                    }
                    ArrowForwardIcon(modifier = Modifier.size(40.px).onClick {

                    })
                }
            }
        }
    }
}