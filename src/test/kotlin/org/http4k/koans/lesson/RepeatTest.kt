package org.http4k.koans.lesson

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.koans.student.ReferenceStudent
import org.http4k.koans.testStudentServer
import org.junit.Test

class RepeatTest {

    @Test
    fun `repeats message n-times`(){
        val repeat = LessonRoutes(ReferenceStudent(), "", Repeat)

        val response = repeat(Request(Method.GET, "/run").testStudentServer("/"))

        assertThat(response.status, equalTo(Status.OK))
    }
}