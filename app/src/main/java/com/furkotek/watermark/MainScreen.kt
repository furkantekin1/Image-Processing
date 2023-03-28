package com.furkotek.watermark

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.furkotek.watermark.fragments.ButtonsFragment
import kotlinx.coroutines.MainScope

class MainScreen : AppCompatActivity() {

    val IMAGE_SELECT : Int = 100

    lateinit var imgView : ImageView

    val selectIntent : Intent = Intent()

    fun init(){
        imgView = findViewById(R.id.img_selected)

        selectIntent.setAction(Intent.ACTION_GET_CONTENT)
        selectIntent.setType("image/*")
        supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, ButtonsFragment()).commit()

        imgView.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent.createChooser(selectIntent, "Select Image File"), IMAGE_SELECT)

        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null){
            var bitmap: Bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data.data!!))
            imgView.setImageBitmap(bitmap)
            Utils.Companion.saveImageToFile(bitmap, Utils.Companion.tempImagePath())

        } else {
            Toast.makeText(applicationContext, "Request Cancelled By User", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}