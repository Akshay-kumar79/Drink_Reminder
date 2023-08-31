package com.akshaw.drinkreminder.settingspresentation.settings.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.corecompose.theme.composables.RadioGroup
import com.akshaw.drinkreminder.corecompose.theme.composables.RadioGroupOrientation

@Preview(showSystemUi = true, apiLevel = 33)
@Composable
private fun ChangeUnitDialogPreview() {
    DrinkReminderTheme {
        ChangeUnitDialog(
            selectedWaterUnit = Constants.DEFAULT_WATER_UNIT,
            selectedWeightUnit = Constants.DEFAULT_WEIGHT_UNIT,
            onWaterUnitChange = {},
            onWeightUnitChange = {},
            onCancel = {},
            onConfirm = {}
        )
    }
}

@Composable
fun ChangeUnitDialog(
    modifier: Modifier = Modifier,
    selectedWaterUnit: WaterUnit,
    selectedWeightUnit: WeightUnit,
    onWaterUnitChange: (waterUnit: WaterUnit) -> Unit,
    onWeightUnitChange: (weightUnit: WeightUnit) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    
    val allWaterUnits = listOf(WaterUnit.ML, WaterUnit.FL_OZ)
    val allWeightUnits = listOf(WeightUnit.KG, WeightUnit.LBS)
    
    Dialog(
        onDismissRequest = { onCancel() }
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp, start = 24.dp, top = 24.dp),
                text = "Units",
                fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                fontSize = 20.sp,
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 24.dp),
                        text = stringResource(id = R.string.water_unit),
                        fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 24.dp),
                        text = stringResource(id = R.string.weight_unit),
                        fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                
                Column(
                    modifier = Modifier
                        .padding(end = 54.dp),
                ) {
                    // Water Unit radio group
                    RadioGroup(
                        items = allWaterUnits.map { it.name }.toSet(),
                        orientation = RadioGroupOrientation.Horizontal,
                        selectedPosition = allWaterUnits.indexOf(selectedWaterUnit),
                        onItemSelect = {
                            onWaterUnitChange(allWaterUnits.getOrNull(it) ?: WaterUnit.ML)
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Weight Unit radio group
                    RadioGroup(
                        items = allWeightUnits.map { it.name }.toSet(),
                        orientation = RadioGroupOrientation.Horizontal,
                        selectedPosition = allWeightUnits.indexOf(selectedWeightUnit),
                        onItemSelect = {
                            onWeightUnitChange(allWeightUnits.getOrNull(it) ?: WeightUnit.KG)
                        }
                    )
                }
                
                
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
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
                            onConfirm()
                        }
                        .padding(4.dp),
                    text = "OK",
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}