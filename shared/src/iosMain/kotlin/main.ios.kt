import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.coroutines.launch
import org.jetbrains.skia.Image

actual fun getPlatformName(): String = "iOS"

@Composable
actual fun UrlImage(url: String, modifier: Modifier) {
    val scope = rememberCoroutineScope()
    var imageResponse: ImageResponse? by remember { mutableStateOf(null) }

    LaunchedEffect(true) {

        scope.launch {
            imageResponse = Utils.loadImageFromURL(Utils.getClient(), url)
        }
    }

    when (imageResponse) {
        is ImageResponse.Loading -> {
            CircularProgressIndicator()
        }

        is ImageResponse.Error -> {
            Text("Error loading image ${(imageResponse as ImageResponse.Error).exception.message}")
        }

        is ImageResponse.Success -> {
            Image(
                bitmap = Image.makeFromEncoded((imageResponse as ImageResponse.Success).data)
                    .toComposeImageBitmap(), contentDescription = null,
                modifier = modifier.fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(25))
            )
        }

        else -> {

        }
    }


}

fun MainViewController() = ComposeUIViewController { App() }