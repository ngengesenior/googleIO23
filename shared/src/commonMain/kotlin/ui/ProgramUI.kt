package ui

import UrlImage
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import conditional
import models.Session

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ProgramUIItem(session: Session, modifier: Modifier = Modifier) {
    var active: Boolean by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(horizontal = 2.dp)
        .conditional(active) {
            border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
        }
        .onPointerEvent(PointerEventType.Enter) {
            active = true
        }.onPointerEvent(PointerEventType.Exit) {
            active = false
        }
    ) {
        UrlImage(session.profileImage, modifier = modifier)
        Spacer(Modifier.height(4.dp))
        Text(
            session.title, style = MaterialTheme.typography.h6,
            modifier = Modifier.width(200.dp)
        )
        Spacer(Modifier.height(2.dp))
        Text(session.shortDescription)
        if (session.tags.isNotEmpty()) {
            LazyRow {
                items(items = session.tags.split(",")) { tag ->
                    Chip(onClick = {}) {
                        Text(tag)
                    }
                    Spacer(modifier = Modifier.width(1.5.dp))
                }
            }
        }
    }
}

