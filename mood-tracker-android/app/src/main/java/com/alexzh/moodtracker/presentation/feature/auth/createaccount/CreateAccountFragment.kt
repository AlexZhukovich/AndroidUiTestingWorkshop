package com.alexzh.moodtracker.presentation.feature.auth.createaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class CreateAccountFragment : Fragment() {

    private val viewModel: CreateAccountViewModel by viewModel()

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
                    CreateAccountScreen(
                        viewModel = viewModel,
                        onLogin = { findNavController().navigate(R.id.action_createAccount_to_login) },
                        onCreateAccountSuccess = { findNavController().navigate(R.id.action_createAccount_to_profile) },
                        onContinueWithoutAccount = { findNavController().navigate(R.id.action_createAccount_to_today) }
                    )
                }
            }
        }
    }
}