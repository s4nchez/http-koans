package org.http4k.koans

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.koans.lesson.Echo
import org.http4k.koans.lesson.LessonRoutes
import org.http4k.koans.lesson.LessonSpec
import org.http4k.koans.lesson.Repeat
import org.http4k.koans.student.ReferenceStudent
import org.http4k.routing.bind
import org.http4k.routing.routes

object HttpKoans {
    private val lessons = listOf(Echo, Repeat)
    val lessonLinks = lessons.map { LessonLink(it.name, it.lessonPath()) }

    operator fun invoke(student: HttpHandler): HttpHandler {
        val koanRoutes = listOf("/" bind Settings())
            .plus(lessons.map { it.lessonPath() bind LessonRoutes(student, it.lessonPath(), it) })

        return DebuggingFilters.PrintRequestAndResponse()
            .then(routes(ReferenceStudent(), *koanRoutes.toTypedArray()))
    }

    private fun LessonSpec.lessonPath() = "/lesson/${name.toLowerCase()}"
}


