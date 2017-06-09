package org.http4k.koans

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.junit.Test

class IdentificationTest {
    val koans = HttpKoans()

    @Test
    fun `requires referer identification`() {
        val response = koans(Request(GET, "/"))
        assertThat(response.status, equalTo(UNAUTHORIZED))
    }

    @Test
    fun `request works if header is present`() {
        val response = koans(Request(GET, "/").header("Referer", "anything"))
        assertThat(response.status, equalTo(OK))
    }
}