package org.http4k.koans.student

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.by
import org.http4k.routing.path
import org.http4k.routing.routes

object ReferenceStudent {
    operator fun invoke() = routes("/echo/{message}" to Method.GET by { r: Request -> Response(Status.OK).body(r.path("message")!!) })
}