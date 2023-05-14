import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import models.AppData
import models.AppDataProvider


object Utils {
    private val appDataProvider = AppDataProvider()
    suspend fun getAppData(fileName: String): AppData {
        return appDataProvider.getAppDataFromFile(fileName)
    }

    fun getClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    prettyPrint = true

                })
            }
        }
    }

    suspend fun loadImageFromURL(client: HttpClient, url: String): ImageResponse {
        var result: ImageResponse = ImageResponse.Loading

        result = try {
            val response = client.get(url).readBytes()
            if (response.isNotEmpty()) {
                ImageResponse.Success(response)
            } else {
                ImageResponse.Error(exception = Exception("The response is empty.Check your internet"))
            }
        } catch (ex: Exception) {
            ImageResponse.Error(ex)
        }

        return result
    }
}


sealed interface ImageResponse {
    object Loading : ImageResponse
    class Error(val exception: Exception) : ImageResponse
    class Success(val data: ByteArray) : ImageResponse
}



