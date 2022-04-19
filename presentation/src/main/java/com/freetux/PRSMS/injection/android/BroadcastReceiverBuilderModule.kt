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
package com.freetux.PRSMS.injection.android

import com.freetux.PRSMS.feature.widget.WidgetProvider
import com.freetux.PRSMS.injection.scope.ActivityScope
import com.freetux.PRSMS.receiver.BlockThreadReceiver
import com.freetux.PRSMS.receiver.BootReceiver
import com.freetux.PRSMS.receiver.DefaultSmsChangedReceiver
import com.freetux.PRSMS.receiver.DeleteMessagesReceiver
import com.freetux.PRSMS.receiver.MarkArchivedReceiver
import com.freetux.PRSMS.receiver.MarkReadReceiver
import com.freetux.PRSMS.receiver.MarkSeenReceiver
import com.freetux.PRSMS.receiver.MmsReceivedReceiver
import com.freetux.PRSMS.receiver.MmsReceiver
import com.freetux.PRSMS.receiver.MmsSentReceiver
import com.freetux.PRSMS.receiver.MmsUpdatedReceiver
import com.freetux.PRSMS.receiver.NightModeReceiver
import com.freetux.PRSMS.receiver.RemoteMessagingReceiver
import com.freetux.PRSMS.receiver.SendScheduledMessageReceiver
import com.freetux.PRSMS.receiver.SmsDeliveredReceiver
import com.freetux.PRSMS.receiver.SmsProviderChangedReceiver
import com.freetux.PRSMS.receiver.SmsReceiver
import com.freetux.PRSMS.receiver.SmsSentReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BroadcastReceiverBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindBlockThreadReceiver(): BlockThreadReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindBootReceiver(): BootReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindDefaultSmsChangedReceiver(): DefaultSmsChangedReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindDeleteMessagesReceiver(): DeleteMessagesReceiver

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMarkArchivedReceiver(): MarkArchivedReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMarkReadReceiver(): MarkReadReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMarkSeenReceiver(): MarkSeenReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMmsReceivedReceiver(): MmsReceivedReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMmsReceiver(): MmsReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMmsSentReceiver(): MmsSentReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMmsUpdatedReceiver(): MmsUpdatedReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindNightModeReceiver(): NightModeReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindRemoteMessagingReceiver(): RemoteMessagingReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindSendScheduledMessageReceiver(): SendScheduledMessageReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindSmsDeliveredReceiver(): SmsDeliveredReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindSmsProviderChangedReceiver(): SmsProviderChangedReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindSmsReceiver(): SmsReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindSmsSentReceiver(): SmsSentReceiver

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindWidgetProvider(): WidgetProvider

}