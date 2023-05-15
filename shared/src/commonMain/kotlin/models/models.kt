package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val name: String,
    val image: String,
    val role: String,
    @SerialName("profile_url") val profileUrl: String,
    val pronoun: String = ""
)

@Serializable
data class Session(
    @SerialName("profile_image") val profileImage: String,
    val title: String,
    val description: String,
    @SerialName("short_description") val shortDescription: String,
    val tags: String,
    val url: String,
    val speakers: List<Speaker>,
    @SerialName("following_session") val followingSession: String
)


@Serializable
class AppData(
    val speakers: List<Speaker>,
    val programs: List<Session>
)


