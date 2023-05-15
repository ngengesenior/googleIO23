package ui

import UrlImage
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import conditional
import models.Speaker

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SpeakerUIItem(speaker: Speaker, modifier: Modifier = Modifier) {
    var active: Boolean by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = modifier.widthIn(150.dp, 180.dp)
            .conditional(active) {
                border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
            }
            /*.onPointerEvent(PointerEventType.Enter) {
                active = true
            }.onPointerEvent(PointerEventType.Exit) {
                active = false
            }*/,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            UrlImage(
                url = speaker.image,
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(100))
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                speaker.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,

                )
            Spacer(Modifier.height(2.dp))
            Text(
                speaker.role, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Text(
                speaker.pronoun, fontWeight = FontWeight.Medium,
            )
        }
    }

}