package com.akshaw.drinkreminder.settingspresentation.settings.dialogs

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.res.ResourcesCompat
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Utility
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.shawnlin.numberpicker.NumberPicker

@Preview
@Composable
private fun ChangeWeightDialogPreview() {
    DrinkReminderTheme {
        ChangeWeightDialog(
            weight = 42,
            onCancel = {},
            onConfirm = {},
            onWeightChange = {},
        )
    }
}

@Suppress("MagicNumber")
@Composable
fun ChangeWeightDialog(
    modifier: Modifier = Modifier,
    weight: Int,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onWeightChange: (weight: Int) -> Unit,
) {
    val view = LocalView.current
    
    Dialog(
        onDismissRequest = {
            onCancel()
        }
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp, start = 24.dp, top = 24.dp),
                text = stringResource(id = R.string.weight),
                fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                fontSize = 20.sp,
            )
            
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                AndroidView(
                    modifier = Modifier
                        .width(78.dp)
                        .height(128.dp)
                        .padding(start = 24.dp, end = 24.dp),
                    factory = { context ->
                        NumberPicker(context).apply {
                            formatter = NumberPicker.Formatter {
                                if (it in 0..9) {
                                    "0$it"
                                } else {
                                    "$it"
                                }
                            }
                            setDividerThickness(0)
                            fadingEdgeStrength = .1f
                            minValue = Constants.MIN_WEIGHT
                            maxValue = Constants.MAX_WEIGHT
                            selectedTextColor = context.getColor(R.color.on_background)
                            selectedTextSize = Utility.getFloatFromSp(context, 14f)
                            textColor = context.getColor(R.color.on_background).apply { alpha = 0.5f }
                            textSize = Utility.getFloatFromSp(context, 14f)
                            wheelItemCount = 3
                            value = weight
                            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
                            setSelectedTypeface(ResourcesCompat.getFont(context, R.font.roboto_regular))
                            setOnValueChangedListener { _, _, newVal ->
                                onWeightChange(newVal)
                            }
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onCancel()
                        }
                        .padding(4.dp),
                    text = stringResource(id = R.string.cancel),
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.width(36.dp))
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onConfirm()
                        }
                        .padding(4.dp),
                    text = "OK",
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}