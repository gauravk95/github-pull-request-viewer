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

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.pullrequest.R
import com.github.pullrequest.base.BaseFragment
import com.github.pullrequest.databinding.FragmentMainBinding

/**
 * Main Fragment where most of the UI stuff happens
 * Extends functionality of [BaseFragment]
 *
 * Created by gk
 */
class MainFragment : BaseFragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        //inject dependencies
        activityComponent.inject(this)

        binding.sampleText = "This is sample text"

        return binding.root
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
