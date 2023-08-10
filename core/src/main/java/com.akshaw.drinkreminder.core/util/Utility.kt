package com.akshaw.drinkreminder.core.util

import android.content.Context
import android.util.TypedValue

object Utility {

    fun getFloatFromDp(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            context.resources.displayMetrics
        )
    }

    fun getFloatFromSp(context: Context, sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, sp,
            context.resources.displayMetrics
        )
    }

}