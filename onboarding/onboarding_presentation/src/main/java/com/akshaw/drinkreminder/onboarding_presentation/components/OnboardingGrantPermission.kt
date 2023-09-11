package com.akshaw.drinkreminder.onboarding_presentation.components

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akshaw.drinkreminder.core.R
import com.akshaw.drinkreminder.corecompose.theme.DrinkReminderTheme

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun PermissionPreview() {
    DrinkReminderTheme {
        Permission(
            permission = Permission("Exact Alarm", Manifest.permission.SCHEDULE_EXACT_ALARM, false),
            onGrantPermissionClick = {}
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun OnboardingGrantPermissionPreview() {
    DrinkReminderTheme {
        OnboardingGrantPermission(
            permissions = listOf(
                Permission("Notification", Manifest.permission.POST_NOTIFICATIONS, true),
                Permission("Exact Alarm", Manifest.permission.SCHEDULE_EXACT_ALARM, false),
            ),
            onGrantPermissionClick = {}
        )
    }
}


@Composable
fun OnboardingGrantPermission(
    modifier: Modifier = Modifier,
    permissions: List<Permission>,
    onGrantPermissionClick: (permission: Permission) -> Unit
) {
    Column(modifier = modifier) {
        permissions.forEachIndexed { index, permission ->
            Permission(
                permission = permission,
                onGrantPermissionClick = onGrantPermissionClick
            )
            
            if (index != permissions.lastIndex)
                Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun Permission(
    permission: Permission,
    onGrantPermissionClick: (permission: Permission) -> Unit
) {
    
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = permission.name,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_medium, FontWeight.Medium)),
            ),
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            if (permission.isGranted) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_granted_permission_icon),
                        contentDescription = "",
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "permission granted",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.ubuntu_regular, FontWeight.Normal)),
                        ),
                    )
                }
            } else {
                Button(
                    onClick = {
                        onGrantPermissionClick(permission)
                    },
                    contentPadding = PaddingValues(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.grant_permission),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


data class Permission(
    val name: String,
    val permission: String,   // Manifest permission
    val isGranted: Boolean
)