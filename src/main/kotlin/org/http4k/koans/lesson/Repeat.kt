package org.http4k.koans.lesson

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import java.util.*

object Repeat : LessonSpec {
    override val name: String = "Repeat"

    val random: Random = Random()

    override fun generateTest(): Pair<Request, (Response) -> Unit> {
        val times = random.nextInt(4) + 1
        val message = UUID.randomUUID().toString()
        return Request(Method.GET, "/repeat/$times/$message") to { response: Response ->
            val expected = (0..times - 1).map { message }.joinToString("\n")
            response.bodyString().shouldMatch(equalTo(expected))
        }
    }


}