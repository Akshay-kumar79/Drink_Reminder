package com.akshaw.drinkreminder.waterpresentation.home.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun TrackableDrinkSections(
    modifier: Modifier = Modifier
) {
    
    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures() {
                
                }
                
                detectDragGestures { change, dragAmount ->
                    Log.v("MYTAG", "detectDragGestures")
                }
                
                detectHorizontalDragGestures { change, dragAmount ->
                
                }
            }
    ) {
    
    
    }
    
}