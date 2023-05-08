package com.alexzh.moodtracker.presentation.feature.auth.login

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
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

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
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = { findNavController().navigate(R.id.action_login_to_profile) },
                        onCreateAccount = { findNavController().navigate(R.id.action_login_to_createAccount) },
                        onContinueWithoutAccount = { findNavController().navigate(R.id.action_login_to_today) },
                    )
                }
            }
        }
    }
}