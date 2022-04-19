/*
 * Copyright (C) 2019 Freetux <thefreetux@gmail.com>
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
package com.freetux.PRSMS.feature.blocking.messages

import com.freetux.PRSMS.common.base.QkViewContract
import io.reactivex.Observable

interface BlockedMessagesView : QkViewContract<BlockedMessagesState> {

    val menuReadyIntent: Observable<Unit>
    val optionsItemIntent: Observable<Int>
    val conversationClicks: Observable<Long>
    val selectionChanges: Observable<List<Long>>
    val confirmDeleteIntent: Observable<List<Long>>
    val backClicked: Observable<*>

    fun clearSelection()
    fun showBlockingDialog(conversations: List<Long>, block: Boolean)
    fun showDeleteDialog(conversations: List<Long>)
    fun goBack()

}
