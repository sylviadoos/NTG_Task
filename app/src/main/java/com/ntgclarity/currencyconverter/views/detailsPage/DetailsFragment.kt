package com.ntgclarity.currencyconverter.views.detailsPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ntgclarity.currencyconverter.R
import com.ntgclarity.currencyconverter.databinding.FragmentDetailsBinding
import com.ntgclarity.currencyconverter.views.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: CurrenciesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_details, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Create a list of fragment titles and IDs
        val fragmentTitles = listOf("Historical list", "Other Currencies")
        val fragmentList = listOf(
            DetailsList(),
            DetailsBaseList(),
        )
        val fragmentIds = listOf(R.id.detailsList, R.id.detailsBaseList)

        // Set up the ViewPager with a FragmentPagerAdapter
        binding.viewPager.adapter =  ViewPagerAdapter(this, fragmentList)


        // Link the TabLayout and the ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentTitles[position]
        }.attach()

        // Set the initial tab to display
        viewModel.setCurrentFragmentId(R.id.detailsList)
        // Observe changes to the current tab and update the ViewPager as needed
        viewModel.currentFragmentId.observe(viewLifecycleOwner, { tabId ->
            val tabPosition = fragmentIds.indexOf(tabId)
            if (tabPosition != -1) {
                binding.viewPager.currentItem = tabPosition
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}