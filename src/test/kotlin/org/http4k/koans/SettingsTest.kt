package org.http4k.koans

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.koans.student.ReferenceStudent
import org.junit.Test

class SettingsTest {

    val koans = HttpKoans(ReferenceStudent())

    @Test
    fun `by default server is unknown`() {
        val response = koans(Request(Method.GET, "/"))
        assertThat(response.bodyString(), containsSubstring("unknown"))
    }

    @Test
    fun `if server is set then it is displayed`(){
        val response = koans(Request(Method.GET, "/").testStudentServer("localhost:8081"))
        assertThat(response.bodyString(), containsSubstring("localhost:8081"))
    }
}

