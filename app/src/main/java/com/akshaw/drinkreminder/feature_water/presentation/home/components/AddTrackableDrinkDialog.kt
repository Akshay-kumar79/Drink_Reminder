package com.akshaw.drinkreminder.feature_water.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeEvent
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrackableDrinkDialog(
    modifier: Modifier = Modifier,
    viewModel: WaterHomeViewModel
) {
    if (viewModel.state.isAddTrackableDrinkDialogShowing)
        Dialog(
            onDismissRequest = {
                viewModel.onEvent(WaterHomeEvent.OnAddTrackableDrinkCancel)
            }
        ) {
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    text = "Add Quantity",
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                    fontSize = 20.sp,
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp, end = 24.dp, start = 24.dp),
                    value = viewModel.state.addQuantityAmount,
                    onValueChange = {
                        viewModel.onEvent(WaterHomeEvent.OnAddQuantityAmountChange(it))
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
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                viewModel.onEvent(WaterHomeEvent.OnAddTrackableDrinkCancel)
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
                                viewModel.onEvent(WaterHomeEvent.OnAddTrackableDrinkConfirm)
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