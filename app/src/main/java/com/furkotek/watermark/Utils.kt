package com.furkotek.watermark

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.furkotek.watermark.awt.net.windward.android.awt.image.BufferedImage
import com.furkotek.watermark.awt.net.windward.android.imageio.ImageIO
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        fun getPathFromUri(ctx: Context, uri: Uri?): String {
            var res = ""
            if (uri != null) {
                var cursor: Cursor? = ctx.contentResolver.query(
                    uri,
                    arrayOf(MediaStore.Images.Media.DATA),
                    null,
                    null,
                    null
                )
                if (cursor != null) {
                    cursor.moveToFirst()
                    var index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    res = cursor.getString(index)
                    cursor.close()
                }
            }

            return res
        }

        fun saveImageToFile(img: Bitmap, dest: String): File? {
            var out: FileOutputStream
            try {
                var f = File(dest)
                out = FileOutputStream(f)
                img.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                return f
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }

        fun getImagePath(): String {
            var f = File("/data/data/" + BuildConfig.APPLICATION_ID + "/files")
            if (!f.exists())
                f.mkdir()
            return "/data/data/" + BuildConfig.APPLICATION_ID + "/files"
        }

        fun tempImagePath(): String {
            return getImagePath() + "/" + Global.Companion.imageName
        }

        fun saveImageToDevice(bufferedImage: BufferedImage) {
            var f = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/ImageProcess/")
            if (!f.exists())
                f.mkdir()
            f = File(f.absolutePath + "/" +getTimeDMYHMS() + ".png")
            if (!f.exists())
                f.createNewFile()
            ImageIO.write(bufferedImage,"png",f)


        }

        fun getTimeDMYHMS(): String {
            val s = SimpleDateFormat("ddMMyyyyhhmmss")
            return s.format(Date())
        }

        fun changeFragment(fragment: Fragment, prevFragment: Fragment) {
            val fragTransaction =
                prevFragment.requireActivity().supportFragmentManager.beginTransaction()
            fragTransaction.replace(R.id.fragmentHolder, fragment)
            fragTransaction.commit()
        }
    }
}