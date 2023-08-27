package com.akshaw.drinkreminder.corecompose.theme.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme

@Preview(showBackground = true)
@Composable
private fun RadioGroupPreview() {
    DrinkReminderTheme {
        val items = remember { setOf("ml", "fl oz") }
        var pos by remember { mutableStateOf(0) }
        
        RadioGroup(
            items = items,
            orientation = RadioGroupOrientation.Horizontal,
            selectedPosition = pos,
            radioButtonSpace = 28.dp,
            onItemSelect = {
                pos = it
            }
        )
    }
}

enum class RadioGroupOrientation {
    Vertical,
    Horizontal
}

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    items: Set<String>,
    orientation: RadioGroupOrientation,
    selectedPosition: Int,
    radioButtonSpace: Dp = 28.dp,
    onItemSelect: (pos: Int) -> Unit
) {
    if (items.isNotEmpty()) {
        when (orientation) {
            RadioGroupOrientation.Vertical -> {
                Column(
                    modifier = modifier
                ) {
                    renderRadioButtons(
                        items = items,
                        orientation = orientation,
                        selectedPosition = selectedPosition,
                        radioButtonSpace = radioButtonSpace,
                        onItemSelect = onItemSelect
                    )
                }
            }
            
            RadioGroupOrientation.Horizontal -> {
                Row(
                    modifier = modifier
                ) {
                    renderRadioButtons(
                        items = items,
                        orientation = orientation,
                        selectedPosition = selectedPosition,
                        radioButtonSpace = radioButtonSpace,
                        onItemSelect = onItemSelect
                    )
                }
            }
        }
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun renderRadioButtons(
    items: Set<String>,
    orientation: RadioGroupOrientation,
    selectedPosition: Int,
    radioButtonSpace: Dp,
    onItemSelect: (pos: Int) -> Unit
) {
    items.forEachIndexed { index, text ->
        
        val itemPadding = when (orientation) {
            RadioGroupOrientation.Vertical -> PaddingValues(top = if (index > 0) radioButtonSpace else 0.dp)
            RadioGroupOrientation.Horizontal -> PaddingValues(start = if (index > 0) radioButtonSpace else 0.dp)
        }
        
        CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
            Row(
                modifier = Modifier
                    .padding(itemPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = if (selectedPosition >= 0) selectedPosition == index else index == 0,
                    onClick = { onItemSelect(index) }
                )
                
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = text,
                    fontFamily = FontFamily(Font(R.font.roboto_light, FontWeight.Light)),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}