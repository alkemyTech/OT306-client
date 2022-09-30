package com.melvin.ongandroid.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.melvin.ongandroid.databinding.FragmentMembersBinding
import com.melvin.ongandroid.model.nosotrosActivities.model.MemberDto
import com.melvin.ongandroid.utils.Extensions
import com.melvin.ongandroid.utils.LoadingSpinner
import com.melvin.ongandroid.utils.ResultState
import com.melvin.ongandroid.view.adapter.MembersAdapter
import com.melvin.ongandroid.viewmodel.MembersViewModel
import com.melvin.ongandroid.viewmodel.MembersViewModelFactory

const val TAG = "MembersFragment"

@Suppress("UNCHECKED_CAST")
class MembersFragment : Fragment(), MembersAdapter.OnMembersClicked {

    private var _binding: FragmentMembersBinding? = null
    private val binding get() = _binding!!
    private lateinit var membersAdapter: MembersAdapter
    private lateinit var loadingSpinner: LoadingSpinner

    private val membersViewModel: MembersViewModel by viewModels(
        factoryProducer = { MembersViewModelFactory() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingSpinner = LoadingSpinner()

        val manager = MembersAdapter(listOf(), this)
        binding.membersRV.layoutManager = GridLayoutManager(context, 2)
        binding.membersRV.adapter = manager

        subscribeUi()
    }

    /*
     * Subscribe Ui to Result State to Members Result State Live Data
     */
    private fun subscribeUi() {
        membersViewModel.membersResultState.observe(viewLifecycleOwner, Observer { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                    Log.d(TAG, "Data is loading")
                    setLoadingSpinner(true)
                }
                is ResultState.Success -> {
                    if (resultState.data == null) {
                        showErrorSnackBar()
                    } else {
                        val membersList = (resultState.data as? List<MemberDto>) ?: emptyList()
                        if (membersList.isNotEmpty()) setMembersAdapter(membersList) else showErrorSnackBar()
                    }
                }
                is ResultState.Error -> {
                    Log.e(TAG, resultState.exception.toString())
                    showErrorSnackBar()
                }
            }
        })
    }

    /*
     * Call with isLoading = true if Result State is Loading. Function will start the animation.
     * Call with isLoading = false if Result State isn´t Loading. Function will stop the animation
     * and restore the image logo
     */
    private fun setLoadingSpinner(isLoading: Boolean) {
        if (isLoading) {
            loadingSpinner.start(binding.membersImageLogo)
        } else {
            loadingSpinner.stop(binding.membersImageLogo)
        }
    }


    /*
     * Call it when the members list is retrieve successfully
     * Set list to adapter for Members Recycler View
     */
    private fun setMembersAdapter(members: List<MemberDto>){
        Log.d(TAG, "Data successfully retrieved")
        setLoadingSpinner(false)
        binding.membersRV.adapter = MembersAdapter(members,this)
    }

    /*
     * Call it when have an error result from membersViewModel.memberResultState
     * or when the members list is empty.
     * Function will stop the loading animation, inform to user the error and give him a button
     * to retry the petition.
     */
    private fun showErrorSnackBar(){
        setLoadingSpinner(false)
        Extensions.errorSnackBar(binding.root) {
            membersViewModel.loadMembersResult()
        }
    }

    // onClick listener members
    override fun onMemberClickListener(member: MemberDto, position: Int) {
        // navigate to detail member fragment
        val action = MembersFragmentDirections.actionMembersFragmentToDetailFragment()
        findNavController().navigate(action)
    }

}