package com.ikhsan.storyapp.base.helper

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ikhsan.storyapp.R
import com.bumptech.glide.Priority
import com.ikhsan.storyapp.BuildConfig
import com.ikhsan.storyapp.base.UIConstant.DD_MMMM_YYYY_HH_MM_S
import com.ikhsan.storyapp.base.UIConstant.DMY
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val MAXIMAL_SIZE = 1000000
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


fun EditText.getTexts() = text.toString()

fun ImageView.loadImageFrom(imageUrl: String) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.img_no_image)
        .error(R.drawable.img_no_image)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.HIGH)
    Glide.with(this.context)
        .load(imageUrl)
        .apply(options)
        .into(this)
}

fun <RV : RecyclerView.ViewHolder?> RecyclerView.initRecycleView(
    adapterRV: RecyclerView.Adapter<RV>,
    isVertical: Boolean = true,
    isReverse: Boolean = false,
    fixedSize: Boolean = false,
    cacheRv: Boolean = false,
    customLinearLayoutManager: LinearLayoutManager? = null,
) =
    this.apply {
        layoutManager = customLinearLayoutManager ?: LinearLayoutManager(
            this.context,
            if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
            isReverse
        )
        adapter = adapterRV
        setHasFixedSize(fixedSize)
        if (cacheRv) setItemViewCacheSize(25)

    }

fun String.formatToReadableDate(): String {
    val inputFormat = SimpleDateFormat(DD_MMMM_YYYY_HH_MM_S, Locale.getDefault())
    val outputFormat = SimpleDateFormat(DMY, Locale("id", "ID"))

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        this
    }
}

fun SwipeRefreshLayout.onSwipeListener(callback: () -> Unit) = this.setOnRefreshListener {
    callback()
    this.isRefreshing = false
}

fun getImageUri(context: Context): Uri {
    var uri: Uri? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
        }
        uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }
    return uri ?: getImageUriForPreQ(context)
}

private fun getImageUriForPreQ(context: Context): Uri {
    val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
    if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        imageFile
    )
}


fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}


fun File.reduceFileImage(): File {
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

    return file
}

fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
    val orientation = ExifInterface(file).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270F)
        ExifInterface.ORIENTATION_NORMAL -> this
        else -> this
    }
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height, matrix, true
    )
}

