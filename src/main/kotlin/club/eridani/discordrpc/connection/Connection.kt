package club.eridani.discordrpc.connection

import club.eridani.discordrpc.Opcode
import club.eridani.discordrpc.Packet
import kotlinx.serialization.json.Json
import java.nio.ByteBuffer

private val unixTempPath = arrayOf("XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP")


abstract class Connection : AutoCloseable {

    companion object {
        fun open(json: Json, packetHandler: (Packet) -> Unit): Connection {
            repeat(10) { i ->
                return WinConnection("\\\\?\\pipe\\discord-ipc-$i", json, packetHandler)
            }

            error("Could not open a connection to Discord")
        }
    }

    fun writePacket(opcode: Opcode, data: String) {
        val d = data.encodeToByteArray()
        val packet = ByteBuffer.allocate(d.size + 8)
        packet.putInt(Integer.reverseBytes(opcode.ordinal))
        packet.putInt(Integer.reverseBytes(d.size))
        packet.put(d)
        packet.rewind()
        write(packet.array())
        packet.clear()
    }

    abstract fun write(p: ByteArray)

    abstract override fun close()
}