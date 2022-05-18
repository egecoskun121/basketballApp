package com.ege.basketballapp.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.io.File

class FileUtils {
    companion object {
        fun getFileMimeType(context: Context, uri: Uri): String? {
            return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val mime = MimeTypeMap.getSingleton()
                mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
            } else {
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
            }
        }

        fun getFileName(context: Context, uri: Uri):String{

                context.contentResolver.query(uri, null, null, null, null)
            ?.use { cursor ->

                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                return cursor.getString(nameIndex);
            }
            return ""
        }
    }

}