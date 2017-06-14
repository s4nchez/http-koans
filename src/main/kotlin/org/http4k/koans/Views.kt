package org.http4k.koans

import org.http4k.core.Uri
import org.http4k.template.ViewModel

interface KoanView : ViewModel {
    val studentServer: Uri?
    val lessons: List<LessonLink>
}

data class LessonLink(val name: String, val path: String)