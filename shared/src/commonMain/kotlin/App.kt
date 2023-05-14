import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.unit.dp
import models.AppData
import org.lighthousegames.logging.KmLog
import ui.ProgramUIItem

val logger = KmLog("com.ngengeapps.io")

@OptIn(ExperimentalFoundationApi::class)
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
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                if (maxWidth <= 600.dp) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {

                        stickyHeader {
                            TopAppBar {
                                Text("IO 2023 Sessions")
                            }
                        }
                        itemsIndexed(items = appData!!.programs) { index, item ->
                            ProgramUIItem(item)
                        }
                    }
                } else if (maxWidth <= 840.dp) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(count = appData!!.programs.size) { index ->
                            ProgramUIItem(appData!!.programs[index])
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(count = appData!!.programs.size) { index ->
                            ProgramUIItem(appData!!.programs[index])
                        }
                    }
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


fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

expect fun getPlatformName(): String

@Composable
expect fun UrlImage(url: String, modifier: Modifier = Modifier)