package club.eridani.discordrpc

import kotlinx.serialization.json.JsonObject

data class Packet(val opcode: Opcode, val data: JsonObject)