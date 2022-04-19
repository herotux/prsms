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
package com.freetux.PRSMS.feature.conversationinfo

import com.freetux.PRSMS.common.base.QkViewContract
import io.reactivex.Observable

interface ConversationInfoView : QkViewContract<ConversationInfoState> {

    fun recipientClicks(): Observable<Long>
    fun recipientLongClicks(): Observable<Long>
    fun themeClicks(): Observable<Long>
    fun nameClicks(): Observable<*>
    fun nameChanges(): Observable<String>
    fun notificationClicks(): Observable<*>
    fun archiveClicks(): Observable<*>
    fun blockClicks(): Observable<*>
    fun deleteClicks(): Observable<*>
    fun confirmDelete(): Observable<*>
    fun mediaClicks(): Observable<Long>

    fun showNameDialog(name: String)
    fun showThemePicker(recipientId: Long)
    fun showBlockingDialog(conversations: List<Long>, block: Boolean)
    fun requestDefaultSms()
    fun showDeleteDialog()

}
