package com.programming.pt.kwejk

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "img", strict = false)
data class Image(@field:Element(name = "source") var source: String,
                 @field:Element(name = "title") var title: String,
                 @field:Element(name = "link") private var link: String)
{
    constructor() : this("", "", "")

    fun isNotArticle(): Boolean = !link.startsWith("/article")
}