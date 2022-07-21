package com.example.servicejob.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.servicejob.R
import com.example.servicejob.databinding.FragmentLoginBinding
import com.example.servicejob.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {
    //Binding
    private lateinit var _binding: FragmentLoginBinding

    //Instancia de firebase
    private lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        //Autenticacion Firebase
        auth = Firebase.auth

        _binding.btnIrRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        _binding.btnIniciodeSesion.setOnClickListener {
           reload()
        }
    }



    //Verificar que los campos esten rellenados correctamente
    private fun reload() {
        if (_binding.EtCorreoLogin.text.toString().isEmpty()) {
            _binding.EtCorreoLogin.error = getString(R.string.message_error_email)
            _binding.EtCorreoLogin.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_binding.EtCorreoLogin.text.toString()).matches()) {
            _binding.EtCorreoLogin.error = getString(R.string.message_email_error)
            return
        }
        if (_binding.EtContraseALogin.text.toString().isEmpty()) {
            _binding.EtContraseALogin.error = getString(R.string.message_email_error)
            _binding.EtContraseALogin.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(
            _binding.EtCorreoLogin.text.toString(),
            _binding.EtContraseALogin.text.toString()
        )
            .addOnCompleteListener(requireActivity()) { tast ->
                if (tast.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }

    }

    //Verifica el email, y accede a la aplicacion
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
          //  if (currentUser.isEmailVerified) {

            //} else {
              //  Toast.makeText(requireContext(), getString(R.string.message_verify_email), Toast.LENGTH_SHORT)
                //   .show()
            //}
        //} else {
          //  Toast.makeText(requireContext(), getString(R.string.autentication_failed), Toast.LENGTH_SHORT)
            //    .show()
        }
    }

}