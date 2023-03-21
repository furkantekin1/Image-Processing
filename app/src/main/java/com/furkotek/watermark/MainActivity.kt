package com.furkotek.watermark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val IMAGE_ONE = 100
    val IMAGE_TWO = 101
    lateinit var imgView : ImageView
    lateinit var btnSelectImageOne : Button
    lateinit var btnSelectImageTwo : Button
    var selectIntent : Intent = Intent()


    fun init(){
        //This function init activity and events
        imgView = findViewById(R.id.img_selected)
        btnSelectImageOne = findViewById(R.id.btn_image_one)
        btnSelectImageTwo = findViewById(R.id.btn_image_two)

        selectIntent.setType("image/*")
        selectIntent.setAction(Intent.ACTION_GET_CONTENT)

        btnSelectImageOne.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent.createChooser(selectIntent,"Select Image 1"),IMAGE_ONE)
        })

        btnSelectImageTwo.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent.createChooser(selectIntent,"Select Image 2"),IMAGE_TWO)
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null){
            var path = Utils.Companion.getPathFromUri(applicationContext, data.data)
            Toast.makeText(applicationContext, path, Toast.LENGTH_LONG)
            when (requestCode){

                IMAGE_ONE -> {

                }
                IMAGE_TWO -> {

                }
            }
        } else {
            Toast.makeText(applicationContext, "Request Cancelled By User", Toast.LENGTH_SHORT).show()

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}