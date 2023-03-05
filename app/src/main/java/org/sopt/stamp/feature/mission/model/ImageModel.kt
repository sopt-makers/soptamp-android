package org.sopt.stamp.feature.mission.model

import android.net.Uri

sealed interface ImageModel {
    data class Remote(val url: List<String>) : ImageModel {
        override fun isEmpty() = url.isEmpty()
        override val size = url.size
    }

    data class Local(val uri: List<Uri>) : ImageModel {
        override fun isEmpty() = uri.isEmpty()
        override val size = uri.size
    }

    object Empty : ImageModel {
        override fun isEmpty() = true
        override val size = 1
    }

    fun isEmpty(): Boolean
    val size: Int
}
