/*
 * Copyright (C) 2017 Freetux <thefreetux@gmail.com>
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
package com.freetux.PRSMS.feature.compose.part

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.freetux.PRSMS.R
import com.freetux.PRSMS.common.base.QkViewHolder
import com.freetux.PRSMS.common.util.Colors
import com.freetux.PRSMS.common.util.extensions.getDisplayName
import com.freetux.PRSMS.common.util.extensions.resolveThemeColor
import com.freetux.PRSMS.common.util.extensions.setBackgroundTint
import com.freetux.PRSMS.common.util.extensions.setTint
import com.freetux.PRSMS.extensions.isVCard
import com.freetux.PRSMS.extensions.mapNotNull
import com.freetux.PRSMS.feature.compose.BubbleUtils
import com.freetux.PRSMS.model.Message
import com.freetux.PRSMS.model.MmsPart
import ezvcard.Ezvcard
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.mms_vcard_list_item.*
import javax.inject.Inject

class VCardBinder @Inject constructor(colors: Colors, private val context: Context) : PartBinder() {

    override val partLayout = R.layout.mms_vcard_list_item
    override var theme = colors.theme()

    override fun canBindPart(part: MmsPart) = part.isVCard()

    override fun bindPart(
        holder: QkViewHolder,
        part: MmsPart,
        message: Message,
        canGroupWithPrevious: Boolean,
        canGroupWithNext: Boolean
    ) {
        BubbleUtils.getBubble(false, canGroupWithPrevious, canGroupWithNext, message.isMe())
                .let(holder.vCardBackground::setBackgroundResource)

        holder.containerView.setOnClickListener { clicks.onNext(part.id) }

        Observable.just(part.getUri())
                .map(context.contentResolver::openInputStream)
                .mapNotNull { inputStream -> inputStream.use { Ezvcard.parse(it).first() } }
                .map { vcard -> vcard.getDisplayName() ?: "" }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { displayName ->
                    holder.name?.text = displayName
                    holder.name.isVisible = displayName.isNotEmpty()
                }

        val params = holder.vCardBackground.layoutParams as FrameLayout.LayoutParams
        if (!message.isMe()) {
            holder.vCardBackground.layoutParams = params.apply { gravity = Gravity.START }
            holder.vCardBackground.setBackgroundTint(theme.theme)
            holder.vCardAvatar.setTint(theme.textPrimary)
            holder.name.setTextColor(theme.textPrimary)
            holder.label.setTextColor(theme.textTertiary)
        } else {
            holder.vCardBackground.layoutParams = params.apply { gravity = Gravity.END }
            holder.vCardBackground.setBackgroundTint(holder.containerView.context.resolveThemeColor(R.attr.bubbleColor))
            holder.vCardAvatar.setTint(holder.containerView.context.resolveThemeColor(android.R.attr.textColorSecondary))
            holder.name.setTextColor(holder.containerView.context.resolveThemeColor(android.R.attr.textColorPrimary))
            holder.label.setTextColor(holder.containerView.context.resolveThemeColor(android.R.attr.textColorTertiary))
        }
    }

}
