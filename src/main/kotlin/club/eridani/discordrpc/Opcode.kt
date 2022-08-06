package club.eridani.discordrpc

enum class Opcode {
    Handshake,
    Frame,
    Close,
    Ping,
    Pong
}