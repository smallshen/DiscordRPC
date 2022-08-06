package club.eridani.discordrpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscordUser(
    val id: String,
    val username: String,
    val discriminator: String,
    val avatar: String,
    val bot: Boolean,
    val flags: Int,
    val avatar_decoration: String? = null,
    @SerialName("premium_type")
    val premiumType: Int = 0
)