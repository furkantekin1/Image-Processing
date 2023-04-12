package com.furkotek.watermark

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup

class Global {

    companion object {
        val imageName : String = "temp_img"
        val opacityDefault = 255
        var dialog: Dialog? = null



        fun showDialog(ctx: Context){
            if(dialog == null)
                dialog = Dialog(ctx)
            if (!dialog!!.isShowing){
                dialog!!.setContentView(R.layout.loading_layout);
                dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(ctx.getColor(R.color.loading_transparent)))
                dialog!!.setCancelable(false)
                dialog!!.show()
            }
        }

        fun closeDialog(){
            if (dialog != null && dialog!!.isShowing)
                dialog!!.dismiss()
        }
    }
}