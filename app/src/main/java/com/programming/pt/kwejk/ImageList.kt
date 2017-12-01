package com.programming.pt.kwejk

class ImageList(var currentPage: Int,
                val source: MutableList<Image>,
                private val loadFunc: (Int, (() -> Unit)?, (() -> Unit)?) -> Unit)
{
    private val itemsDistanceReload = 3
    private var currentImgIndex = 0

    fun getImage() : Image? = if (source.isNotEmpty()) source[currentImgIndex] else null

    fun next(): Boolean {
        checkForReload()
        return@next if (currentImgIndex < (source.size - 1)) {
            ++currentImgIndex
            true
        } else false
    }

    fun previous(): Boolean {
        return@previous if (currentImgIndex > 0) {
            --currentImgIndex
            true
        } else false
    }

    private fun checkForReload() = if (currentImgIndex >= (source.size - itemsDistanceReload))
                                        loadFunc(currentPage - 1, null, null)
                                   else
                                        null
}