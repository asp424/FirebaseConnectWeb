package dev.test.ru.ui.screens

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import org.jetbrains.compose.web.dom.Progress

@Composable
fun progressScreen() = Box(Modifier.fillMaxSize(), Alignment.Center) { Progress { } }
