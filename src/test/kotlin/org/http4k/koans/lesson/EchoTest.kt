package org.http4k.koans.lesson

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.koans.student.ReferenceStudent
import org.junit.Test

class EchoTest {

    @Test
    fun `client echoes the path parameter`() {
        val echo = Echo(ReferenceStudent())

        val response = echo(Request(GET, "/run").testStudentServer("/"))

        assertThat(response.status, equalTo(Status.Companion.OK))
    }

    @Test
    fun `when client fails to respond`() {
        val echo = Echo({ _: Request -> Response.Companion(Status.Companion.OK) })

        val response = echo(Request(GET, "/run").testStudentServer("/"))

        assertThat(response.status, equalTo(Status.Companion.INTERNAL_SERVER_ERROR))
    }
}

private fun Request.testStudentServer(server: String): Request = header("referer", server)
