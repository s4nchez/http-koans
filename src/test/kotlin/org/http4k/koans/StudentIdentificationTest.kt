package org.http4k.koans

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.core.cookie.cookie
import org.http4k.koans.StudentIdentification.cookieName
import org.junit.Test

class StudentIdentificationTest {
    val koans = HttpKoans({ _: Request -> Response(OK) })

    @Test
    fun `request works if header is present`() {
        val response = koans(Request(GET, "/").cookie(cookieName, "anything"))
        assertThat(response.status, equalTo(OK))
    }

    @Test
    fun `student server uri can be retrieved via extension function`() {
        val request = Request(GET, "/").cookie(cookieName, "http://some_server:1234/aPath")
        assertThat(request.studentServer(), equalTo(Uri.of("http://some_server:1234")))
    }
}

fun Request.testStudentServer(server: String): Request = cookie(cookieName, server)