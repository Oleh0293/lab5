package com.lab5.ui.screens.subjectsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.ui.theme.Lab5Theme
import org.koin.androidx.compose.getViewModel

@Composable
fun SubjectsListScreen(
    viewModel: SubjectsListViewModel = getViewModel(),
    onDetailsScreen: (Int) -> Unit,
) {
    val subjectsListState = viewModel.subjectListStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок екрану
        Text(
            text = "Предмети",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(subjectsListState.value) { subject ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDetailsScreen(subject.id) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Назва предмета
                        Text(
                            text = subject.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Preview can't display data from ViewModel
 */
@Preview(showBackground = true)
@Composable
private fun SubjectsListScreenPreview() {
    Lab5Theme {
//        SubjectsListScreen({})
    }
}