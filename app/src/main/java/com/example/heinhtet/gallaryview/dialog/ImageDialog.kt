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
    lateinit var desTv: TextView
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
        desTv = v
                .findViewById(R.id.description_tv)
//        lblTitle = v.findViewById<TextView>(R.id.title) as TextView
//        lblDate = v.findViewById<TextView>(R.id.date) as TextView
        dialogAdpter = DialogImageAdapter(fragmentActivity)
        dAdapter = DialogRecyclerAdapter()
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        var layout = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerView.setLayoutManager(MyLinearManagerWithSmoothScroller(fragmentActivity))
        recyclerView.adapter = dAdapter
        var exist = v.findViewById<ImageView>(R.id.exist) as ImageView
        exist.setOnClickListener {
            this.dismiss()
        }

        getImages = arguments?.getSerializable("image_list") as ArrayList<DialogImageData>
        name = arguments?.getString("name")
        selectedPosition = arguments!!.getInt("position")
        //  dialogAdpter.addAll(testImageArray)
        dialogAdpter.addAll(getImages)
        dAdapter.ClickListener(this)
        dAdapter.addAll(getImages)
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
        desTv.text = "${getImages[position].des} of $position"
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
}