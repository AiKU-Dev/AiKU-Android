package com.hyunjung.aiku.core.ui.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.scale
import java.io.File
import java.io.FileOutputStream
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal fun Uri.toCompressedFile(
    context: Context,
    maxWidth: Int = 200,
    quality: Int = 80
): File {
    val inputStream = context.contentResolver.openInputStream(this)
        ?: error("Failed to open InputStream from URI")

    val originalBitmap = BitmapFactory.decodeStream(inputStream)
        ?: error("Failed to decode bitmap from URI")

    val scaledBitmap = if (originalBitmap.width <= maxWidth) {
        originalBitmap
    } else {
        val ratio = maxWidth.toFloat() / originalBitmap.width
        val height = (originalBitmap.height * ratio).toInt()
        originalBitmap.scale(maxWidth, height)
    }

    val fileName = "compressed_${Uuid.random()}.jpg"
    val compressedFile = File(context.cacheDir, fileName)

    FileOutputStream(compressedFile).use { out ->
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
    }

    return compressedFile
}