package com.akshaw.drinkreminder.waterpresentation.home.components

import android.content.Context
import android.view.SoundEffectConstants
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.domain.model.TrackableDrink
import com.akshaw.drinkreminder.corecompose.utils.bounceClick
import com.akshaw.drinkreminder.waterpresentation.home.events.WaterHomeEvent
import com.akshaw.drinkreminder.waterpresentation.home.WaterHomeViewModel
import com.akshaw.drinkreminder.waterpresentation.home.events.DialogAddTrackableDrinkEvent
import com.akshaw.drinkreminder.waterpresentation.home.events.DialogRemoveTrackableDrinkEvent
import com.shawnlin.numberpicker.NumberPicker
import kotlin.math.roundToInt

@Suppress("MagicNumber")
@Composable
fun TrackableDrinkSection(
    modifier: Modifier = Modifier,
    context: Context,
    viewModel: WaterHomeViewModel,
    trackableDrinks: List<TrackableDrink>,
    selectedTrackableDrink: TrackableDrink,
    waterUnit: WaterUnit,
    isAddTrackableDrinkDialogShowing: Boolean,
    addTrackableDrinkDialogQuantity: String,
    isRemoveTrackableDrinkDialogShowing: Boolean
) {
    
    val view = LocalView.current
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .bounceClick {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    viewModel.onEvent(DialogRemoveTrackableDrinkEvent.OnRemoveTrackableDrinkClick)
                }
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(10.dp))
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
        
        DialogRemoveTrackableDrink(
            isDialogShowing = isRemoveTrackableDrinkDialogShowing,
            onCancel = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(DialogRemoveTrackableDrinkEvent.OnDismiss)
            },
            onConfirm = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(DialogRemoveTrackableDrinkEvent.OnConfirmClick)
            }
        )
        
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(45.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            
            val surfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
            val primary = MaterialTheme.colorScheme.primary.toArgb()
            key(trackableDrinks.size) {
                AndroidView(
                    factory = {
                        NumberPicker(context).apply {
                            setDividerDistance(Utility.getFloatFromDp(context, 60f).roundToInt())
                            setDividerThickness(Utility.getFloatFromDp(context, 0f).roundToInt())
                            fadingEdgeStrength = 0.3f
                            minValue = 0
                            maxValue = trackableDrinks.lastIndex.let { if (it < 0) 0 else it }
                            selectedTextColor = surfaceVariant
                            selectedTextSize = Utility.getFloatFromSp(context, 16f)
                            textColor = primary
                            displayedValues = trackableDrinks.map {
                                "${it.amount.toInt()} ${waterUnit.text}"
                            }.toTypedArray().let {
                                if (it.isEmpty())
                                    arrayOf("")
                                else
                                    it
                            }
                            wrapSelectorWheel = trackableDrinks.size >= 5
                            textSize = Utility.getFloatFromSp(context, 16f)
                            wheelItemCount = 5
                            value = trackableDrinks.indexOf(selectedTrackableDrink).let { if (it < 0) 0 else it }
                            typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.roboto_medium))
                            setOnValueChangedListener { _, _, newVal ->
                                viewModel.onEvent(
                                    WaterHomeEvent.OnTrackableDrinkChange(
                                        trackableDrinks[newVal]
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
                .bounceClick {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    viewModel.onEvent(DialogAddTrackableDrinkEvent.OnAddTrackableDrinkClick)
                }
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(10.dp))
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
        
        DialogAddTrackableDrink(
            isDialogShowing = isAddTrackableDrinkDialogShowing,
            quantity = addTrackableDrinkDialogQuantity,
            onCancel = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(DialogAddTrackableDrinkEvent.OnDismiss)
            },
            onQuantityChange = {
                viewModel.onEvent(DialogAddTrackableDrinkEvent.OnQuantityAmountChange(it))
            },
            onConfirm = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                viewModel.onEvent(DialogAddTrackableDrinkEvent.OnConfirmClick)
            }
        )
    }
}