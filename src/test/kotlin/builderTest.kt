import club.eridani.discordrpc.DiscordIPC
import club.eridani.discordrpc.richPresence
import java.io.File
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds


fun main() {
    val (appId, startTime) = File("run/testData.txt").readLines()

    val rpc = richPresence {
        state = "Checking proxies"
        details = "I love proxies"

        timestamps {
            start = ((startTime.toLong()).milliseconds - 3.hours).inWholeMilliseconds
        }

        assets {
            largeImage = "main"
            largeText = "Proxy Scraper"
        }


    }

    val ipc = DiscordIPC(appId, rpc)
    ipc.start()
    while (true) {

    }

}