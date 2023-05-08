package com.alexzh.moodtracker.presentation.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3Api::class)
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()
    private var menuProvider: MenuProvider? = null

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
                    ProfileScreenContent(
                        viewModel = viewModel,
                        onCreateAccount = { findNavController().navigate(R.id.action_profile_to_createAccount) },
                        onLogin = { findNavController().navigate(R.id.action_profile_to_login) },
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        lifecycleScope.launch {
            viewModel.isLoggedInUser.collect { isUserLoggedIn ->
                menuProvider?.let { menuHost.removeMenuProvider(it) }
                menuProvider = object : MenuProvider {
                    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                        if (isUserLoggedIn) {
                            menuInflater.inflate(R.menu.profile_logged_in_menu, menu)
                        }
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        viewModel.onEvent(ProfileEvent.LogOut)
                        return true
                    }
                }
                menuHost.addMenuProvider(
                    menuProvider as MenuProvider,
                    viewLifecycleOwner,
                    Lifecycle.State.RESUMED
                )
            }
        }
    }
}