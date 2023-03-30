package com.furkotek.watermark

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.furkotek.watermark.fragments.ButtonsFragment
import com.furkotek.watermark.fragments.viewmodels.ImagePropertiesViewModel
import kotlinx.coroutines.MainScope

class MainScreen : AppCompatActivity() {

    val IMAGE_SELECT : Int = 100

    lateinit var imgView : ImageView

    lateinit var imagePropertiesVM: ImagePropertiesViewModel
    val selectIntent : Intent = Intent()

    fun init(){
        imgView = findViewById(R.id.img_selected)

        selectIntent.setAction(Intent.ACTION_GET_CONTENT)
        selectIntent.setType("image/*")
        supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, ButtonsFragment()).commit()

        imgView.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent.createChooser(selectIntent, "Select Image File"), IMAGE_SELECT)

        })
        imagePropertiesVM = ViewModelProvider(this)[ImagePropertiesViewModel::class.java]
        imagePropertiesVM.opacityData.observe(this, Observer { opacity ->
            imgView.imageAlpha = opacity
            Log.i("a", opacity.toString())
        })


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null){
            try {
                var bitmap: Bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data.data!!))
                imgView.setImageBitmap(bitmap)
                Utils.Companion.saveImageToFile(bitmap, Utils.Companion.tempImagePath())
                imagePropertiesVM.isImageSelectedData.value = true
            } catch (e: Exception){
                Toast.makeText(applicationContext, "An error occurred when selecting Image. Try again", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(applicationContext, "Request Cancelled By User", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}