package club.eridani.discordrpc.packet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class HandshakePacket(
    val v: Int = 1,
    @SerialName("client_id")
    val clientId: String,
    val nonce: String = UUID.randomUUID().toString()
) {
    constructor(clientId: String) : this(1, clientId)
}