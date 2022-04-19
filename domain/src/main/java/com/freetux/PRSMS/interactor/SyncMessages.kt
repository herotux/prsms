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

import com.freetux.PRSMS.repository.SyncRepository
import io.reactivex.Flowable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncMessages @Inject constructor(
    private val syncManager: SyncRepository,
    private val updateBadge: UpdateBadge
) : Interactor<Unit>() {

    override fun buildObservable(params: Unit): Flowable<*> {
        return Flowable.just(System.currentTimeMillis())
                .doOnNext { syncManager.syncMessages() }
                .map { startTime -> System.currentTimeMillis() - startTime }
                .map { elapsed -> TimeUnit.MILLISECONDS.toSeconds(elapsed) }
                .doOnNext { seconds -> Timber.v("Completed sync in $seconds seconds") }
                .flatMap { updateBadge.buildObservable(Unit) } // Update the badge
    }

}