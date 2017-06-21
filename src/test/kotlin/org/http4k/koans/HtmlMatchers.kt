package org.http4k.koans

import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import com.natpryce.hamkrest.should.shouldMatch
import org.http4k.core.Response
import org.http4k.core.Status
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.junit.Test

fun Response.html(): Document = Jsoup.parse(bodyString())

fun hasElement(selector: String, matcher: Matcher<Element>) = has("element", { doc: Document -> doc.selectSingle(selector) }, matcher)

fun hasText(value: String) = hasText(equalTo(value))

fun hasText(matcher: Matcher<String>) = has("text", { element: Element -> element.text() }, matcher)

fun hasValue(value: String) = hasValue(equalTo(value))

fun hasValue(matcher: Matcher<String>) = has("value", { element: Element -> element.attr("value") }, matcher)

private fun Document.selectSingle(selector: String): Element = select(selector).singleOrNull() ?: throw IllegalArgumentException("not unique")

class MatcherTests {
    private val response = Response(Status.OK).body(String(this.javaClass.getResourceAsStream("/test.html").readBytes()))

    @Test
    fun `can extract html from response`() {
        response.html().select("body").size.shouldMatch(equalTo(1))
    }

    @Test
    fun `can match element value`() {
        response.html().shouldMatch(hasElement("#myInput", hasValue(equalTo("myInputValue"))))
        response.html().shouldMatch(hasElement("#myInput", hasValue("myInputValue")))
    }

    @Test
    fun `can match element text`() {
        response.html().shouldMatch(hasElement("#myPara", hasText(equalTo("my text"))))
        response.html().shouldMatch(hasElement("#myPara", hasText("my text")))
    }
}