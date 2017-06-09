package org.http4k.koans.student

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.by
import org.http4k.routing.path
import org.http4k.routing.routes

object ReferenceStudent {
    operator fun invoke() = routes("/echo/{message}" to GET by {
        response: Request ->
        Response(OK).body(response.path("message")!!)
    })
}