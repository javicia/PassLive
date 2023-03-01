package com.example.passlive.presentation.signup


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.passlive.R
import com.example.passlive.databinding.FragmentSignupBinding
import com.example.passlive.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: Fragment() {

    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstance: Bundle?
    ):View{
        //Aumentamos layout con fragment
        _binding = FragmentSignupBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }
    private fun initObservers(){
        viewModel.signUpState.observe(viewLifecycleOwner){ state ->
            when (state) {
                is Resource.Sucess -> {
                    handleLoading(isLoading = false)
                    activity?.onBackPressed()
                    Toast.makeText(
                    requireContext(),
                    "Se registró con éxito",
                    Toast.LENGTH_SHORT
                ).show()
            }
                is Resource.Error -> {
                    handleLoading(isLoading = false)
                    Toast.makeText(
                    requireContext(),
                    "No se pudo realizar el registro",
                    Toast.LENGTH_SHORT
                ).show()
            }
                is Resource.Loading -> handleLoading(isLoading = true)
                else -> Unit
            }

        }
    }
    private fun initListeners(){
        with(binding) {
            bBack.setOnClickListener {
                activity?.onBackPressed()
            }
            bSignUp.setOnClickListener {
            handleSignUp()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun handleSignUp(){
    val email:String = binding.etEmail.text.toString()
      val password: String = binding.etPassword.text.toString()

        viewModel.signUp(email, password)
    }
    //Si carga el boton signup desaparece y aparece el simbolo de carga
    private fun handleLoading(isLoading:Boolean){
        with(binding){
            if (isLoading){
                bSignUp.text =""
                bSignUp.isEnabled = false
                pbSignUp.visibility = View.VISIBLE
            }else{
                pbSignUp.visibility= View.GONE
            bSignUp.text = getString(R.string.login__signup_button)
                bSignUp.isEnabled= true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}