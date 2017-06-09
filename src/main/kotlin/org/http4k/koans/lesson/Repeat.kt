package org.http4k.koans.lesson

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.koans.studentServer
import org.http4k.routing.by
import org.http4k.routing.routes
import java.util.*

object Repeat {

    operator fun invoke(student: HttpHandler, random: Random = Random()) =
        routes("/run" to Method.GET by { request: Request ->
            val times = random.nextInt(5)
            val message = UUID.randomUUID().toString()
            val studentResponse = student(Request(Method.GET, request.studentServer().path("/repeat/$times/$message")))
            val expected = (0..times - 1).map { message }.joinToString("\n")
            if (studentResponse.status.successful && studentResponse.bodyString() == expected) {
                Response(Status.OK)
            } else {
                Response(Status.INTERNAL_SERVER_ERROR).body("expected: \n$expected")
            }
        })
}