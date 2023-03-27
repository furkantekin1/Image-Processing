package com.furkotek.watermark

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

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

        fun saveImageToFile(img: Bitmap, dest: String) : File? {
            var out : FileOutputStream
            try {
                var f : File = File(dest)
                out = FileOutputStream(f)
                img.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                return f
            } catch (e : Exception){
                e.printStackTrace()
                return null
            }
        }

        fun getImagePath () : String {
            var f: File = File("/data/data/" + BuildConfig.APPLICATION_ID + "/files")
            if (!f.exists())
                f.mkdir()
            return "/data/data/" + BuildConfig.APPLICATION_ID + "/files"
        }
        fun tempImagePath () : String {
            return getImagePath() + "/" + Global.Companion.imageName
        }
    }
}