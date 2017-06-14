package org.http4k.koans

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.core.cookie.cookie
import org.http4k.koans.StudentIdentification.cookieName

object StudentIdentification : Filter {
    override fun invoke(next: HttpHandler): HttpHandler =
        {
            request ->
            if (request.cookie(cookieName) == null) {
                Response(Status.UNAUTHORIZED.description("Identify your server by sending 'Referer' header (e.g. 'Referer: http://localhost:8080')"))
            } else {
                next(request)
            }
        }

    val cookieName = "student-server"
}

fun Request.studentServer(): Uri? = cookie(cookieName)?.let { if (!it.value.startsWith("http")) "http://${it.value}" else it.value }?.let { Uri.of(it).path("") }
fun Request.studentServerPath(path: String): Uri = (studentServer() ?: Uri.of("")).path(path)

