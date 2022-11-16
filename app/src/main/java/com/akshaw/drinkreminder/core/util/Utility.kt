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

//    fun Double.toMl(): Double = this * Constants.FLOZ_TO_ML
//    fun Double.toFLOZ(): Double = this * Constants.ML_TO_FLOZ
//    fun Double.toKG(): Double = this * Constants.LBS_TO_KG
//    fun Double.toLBS(): Double = this * Constants.KG_TO_LBS

}