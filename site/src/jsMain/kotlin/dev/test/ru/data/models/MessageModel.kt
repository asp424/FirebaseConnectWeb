package dev.test.ru.data.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lm.firebaseconnect.models.TypeMessage
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors.Gray
import com.varabyte.kobweb.compose.ui.graphics.Colors.LightGray

data class MessageModel(
    val key: String = "",
    val text: String = "",
    val alignment: Alignment = Alignment.CenterEnd,
    val type: TypeMessage = TypeMessage.MESSAGE,
    val time: String = "",
    val date: String = "",
    val timeStamp: String = "",
    var voiceTimeStamp: String = "",
    val name: String = "",
    val wasRead: Dp = 0.dp,
    val wasReadColor: Color = Gray,
    val digit: String = "",
    val mustSetWasRead: Boolean = false,
    val isNewDate: Boolean = false,
    var isUnreadFlag: Boolean = false,
    var topStartShape: Dp = 0.dp,
    var bottomEndShape: Dp = 0.dp,
    var isReply: Boolean = false,
    var replyName: String = "",
    var replyText: String = "",
    var replyKey: String = "",
    var isNew: Boolean = false,
    var imageMessage: ImageModel = ImageModel(),
)
