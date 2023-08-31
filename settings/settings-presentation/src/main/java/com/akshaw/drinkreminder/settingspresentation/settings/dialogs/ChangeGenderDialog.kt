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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.Constants
import com.akshaw.drinkreminder.core.util.Gender
import com.akshaw.drinkreminder.core.util.WaterUnit
import com.akshaw.drinkreminder.core.util.WeightUnit
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.corecompose.theme.composables.RadioGroup
import com.akshaw.drinkreminder.corecompose.theme.composables.RadioGroupOrientation


@Preview
@Composable
private fun ChangeGenderDialogPreview() {
    DrinkReminderTheme {
        ChangeGenderDialog(
            selectedGender = Constants.DEFAULT_GENDER,
            onGenderSelected = {},
            onCancel = {},
        )
    }
}

@Composable
fun ChangeGenderDialog(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    onGenderSelected: (gender: Gender) -> Unit,
    onCancel: () -> Unit,
) {
    val allGenders = listOf(Gender.Female, Gender.Male)
    
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
                text = stringResource(id = R.string.gender),
                fontFamily = FontFamily(Font(R.font.ubuntu_bold, FontWeight.Bold)),
                fontSize = 20.sp,
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            
            // Water Unit radio group
            RadioGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp),
                items = allGenders.map { it.name }.toSet(),
                orientation = RadioGroupOrientation.Vertical,
                selectedPosition = allGenders.indexOf(selectedGender),
                radioButtonSpace = 12.dp,
                onItemSelect = {
                    onGenderSelected(allGenders.getOrNull(it) ?: Gender.Male)
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
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
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}