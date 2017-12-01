package com.programming.pt.kwejk

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

@Root(name = "xml", strict = false)
data class KwejkXmlFeed(@field:Element(name = "current_page") @field:Path("paging") var currentPage: Int,
                        @field:ElementList(name = "img", inline = true) var images: List<Image>)
{
    constructor() : this(-1, ArrayList())
}