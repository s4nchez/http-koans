package org.http4k.koans

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.koans.lesson.Echo
import org.http4k.koans.lesson.Repeat
import org.http4k.koans.student.ReferenceStudent
import org.http4k.routing.bind
import org.http4k.routing.routes

object HttpKoans {

    operator fun invoke(student: HttpHandler): HttpHandler {

        val welcome = routes("/" to GET bind { Response(OK) })

        return DebuggingFilters.PrintRequestAndResponse()
            .then(routes(ReferenceStudent(),
                "/" bind welcome,
                "/1" bind StudentIdentification.then(Echo(student)),
                "/2" bind Repeat(student)
            ))
    }
}