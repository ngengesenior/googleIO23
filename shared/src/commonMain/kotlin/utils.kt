import models.AppData
import models.AppDataProvider

object Utils {
    private val appDataProvider = AppDataProvider()
    suspend fun getAppData(fileName:String):AppData {
        return appDataProvider.getAppDataFromFile(fileName)
    }
}