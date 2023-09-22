package com.akshaw.drinkreminder.settingspresentation.settings.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.settings.presentation.BuildConfig

@Composable
fun SectionOtherSettings(
    modifier: Modifier,
    onFaqClick: () -> Unit,
    onBugReportClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    val view = LocalView.current
    
    Column(
        modifier = modifier
    ) {
        
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = "OTHER",
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.ubuntu_bold,
                    FontWeight.Bold
                )
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
    
//        // FAQ preference
//        NormalPreference(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    onFaqClick()
//                },
//            text = "FAQ",
//            iconResId = R.drawable.question
//        )
//
//        // Bug report & Feedback preference
//        NormalPreference(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    onBugReportClick()
//                },
//            text = "Bug report & Feedback",
//            iconResId = R.drawable.report
//        )
        
        // Privacy policy preference
        NormalPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onPrivacyPolicyClick()
                },
            text = "Privacy policy",
            iconResId = R.drawable.shield_fill_exclamation
        )
        
        // Version preference
        SummaryPreference(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                },
            mainText = "Version",
            summaryText = BuildConfig.VERSION_NAME,
            iconResId = R.drawable.versions
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}