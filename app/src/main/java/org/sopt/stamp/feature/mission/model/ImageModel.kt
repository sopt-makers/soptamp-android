package org.sopt.stamp.feature.mission.model

import android.net.Uri

sealed interface ImageModel {
    data class Remote(val url: List<String>) : ImageModel {
        override fun isEmpty() = url.isEmpty()
    }

    data class Local(val uri: List<Uri>) : ImageModel {
        override fun isEmpty() = uri.isEmpty()
    }

    object Empty : ImageModel {
        override fun isEmpty() = true
    }

    fun isEmpty(): Boolean
}
