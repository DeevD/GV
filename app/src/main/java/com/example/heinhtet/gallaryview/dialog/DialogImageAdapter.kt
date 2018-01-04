package com.dev.sample.features.homefrag

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.heinhtet.gallaryview.R
import com.example.heinhtet.gallaryview.dialog.DialogImageData
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter

/**
 * Created by heinhtet on 11/17/2017.
 */

open class DialogImageAdapter(context: Context) : RecyclerArrayAdapter<DialogImageData>(context) {
    lateinit var click: ClickListener
    var selectedPostion: Int? = null
    var selectedImage: ImageView? = null


    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ImageViewHolder(parent)
    }

    interface ClickListener {
        fun imageClick(position: Int)
    }

    fun selectedPostion(position: Int) {
        this.selectedPostion = position
    }

    open fun ClickListener(clickListener: ClickListener) {
        this.click = clickListener
    }

    inner class ImageViewHolder(parent: ViewGroup) : BaseViewHolder<DialogImageData>(parent, R.layout.dialog_recycler_image) {
        private val dialogImage: ImageView

        init {
            dialogImage = `$`(R.id.dialog_recycler_iv)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun setData(product: DialogImageData?) {
            if (product!=null)
            {
                Glide.with(context).load(product.imagePath).into(dialogImage)

                if (product.type) {
                    dialogImage.foreground = context.getDrawable(R.drawable.image_border)
                } else {
                    dialogImage.foreground = context.getDrawable(R.drawable.image_ripple)
                }
            }


            selectedImage = dialogImage
            selectedImage!!.setOnClickListener({

                click.imageClick(adapterPosition)
            })
        }
    }
}