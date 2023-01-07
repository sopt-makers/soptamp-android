package org.sopt.stamp.feature.mission.model

import android.net.Uri

sealed class ImageModel {
    data class Remote(val url: String) : ImageModel()
    data class Local(val uri: Uri) : ImageModel()
    object Empty : ImageModel()
}
