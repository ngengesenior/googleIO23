import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import models.AppData
import models.Session
import models.Speaker
import org.lighthousegames.logging.KmLog
import ui.ProgramUIItem
import ui.SpeakerUIItem

val logger = KmLog("com.ngengeapps.io")

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
                isError = false

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
                    CompactNavigation(appData!!)
                } else {
                    MediumNavigationAndAbove(appData!!, maxWidth)
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


@Composable
fun MediumNavigationAndAbove(appData: AppData, maxWidth: Dp) {
    var currentIndex: Int by rememberSaveable { mutableStateOf(0) }
    Row {
        NavigationRail {
            NavigationRailItem(selected = currentIndex == 0, onClick = {
                if (currentIndex != 0) {
                    currentIndex = 0
                }
            }, icon = {
                Icon(Icons.Filled.Person, null)
            }, label = { Text("Speakers") })

            NavigationRailItem(selected = currentIndex == 1, onClick = {
                if (currentIndex != 1) {
                    currentIndex = 1
                }
            }, icon = {
                Icon(Icons.Filled.Person, null)
            }, label = { Text("Sessions") })

        }
        if (currentIndex == 1) {
            if (maxWidth <= 840.dp) {
                GridSessions(sessions = appData.programs, numCells = 2)
            } else if (maxWidth > 840.dp) {
                GridSessions(sessions = appData.programs, numCells = 3)
            }
        } else if (currentIndex == 0) {
            if (maxWidth <= 840.dp) {
                GridSpeakers(appData.speakers, numCells = 2)
            } else if (maxWidth > 840.dp) {
                GridSpeakers(appData.speakers, 3)
            }
        }


    }
}

@Composable
fun CompactNavigation(appData: AppData) {
    var currentIndex: Int by rememberSaveable { mutableStateOf(0) }
    var title: String by rememberSaveable { mutableStateOf("IO'23 Speakers") }
    Scaffold(bottomBar = {
        BottomAppBar {

            BottomNavigationItem(selected = currentIndex == 0, icon = {
                Icon(Icons.Filled.Person, null)
            }, onClick = {
                if (currentIndex != 0) {
                    currentIndex = 0
                    title = "IO'23 Speakers"
                }
            })

            BottomNavigationItem(selected = currentIndex == 1, icon = {
                Icon(Icons.Filled.Info, null)
            }, onClick = {
                if (currentIndex != 1) {
                    currentIndex = 1
                    title = "IO'23 Sessions"
                }
            })
        }
    }, topBar = {
        TopAppBar {
            Text(title)
        }
    }) {
        if (currentIndex == 0) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = appData.speakers) { speaker ->
                    SpeakerUIItem(speaker)
                }
            }
        } else if (currentIndex == 1) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = appData.programs) { session ->
                    ProgramUIItem(session)
                }
            }
        }


    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridSessions(sessions: List<Session>, numCells: Int) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(numCells),
        verticalItemSpacing = 3.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(sessions) { session ->
            ProgramUIItem(session)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridSpeakers(speakers: List<Speaker>, numCells: Int) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(numCells),
        verticalItemSpacing = 3.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(speakers) { speaker ->
            SpeakerUIItem(speaker)
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