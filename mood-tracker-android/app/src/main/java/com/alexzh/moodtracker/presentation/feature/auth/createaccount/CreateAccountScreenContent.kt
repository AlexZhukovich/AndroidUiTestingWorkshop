package com.alexzh.moodtracker.presentation.feature.auth.createaccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.component.LoadingButton
import com.alexzh.moodtracker.presentation.component.text.EmailOutlinedTextFieldWithError
import com.alexzh.moodtracker.presentation.component.text.NameOutlinedTextFieldWithError
import com.alexzh.moodtracker.presentation.component.text.PasswordOutlinedTextFieldWithError
import com.alexzh.moodtracker.presentation.component.text.TextRedirect
import com.alexzh.moodtracker.presentation.core.res.highlightLastItem
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel,
    onLogin: () -> Unit,
    onCreateAccountSuccess: () -> Unit,
    onContinueWithoutAccount: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary),
                title = { Text(stringResource(R.string.createAccountScreen_title)) },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { Snackbar(it) }
            )
        }
    ) { paddingValues ->
        val uiState by viewModel.state
        if (uiState.accountCreated) {
            onCreateAccountSuccess()
        } else {
            uiState.errorMessage?.let {
                val errorMessage = stringResource(it)
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    top = paddingValues.calculateTopPadding(),
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val nameErrorMessage = uiState.nameErrorMessage?.let { stringResource(it) }
            val emailErrorMessage = uiState.emailErrorMessage?.let { stringResource(it) }
            val passwordErrorMessage = uiState.passwordErrorMessage?.let { stringResource(it) }

            Image(
                painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(140.dp).align(Alignment.CenterHorizontally)
            )
            NameOutlinedTextFieldWithError(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.name,
                onValueChange = { viewModel.onEvent(CreateAccountEvent.NameChange(it)) },
                enabled = !uiState.loading,
                isError = nameErrorMessage != null,
                errorLabel = nameErrorMessage
            )
            EmailOutlinedTextFieldWithError(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = { viewModel.onEvent(CreateAccountEvent.EmailChange(it)) },
                enabled = !uiState.loading,
                isError = emailErrorMessage != null,
                errorLabel = emailErrorMessage
            )
            PasswordOutlinedTextFieldWithError(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                onValueChange = { viewModel.onEvent(CreateAccountEvent.PasswordChange(it)) },
                enabled = !uiState.loading,
                isError = passwordErrorMessage != null,
                errorLabel = passwordErrorMessage
            )
            LoadingButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(CreateAccountEvent.CreateAccount) },
                text = stringResource(R.string.createAccountScreen_createAccount_button),
                isLoading = uiState.loading
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextRedirect(
                text = stringArrayResource(R.array.createAccountScreen_login_label).highlightLastItem(),
                onClick = { onLogin() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            TextRedirect(
                text = stringArrayResource(R.array.createAccountScreen_useOfflineAccount).highlightLastItem(),
                onClick = { onContinueWithoutAccount() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}