package com.poulastaa.mflix.core.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Stable
@Composable
fun animateText(text: String): String {
    if (text.isEmpty()) return ""

    var tempStr by remember { mutableStateOf(text) }

    LaunchedEffect(text) {
        text.indices.forEach { index ->
            val targetChar = text[index]
            val startChar = (targetChar.code - 10).coerceAtLeast(32).toChar()

            launch {
                try {
                    for (charCode in startChar.code..targetChar.code) {
                        delay(Random.nextInt(30, 50).toLong())
                        tempStr =
                            tempStr.replaceRange(index, index + 1, charCode.toChar().toString())
                    }
                } catch (_: Exception) {
                    tempStr = text
                    return@launch
                }
            }
        }
    }

    return tempStr
}

@Preview
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            val text = animateText("Female")

            Text(
                text = text,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}