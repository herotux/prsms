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
package com.freetux.PRSMS.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.freetux.PRSMS.R
import com.freetux.PRSMS.common.Navigator
import com.freetux.PRSMS.common.util.Colors
import com.freetux.PRSMS.common.util.extensions.setBackgroundTint
import com.freetux.PRSMS.common.util.extensions.setTint
import com.freetux.PRSMS.injection.appComponent
import com.freetux.PRSMS.model.Recipient
import com.freetux.PRSMS.util.GlideApp
import kotlinx.android.synthetic.main.avatar_view.view.*
import javax.inject.Inject

class AvatarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    @Inject lateinit var colors: Colors
    @Inject lateinit var navigator: Navigator

    private var lookupKey: String? = null
    private var fullName: String? = null
    private var photoUri: String? = null
    private var lastUpdated: Long? = null
    private var theme: Colors.Theme

    init {
        if (!isInEditMode) {
            appComponent.inject(this)
        }

        theme = colors.theme()

        View.inflate(context, R.layout.avatar_view, this)
        setBackgroundResource(R.drawable.circle)
        clipToOutline = true
    }

    /**
     * Use the [contact] information to display the avatar.
     */
    fun setRecipient(recipient: Recipient?) {
        lookupKey = recipient?.contact?.lookupKey
        fullName = recipient?.contact?.name
        photoUri = recipient?.contact?.photoUri
        lastUpdated = recipient?.contact?.lastUpdate
        theme = colors.theme(recipient)
        updateView()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if (!isInEditMode) {
            updateView()
        }
    }

    private fun updateView() {
        // Apply theme
        setBackgroundTint(theme.theme)
        initial.setTextColor(theme.textPrimary)
        icon.setTint(theme.textPrimary)

        val initials = fullName
                ?.substringBefore(',')
                ?.split(" ").orEmpty()
                .filter { name -> name.isNotEmpty() }
                .map { name -> name[0] }
                .filter { initial -> initial.isLetterOrDigit() }
                .map { initial -> initial.toString() }

        if (initials.isNotEmpty()) {
            initial.text = if (initials.size > 1) initials.first() + initials.last() else initials.first()
            icon.visibility = GONE
        } else {
            initial.text = null
            icon.visibility = VISIBLE
        }

        photo.setImageDrawable(null)
        photoUri?.let { photoUri ->
            GlideApp.with(photo)
                    .load(photoUri)
                    .into(photo)
        }
    }
}
