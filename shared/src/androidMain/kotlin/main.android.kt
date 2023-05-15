import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

actual fun getPlatformName(): String = "Android"

@Composable
actual fun UrlImage(url: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = url,
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier.fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun MainView() = App()
