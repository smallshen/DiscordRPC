package club.eridani.discordrpc


inline fun richPresence(block: RichPresence.() -> Unit): RichPresence {
    return RichPresence().apply(block)
}