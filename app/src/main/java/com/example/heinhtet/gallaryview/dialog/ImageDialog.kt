package com.dev.sample.features.dialogImage

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dev.sample.features.homefrag.DialogImageAdapter
import com.example.heinhtet.gallaryview.R
import com.example.heinhtet.gallaryview.dialog.DialogImageData
import com.example.heinhtet.gallaryview.dialog.DialogRecyclerAdapter
import com.example.heinhtet.gallaryview.dialog.dialogImage.MyLinearManagerWithSmoothScroller
import com.jude.easyrecyclerview.EasyRecyclerView
import kotlinx.android.synthetic.main.dialog_image_layout.*


class ImageDialog : DialogFragment(), DialogRecyclerAdapter.ClickListener {


    override fun imageClick(position: Int) {
        setCurrentItem(position)
        displayMetaInfo(position)
    }

    private val TAG = ImageDialog::class.java.getSimpleName()
    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var imageTotalTv: TextView? = null
    private var lblTitle: TextView? = null
    private var lblDate: TextView? = null
    lateinit var recyclerView: RecyclerView
    lateinit var dialogAdpter: DialogImageAdapter
    lateinit var dAdapter: DialogRecyclerAdapter
    private var selectedPosition = 0
    var name: String? = null
    var testImageArray = ArrayList<DialogImageData>()
    lateinit var fragmentActivity: FragmentActivity

    var getImages = ArrayList<DialogImageData>()

    companion object {

        open fun newInstance(): ImageDialog {
            return ImageDialog()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentActivity = context as FragmentActivity

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_layout, container, false)
        viewPager = v.findViewById(R.id.viewpager)
        imageTotalTv = v.findViewById<TextView>(R.id.image_total_count_tv)
//        lblTitle = v.findViewById<TextView>(R.id.title) as TextView
//        lblDate = v.findViewById<TextView>(R.id.date) as TextView
        dialogAdpter = DialogImageAdapter(fragmentActivity)
        dAdapter = DialogRecyclerAdapter()
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        var layout = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerView.setLayoutManager(MyLinearManagerWithSmoothScroller(fragmentActivity))
        recyclerView.adapter = dAdapter
        dAdapter.addAll(getPersonList(3))
        var exist = v.findViewById<ImageView>(R.id.exist) as ImageView
        exist.setOnClickListener {
            this.dismiss()
        }


        testImageArray.addAll(getPersonList(6))

        getImages = arguments?.getSerializable("image_list") as ArrayList<DialogImageData>
        name = arguments?.getString("name")
        selectedPosition = arguments!!.getInt("position")
        //  dialogAdpter.addAll(testImageArray)
        dialogAdpter.addAll(getImages)
        dAdapter.ClickListener(this)

        Log.e(TAG, "position: " + selectedPosition)
        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
        setCurrentItem(selectedPosition)

        return v
    }

    private fun setCurrentItem(position: Int) {
        viewPager!!.setCurrentItem(position, false)
        displayMetaInfo(position)
        dAdapter.setPosition(position)
    }

    //	page change listener
    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            displayMetaInfo(position)
            recyclerView.smoothScrollToPosition(position)
            dAdapter.setPosition(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    private fun displayMetaInfo(position: Int) {
        imageTotalTv!!.text = (position + 1).toString() + " of " + getImages!!.size
//        lblTitle!!.setText(name)
//        lblTitle!!.text = testImageArray[position].description

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }


    //	adapter
    inner class MyViewPagerAdapter : PagerAdapter() {

        private var layoutInflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater!!.inflate(R.layout.dialog_image, container, false)

            val imageViewPreview = view.findViewById<ImageView>(R.id.dialog_iv) as ImageView

            Glide.with(fragmentActivity).load(getImages[position].imagePath).into(imageViewPreview)

            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return getImages.size
        }

        override
        fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj as View
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    fun getPersonList(page: Int): ArrayList<DialogImageData> {
        val arr = ArrayList<DialogImageData>()
        arr.add(DialogImageData(4, "http://i2.hdslb.com/52_52/user/61175/6117592/myface.jpg", false))
        arr.add(DialogImageData(9, "http://i1.hdslb.com/52_52/user/6738/673856/myface.jpg", false))
        arr.add(DialogImageData(8, "http://i1.hdslb.com/account/face/1467772/e1afaf4a/myface.png", false))
        arr.add(DialogImageData(7, "http://i0.hdslb.com/52_52/user/18494/1849483/myface.jpg", false))
        arr.add(DialogImageData(5, "http://i0.hdslb.com/52_52/account/face/4613528/303f4f5a/myface.png", false))
        arr.add(DialogImageData(5, "http://i0.hdslb.com/52_52/account/face/611203/76c02248/myface.png", false))
        arr.add(DialogImageData(43, "http://i2.hdslb.com/52_52/user/46230/4623018/myface.jpg", false))
        arr.add(DialogImageData(43, "http://i2.hdslb.com/52_52/user/66723/6672394/myface.jpg", false))
        arr.add(DialogImageData(1, "http://i1.hdslb.com/user/3039/303946/myface.jpg", false))
        arr.add(DialogImageData(2, "http://i2.hdslb.com/account/face/9034989/aabbc52a/myface.png", false))
        arr.add(DialogImageData(2, "http://i0.hdslb.com/account/face/1557783/8733bd7b/myface.png", false))
        arr.add(DialogImageData(1,
                "http://i2.hdslb.com/user/3716/371679/myface.jpg",
                false))
        return arr
    }
}