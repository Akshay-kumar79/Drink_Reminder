package com.akshaw.drinkreminder.waterpresentation.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.core.util.ReminderType
import com.akshaw.drinkreminder.waterpresentation.reminders.components.AIReminderSection
import com.akshaw.drinkreminder.waterpresentation.reminders.components.TSReminderSection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun WaterReminderScreen(
    onBackClicked: () -> Unit
) {
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = .2f))
    ) {
        
        val allReminderTypes = listOf(ReminderType.TSReminder, ReminderType.AIReminder)
        var isSelectReminderTypeExpanded by remember { mutableStateOf(false) }
        var selectedReminderType by remember { mutableStateOf(allReminderTypes[0]) }
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier
                            .padding(start = 4.dp),
                        onClick = {
                            onBackClicked()
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.left_arrow_icon),
                            contentDescription = null
                        )
                    }
                    
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Reminders",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.ubuntu_bold,
                                FontWeight.Bold
                            )
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                expanded = isSelectReminderTypeExpanded,
                onExpandedChange = { isSelectReminderTypeExpanded = !isSelectReminderTypeExpanded },
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = selectedReminderType.getText(),
                    onValueChange = {},
                    label = { Text("Reminder Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSelectReminderTypeExpanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    textStyle = TextStyle(
                        fontSize = 14.sp
                    )
                )
                
                ExposedDropdownMenu(
                    modifier = Modifier
                        .exposedDropdownSize(true),
                    expanded = isSelectReminderTypeExpanded,
                    onDismissRequest = { isSelectReminderTypeExpanded = false },
                ) {
                    allReminderTypes.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = selectionOption.getText(),
                                    fontSize = 14.sp
                                )
                            },
                            onClick = {
                                selectedReminderType = selectionOption
                                isSelectReminderTypeExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 72.dp),
                text = selectedReminderType.getDescription(),
                fontSize = 10.sp,
                fontFamily = FontFamily(
                    Font(
                        R.font.roboto_regular,
                        FontWeight.Normal
                    )
                ),
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(14f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            when(selectedReminderType){
                ReminderType.AIReminder -> AIReminderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .clickable {
                        
                        }
                )
                ReminderType.TSReminder -> TSReminderSection()
                ReminderType.Invalid -> {
                
                }
            }
        }
        
    }
    
    
}