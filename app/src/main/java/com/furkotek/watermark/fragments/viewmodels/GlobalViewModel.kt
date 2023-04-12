package com.furkotek.watermark.fragments.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GlobalViewModel: ViewModel() {
    var isShowLoading = MutableLiveData<Boolean>(false)

}