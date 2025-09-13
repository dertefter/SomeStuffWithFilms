package com.dertefter.somestuffwithfilms.fragments.films

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    context: Context,
    private val spanCount: Int,
    @DimenRes spacingRes: Int,
    @DimenRes edgeSpacingRes: Int
) : RecyclerView.ItemDecoration() {

    private val spacing: Int = context.resources.getDimensionPixelSize(spacingRes)
    private val edgeSpacing: Int = context.resources.getDimensionPixelSize(edgeSpacingRes)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = if (column == 0) {
            edgeSpacing
        } else {
            spacing / 2
        }
        outRect.right = if (column == spanCount - 1) {
            edgeSpacing
        } else {
            spacing / 2
        }
    }
}
