package org.http4k.koans

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.core.body.form
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel

val renderer = HandlebarsTemplates().HotReload("src/main/resources")

object Welcome {
    operator fun invoke(): RoutingHttpHandler {
        return routes(
            "/" to GET bind { request: Request -> Response(Status.OK).body(renderer(View(request.studentServer()))) },
            "/" to POST bind { request: Request ->
                Response(Status.FOUND)
                    .header("location", "/")
                    .cookie(Cookie(StudentIdentification.cookieName, request.form("student-server") ?: ""))
            }
        )
    }

    data class View(val studentServer: Uri?) : ViewModel
}



