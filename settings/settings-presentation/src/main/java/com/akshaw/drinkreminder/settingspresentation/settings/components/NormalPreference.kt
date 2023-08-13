package com.akshaw.drinkreminder.settingspresentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.akshaw.drinkreminder.core.R

@Composable
fun NormalPreference(
    modifier: Modifier,
    iconResId: Int,
    text: String,
) {
    Row(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            modifier = Modifier
                .padding(vertical = 14.dp)
                .weight(1f),
            text = text,
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.roboto_regular,
                    FontWeight.Normal
                )
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}