package com.app.foodiehub.presentation.view.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.databinding.FragmentProfileTabBinding
import com.app.foodiehub.presentation.model.UserDataModel
import com.app.foodiehub.presentation.model.UserDetailDataModel
import com.app.foodiehub.presentation.viewModel.AuthViewModel
import com.app.foodiehub.presentation.viewModel.VMScope
import com.app.foodiehub.utils.FormValidationEnum
import com.app.foodiehub.utils.getValue

class ProfileTab : Fragment() {
    private lateinit var binding: FragmentProfileTabBinding
    private val authViewModel: AuthViewModel = VMScope.authViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileTabBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData(authViewModel.observeUserDataLiveDataResult.value)

        binding.saveBtn.setOnClickListener {
            authViewModel.updateUser(
                binding.name.getValue,
                binding.phone.getValue
            )
        }

        authViewModel.observeUserDataLiveDataResult.observe(viewLifecycleOwner) {
            setData(it)
        }

        authViewModel.observeProfileDataLiveDataResult.observe(viewLifecycleOwner) {
            observeUpdate(it)
        }
    }

    private fun observeUpdate(result: BaseResponse<Boolean>) {
        when (result) {
            is BaseResponse.Success -> {

            }

            is BaseResponse.FormValidationError -> {
                when (result.key) {
                    FormValidationEnum.username -> binding.name.setError(result.msg)
                    FormValidationEnum.phone -> binding.phone.setError(result.msg)
                    else -> {}
                }
            }

            is BaseResponse.Error -> {

            }

            else -> {

            }
        }

    }

    private fun setData(value: UserDataModel<UserDetailDataModel>?) {
        if (value != null) {
            val detail = value.data
            binding.email.setText(detail.email)
            binding.name.setText(detail.name)
            binding.phone.setText(detail.phone)
        }
    }
}