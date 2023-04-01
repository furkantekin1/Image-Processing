package com.furkotek.watermark.fragments.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkotek.watermark.Global

class ImagePropertiesViewModel : ViewModel() {
    val opacityData = MutableLiveData<Int>()
    val isImageSelectedData = MutableLiveData<Boolean>()
    val isAnyDataChanged = MutableLiveData<Boolean>()

    init {
        opacityData.value = Global.Companion.opacityDefault
        isImageSelectedData.value = false
        isAnyDataChanged.value = false
    }
}