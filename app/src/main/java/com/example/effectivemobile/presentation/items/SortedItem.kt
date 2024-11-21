package com.example.effectivemobile.presentation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.effectivemobile.R
import com.example.effectivemobile.presentation.viewmodel.HomeViewModel
import com.example.effectivemobile.presentation.viewmodel.SortedState
import com.example.effectivemobile.ui.theme.Green
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto

@Composable
fun SortedItem(
    homeViewModel: HomeViewModel
) {

    val sortedState = homeViewModel.sortedState.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var expandedDate by remember { mutableStateOf(false) }
    var expandedRating by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                }
                .align(Alignment.TopEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = sortedState.value.text,
                fontFamily = Roboto,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Green
            )
            Icon(
                painter = painterResource(id = R.drawable.sorted),
                contentDescription = null,
                tint = Green
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            expandedRating = true
                            expandedDate = false
                            expanded = false
                            homeViewModel.sortedByRating()
                        },
                    text = SortedState.SortedByRating.text,
                    color = if (expandedRating) Green else LightGrey,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = LightGrey
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            expandedRating = false
                            expandedDate = true
                            expanded = false
                            homeViewModel.sortedByDate()
                        },
                    text = SortedState.SortedByDate.text,
                    color = if (expandedDate) Green else LightGrey,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}