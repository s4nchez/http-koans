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
import org.http4k.koans.HttpKoans.lessonLinks
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates

object Settings {

    val renderer = HandlebarsTemplates().HotReload("src/main/resources")

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

    data class View(override val studentServer: Uri?, override val lessons: List<LessonLink> = lessonLinks) : KoanView
}



