package com.furkotek.watermark.fragments.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImagePropertiesViewModel : ViewModel() {
    val opacityData = MutableLiveData<Int>(255)
    val isImageSelectedData = MutableLiveData<Boolean>(false)

}