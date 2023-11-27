package com.akshaw.drinkreminder.waterpresentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.akshaw.drinkreminder.core.R

@Composable
fun DialogRemoveTrackableDrink(
    modifier: Modifier = Modifier,
    isDialogShowing: Boolean,
    onCancel: () -> Unit,
    onConfirm: () -> Unit

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
                        .padding(bottom = 16.dp, end = 24.dp, start = 24.dp, top = 24.dp),
                    text = "Remove Quantity",
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                    fontSize = 20.sp,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, end = 24.dp, start = 24.dp),
                    text = "Are you sure, you want to remove this quantity",
                    fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Medium)),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
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