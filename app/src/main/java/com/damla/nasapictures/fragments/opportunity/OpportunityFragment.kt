package com.damla.nasapictures.fragments.opportunity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damla.nasapictures.R
import com.damla.nasapictures.activity.MainActivity
//import com.damla.nasapictures.api.ViewModel.ApiViewModel
import com.damla.nasapictures.dataSource.ViewModelDataSource
import com.damla.nasapictures.databinding.FragmentOppurtunityBinding
import com.damla.nasapictures.recyclerview.AdapterCuriosity
import com.damla.nasapictures.recyclerview.AdapterOpportunity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OpportunityFragment : Fragment() {

    private lateinit var binding: FragmentOppurtunityBinding
    private lateinit var mApiViewModel: ViewModelDataSource
    private lateinit var recyclerView: RecyclerView
    private lateinit var oViewModel : OpportunityViewModel
    private lateinit var opportunityAdapter:AdapterOpportunity
    private lateinit var preferences : SharedPreferences
    var hasLoadedOnce = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOppurtunityBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewOpportunity
        preferences = requireActivity().getSharedPreferences("com.damla.finalproject", Context.MODE_PRIVATE)

        mApiViewModel = ViewModelProvider(this).get(ViewModelDataSource::class.java)
        oViewModel = ViewModelProvider(this).get(OpportunityViewModel::class.java)

        setupRecyclerView()
        loaddata()
        setHasOptionsMenu(true)
        return binding.root
    }
    fun setupRecyclerView(){
        opportunityAdapter = AdapterOpportunity()
        binding.recyclerViewOpportunity.apply {
            adapter = opportunityAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }


    fun loaddata(){
        lifecycleScope.launch {
            mApiViewModel.getPhotos("oppartunity").collect {
                opportunityAdapter.submitData(it)}

        }
         /*   mApiViewModel.getPhotosLiveData("opportunity").observe(viewLifecycleOwner, Observer {
                opportunityAdapter.submitData(this.lifecycle,it)})*/


    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if(this.isVisible){
            if(!isVisibleToUser && !hasLoadedOnce){
                hasLoadedOnce = true
            }
        }

        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.nav_menuopporunity, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cameraSOCFHAZ) {
            setupRecyclerView()
            mApiViewModel.getPhotosFilteredLiveData("opportunity","fhaz").observe(viewLifecycleOwner,Observer{
                opportunityAdapter.submitData(this.lifecycle,it) })
        }

        if (item.itemId == R.id.cameraSOCRHAZ) {
            setupRecyclerView()
            mApiViewModel.getPhotosFilteredLiveData("opportunity","rhaz").observe(viewLifecycleOwner,Observer{
                opportunityAdapter.submitData(this.lifecycle,it) })
        }
        if (item.itemId == R.id.cameraSOCNAVCAM) {
            setupRecyclerView()
            mApiViewModel.getPhotosFilteredLiveData("opportunity","navcam").observe(viewLifecycleOwner,Observer{
                opportunityAdapter.submitData(this.lifecycle,it) })
        }
        if (item.itemId == R.id.cameraSOPANCAM) {
            setupRecyclerView()
            mApiViewModel.getPhotosFilteredLiveData("opportunity","pancam").observe(viewLifecycleOwner,Observer{
                opportunityAdapter.submitData(this.lifecycle,it) })
        }
        if (item.itemId == R.id.cameraSOMINITES) {
            setupRecyclerView()
            mApiViewModel.getPhotosFilteredLiveData("opportunity","minites").observe(viewLifecycleOwner,Observer{
                opportunityAdapter.submitData(this.lifecycle,it) })

        }



        return super.onOptionsItemSelected(item)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu?.removeItem(R.id.cameraCMAST)
        menu?.removeItem(R.id.cameraCCHEMCAM)
        menu?.removeItem(R.id.cameraCMAHLI)
        menu?.removeItem(R.id.cameraCMARDI)
    }

}





