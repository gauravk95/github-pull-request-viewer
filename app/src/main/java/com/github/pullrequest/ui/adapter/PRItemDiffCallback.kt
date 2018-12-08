package com.github.pullrequest.ui.adapter

import android.support.v7.util.DiffUtil
import com.github.pullrequest.data.models.local.PullRequest

class PRItemDiffCallback : DiffUtil.ItemCallback<PullRequest>() {
    override fun areItemsTheSame(item1: PullRequest, item2: PullRequest): Boolean {
        return item1 == item2
    }

    override fun areContentsTheSame(item1: PullRequest, item2: PullRequest): Boolean {
        return item1.id == item2.id
    }
}