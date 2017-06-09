package org.http4k.koans

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.http4k.core.then
import org.http4k.routing.by
import org.http4k.routing.routes

object HttpKoans {

    val studentId = Filter { next ->
        {
            request ->
            if (request.header("referer").isNullOrBlank()) {
                Response(UNAUTHORIZED.description("Identify your server by sending 'Referer' header (e.g. 'Referer: http://localhost:8080')"))
            } else {
                next(request)
            }
        }
    }

    operator fun invoke(): HttpHandler = studentId.then(routes("/" to GET by { Response(OK) }))
}