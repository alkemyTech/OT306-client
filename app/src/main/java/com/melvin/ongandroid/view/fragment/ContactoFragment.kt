package com.melvin.ongandroid.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.melvin.ongandroid.businesslogic.IsInputValidUseCase
import com.melvin.ongandroid.databinding.FragmentContactBinding
import com.melvin.ongandroid.model.ContactMessageDto
import com.melvin.ongandroid.model.OngRemoteDataSource
import com.melvin.ongandroid.model.OngRepository
import com.melvin.ongandroid.utils.Extensions.showToast
import com.melvin.ongandroid.viewmodel.ContactViewModel
import com.melvin.ongandroid.viewmodel.ContactViewModelFactory

//const val TAG = "ContactFragment"

class ContactoFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ContactViewModel> {
        ContactViewModelFactory(OngRepository(OngRemoteDataSource()), IsInputValidUseCase())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreUIWithUserInput()

        subscribeSendMessageBtn()

        binding.sendMessageBtn.setOnClickListener {
            sendMessageFromContact()
            //Log.d(TAG, "sendMessageBtn On Click Listener enabled")
        }

        viewModel.messageFromContact.observe(viewLifecycleOwner, Observer { response ->
            if (response != null) {
                executeAlertDialog()
                showToast("envio exitoso")
                //Log.d("Main", response.message.toString())
                //Log.d("Main", response.email.toString())
               // Log.d("Main", response.nameAndLastName.toString())
            } else {
                showToast("error en el envio")
            }
            // le da visibilidad al progress bar
            viewModel.isLoading.observe(viewLifecycleOwner, Observer {
                binding.contactProgressBar.isVisible = it
            })


        })
        setupEditText()
    }

    /*
     * Subscribe send message button to is Valid Input in ViewModel
     * for instant enabled and disabled button
     */
    private fun subscribeSendMessageBtn() {
        viewModel.isValidInput.observe(viewLifecycleOwner) {
            binding.sendMessageBtn.isEnabled = viewModel.isValidInput.value ?: false
            //Log.d(TAG, "isValidInput LiveData ${viewModel.isValidInput.value}")
            //Log.d(TAG, "sendMessageButton isEnabled ${binding.sendMessageBtn.isEnabled}")
        }
    }

    // captura los textos del formulario para realizar el POST a la API
    private fun sendMessageFromContact() {
        val name = binding.nameET.text.toString()
        val email = binding.emailET.text.toString()
        val message = binding.messageET.text.toString()
        viewModel.pushPost(
            ContactMessageDto(
                nameAndLastName = name,
                email = email,
                message = message
            )
        )
    }

    private fun executeAlertDialog() {
        //asignando valores al modal dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Envio exitoso")
        builder.setMessage("¡Tu solicitud ha sido enviada con exito!\nPronto nos contactaremos con vos")
        builder.setPositiveButton("Aceptar", null)
        builder.show()
        binding.nameET.setText("")
        binding.emailET.setText("")
        binding.messageET.setText("")
    }

    /*
     * Setup on text change listener of de edit text
     */
    private fun setupEditText() {
        binding.apply {
            nameET.addTextChangedListener { text ->
                text?.apply { viewModel.updateName(text.toString()) }
            }
            emailET.addTextChangedListener { text ->
                text?.apply { viewModel.updateEmail(text.toString()) }
            }
            messageET.addTextChangedListener { text ->
                text?.apply { viewModel.updateMessage(text.toString()) }
            }
        }
    }

    /*
     * Restore user input if the view was destroyed
     */
    private fun restoreUIWithUserInput() {
        if (viewModel.contact.value != null) {
            binding.apply {
                if (viewModel.contact.value!!.nameAndLastName != null) {
                    nameET.setText(viewModel.contact.value!!.nameAndLastName!!.toString())
                }
                if (viewModel.contact.value!!.email != null) {
                    emailET.setText(viewModel.contact.value!!.email!!.toString())
                }
                if (viewModel.contact.value!!.message != null) {
                    messageET.setText(viewModel.contact.value!!.message!!.toString())
                }
            }
        }
    }

    /*
     * Restore user input at resume view
     */
    override fun onResume() {
        restoreUIWithUserInput()
        super.onResume()
    }

}
