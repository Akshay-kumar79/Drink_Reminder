package com.akshaw.drinkreminder.settingspresentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R

@Composable
fun SectionOtherSettings(
    modifier: Modifier,
    onFaqClick: () -> Unit,
    onBugReportClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
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
                
                },
            mainText = "Version",
            // TODO show version from BUILD CONFIG
//            summaryText = BuildConfig.VERSION_NAME,
            summaryText = "1.0.0",
            iconResId = R.drawable.versions
        )
        
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}