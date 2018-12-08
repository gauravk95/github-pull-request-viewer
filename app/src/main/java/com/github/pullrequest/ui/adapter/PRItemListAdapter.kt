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
package com.github.pullrequest.ui.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.github.pullrequest.data.models.local.PullRequest
import com.github.pullrequest.databinding.ItemPrListBinding

/**
 * Adapter that used to display [PullRequest] in a recycler view
 *
 * Created by gk
 */
class PRItemListAdapter : ListAdapter<PullRequest, PRItemListAdapter.ViewHolder>(PRItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPrListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    class ViewHolder(private val binding: ItemPrListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(curItem: PullRequest) {
            binding.apply {
                item = curItem
                executePendingBindings()
            }
        }
    }
}