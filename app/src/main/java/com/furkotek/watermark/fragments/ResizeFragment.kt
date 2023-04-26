package com.furkotek.watermark.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.furkotek.watermark.Global
import com.furkotek.watermark.R
import com.furkotek.watermark.Utils
import com.furkotek.watermark.fragments.viewmodels.ImagePropertiesViewModel


class ResizeFragment : Fragment() {
    lateinit var imgBack: ImageView
    lateinit var txtWidth: EditText
    lateinit var txtHeight: EditText
    lateinit var btnReset: Button
    lateinit var btnConfirm: Button
    lateinit var imagePropertiesVM: ImagePropertiesViewModel
    var map = HashMap<String, Int>()

    fun initFragment(view: View){
        imgBack = view.findViewById<ImageView>(R.id.img_back)
        txtWidth = view.findViewById<EditText>(R.id.txt_width)
        txtHeight = view.findViewById<EditText>(R.id.txt_height)
        btnReset = view.findViewById<Button>(R.id.btn_reset)
        btnConfirm = view.findViewById<Button>(R.id.btn_confirm)

        txtWidth.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
        txtHeight.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
        imagePropertiesVM = ViewModelProvider(requireActivity())[ImagePropertiesViewModel::class.java]
        map = imagePropertiesVM.imageSizeData.value!!.clone() as HashMap<String, Int>
        setWHText(map)
        imgBack.setOnClickListener {
            Utils.Companion.changeFragment(ButtonsFragment(), this)
        }
        btnReset.setOnClickListener {
            imagePropertiesVM.imageSizeData.value = Global.Companion.sizeDefault
            setWHText(Global.Companion.sizeDefault)
        }
        btnConfirm.setOnClickListener {
            map["width"] = Integer.parseInt(txtWidth.text.toString())
            map["height"] = Integer.parseInt(txtHeight.text.toString())
            imagePropertiesVM.imageSizeData.value = map
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

    fun setWHText(map: HashMap<String, Int>){
        txtWidth.setText(map["width"].toString())
        txtHeight.setText(map["height"].toString())
    }

}