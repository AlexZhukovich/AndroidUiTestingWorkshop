package com.alexzh.moodtracker.presentation.feature.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
class TodayFragment : Fragment() {

    private val viewModel: TodayViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                AppTheme {
                    TodayScreenContent(
                        viewModel = viewModel,
                        onAdd = {
                            findNavController().navigate(R.id.action_today_to_addMood)
                        },
                        onEdit = { id ->
                            val bundle = bundleOf("emotionHistoryId" to id)
                            findNavController().navigate(
                                R.id.action_today_to_addMood,
                                bundle
                            )
                        }
                    )
                }
            }
        }
    }
}