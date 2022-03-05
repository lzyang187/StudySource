package com.lzy.studysource.fileprovider

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 * Created by zhaoyang.li5 on 2022/3/5 11:57
 */
object FileProviderUtil {

    private const val AUTHORITY_FILE_PROVIDER_SUFFIX = ".fileprovider"

    fun getUriForFile(context: Context, file: File): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getUriForFileWithNougat(context, file)
        } else {
            Uri.fromFile(file)
        }
    }

    private fun getUriForFileWithNougat(context: Context, file: File) = FileProvider.getUriForFile(
        context, context.packageName + AUTHORITY_FILE_PROVIDER_SUFFIX, file
    )

    fun setIntentDataAndType(
        context: Context, intent: Intent, type: String, file: File, writeAble: Boolean
    ) {
        intent.setDataAndType(getUriForFile(context, file), type)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        }
    }

}