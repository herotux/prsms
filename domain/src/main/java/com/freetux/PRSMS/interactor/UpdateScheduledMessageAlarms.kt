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
package com.freetux.PRSMS.interactor

import com.freetux.PRSMS.manager.AlarmManager
import com.freetux.PRSMS.repository.ScheduledMessageRepository
import io.reactivex.Flowable
import javax.inject.Inject

class UpdateScheduledMessageAlarms @Inject constructor(
    private val alarmManager: AlarmManager,
    private val scheduledMessageRepo: ScheduledMessageRepository,
    private val sendScheduledMessage: SendScheduledMessage
) : Interactor<Unit>() {

    override fun buildObservable(params: Unit): Flowable<*> {
        return Flowable.just(params)
                .map { scheduledMessageRepo.getScheduledMessages() } // Get all the scheduled messages
                .map { it.map { message -> Pair(message.id, message.date) } } // Map the data we need out of Realm
                .flatMap { messages -> Flowable.fromIterable(messages) } // Turn the list into a stream
                .doOnNext { (id, date) ->
                    alarmManager.setAlarm(date, alarmManager.getScheduledMessageIntent(id)) // Create alarm
                }
                .filter { (_, date) -> date < System.currentTimeMillis() } // Filter messages that should have already been sent
                .flatMap { (id, _) -> sendScheduledMessage.buildObservable(id) } // Send them
    }

}