package com.example.servicejob.UILogin

import CuerpoApp.Cuerpo_Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.servicejob.R
import com.example.servicejob.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(R.layout.fragment_login) {
    //Binding
    private var _binding:FragmentLoginBinding? = null
    //Instancia de firebase
    private lateinit var  auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //iniciar binding
        _binding = FragmentLoginBinding.bind(view)
        //Autenticacion Firebase
        auth = Firebase.auth
        //Boton registro
        _binding?.btnIrRegistro?.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        //boton inicio de sesion
        _binding?.btnIniciodeSesion?.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null){
                reload();
            }else{
                sigIn(_binding?.EtCorreoLogin?.text.toString(),_binding?.EtContraseALogin?.text.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

    }

    private fun sigIn(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()){ task ->
                if (task.isSuccessful){
                    //Si funciona la autenticacion
                    Log.d(TAG,"Inicio de sesion exitoso")
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    //Si falla la autenticacion
                    Log.w(TAG,"Inicio de sesion fallido", task.exception)
                    Toast.makeText(context, "autenticacion fallida", Toast.LENGTH_SHORT)
                        .show()
                    updateUI(null)
                }
            }
    }
    //Verificar que los campos esten rellenados correctamente
    private fun reload() {
        if(_binding?.EtCorreoLogin?.text.toString().isEmpty()){
            _binding?.EtCorreoLogin?.error= "Porfavor introduce un correo electronico"
            _binding?.EtCorreoLogin?.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(_binding?.EtCorreoLogin?.text.toString()).matches()){
            _binding?.EtCorreoLogin?.error="Porfavor introduce un email valido"
            return
        }
        if(_binding?.EtContraseALogin?.text.toString().isEmpty()){
            _binding?.EtContraseALogin?.error= "Porfavor introduce un correo electronico valido."
            _binding?.EtContraseALogin?.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(_binding?.EtCorreoLogin?.text.toString(),_binding?.EtContraseALogin?.text.toString())
            .addOnCompleteListener(requireActivity()){ tast ->
                if (tast.isSuccessful){
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    updateUI(null)
                }
            }

    }
    //Verifica el email, y accede a la aplicacion
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified){
                val intent = Intent(activity, Cuerpo_Activity::class.java)
                activity?.startActivity(intent)
                activity?.finish()
            }else{
                Toast.makeText(requireContext(),"Porfavor verifica tu email",Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            Toast.makeText(requireContext(),"Auntenticacion fallida.",Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}