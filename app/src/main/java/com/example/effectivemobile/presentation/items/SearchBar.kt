package com.example.effectivemobile.presentation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.effectivemobile.presentation.viewmodel.SearchViewModel
import com.example.effectivemobile.ui.theme.LightGrey
import com.example.effectivemobile.ui.theme.Roboto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchViewModel: SearchViewModel,
    action: () -> Unit
) {

    val searchText = searchViewModel.searchText.collectAsState()
    val isSearching = searchViewModel.isSearching.collectAsState()
    val coursesList = searchViewModel.coursesList.collectAsState()

    androidx.compose.material3.SearchBar(
        query = searchText.value,
        onQueryChange = searchViewModel::onSearchTextChange,
        onSearch = searchViewModel::onSearchTextChange,
        active = isSearching.value,
        onActiveChange = { searchViewModel.onToogleSearch() },
        placeholder = {
            Text(
                text = "Search courses...",
                fontFamily = Roboto,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = LightGrey.copy(0.5f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (isSearching.value) {
                Icon(
                    modifier = Modifier.clickable {
                        if (searchText.value.isNotEmpty()) {
                            searchViewModel.onSearchTextChange("")
                        } else {
                            searchViewModel.onToogleSearch()
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        LazyColumn(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(coursesList.value) { courseInfo ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Sharp.Refresh,
                        contentDescription = null,
                        tint = LightGrey
                    )
                    Text(
                        text = courseInfo.title,
                        fontFamily = Roboto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        maxLines = 1,
                        modifier = Modifier
                            .clickable {
                                searchViewModel.takeCourseById(courseInfo.id)
                                action()
                            }
                    )
                }
            }
        }
    }
}