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
package com.freetux.PRSMS.util

import com.freetux.PRSMS.compat.SubscriptionInfoCompat
import com.freetux.PRSMS.compat.SubscriptionManagerCompat
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ActiveSubscriptionObservable(
    private val subscriptionManager: SubscriptionManagerCompat
) : Observable<List<SubscriptionInfoCompat>>() {

    override fun subscribeActual(observer: Observer<in List<SubscriptionInfoCompat>>) {
        observer.onNext(subscriptionManager.activeSubscriptionInfoList)

        val listener = Listener(subscriptionManager, observer)
        observer.onSubscribe(listener)
        subscriptionManager.addOnSubscriptionsChangedListener(listener)
    }

    internal class Listener(
        private val subscriptionManager: SubscriptionManagerCompat,
        private val observer: Observer<in List<SubscriptionInfoCompat>>
    ) : Disposable, SubscriptionManagerCompat.OnSubscriptionsChangedListener() {

        private var disposed: Boolean = false

        override fun onSubscriptionsChanged() {
            if (!isDisposed) {
                observer.onNext(subscriptionManager.activeSubscriptionInfoList)
            }
        }

        override fun isDisposed(): Boolean = disposed

        override fun dispose() {
            disposed = true
            subscriptionManager.removeOnSubscriptionsChangedListener(this)
        }

    }

}