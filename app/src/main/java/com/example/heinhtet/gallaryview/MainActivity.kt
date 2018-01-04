package com.example.heinhtet.gallaryview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dev.sample.features.dialogImage.ImageDialog
import com.example.heinhtet.gallaryview.dialog.DialogImageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        show_gallary.setOnClickListener {
            showGallaryDialog()
        }
    }

    private fun showGallaryDialog() {
        var dialogImage = ArrayList<DialogImageData>()
        val bundle = Bundle()
        bundle.putSerializable("images", dialogImage)
        bundle.putInt("position", 0)
        bundle.putString("name", "hello")
        bundle.putSerializable("image_list", getPersonList(3))
        var ft = supportFragmentManager.beginTransaction()
        var newFragment = ImageDialog.newInstance()
        newFragment.setArguments(bundle)
        newFragment.show(ft, "slideshow")
    }

    fun getPersonList(page: Int): ArrayList<DialogImageData> {
        val arr = ArrayList<DialogImageData>()
        arr.add(DialogImageData(4, "afdafdasfsf", "http://i2.hdslb.com/52_52/user/61175/6117592/myface.jpg", false))
        arr.add(DialogImageData(9, "afdafdasfsf", "http://i1.hdslb.com/52_52/user/6738/673856/myface.jpg", false))
        arr.add(DialogImageData(8, "afdafdasfsf", "http://i1.hdslb.com/account/face/1467772/e1afaf4a/myface.png", false))
        arr.add(DialogImageData(7, "afdafdasfsf", "http://i0.hdslb.com/52_52/user/18494/1849483/myface.jpg", false))
        arr.add(DialogImageData(5, "afdafdasfsf", "http://i0.hdslb.com/52_52/account/face/4613528/303f4f5a/myface.png", false))
        arr.add(DialogImageData(5, "afdafdasfsf", "http://i0.hdslb.com/52_52/account/face/611203/76c02248/myface.png", false))
        arr.add(DialogImageData(43, "afdafdasfsf", "http://i2.hdslb.com/52_52/user/46230/4623018/myface.jpg", false))
        arr.add(DialogImageData(43, "afdafdasfsf", "http://i2.hdslb.com/52_52/user/66723/6672394/myface.jpg", false))
        arr.add(DialogImageData(1, "afdafdasfsf", "http://i1.hdslb.com/user/3039/303946/myface.jpg", false))
        arr.add(DialogImageData(2, "afdafdasfsf", "http://i2.hdslb.com/account/face/9034989/aabbc52a/myface.png", false))
        arr.add(DialogImageData(2, "afdafdasfsfafdafdasfsf", "http://i0.hdslb.com/account/face/1557783/8733bd7b/myface.png", false))
        arr.add(DialogImageData(1, "afdafdasfsf",
                "http://i2.hdslb.com/user/3716/371679/myface.jpg",
                false))
        return arr
    }
}
