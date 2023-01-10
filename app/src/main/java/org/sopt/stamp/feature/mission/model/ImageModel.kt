package org.sopt.stamp.feature.mission.model

import android.net.Uri

sealed class ImageModel {
    data class Remote(val url: List<String>) : ImageModel()
    data class Local(val uri: List<Uri>) : ImageModel()
    object Empty : ImageModel()
}
