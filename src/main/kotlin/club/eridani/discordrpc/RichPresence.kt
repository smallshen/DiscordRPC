@file:Suppress("ArrayInDataClass")

package club.eridani.discordrpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RichPresence(
    var state: String? = null,
    var details: String? = null,
    var timestamps: Timestamp? = null,
    var assets: Assets? = null,
    var party: Party? = null,
    var secrets: Secrets? = null,
    var instance: Boolean = true
) {
    inline fun timestamps(block: Timestamp.() -> Unit) {
        timestamps = (timestamps ?: Timestamp()).apply(block)
    }

    inline fun assets(block: Assets.() -> Unit) {
        assets = (assets ?: Assets()).apply(block)
    }

    inline fun party(id: String, block: Party.() -> Unit) {
        party = (party ?: Party(id)).apply(block)
    }


    inline fun secrets(block: Secrets.() -> Unit) {
        secrets = (secrets ?: Secrets()).apply(block)
    }

}

@Serializable
data class Timestamp(
    var start: Long? = null,
    var end: Long? = null
)

@Serializable
data class Assets(
    @SerialName("large_image")
    var largeImage: String = "null",
    @SerialName("large_text")
    var largeText: String = "null",
    @SerialName("small_image")
    var smallImage: String = "null",
    @SerialName("small_text")
    var smallText: String = "null"
)

@Serializable
data class Party(
    var id: String,
    var size: IntArray? = null
) {
    operator fun Int.contains(max: Int): Boolean {
        size = intArrayOf(max, this)
        return true
    }
}

@Serializable
data class Secrets(
    var join: String? = null,
    var spectate: String? = null,
    var match: String? = null
)

