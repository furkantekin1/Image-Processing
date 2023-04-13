package com.furkotek.watermark

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.furkotek.watermark.awt.net.windward.android.awt.AlphaComposite
import com.furkotek.watermark.awt.net.windward.android.awt.Color
import com.furkotek.watermark.awt.net.windward.android.awt.image.BufferedImage
import com.furkotek.watermark.awt.net.windward.android.imageio.ImageIO
import com.furkotek.watermark.fragments.ButtonsFragment
import com.furkotek.watermark.fragments.viewmodels.GlobalViewModel
import com.furkotek.watermark.fragments.viewmodels.ImagePropertiesViewModel
import kotlinx.coroutines.*
import java.io.File

class MainScreen : AppCompatActivity() {

    val IMAGE_SELECT: Int = 100
    val selectIntent: Intent = Intent()
    lateinit var imgView: ImageView
    lateinit var txtSave: TextView
    lateinit var txtDelete: TextView
    lateinit var imagePropertiesVM: ImagePropertiesViewModel
    lateinit var globalVM: GlobalViewModel
    var dialog: AlertDialog.Builder? = null
    var originalBitmap: Bitmap? = null
    var tempBitmap: Bitmap? = null

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
            imageOp(ImageOperation.SAVE)
        })
        txtDelete.setOnClickListener() {
            imageOp(ImageOperation.DELETE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        init()
        initObservers()
    }

    fun imageOp(op: ImageOperation) {
        if (dialog == null)
            dialog = AlertDialog.Builder(this)
        if (op == ImageOperation.SAVE)
            dialog!!.setMessage(getString(R.string.save_image_question))
        else if (op == ImageOperation.DELETE)
            dialog!!.setMessage(getString(R.string.delete_image_question))
        dialog!!.setPositiveButton(R.string.yes) { _, _ ->
            when(op){
                ImageOperation.SAVE->{
                    globalVM.isShowLoading.value = true
                    CoroutineScope(Dispatchers.IO).launch (Dispatchers.IO)  {
                        Utils.saveImageToDevice(prepareImageToSave())
                        imagePropertiesVM.isImageSelectedData.postValue(false)
                        globalVM.isShowLoading.postValue(false)
                    }
                }
                ImageOperation.DELETE->
                    imagePropertiesVM.isImageSelectedData.postValue(false)

            }
        }
        dialog!!.setNegativeButton(R.string.no, null)
        dialog!!.show()


    }


    fun prepareImageToSave(): BufferedImage {

        var sourceImg = ImageIO.read(File(Utils.tempImagePath()))
        var resultImg =
            BufferedImage(sourceImg.width, sourceImg.height, BufferedImage.TYPE_INT_ARGB)
        var g = resultImg.createGraphics()
        g.drawImage(sourceImg, 0, 0, Color.WHITE, null)
        g.setComposite(
            AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                1f - (imagePropertiesVM.opacityData.value!!.toFloat() / 255f)
            )
        );
        g.color = Color.WHITE
        g.fillRect(0, 0, sourceImg.width, sourceImg.height)
        g.dispose()
        return resultImg

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            try {
                originalBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data.data!!))
                imgView.setImageBitmap(originalBitmap)
                Utils.Companion.saveImageToFile(originalBitmap!!, Utils.Companion.tempImagePath())
                Global.Companion.sizeDefault.put("width", originalBitmap!!.width)
                Global.Companion.sizeDefault.put("height", originalBitmap!!.height)
                imagePropertiesVM.imageSizeData.value = Global.Companion.sizeDefault

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
    fun initObservers(){
        imagePropertiesVM = ViewModelProvider(this)[ImagePropertiesViewModel::class.java]
        globalVM = ViewModelProvider(this)[GlobalViewModel::class.java]

        imagePropertiesVM.opacityData.observe(this, Observer { opacity ->
            imgView.imageAlpha = opacity
            if (imagePropertiesVM.isImageSelectedData.value!!) {
                if (Global.opacityDefault != opacity) imagePropertiesVM.isAnyDataChanged.value =
                    true
            }
        })
        imagePropertiesVM.isAnyDataChanged.observe(this) { data ->
            if (imagePropertiesVM.isImageSelectedData.value!!) {
                txtSave.isEnabled = data
            }

        }
        imagePropertiesVM.isImageSelectedData.observe(this) { data ->
            txtDelete.isEnabled = data
            val visible = if (data) View.VISIBLE else View.GONE
            txtDelete.visibility = visible
            txtSave.visibility = visible
            if (data == false) {
                imgView.setImageDrawable(getDrawable(R.drawable.img_empty))
                imagePropertiesVM.opacityData.value = Global.opacityDefault
                imagePropertiesVM.isAnyDataChanged.value = false
                txtSave.isEnabled = false
                imagePropertiesVM.imageSizeData.value = null
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentHolder, ButtonsFragment())
                    .commit()
            }
        }
        imagePropertiesVM.imageSizeData.observe(this){ data ->
            if(data!=null){
                imagePropertiesVM.isImageSelectedData.value = true
                tempBitmap = Bitmap.createScaledBitmap(originalBitmap!!, data["width"]!!, data["height"]!!, false)
                imgView.setImageBitmap(tempBitmap)
            }
        }

        globalVM.isShowLoading.observe(this){data ->
            if (data)
                Global.Companion.showDialog(this)
            else
                Global.Companion.closeDialog()
        }

    }
    enum class ImageOperation(i: Int) {
        SAVE(1),
        DELETE(2)
    }

}