package org.http4k.koans.lesson

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.koans.student.ReferenceStudent
import org.http4k.koans.testStudentServer
import org.junit.Test

class EchoTest {

    @Test
    fun `client echoes the path parameter`() {
        val echo = LessonRoutes(ReferenceStudent(), "", Echo)

        val response = echo(Request(GET, "/run").testStudentServer("/"))

        assertThat(response.status, equalTo(OK))
    }

    @Test
    fun `when client fails to respond`() {
        val echo = LessonRoutes({ _: Request -> Response(OK) }, "", Echo)

        val response = echo(Request(GET, "/run").testStudentServer("/"))

        assertThat(response.bodyString(), containsSubstring("Fail"))
    }
}
