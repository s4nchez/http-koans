package org.http4k.koans

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.junit.Test

class StudentIdentificationTest {
    val koans = HttpKoans({ _: Request -> Response(OK) })

    @Test
    fun `request works if header is present`() {
        val response = koans(Request(GET, "/").header("Referer", "anything"))
        assertThat(response.status, equalTo(OK))
    }

    @Test
    fun `student server uri can be retrieved via extension function`() {
        val request = Request(GET, "/").header("Referer", "http://some_server:1234/aPath")
        assertThat(request.studentServer(), equalTo(Uri.of("http://some_server:1234")))
    }

    @Test(expected = IllegalStateException::class)
    fun `breaks if student server is not available`() {
        Request(GET, "/").studentServer()
    }
}

fun Request.testStudentServer(server: String): Request = header("referer", server)