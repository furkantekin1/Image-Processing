package com.furkotek.watermark.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.furkotek.watermark.Global
import com.furkotek.watermark.R
import com.furkotek.watermark.Utils
import com.furkotek.watermark.fragments.viewmodels.ImagePropertiesViewModel


class ResizeFragment : Fragment() {
    lateinit var imgBack: ImageView
    lateinit var txtWidth: EditText
    lateinit var txtHeight: EditText
    lateinit var imagePropertiesVM: ImagePropertiesViewModel

    fun initFragment(view: View){
        imgBack = view.findViewById<ImageView>(R.id.img_back)
        txtWidth = view.findViewById<EditText>(R.id.txt_width)
        txtHeight = view.findViewById<EditText>(R.id.txt_height)

        imagePropertiesVM = ViewModelProvider(requireActivity())[ImagePropertiesViewModel::class.java]
        txtWidth.setText(imagePropertiesVM.imageSizeData.value!!["width"].toString())
        txtHeight.setText(imagePropertiesVM.imageSizeData.value!!["height"].toString())
        imgBack.setOnClickListener {
            Utils.Companion.changeFragment(ButtonsFragment(), this)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_resize, container, false)
        initFragment(view)
        return view
    }

}