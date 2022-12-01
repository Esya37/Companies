package com.example.companies.fragments

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.companies.R
import com.example.companies.adapters.companyAdapterDelegate
import com.example.companies.models.Company
import com.example.companies.utils.Result
import com.example.companies.viewmodels.CompanyViewModel
import com.example.companies.viewmodels.ViewModelFactory
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CompaniesFragment : BaseFragment(R.layout.fragment_companies) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CompanyViewModel

    private lateinit var companiesRecyclerView: RecyclerView

    private lateinit var companiesAdapterDelegate: ListDelegationAdapter<List<Company>>

    override fun findViewsById() {
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[CompanyViewModel::class.java]

        companiesRecyclerView = inflatedView.findViewById(R.id.rv_companies_companies_list)
    }

    override fun viewsInitialization() {
        companiesAdapterDelegate = ListDelegationAdapter<List<Company>>(
            companyAdapterDelegate {
                viewModel.selectedCompanyId = it.id
                inflatedView.findNavController()
                    .navigate(R.id.action_companiesFragment_to_companyDetailsFragment)
            }
        )
        companiesRecyclerView.also {
            it.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            it.adapter = companiesAdapterDelegate
        }
    }

    override fun informationInitialization() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getCompaniesList().also {
                if (it is Result.Error) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    viewModel.companiesList = (it as Result.Success).data
                    companiesAdapterDelegate.items = viewModel.companiesList
                    withContext(Dispatchers.Main) {
                        companiesRecyclerView.adapter = companiesAdapterDelegate
                        if (viewModel.selectedCompanyId.isNotEmpty()) {
                            companiesRecyclerView.scrollToPosition(companiesAdapterDelegate.items!!.indexOfFirst { company ->
                                viewModel.selectedCompanyId == company.id
                            })
                        }
                    }
                }
            }
        }
    }

    override fun injectFragment() {
        AndroidSupportInjection.inject(this)
    }
}
