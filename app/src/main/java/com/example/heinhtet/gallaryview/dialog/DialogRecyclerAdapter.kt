package com.example.heinhtet.gallaryview.dialog

import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.heinhtet.gallaryview.R

/**
 * Created by heinhtet on 1/4/2018.
 */
class DialogRecyclerAdapter() : RecyclerView.Adapter<DialogRecyclerAdapter.DVH>() {
    var imageList = ArrayList<DialogImageData>()
    lateinit var click: ClickListener

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: DVH?, position: Int) {
        var image = imageList[position]
        holder?.onBind(image)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DVH {

        return DVH(LayoutInflater.from(parent?.context).inflate(R.layout.dialog_recycler_image, parent, false))
    }

    override fun getItemCount(): Int {
        if (imageList.size == 0) {
            return 0
        } else {
            return imageList.size
        }
    }

    fun addAll(list: List<DialogImageData>) {
        list.forEach {
            add(it)
        }
    }

    fun add(data: DialogImageData) {
        this.imageList.add(data)
        notifyItemInserted(imageList.size)
    }

    /*update row */
    fun update(position: Int, result: DialogImageData) {
        imageList.removeAt(position)
        notifyItemChanged(position)
        imageList.add(position, result)
        notifyItemChanged(position, imageList.size)
    }


    inner class DVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dialogImage: ImageView

        init {
            dialogImage = itemView.findViewById(R.id.dialog_recycler_iv)
        }

        @RequiresApi(Build.VERSION_CODES.M)
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun onBind(image: DialogImageData?) {
            Log.i("adapter onbind", image.toString())
            if (image != null) {
                Glide.with(itemView.context).load(image.imagePath).into(dialogImage)
                if (image.type) {
                    Log.i("adapter type true", image.type.toString())
                    dialogImage.foreground = itemView.context.getDrawable(R.drawable.image_border)
                } else {
                    dialogImage.foreground = itemView.context.getDrawable(R.drawable.image_ripple)
                }
            }

            dialogImage.setOnClickListener({

                click.imageClick(adapterPosition)
            })
        }

    }


    fun ClickListener(clickListener: ClickListener) {
        this.click = clickListener
    }

    interface ClickListener {
        fun imageClick(position: Int)
    }

    fun setPosition(position: Int) {
        var tempList = ArrayList<DialogImageData>()
        imageList.forEachIndexed { index, dialogImageData ->
            if (position == index) {
                Log.i("index", "position same  " + index)
                tempList.add(DialogImageData(imageList[position].id, imageList[position].des, imageList[position].imagePath, true))
            } else {
                Log.i("index", "position not same  " + index)
                tempList.add(DialogImageData(imageList[index].id, imageList[position].des, imageList[index].imagePath, false))
            }
        }
        imageList.clear()
        this.addAll(tempList)
        notifyDataSetChanged()
        Log.i("templist ", tempList.toString())
    }

}