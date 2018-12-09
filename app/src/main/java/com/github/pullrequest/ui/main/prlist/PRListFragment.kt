/*
    Copyright 2018 Gaurav Kumar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.github.pullrequest.ui.main.prlist

import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.github.pullrequest.data.source.state.LoadingStatus
import com.github.pullrequest.R
import com.github.pullrequest.base.BaseFragment
import com.github.pullrequest.databinding.FragmentPrListBinding
import com.github.pullrequest.di.factory.AppViewModelFactory
import com.github.pullrequest.ui.adapter.PRItemListAdapter
import com.github.pullrequest.utils.AppLogger
import com.github.pullrequest.utils.ext.getViewModel
import com.github.pullrequest.utils.ext.hideKeyboard
import com.github.pullrequest.utils.ext.toGone
import com.github.pullrequest.utils.ext.toVisible
import java.lang.Exception

import javax.inject.Inject

/**
 * Main Fragment where most of the UI stuff happens
 * Extends functionality of [BaseFragment]
 *
 * Created by gk
 */
class PRListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private lateinit var binding: FragmentPrListBinding
    private lateinit var prListViewModel: PRListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pr_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //inject dependencies
        activityComponent.inject(this)

        //create the adapter
        val adapter = PRItemListAdapter {
            prListViewModel.onPullRequestItemClicked(it)
        }
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.itemRecyclerView.adapter = adapter

        //subscribe ui
        subscribeUi(adapter)
        //setup search
        setupSearchBar()
    }

    private fun setupSearchBar() {
        val newQueryListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                hideKeyboard()
                prListViewModel.search(query)
                return true
            }
        }
        binding.searchView.setOnQueryTextListener(newQueryListener)
        binding.searchView.isSubmitButtonEnabled = true
    }

    private fun subscribeUi(adapter: PRItemListAdapter) {
        prListViewModel = getViewModel(PRListViewModel::class.java, viewModelFactory)
        binding.hasItems = false
        binding.viewModel = prListViewModel

        prListViewModel.getErrorMsg().observe(this, Observer { errorMessage ->
            if (errorMessage != null) onError(errorMessage)
        })
        prListViewModel.toastMsg.observe(this, Observer {
            if (it != null) showToastMessage(it)
        })
        prListViewModel.pullRequestList.observe(this, Observer { items ->
            val hasItems = (items != null && items.isNotEmpty())
            binding.hasItems = hasItems
            adapter.submitList(items)
        })
        prListViewModel.loadingState.observe(this, Observer {
            when (it?.loadingStatus) {
                LoadingStatus.FIRST_RUNNING -> showProgress(true)
                LoadingStatus.FIRST_EMPTY -> {
                    showSearchStatusMessage(R.string.search_msg_repo_not_found)
                    showProgress(false)
                }
                LoadingStatus.FIRST_SUCCESS -> showProgress(false)
                LoadingStatus.FIRST_FAILED -> {
                    showSearchStatusMessage(R.string.search_msg_repo_not_found)
                    showProgress(false)
                }
                LoadingStatus.RUNNING -> {
                    showNextLoadProgress(true)
                }
                LoadingStatus.SUCCESS -> {
                    showNextLoadProgress(false)
                }
                LoadingStatus.FAILED -> {
                    showNextLoadProgress(false)
                    showToastMessage(R.string.error_cannot_load_more)
                }
                else -> {
                    showProgress(false)
                }
            }
        })
        prListViewModel.pullRequestSelected.observe(this, Observer {
            val url = it?.getContentIfNotHandled()
            if (!url.isNullOrEmpty())
                launchPullRequestUrl(url)
        })
    }

    private fun launchPullRequestUrl(url: String) {
        var finalUrl: String = url
        if (!url.startsWith("https://") && !url.startsWith("http://"))
            finalUrl = "http://$url"

        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl))
            startActivity(browserIntent)
        } catch (e: Exception) {
            AppLogger.e(TAG, "Error occurred while launching...")
        }

    }

    private fun showSearchStatusMessage(resId: Int) {
        binding.searchStatusMsg.toVisible()
        binding.searchStatusMsg.text = getText(resId)
    }

    private fun showProgress(status: Boolean?) {
        if (status != null && status) {
            binding.searchStatusMsg.toGone()
            binding.itemLoading.toVisible()
        } else {
            binding.itemLoading.toGone()
        }
    }

    private fun showNextLoadProgress(status: Boolean?) {
        if (status != null && status) {
            binding.itemLoadingNext.toVisible()
        } else {
            binding.itemLoadingNext.toGone()
        }
    }

    companion object {
        const val TAG = "PRListFragment"

        fun newInstance(): PRListFragment {
            return PRListFragment()
        }
    }
}
