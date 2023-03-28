package com.furkotek.watermark.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import com.furkotek.watermark.MainActivity
import com.furkotek.watermark.MainScreen
import com.furkotek.watermark.R
import com.furkotek.watermark.Utils

class ButtonsFragment : Fragment() {

    lateinit var btnOpacity : Button
    lateinit var btnResize : Button
    lateinit var btnCompress : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View =  inflater.inflate(R.layout.fragment_buttons, container, false)
        btnOpacity =  view.findViewById(R.id.btn_opacity)
        btnCompress = view.findViewById(R.id.btn_compress)
        btnResize = view.findViewById(R.id.btn_resize)
        btnOpacity.setOnClickListener {
            Utils.Companion.changeFragment(OpacityFragment(), this)
        }
        return view
    }


}