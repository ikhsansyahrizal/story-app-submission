package com.ikhsan.storyapp.base.helper

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.text.format.Formatter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

fun Bitmap.toMultipart(context: Context): MultipartBody.Part {
    val bitmap = if (this.checkIfImageNeedToCompress(context)) this.createSmallerBitmap() else this
    val fileName = "photo"
    val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        fileName
    )
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
    val bitmapdata = bos.toByteArray()

    try {
        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(
        "image",
        "${fileName}.jpg",
        requestBody
    )
}


private fun Bitmap.checkIfImageNeedToCompress(context: Context): Boolean {
    val size = Formatter.formatFileSize(context, this.byteCount.toLong())

    val regex = Regex("([0-9.]+)\\s*([a-zA-Z]+)")
    val matchResult = regex.find(size)

    if (matchResult != null) {
        val (fileSize, unit) = matchResult.destructured
        return unit == "MB" && fileSize.toDouble() > 4.99
    } else {
        return false
    }
}

private fun Bitmap.createSmallerBitmap(maxResolution: Int = 300) : Bitmap {
    var width = this.width
    var height = this.height

    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxResolution
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxResolution
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(this, width, height, true)
}