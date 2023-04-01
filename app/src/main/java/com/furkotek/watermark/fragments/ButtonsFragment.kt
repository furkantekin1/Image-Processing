package com.furkotek.watermark.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.furkotek.watermark.R
import com.furkotek.watermark.Utils
import com.furkotek.watermark.fragments.viewmodels.ImagePropertiesViewModel

class ButtonsFragment : Fragment() {

    lateinit var btnOpacity : Button
    lateinit var btnResize : Button
    lateinit var btnCompress : Button
    lateinit var viewModelDatas: ImagePropertiesViewModel

    fun initFragment(view: View){
        btnOpacity =  view.findViewById(R.id.btn_opacity)
        btnCompress = view.findViewById(R.id.btn_compress)
        btnResize = view.findViewById(R.id.btn_resize)
        setButtonsStatus(false)
        viewModelDatas = ViewModelProvider(requireActivity())[ImagePropertiesViewModel::class.java]
        btnOpacity.setOnClickListener {
            Utils.Companion.changeFragment(OpacityFragment(), this)
        }
        viewModelDatas.isImageSelectedData.observe(requireActivity(), Observer { data ->
            setButtonsStatus(data)
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View =  inflater.inflate(R.layout.fragment_buttons, container, false)
        initFragment(view)
        return view
    }

    fun setButtonsStatus(enable: Boolean){
        btnOpacity.isEnabled = enable
        btnCompress.isEnabled = enable
        btnResize.isEnabled = enable

    }

}