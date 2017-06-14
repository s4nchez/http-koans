package org.http4k.koans.lesson

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import java.util.*

object Echo : LessonSpec {
    override val name: String = "Echo"

    override fun generateTest(): Pair<Request, (Response) -> Unit> {
        val message = UUID.randomUUID().toString()

        return Request(GET, "/echo/$message") to { studentResponse ->
            studentResponse.bodyString().shouldMatch(equalTo(message))
        }
    }
}
