package org.http4k.koans.student

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.Path
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

object ReferenceStudent {
    operator fun invoke() = routes(
        "/echo/{message}" to GET bind { request: Request -> Response(OK).body(request.path("message")!!) },
        "/repeat/{times}/{message}" to GET bind { request: Request ->
            val timesLens = Path.int().of("times")
            val message = request.path("message")!!
            val times = timesLens.extract(request)
            val body = (0..times - 1).map { message }.joinToString("\n")
            Response(OK).body(body)

    })
}