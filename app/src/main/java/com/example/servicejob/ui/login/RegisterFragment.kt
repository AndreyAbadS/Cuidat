package com.example.servicejob.ui.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.servicejob.Data.model.usersData
import com.example.servicejob.R
import com.example.servicejob.databinding.FragmentRegisterBinding
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var _binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //val fragmentManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        val spinner: AppCompatSpinner = _binding.SpinnerEstados
        auth = Firebase.auth
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        SpinnersAdaptador(spinner)
        _binding.btnRegistroUsuario.setOnClickListener {
            singUpUser()
        }
    }

    //Rellenar el spinner
    fun SpinnersAdaptador(spinner: AppCompatSpinner?) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.Estados_Array, android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                spinner?.adapter = adapter
                spinner?.setSelection(0)
            }
    }


    fun singUpUser() {
        //Validacion parametros
        if (_binding.etCorreoRegistro.text.toString().isEmpty()) {
            _binding.etCorreoRegistro.error = getString(R.string.message_error_email)
            _binding.etCorreoRegistro.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_binding.etCorreoRegistro.text.toString()).matches()) {
            _binding.etCorreoRegistro.error = getString(R.string.message_email_valid)
            _binding.etCorreoRegistro.requestFocus()
            return
        }
        if (_binding.etContraseARegistro.text.toString().isEmpty()) {
            _binding.etContraseARegistro.error = getString(R.string.message_password)
            _binding.etContraseARegistro.requestFocus()
            return
        }
        if (_binding.etEdadUsuario.text.toString().isEmpty()) {
            _binding.etEdadUsuario.error = getString(R.string.message_age)
            _binding.etEdadUsuario.requestFocus()
            return
        }
        if (_binding.etDireccionUsuario.text.toString().isEmpty()) {
            _binding.etDireccionUsuario.error = getString(R.string.message_direction)
            _binding.etDireccionUsuario.requestFocus()
            return
        }
        if (_binding.etNombre.text.toString().isEmpty()) {
            _binding.etNombre.error = getString(R.string.message_name_register)
            _binding.etNombre.requestFocus()
            return
        }
        if (_binding.etNombreUsuario.text.toString().isEmpty()) {
            _binding.etNombreUsuario.error = getString(R.string.message_name_user)
            _binding.etNombreUsuario.requestFocus()
            return
        }
        val name = _binding.etNombre.text.toString()
        val userName = _binding.etNombreUsuario.text.toString()
        val edad = _binding.etEdadUsuario.text.toString().toInt()
        val direccion = _binding.etDireccionUsuario.text.toString()
        val email = _binding.etCorreoRegistro.text.toString()
        val password = _binding.etContraseARegistro.text.toString()
        registerDataUsers(name, userName, email, edad, direccion, password)

    }

    private fun registerDataUsers(
        name: String,
        username: String,
        email: String,
        edad: Int,
        direccion: String,
        password: String
        //estado: String
    ) {
        var userRef = db.collection("usersData").document(username)
        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    if (document.exists()) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.message_user_exist),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        userRef.set(usersData(name, username, email, edad, direccion))
                            .addOnCompleteListener {
                                auth.createUserWithEmailAndPassword(
                                    email, password
                                ).addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
                                        //Enviar correo de verificacion
                                                if (task.isSuccessful) {
                                                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                                    Toast.makeText(
                                                        requireContext(),
                                                        getString(R.string.user_created),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        getString(R.string.message_failed_register),
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                    }
                                }
                            }.addOnFailureListener { error ->
                                Log.d("failded", error.toString())
                            }
                    }
                }
            }
        }


    }

    private fun buildActionCodeSettings() {
        val actionCodeSettings = actionCodeSettings {
            url = "\"https://www.example.com/finishSignUp?cartId=1234"
            handleCodeInApp = true
            setIOSBundleId("com.example.ios")
            setAndroidPackageName("com.example.servicejob", true, "12")
        }

    }

    private fun sendSignInLink(email: String, actionCodeSettings: ActionCodeSettings){
        Firebase.auth.sendSignInLinkToEmail(email,actionCodeSettings).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.d(TAG,"email sent")
            }
        }
    }
}