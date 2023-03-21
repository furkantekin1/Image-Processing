package com.furkotek.watermark

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.net.URI

class Utils {

    companion object {
        fun getPathFromUri(ctx: Context, uri: Uri?) : String{
            var res: String = ""
            if(uri!=null){
                var cursor: Cursor? = ctx.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA),null,null,null)
                if (cursor != null) {
                    cursor.moveToFirst()
                    var index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    res = cursor.getString(index)
                    cursor.close()
                }
            }

            return res
        }
    }
}