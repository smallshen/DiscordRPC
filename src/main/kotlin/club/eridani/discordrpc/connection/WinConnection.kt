package club.eridani.discordrpc.connection

import club.eridani.discordrpc.Opcode
import club.eridani.discordrpc.Packet
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.RandomAccessFile
import kotlin.concurrent.thread


class WinConnection(
    name: String,
    private val json: Json = Json,
    val callback: (Packet) -> Unit
) : Connection() {

    private val raf = RandomAccessFile(name, "rw")

    init {
        thread {
            run()
        }
    }

    private fun run() {
        try {
            while (true) {
                val opcode = Opcode.values()[Integer.reverseBytes(readInt())]
                val data = readString()
                callback(Packet(opcode, Json.decodeFromString(data)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun write(p: ByteArray) {
        try {
            raf.write(p)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readInt(): Int {
        receiveData(4)
        return raf.readInt()
    }

    private fun readString(): String {
        val len = Integer.reverseBytes(readInt())
        receiveData(len)
        val b = ByteArray(len)
        raf.read(b)
        return b.decodeToString()
    }

    private fun receiveData(size: Int) {
        while (raf.length() < size) {
            Thread.onSpinWait()
        }
    }

    override fun close() {
        try {
            raf.close()
        } catch (_: IOException) {

        }
    }

}