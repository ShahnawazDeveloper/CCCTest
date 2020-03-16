package com.hayati.staffapp.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions


fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun AppCompatActivity.changeFragment(
    containerId: Int,
    fragment: Fragment,
    tag: String = fragment.javaClass.simpleName
) {
    supportFragmentManager.beginTransaction().replace(containerId, fragment, tag).commit()
}

fun Activity.hideKeyboard() {
    var inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager;
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    var inputMethodManager =
        this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager;
    inputMethodManager.hideSoftInputFromWindow(this.activity?.currentFocus?.windowToken, 0)
}


fun Activity.showToast(message: String?) {
    showToast(this, message)
}

fun Fragment.showToast(message: String?) {
    showToast(activity, message)
}

fun RecyclerView.setVerticalItemDecoration(space: Int, initialPadding: Int) {
    addItemDecoration(
        VerticalSpacesItemDecoration(
            space, initialPadding
        )
    )
}

private fun showToast(context: Context?, message: String?) {
    if (null == context || null == message)
        return
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    //Toast(context).showOnTop(context, message)
}

fun RecyclerView.setHorizontalManager() {
    layoutManager =
        LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }

}

fun RecyclerView.setVerticalManager() {
    layoutManager =
        LinearLayoutManager(context)

}

fun RecyclerView.setGridLayoutManager(columns: Int) {
    layoutManager = GridLayoutManager(context, columns)
}


class VerticalSpacesItemDecoration(
    private val space: Int,
    private val initialPadding: Int = 0
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == 0) {
            outRect.top = if (initialPadding == 0) space else initialPadding
        } else {
            outRect.top = space
        }


        if (itemPosition == state.itemCount - 1) {
            outRect.bottom = if (initialPadding == 0) space else initialPadding
        } else {
            outRect.bottom = 0
        }
    }
}


fun RecyclerView.setGridItemDecoration(
    spanCount: Int,
    horizontalSpacing: Int,
    verticalSpacing: Int,
    includeEdge: Boolean
) {
    addItemDecoration(
        GridSpacingItemDecoration(
            spanCount, horizontalSpacing, verticalSpacing, includeEdge
        )
    )
}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val horizontalSpacing: Int,
    private val verticalSpacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left =
                horizontalSpacing - column * horizontalSpacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * horizontalSpacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = verticalSpacing
            }
            outRect.bottom = verticalSpacing // item bottom
        } else {
            outRect.left =
                column * horizontalSpacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                horizontalSpacing - (column + 1) * horizontalSpacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = verticalSpacing // item top
            }
        }
    }
}


fun RecyclerView.setHorizontalItemDecoration(space: Int, initialPadding: Int, isRtl: Boolean) {
    addItemDecoration(
        HorizontalSpacesItemDecoration(
            space, initialPadding, isRtl
        )
    )
}

internal class HorizontalSpacesItemDecoration(
    private val space: Int,
    private val initialPadding: Int = 0,
    private val isRtl: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        Log.e("ItemPadding", "itemPosition: $itemPosition count: ${parent.adapter?.itemCount}")

        if (isRtl) {
            if (itemPosition == 0) {
                outRect.left = if (initialPadding == 0) space else initialPadding
                outRect.right = initialPadding
            } else {
                outRect.left = space
            }

        } else {
            if (itemPosition == 0) {
                outRect.right = if (initialPadding == 0) space else initialPadding
                outRect.left = initialPadding
            } else {
                outRect.right = space
            }

        }
    }
}

fun ImageView.loadImage(context: Context, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(context).load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(this)
    }
}

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))


