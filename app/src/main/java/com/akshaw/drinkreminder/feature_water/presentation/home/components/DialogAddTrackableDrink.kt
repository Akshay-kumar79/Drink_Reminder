package com.akshaw.drinkreminder.feature_water.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.feature_water.presentation.home.events.WaterHomeEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddTrackableDrink(
    modifier: Modifier = Modifier,
    isDialogShowing: Boolean,
    quantity: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onQuantityChange: (amount: String) -> Unit
) {
    if (isDialogShowing)
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
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
                    text = "Add Quantity",
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                    fontSize = 20.sp,
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, end = 24.dp, start = 24.dp),
                    value = quantity,
                    onValueChange = {
                        onQuantityChange(it)
                    },
                    label = {
                        Text(text = "quantity")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

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
                                onCancel()
                            }
                            .padding(4.dp),
                        text = "CANCEL",
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
            }
        }
}