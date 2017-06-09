package org.http4k.koans

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.by
import org.http4k.routing.routes

object HttpKoans {
    operator fun invoke(): HttpHandler = routes("/" to Method.GET by { Response(OK) })
}