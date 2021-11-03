package com.lzy.studysource.opengles.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author: cyli8
 * @date: 2021/11/3 22:24
 */
object TextResourceRender {

    fun readTextFileFromResource(context: Context, resourceId: Int): String {
        val sb = StringBuilder()
        try {
            val inputStream = context.resources.openRawResource(resourceId)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var nextLine: String?
            do {
                nextLine = bufferedReader.readLine()
                if (nextLine != null) {
                    sb.append(nextLine)
                    sb.append("\n")
                }
            } while ((nextLine != null))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }

}