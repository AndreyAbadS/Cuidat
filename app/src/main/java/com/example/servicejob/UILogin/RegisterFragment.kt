package com.example.servicejob.UILogin

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.servicejob.R
import com.example.servicejob.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment(R.layout.fragment_register){
    private var _binding:FragmentRegisterBinding? = null
    private lateinit var  auth: FirebaseAuth
    //val fragmentManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        val spinner: AppCompatSpinner? = _binding?.SpinnerEstados
        auth = Firebase.auth
        SpinnersAdaptador(spinner)
        _binding!!.btnRegistroUsuario.setOnClickListener {
            singUpUser()

        }
    }
    //Rellenar el spinner
    fun SpinnersAdaptador(spinner: AppCompatSpinner?){
        ArrayAdapter.createFromResource(requireContext(),
            R.array.Estados_Array,android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                spinner?.adapter = adapter
                spinner?.setSelection(0)
            }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun singUpUser(){
        //Validacion parametros
        if (_binding?.etCorreoRegistro?.text.toString().isEmpty()){
            _binding?.etCorreoRegistro?.error = "porfavor introduce un correo electronico"
            _binding?.etCorreoRegistro?.requestFocus()
            return
        }
        if (_binding?.etContraseARegistro?.text.toString().isEmpty()){
            _binding?.etContraseARegistro?.error = "porfavor introduce una contraseña"
            _binding?.etContraseARegistro?.requestFocus()
            return
        }
        if (_binding?.etEdadUsuario?.text.toString().isEmpty()){
            _binding?.etEdadUsuario?.error = "porfavor introduce tu edad"
            _binding?.etEdadUsuario?.requestFocus()
            return
        }
        if (_binding?.etDireccionUsuario?.text.toString().isEmpty()){
            _binding?.etDireccionUsuario?.error = "porfavor introduce tu direccion"
            _binding?.etDireccionUsuario?.requestFocus()
            return
        }
        //Crear usuario
        auth.createUserWithEmailAndPassword(_binding?.etCorreoRegistro?.text.toString(),_binding?.etContraseARegistro?.text.toString())
            .addOnCompleteListener(requireActivity()){ task ->
                if (task.isSuccessful){
                    //Mensaje de usuario registrado
                    Log.d(ContentValues.TAG,"Usuario creado")
                    val user = auth.currentUser
                    //Enviar correo de verificacion
                    user!!.sendEmailVerification().addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    }
                }else{
                    Toast.makeText(requireContext(),"Fallo en el registro, intente despues",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }


    private fun updateUI(user: FirebaseUser?) {

    }

    private fun sendEmailVerificacion(){
        //Enviar email de verificacion
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(requireActivity()){ task ->
                //Email verification sent
            }
    }


}