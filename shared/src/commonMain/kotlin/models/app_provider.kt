package models

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

class AppDataProvider {
    @OptIn(ExperimentalResourceApi::class)
    suspend fun getAppDataFromFile(name: String): AppData {
        try {
            val fileBytes = resource(name).readBytes()
            val jsonString = fileBytes.decodeToString()
            return Json.decodeFromString<AppData>(jsonString)
        } catch (ex: Exception) {
            throw ex
        }
    }

}