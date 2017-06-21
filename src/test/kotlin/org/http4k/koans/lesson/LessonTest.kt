package org.http4k.koans.lesson

import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.koans.hasHtmlElement
import org.http4k.koans.hasText
import org.http4k.koans.html
import org.http4k.koans.testStudentServer
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.junit.Test

class LessonTest {
    val testStudent = routes(
        "/hello" to Method.GET bind { Response(Status.OK).body("Hello World!") }
    )

    val lesson = LessonRoutes(testStudent, "", spec())

    private fun spec(responseMatching: (Response) -> Unit = { _: Response -> }): LessonSpec {
        return object : LessonSpec {
            override val name: String = "TestLesson"
            override fun generateTest(): Pair<Request, (Response) -> Unit> {
                return Request(Method.GET, "/hello") to responseMatching
            }
        }
    }

    @Test
    fun `presents its description by default`() {
        val response = lesson(Request(Method.GET, "/"))

        response.html().shouldMatch(hasHtmlElement("div#TestLessionDescription"))
    }

    @Test
    fun `on run prints the whole request roundtrip`() {
        val request = Request(Method.GET, "/run").testStudentServer("/")

        val response = lesson(request)

        response.html().shouldMatch(hasHtmlElement("div.result", hasText(containsSubstring("Success"))))
        response.html().shouldMatch(hasHtmlElement("pre#request-sent", hasText(containsSubstring("GET /hello"))))
        response.html().shouldMatch(hasHtmlElement("pre#response-received", hasText(containsSubstring("Hello World!"))))
    }

    @Test
    fun `on run assertion error prints the whole request roundtrip`() {
        val failingLesson = LessonRoutes(testStudent, "", spec({ 1.shouldMatch(equalTo(2)) }))
        val request = Request(Method.GET, "/run").testStudentServer("/")

        val response = failingLesson(request)

        response.html().shouldMatch(hasHtmlElement("div.result", hasText(containsSubstring("Fail"))))
        response.html().shouldMatch(hasHtmlElement("pre#request-sent", hasText(containsSubstring("GET /hello"))))
        response.html().shouldMatch(hasHtmlElement("pre#response-received", hasText(containsSubstring("Hello World!"))))
    }

    @Test
    fun `on run exception prints just the request`() {
        val failingLesson = LessonRoutes({ _: Request -> throw RuntimeException("boom!") }, "", spec())
        val request = Request(Method.GET, "/run").testStudentServer("/")
        val response = failingLesson(request)
        response.html().shouldMatch(hasHtmlElement("div.result", hasText(containsSubstring("Fail"))))
        response.html().shouldMatch(hasHtmlElement("pre#request-sent", hasText(containsSubstring("GET /hello"))))
        response.html().shouldMatch(!hasHtmlElement("pre#response-received"))
    }
}
