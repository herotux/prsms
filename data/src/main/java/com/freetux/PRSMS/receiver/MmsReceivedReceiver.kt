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

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.klinker.android.send_message.MmsReceivedReceiver
import com.freetux.PRSMS.interactor.ReceiveMms
import dagger.android.AndroidInjection
import javax.inject.Inject

class MmsReceivedReceiver : MmsReceivedReceiver() {

    @Inject lateinit var receiveMms: ReceiveMms

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)
        super.onReceive(context, intent)
    }

    override fun onMessageReceived(messageUri: Uri?) {
        messageUri?.let { uri ->
            val pendingResult = goAsync()
            receiveMms.execute(uri) { pendingResult.finish() }
        }
    }

}
