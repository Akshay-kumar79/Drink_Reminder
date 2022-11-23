package com.akshaw.drinkreminder.feature_water.presentation.home.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeViewModel
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt

@Composable
fun TrackableDrinkSection(
    modifier: Modifier = Modifier,
    context: Context,
    viewModel: WaterHomeViewModel
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    viewModel.onEvent(WaterHomeEvent.OnRemoveTrackableDrinkClick)
                }
                .height(34.dp)
                .width(52.dp)
                .background(MaterialTheme.colorScheme.primary),

            ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = ImageVector.vectorResource(R.drawable.minus_icon),
                contentDescription = "Remove Quantity",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        RemoveTrackableDrinkDialog(viewModel = viewModel)

        Box {
            Box(
                modifier = Modifier
                    .height(45.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .align(Alignment.Center)
            )

            val surfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
            val primary = MaterialTheme.colorScheme.primary.toArgb()
            key(viewModel.state.trackableDrinks.size) {
                AndroidView(
                    modifier = Modifier
                        .align(Alignment.Center),
                    factory = {
                        NumberPicker(context).apply {
                            setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                            setDividerThickness(Utility.getFloatFromDp(context, 0f).roundToInt())
                            fadingEdgeStrength = 0.3f
                            minValue = 0
                            maxValue = viewModel.state.trackableDrinks.lastIndex.let { if (it < 0) 0 else it }
                            selectedTextColor = surfaceVariant
                            selectedTextSize = Utility.getFloatFromSp(context, 16f)
                            textColor = primary
                            displayedValues = viewModel.state.trackableDrinks.map {
                                "${it.amount.toInt()} ${viewModel.state.waterUnit.name}"
                            }.toTypedArray().let {
                                if (it.isEmpty())
                                    arrayOf("")
                                else
                                    it
                            }
                            textSize = Utility.getFloatFromSp(context, 16f)
                            wheelItemCount = 5
                            value = viewModel.state.trackableDrinks.indexOf(viewModel.state.selectedTrackableDrink).let { if (it < 0) 0 else it}
                            typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.ubuntu_medium))
                            setOnValueChangedListener { picker, oldVal, newVal ->
                                viewModel.onEvent(
                                    WaterHomeEvent.OnTrackableDrinkChange(
                                        viewModel.state.trackableDrinks[newVal]
                                    )
                                )
                            }
                        }
                    }
                )
            }

        }


        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    viewModel.onEvent(WaterHomeEvent.OnAddTrackableDrinkClick)
                }
                .height(34.dp)
                .width(52.dp)
                .background(MaterialTheme.colorScheme.primary),

            ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = ImageVector.vectorResource(R.drawable.plus_icon),
                contentDescription = "Add Quantity",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        AddTrackableDrinkDialog(viewModel = viewModel)
    }
}