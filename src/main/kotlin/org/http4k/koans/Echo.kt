package org.http4k.koans

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.INTERNAL_SERVER_ERROR
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.by
import org.http4k.routing.routes
import java.util.*

object Echo {
    operator fun invoke(student: HttpHandler) =
        routes("/run" to GET by { request: Request ->
            val message = UUID.randomUUID().toString()
            val studentResponse = student(Request(GET, request.studentServer().path("/echo/$message")))
            if (studentResponse.status.successful && studentResponse.bodyString() == message) {
                Response(OK)
            } else {
                Response(INTERNAL_SERVER_ERROR)
            }
        })
}
