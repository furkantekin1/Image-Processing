package com.furkotek.watermark

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.furkotek.watermark.awt.net.windward.android.awt.AlphaComposite
import com.furkotek.watermark.awt.net.windward.android.awt.Color
import com.furkotek.watermark.awt.net.windward.android.awt.image.BufferedImage
import com.furkotek.watermark.awt.net.windward.android.imageio.ImageIO
import com.furkotek.watermark.fragments.ButtonsFragment
import com.furkotek.watermark.fragments.viewmodels.ImagePropertiesViewModel
import java.io.File

class MainScreen : AppCompatActivity() {

    val IMAGE_SELECT: Int = 100
    val selectIntent: Intent = Intent()
    lateinit var imgView: ImageView
    lateinit var txtSave: TextView
    lateinit var txtDelete: TextView
    lateinit var imagePropertiesVM: ImagePropertiesViewModel
    var dialog: AlertDialog.Builder? = null
    fun init() {
        imgView = findViewById(R.id.img_selected)
        txtSave = findViewById(R.id.txtSave)
        txtDelete = findViewById(R.id.txtDelete)
        selectIntent.setAction(Intent.ACTION_GET_CONTENT)
        selectIntent.setType("image/*")

        supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, ButtonsFragment())
            .commit()
        imgView.setOnClickListener(View.OnClickListener {
            startActivityForResult(
                Intent.createChooser(selectIntent, "Select Image File"),
                IMAGE_SELECT
            )

        })
        txtSave.setOnClickListener(View.OnClickListener {
            saveImage()
        })
        txtDelete.setOnClickListener() {
            deleteImage()
        }
        imagePropertiesVM = ViewModelProvider(this)[ImagePropertiesViewModel::class.java]
        imagePropertiesVM.opacityData.observe(this, Observer { opacity ->
            imgView.imageAlpha = opacity
            if(imagePropertiesVM.isImageSelectedData.value!!){
                if (Global.opacityDefault != opacity) imagePropertiesVM.isAnyDataChanged.value = true
            }
        })
        imagePropertiesVM.isAnyDataChanged.observe(this) { data ->
            if(imagePropertiesVM.isImageSelectedData.value!!){
                txtSave.isEnabled = data
            }

        }
        imagePropertiesVM.isImageSelectedData.observe(this) {data ->
            txtDelete.isEnabled = data
            val visible = if (data) View.VISIBLE else View.GONE
            txtDelete.visibility = visible
            txtSave.visibility = visible
            if(data == false){
                imgView.setImageDrawable(getDrawable(R.drawable.img_empty))
                imagePropertiesVM.opacityData.value = Global.opacityDefault
                imagePropertiesVM.isAnyDataChanged.value = false
                txtSave.isEnabled = false
                supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, ButtonsFragment())
                    .commit()
            }
        }



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        init()
    }

    fun saveImage() {
        if (dialog == null)
            dialog = AlertDialog.Builder(this)
        dialog!!.setMessage(getString(R.string.save_image_question))
        dialog!!.setPositiveButton(R.string.yes) { _, _ ->
            Utils.saveImageToDevice(prepareImageToSave())
            imagePropertiesVM.isImageSelectedData.value = false
        }
        dialog!!.setNegativeButton(R.string.no, null)
        dialog!!.show()

    }

    fun deleteImage() {
        if (dialog == null)
            dialog = AlertDialog.Builder(this)
        dialog!!.setMessage(R.string.delete_image_question)
        dialog!!.setPositiveButton(R.string.yes) { _, _ ->
            imagePropertiesVM.isImageSelectedData.value = false
        }
        dialog!!.setNegativeButton(R.string.no, null)
        dialog!!.show()
    }

    fun prepareImageToSave(): BufferedImage {
        val opacity = 0.5f
        var sourceImg = ImageIO.read(File(Utils.tempImagePath()))
        var resultImg = BufferedImage(sourceImg.width, sourceImg.height, BufferedImage.TYPE_INT_ARGB)
        var g = resultImg.createGraphics()
        g.drawImage(sourceImg,0,0, Color.WHITE,null)
        g.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)
        g.dispose()
        return resultImg

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            try {
                var bitmap: Bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data.data!!))
                imgView.setImageBitmap(bitmap)
                Utils.Companion.saveImageToFile(bitmap, Utils.Companion.tempImagePath())
                imagePropertiesVM.isImageSelectedData.value = true
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "An error occurred when selecting Image. Try again",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(applicationContext, "Request Cancelled By User", Toast.LENGTH_SHORT)
                .show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}