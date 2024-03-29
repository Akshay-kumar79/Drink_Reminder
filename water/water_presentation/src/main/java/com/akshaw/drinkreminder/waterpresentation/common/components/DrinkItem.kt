package com.akshaw.drinkreminder.waterpresentation.common.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.domain.preferences.elements.WaterUnit
import com.akshaw.drinkreminder.core.util.formatted24HourTime
import com.akshaw.drinkreminder.core.domain.model.Drink
import com.akshaw.drinkreminder.core.domain.model.convertUnit
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun DrinkItem(
    modifier: Modifier = Modifier,
    drink: Drink,
    waterUnit: WaterUnit,
    onDeleteClick: () -> Unit
) {
    val view = LocalView.current

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
            text = "${ceil(drink.convertUnit(waterUnit).waterIntake).roundToInt()} ${waterUnit.text}",
            fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp),
            text = drink.dateTime.toLocalTime().formatted24HourTime(),
            fontFamily = FontFamily(Font(R.font.ubuntu_regular, FontWeight.Normal)),
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 20.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                    onDeleteClick()
                }
                .padding(8.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.cross_delete_icon),
            contentDescription = "delete"
        )
    }
}