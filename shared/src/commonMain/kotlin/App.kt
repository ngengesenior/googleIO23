import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import models.AppData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.lighthousegames.logging.KmLog

val logger = KmLog("com.ngengeapps.io")

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    MaterialTheme {
        var appData: AppData? by rememberSaveable {
            mutableStateOf(null)
        }

        var isError: Boolean by rememberSaveable { mutableStateOf(false) }
        val file by remember { mutableStateOf("io_2023.json") }

        LaunchedEffect(file) {

            try {

                appData = Utils.getAppData(file)
                logger.i {
                    "The data is $appData"
                }

            } catch (ex: Exception) {
                logger.e {
                    "There was an exception $ex"
                }
                isError = true
            }

        }
        if (!isError && appData != null) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                stickyHeader {
                    TopAppBar {
                        Text("IO 2023 Sessions")
                    }
                }
                itemsIndexed(items = appData!!.programs) { index, item ->
                    Text(item.title)
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

expect fun getPlatformName(): String