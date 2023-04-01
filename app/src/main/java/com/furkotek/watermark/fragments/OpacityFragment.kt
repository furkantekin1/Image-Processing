package com.furkotek.watermark.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import com.furkotek.watermark.R
import com.furkotek.watermark.Utils
import com.furkotek.watermark.fragments.viewmodels.ImagePropertiesViewModel

class OpacityFragment : Fragment() {

    lateinit var seekBar: SeekBar;
    lateinit var imageview: ImageView
    lateinit var imagePropertiesVM : ImagePropertiesViewModel

    fun initFragment(view: View){
        imagePropertiesVM = ViewModelProvider(requireActivity())[ImagePropertiesViewModel::class.java]
        imageview = view.findViewById(R.id.img_back)
        seekBar = view.findViewById(R.id.seekBar)
        seekBar.setProgress(imagePropertiesVM.opacityData.value!!, false)
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                imagePropertiesVM.opacityData.value = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }

        })
        imageview.setOnClickListener(View.OnClickListener {
            Utils.Companion.changeFragment(ButtonsFragment(), this)
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_opacity, container, false)
        initFragment(view)
        return view
    }

}