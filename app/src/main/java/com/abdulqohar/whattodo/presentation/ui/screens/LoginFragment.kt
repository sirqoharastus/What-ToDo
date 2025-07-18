package com.abdulqohar.whattodo.presentation.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abdulqohar.whattodo.R
import com.abdulqohar.whattodo.data.remote.model.LoginRequest
import com.abdulqohar.whattodo.databinding.FragmentLoginBinding
import com.abdulqohar.whattodo.presentation.ui.viewmodel.LoginViewModel
import com.abdulqohar.whattodo.util.Prefs
import com.abdulqohar.whattodo.util.Resource
import com.abdulqohar.whattodo.util.UiHelpers.Companion.dismissProgressDialog
import com.abdulqohar.whattodo.util.UiHelpers.Companion.makeSnackbar
import com.abdulqohar.whattodo.util.UiHelpers.Companion.showProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if (validateForm())  viewModel.login(LoginRequest(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            ))
        }
        onLoginStateChanged()
    }

    private fun validateForm(): Boolean {
        when {
            binding.etUsername.text.isEmpty() -> {
                makeSnackbar(requireContext(), requireView(),"user name cannot be empty", true)
                return false
            }
            binding.etPassword.text.isEmpty() -> {
                makeSnackbar(requireContext(), requireView(),"password cannot be empty", true)
                return false
            }
            else -> return true
        }
    }

    private fun onLoginStateChanged() {
        lifecycleScope.launch {
            viewModel.loginState.collect {
                when (it) {
                    is Resource.Loading -> {
                        showProgressDialog(requireActivity())
                    }
                    is Resource.Success -> {
                        dismissProgressDialog()
                        Prefs.saveAuth(requireContext(), it.data.token, it.data.id)
                        findNavController().navigate(R.id.todoListFragment)
                    }
                    is Resource.Error -> {
                        dismissProgressDialog()
                        makeSnackbar(requireContext(), requireView(), it.message, true)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}