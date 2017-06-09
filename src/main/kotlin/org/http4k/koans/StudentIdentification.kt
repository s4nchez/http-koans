package org.http4k.koans

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri

object StudentIdentification : Filter {
    override fun invoke(next: HttpHandler): HttpHandler =
        {
            request ->
            if (request.header("referer").isNullOrBlank()) {
                Response(Status.UNAUTHORIZED.description("Identify your server by sending 'Referer' header (e.g. 'Referer: http://localhost:8080')"))
            } else {
                next(request)
            }
        }
}

fun Request.studentServer(): Uri = header("referer")?.let { Uri.of(it).copy(path = "") } ?: throw IllegalStateException("Student server is unknown")

