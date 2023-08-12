package com.akshaw.drinkreminder.onboarding_presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.core.R

@Composable
@Preview
private fun Preview() {
    DrinkReminderTheme {
        val selected = remember {
            mutableStateOf(true)
        }
        
        GenderSelectable(
            text = "Male",
            drawableRes = R.drawable.ic_icon_awesome_male,
            isSelected = selected.value,
            cardColor = MaterialTheme.colorScheme.primary.copy(alpha = .15f),
            selectedCardColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            itemColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            selectedItemColor = MaterialTheme.colorScheme.onBackground,
            onClick = { selected.value = !selected.value },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSecondary,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_medium,
                        FontWeight.Medium
                    )
                ),
                fontSize = 20.sp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderSelectable(
    text: String,
    drawableRes: Int,
    isSelected: Boolean,
    cardColor: Color,
    selectedCardColor: Color,
    itemColor: Color,
    selectedItemColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) selectedCardColor else cardColor
        ),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row {
            Image(
                painter = painterResource(drawableRes),
                contentDescription = text,
                modifier = Modifier.padding(24.dp),
                colorFilter = ColorFilter.tint(
                    if (isSelected)
                        selectedItemColor
                    else
                        itemColor
                )
            )
            Text(
                text = text,
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically),
                style = textStyle.copy(
                    color = if(isSelected) selectedItemColor else itemColor
                )
            )
            if (isSelected) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(24.dp)
                )
            }
        }
    }

}

