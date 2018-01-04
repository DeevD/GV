package com.example.heinhtet.gallaryview.dialog.dialogImage

import android.content.Context
import android.support.v7.widget.LinearSmoothScroller.SNAP_TO_START
import android.graphics.PointF
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager


/**
 * Created by heinhtet on 11/18/2017.
 */
class MyLinearManagerWithSmoothScroller : LinearLayoutManager {

    constructor(context: Context) : super(context, LinearLayoutManager.HORIZONTAL, false) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?,
                                        position: Int) {
        val smoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private inner class TopSnappedSmoothScroller(context: Context) : LinearSmoothScroller(context) {

        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@MyLinearManagerWithSmoothScroller
                    .computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }

        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
}