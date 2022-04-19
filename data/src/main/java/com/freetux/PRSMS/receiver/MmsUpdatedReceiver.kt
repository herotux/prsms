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
package com.freetux.PRSMS.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.freetux.PRSMS.interactor.SyncMessage
import dagger.android.AndroidInjection
import javax.inject.Inject

class MmsUpdatedReceiver : BroadcastReceiver() {

    companion object {
        const val URI = "uri"
    }

    @Inject lateinit var syncMessage: SyncMessage

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)

        intent.getStringExtra(URI)?.let { uriString ->
            val pendingResult = goAsync()
            syncMessage.execute(Uri.parse(uriString)) { pendingResult.finish() }
        }
    }

}