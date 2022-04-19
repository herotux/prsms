/*
 * Copyright (C) 2017 Moez Bhatti <freetux.bhatti@gmail.com>
 *
 * This file is part of PRSMS.
 *
 * PRSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PRSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PRSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.freetux.PRSMS.feature.scheduled

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.freetux.PRSMS.R
import com.freetux.PRSMS.common.base.QkAdapter
import com.freetux.PRSMS.common.base.QkViewHolder
import com.freetux.PRSMS.util.GlideApp
import kotlinx.android.synthetic.main.attachment_image_list_item.view.*
import kotlinx.android.synthetic.main.scheduled_message_image_list_item.*
import javax.inject.Inject

class ScheduledMessageAttachmentAdapter @Inject constructor(
    private val context: Context
) : QkAdapter<Uri>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scheduled_message_image_list_item, parent, false)
        view.thumbnail.clipToOutline = true

        return QkViewHolder(view)
    }

    override fun onBindViewHolder(holder: QkViewHolder, position: Int) {
        val attachment = getItem(position)

        GlideApp.with(context).load(attachment).into(holder.thumbnail)
    }

}
