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
package com.github.pullrequest.ui.main

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.pullrequest.R
import com.github.pullrequest.base.BaseFragment
import com.github.pullrequest.databinding.FragmentPrListBinding
import com.github.pullrequest.di.factory.AppViewModelFactory
import com.github.pullrequest.ui.adapter.PRItemListAdapter
import com.github.pullrequest.utils.ext.getViewModel
import com.github.pullrequest.utils.ext.toGone
import com.github.pullrequest.utils.ext.toVisible

import javax.inject.Inject

/**
 * Main Fragment where most of the UI stuff happens
 * Extends functionality of [BaseFragment]
 *
 * Created by gk
 */
class PRListFragment : BaseFragment() {

    private lateinit var inflatedView: View

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private lateinit var binding: FragmentPrListBinding
    private lateinit var prListViewModel: PRListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pr_list, container, false)
        inflatedView = binding.root

        //inject dependencies
        activityComponent.inject(this)

        //create the adapter
        val adapter = PRItemListAdapter()
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.itemRecyclerView.adapter = adapter

        //subscribe ui
        subscribeUi(adapter)

        return inflatedView
    }

    private fun subscribeUi(adapter: PRItemListAdapter) {
        prListViewModel = getViewModel(PRListViewModel::class.java, viewModelFactory)
        binding.hasItems = true

        prListViewModel.isLoading().observe(this, Observer { showProgress(it) })
        prListViewModel.getErrorMsg().observe(this, Observer { errorMessage ->
            if (errorMessage != null) onError(errorMessage)
        })
        prListViewModel.getItemList().observe(this, Observer { items ->
            val hasItems = (items != null && items.isNotEmpty())
            binding.hasItems = hasItems
            if (hasItems)
                adapter.submitList(items)
        })
    }

    private fun showProgress(status: Boolean?) {
        if (status != null && status)
            binding.itemLoading.toVisible()
        else
            binding.itemLoading.toGone()
    }

    companion object {
        fun newInstance(): PRListFragment {
            return PRListFragment()
        }
    }
}
