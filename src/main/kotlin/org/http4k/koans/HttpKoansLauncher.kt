package org.http4k.koans

import org.http4k.client.OkHttp
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main(args: Array<String>) = HttpKoans(OkHttp()).asServer(Jetty(resolvePort(args))).start().block()

private fun resolvePort(args: Array<String>) = if (args.isNotEmpty()) args[0].toInt() else 8000
