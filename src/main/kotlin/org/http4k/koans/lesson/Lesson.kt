package org.http4k.koans.lesson

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.koans.HttpKoans.lessonLinks
import org.http4k.koans.KoanView
import org.http4k.koans.LessonLink
import org.http4k.koans.studentServer
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.template.HandlebarsTemplates

object Lesson {
    data class View(val lessonName: String, val runPath: String, override val studentServer: Uri?, val runResult: RunResult?, override val lessons: List<LessonLink> = lessonLinks) : KoanView

    data class RunResult(val success: Boolean, val explanation: String? = null)

}

interface LessonSpec {
    val name: String
    fun generateTest(): Pair<Request, (Response) -> Unit>
}


object LessonRoutes {
    val renderer = HandlebarsTemplates().HotReload("src/main/resources")

    operator fun invoke(student: HttpHandler,
                        basePath:String,
                        spec: LessonSpec): RoutingHttpHandler {
        return routes(
            "/run" to Method.GET bind { request: Request ->
                val (testRequest, evaluation) = spec.generateTest()

                val runResult = request.studentServer()
                    ?.let {
                        val studentResponse = student(testRequest.uri(request.studentServer()!!.path(testRequest.uri.path)))
                        try {
                            evaluation(studentResponse)
                            Lesson.RunResult(success = true)
                        } catch(e: AssertionError) {
                            Lesson.RunResult(success = false, explanation = e.message)
                        }
                    } ?: Lesson.RunResult(false, explanation = "Student server is not defined")
                Response(Status.OK).body(renderer(Lesson.View(spec.name, "$basePath/run", request.studentServer(), runResult)))
            },
            "/" to Method.GET bind { request: Request -> Response(Status.OK).body(renderer(Lesson.View(spec.name, "$basePath/run", request.studentServer(), null))) })
    }
}