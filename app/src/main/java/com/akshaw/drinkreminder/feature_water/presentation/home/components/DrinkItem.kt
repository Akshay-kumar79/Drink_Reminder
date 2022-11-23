package com.akshaw.drinkreminder.feature_water.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.R
import com.akshaw.drinkreminder.feature_water.domain.model.Drink
import com.akshaw.drinkreminder.feature_water.domain.model.convertUnit
import com.akshaw.drinkreminder.feature_water.presentation.home.WaterHomeViewModel
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun DrinkItem(
    modifier: Modifier = Modifier,
    drink: Drink,
    viewModel: WaterHomeViewModel,
) {
    Row(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 32.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.water_icon),
            contentDescription = "water icon",
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(start = 32.dp),
            text = "${ceil(drink.convertUnit(viewModel.state.waterUnit).waterIntake).roundToInt()} ${viewModel.state.waterUnit.name}",
            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 34.dp),
            text = "${
                if (drink.dateTime.hour <= 9)
                    "0" + drink.dateTime.hour.toString()
                else
                    drink.dateTime.hour
            }:" + "${
                if (drink.dateTime.minute <= 9)
                    "0" + drink.dateTime.minute.toString()
                else
                    drink.dateTime.minute
            }",
            fontFamily = FontFamily(Font(R.font.ubuntu_regular, FontWeight.Normal)),
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}