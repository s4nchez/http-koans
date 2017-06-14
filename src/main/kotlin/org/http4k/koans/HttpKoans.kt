package org.http4k.koans

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.koans.lesson.Echo
import org.http4k.koans.lesson.LessonRoutes
import org.http4k.koans.lesson.Repeat
import org.http4k.koans.student.ReferenceStudent
import org.http4k.routing.bind
import org.http4k.routing.routes

object HttpKoans {
    operator fun invoke(student: HttpHandler): HttpHandler =
        DebuggingFilters.PrintRequestAndResponse()
            .then(routes(ReferenceStudent(),
                "/" bind Settings(),
                "/1" bind LessonRoutes(student, "/1", Echo),
                "/2" bind LessonRoutes(student, "/2", Repeat)
            ))
}