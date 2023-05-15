import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


actual fun getPlatformName(): String = "Desktop"

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
            Text("Error ${(imageResponse as ImageResponse.Error).exception.message}")
        }

        is ImageResponse.Success -> {
            Image(
                bitmap = org.jetbrains.skia.Image.makeFromEncoded((imageResponse as ImageResponse.Success).data)
                    .toComposeImageBitmap(),
                contentDescription = null,
                modifier = modifier.height(200.dp),
                contentScale = ContentScale.Fit
            )
        }

        else -> {

        }
    }

}

@Composable
fun MainView() = App()

@Preview
@Composable
fun AppPreview() {
    App()
}