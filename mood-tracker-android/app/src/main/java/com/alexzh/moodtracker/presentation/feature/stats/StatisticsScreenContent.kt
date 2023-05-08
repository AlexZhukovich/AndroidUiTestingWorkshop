package com.alexzh.moodtracker.presentation.feature.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.component.DateToHappinessChart
import com.alexzh.moodtracker.presentation.component.Section
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun StatisticsScreenContent(
    viewModel: StatisticsViewModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        val uiState = viewModel.state.value

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(
                    start = 0.dp,
                    top = paddingValues.calculateTopPadding(),
                    end = 0.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            item {
                Section(stringResource(R.string.statisticsScreen_moodChart_label)) {
                    ChartHeader(uiState, viewModel)

                    when {
                        uiState.loading -> LoadingScreen(paddingValues)
                        else -> LoadedSuccessfullyScreen(uiState)
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            delay(400)
            viewModel.onEvent(StatisticsEvent.LoadCurrentWeek)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun LoadedSuccessfullyScreen(
    state: StatisticsScreenState
) {
    if (state.dateToHappinessData.items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxWidth().height(72.dp * 2),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(72.dp),
                    painter = painterResource(R.drawable.ic_no_data),
                    contentDescription = null
                )
                Text(
                    stringResource(R.string.statisticsScreen_noData_label),
                    fontSize = 18.sp
                )
            }
        }
    } else {
        DateToHappinessChart(
            state.dateToHappinessData.items,
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
                .padding(8.dp),
            axisColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            showYAxis = false
        )
    }
}

@Composable
private fun ChartHeader(
    state: StatisticsScreenState,
    viewModel: StatisticsViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = state.dateToHappinessData.datePeriod,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = { viewModel.onEvent(StatisticsEvent.LoadPreviousWeek) }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_small_arrow_left),
                contentDescription = stringResource(R.string.statisticsScreen_previousWeek_label),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = { viewModel.onEvent(StatisticsEvent.LoadCurrentWeek) }
        ) {
            Icon(
                modifier = Modifier.size(24.dp).padding(),
                painter = painterResource(R.drawable.ic_nav_today),
                contentDescription = stringResource(R.string.statisticsScreen_currentWeek_label),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = { viewModel.onEvent(StatisticsEvent.LoadNextWeek) }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_small_arrow_right),
                contentDescription = stringResource(R.string.statisticsScreen_nextWeek_label),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun LoadingScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}