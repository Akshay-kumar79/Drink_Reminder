package com.akshaw.drinkreminder.settingspresentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme
import com.akshaw.drinkreminder.settingspresentation.settings.components.*
import com.akshaw.drinkreminder.settingspresentation.settings.dialogs.ChangeUnitDialog

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onRemindersClick: () -> Unit,
    onFaqClick: () -> Unit,
    onBugReportClick: () -> Unit
) {
    
    val isUnitDialogShowing by viewModel.isChangeUnitDialogShowing.collectAsState()
    val selectedWaterUnit by viewModel.selectedWaterUnit.collectAsState()
    val selectedWeightUnit by viewModel.selectedWeightUnit.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = .2f))
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                text = "Settings",
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.ubuntu_bold,
                        FontWeight.Bold
                    )
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onRemindersClick()
                    }
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    imageVector = ImageVector.vectorResource(id = R.drawable.notification),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .weight(1f),
                    text = "Reminders",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.roboto_regular,
                            FontWeight.Normal
                        )
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(14.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        SectionGeneralSettings(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            onUnitClick = {
                viewModel.showUnitDialog()
            },
            onDailyIntakeClick = {
            
            }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        SectionPersonalInformation(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            onGenderClick = {
            
            },
            onAgeClick = {
            
            },
            onWeightClick = {
            
            },
            onSleepTimeClick = {
            
            },
            onWakeUpTimeClick = {
            
            }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        SectionOtherSettings(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            onFaqClick = {
                onFaqClick()
            },
            onBugReportClick = {
                onBugReportClick()
            },
            onPrivacyPolicyClick = {
            
            }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        SectionCriticalSettings(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            onResetDataClick = {
            
            }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
    }
    
    if (isUnitDialogShowing) {
        ChangeUnitDialog(
            selectedWaterUnit = selectedWaterUnit,
            selectedWeightUnit = selectedWeightUnit,
            onWaterUnitChange = {
                viewModel.changeSelectedUnit(waterUnit = it)
            },
            onWeightUnitChange = {
                viewModel.changeSelectedUnit(weightUnit = it)
            },
            onConfirm = { viewModel.saveNewUnits() },
            onCancel = { viewModel.dismissUnitDialog() }
        )
    }
}