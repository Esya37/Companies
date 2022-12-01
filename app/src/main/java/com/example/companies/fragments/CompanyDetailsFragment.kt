package com.example.companies.fragments

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.companies.BuildConfig
import com.example.companies.R
import com.example.companies.models.Company
import com.example.companies.utils.Result
import com.example.companies.viewmodels.CompanyViewModel
import com.example.companies.viewmodels.ViewModelFactory
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CompanyDetailsFragment : BaseFragment(R.layout.fragment_company_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CompanyViewModel

    private lateinit var companyNameTextView: TextView
    private lateinit var companyDescriptionTextView: TextView
    private lateinit var companyLatitudeTextView: TextView
    private lateinit var companyLongitudeTextView: TextView
    private lateinit var companySiteAddressTextView: TextView
    private lateinit var companyPhoneNumberTextView: TextView
    private lateinit var companyImageImageView: ImageView

    override fun findViewsById() {
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[CompanyViewModel::class.java]

        companyNameTextView = inflatedView.findViewById(R.id.tv_company_details_name)
        companyDescriptionTextView = inflatedView.findViewById(R.id.tv_company_details_description)
        companyLatitudeTextView = inflatedView.findViewById(R.id.tv_company_details_latitude)
        companyLongitudeTextView = inflatedView.findViewById(R.id.tv_company_details_longitude)
        companySiteAddressTextView = inflatedView.findViewById(R.id.tv_company_details_site_address)
        companyPhoneNumberTextView = inflatedView.findViewById(R.id.tv_company_details_phone_number)
        companyImageImageView = inflatedView.findViewById(R.id.iv_company_details_company_image)
    }

    override fun viewsInitialization() {

    }

    override fun informationInitialization() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getCompany(viewModel.selectedCompanyId).also {
                if (it is Result.Error) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG)
                            .show()
                        inflatedView.findNavController().navigateUp() // Если произошла ошибка при получении данных (неэкранированные кавычки, например) - возвращаемся на предыдущий экран
                    }
                } else {
                    viewModel.currentCompany = (it as Result.Success).data
                    withContext(Dispatchers.Main) {
                        updateCompanyInfo(viewModel.currentCompany)
                    }

                }
            }
        }

    }

    private fun updateCompanyInfo(newCompanyInfo: Company) {
        companyNameTextView.text = newCompanyInfo.name

        companyDescriptionTextView.text =
            newCompanyInfo.description.replace("\r", "") // Символ новой строки в Android - \n

        companyLatitudeTextView.text = if (newCompanyInfo.lat == 0.0) {
            String.format(
                getString(R.string.company_details_latitude),
                getString(R.string.empty_data_feminine)
            )
        } else {
            String.format(
                getString(R.string.company_details_latitude), newCompanyInfo.lat.toString()
            )
        }

        companyLongitudeTextView.text = if (newCompanyInfo.lon == 0.0) {
            String.format(
                getString(R.string.company_details_longitude),
                getString(R.string.empty_data_feminine)
            )
        } else {
            String.format(
                getString(R.string.company_details_longitude), newCompanyInfo.lon.toString()
            )
        }

        companySiteAddressTextView.text = if (newCompanyInfo.siteAddress.isEmpty()) {
            String.format(
                getString(R.string.company_details_site_address),
                getString(R.string.empty_data_masculine)
            )
        } else {
            String.format(
                getString(R.string.company_details_site_address),
                newCompanyInfo.siteAddress
            )
        }

        companyPhoneNumberTextView.text = if (newCompanyInfo.phone.isEmpty()) {
            String.format(
                getString(R.string.company_details_phone_number),
                getString(R.string.empty_data_masculine)
            )
        } else {
            String.format(
                getString(R.string.company_details_phone_number), newCompanyInfo.phone
            )
        }

        Picasso.get().load(BuildConfig.BASE_URL + newCompanyInfo.imgUrl)
            .error(R.drawable.icon_internet_error).into(companyImageImageView)
    }

    override fun injectFragment() {
        AndroidSupportInjection.inject(this)
    }

}