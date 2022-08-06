package club.eridani.discordrpc.packet

import club.eridani.discordrpc.RichPresence
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ActivityPacket(
    val cmd: String = "SET_ACTIVITY",
    val args: ActivityArgs,
    val nonce: String = UUID.randomUUID().toString(),
) {
    constructor(richPresence: RichPresence) : this(args = ActivityArgs(activity = richPresence))
}

@Serializable
data class ActivityArgs(
    val pid: Long = ProcessHandle.current().pid(),
    val activity: RichPresence
)