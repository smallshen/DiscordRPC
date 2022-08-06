@file:OptIn(ExperimentalSerializationApi::class)

package club.eridani.discordrpc

import club.eridani.discordrpc.connection.Connection
import club.eridani.discordrpc.packet.ActivityPacket
import club.eridani.discordrpc.packet.HandshakePacket
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

private val defaultJson = Json {
    encodeDefaults = true
    explicitNulls = false
}


class DiscordIPC(
    private val appId: String,
    var richPresence: RichPresence = RichPresence(),

    val json: Json = defaultJson,
) {

    lateinit var connection: Connection
        private set
    var currentUser: DiscordUser? = null
        private set

    fun start() {
        connection = Connection.open(json, ::onPacket)
        connection.writePacket(Opcode.Handshake, json.encodeToString(HandshakePacket(appId)))
    }

    fun onPacket(packet: Packet) {
        println(packet.data)
        when (packet.opcode) {
            Opcode.Close -> {
                close()
            }

            Opcode.Frame -> processFrame(packet.data)
            else -> {
                // ignored
            }
        }
    }

    private fun close() {
        connection.close()
    }


    private fun processFrame(data: JsonObject) {

        if (data.containsKey("evt") && data["evt"]!!.jsonPrimitive.contentOrNull == "ERROR") {
            return
        }

        if (data.containsKey("cmd") && data["cmd"]!!.jsonPrimitive.contentOrNull == "DISPATCH") {
            currentUser = json.decodeFromJsonElement(data["data"]!!.jsonObject["user"]!!)
            updateRPC()
        }


    }

    fun updateRPC() {
        connection.writePacket(Opcode.Frame, json.encodeToString(ActivityPacket(richPresence)))
    }
}