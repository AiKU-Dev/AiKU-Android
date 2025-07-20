package com.hyunjung.aiku.core.ui.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.scale
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
suspend fun Uri.toCompressedFile(
    context: Context,
    maxWidth: Int = 1280,
    maxFileSizeInMB: Int = 5,
    initialQuality: Int = 100,
    minQuality: Int = 50,
    dispatcher: CoroutineDispatcher = Dispatchers.IO // ✨ 주입 가능
): File = withContext(dispatcher) {
    val inputStream = context.contentResolver.openInputStream(this@toCompressedFile)
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

    var quality = initialQuality
    var fileSize: Long

    while (true) {
        FileOutputStream(compressedFile).use { out ->
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
        }
        fileSize = compressedFile.length()
        if (fileSize <= maxFileSizeInMB * 1024 * 1024 || quality <= minQuality) break
        quality -= 5
    }

    compressedFile
}