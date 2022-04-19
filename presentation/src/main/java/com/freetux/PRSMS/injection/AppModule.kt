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
package com.freetux.PRSMS.injection

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.freetux.PRSMS.blocking.BlockingClient
import com.freetux.PRSMS.blocking.BlockingManager
import com.freetux.PRSMS.common.ViewModelFactory
import com.freetux.PRSMS.common.util.BillingManagerImpl
import com.freetux.PRSMS.common.util.NotificationManagerImpl
import com.freetux.PRSMS.common.util.ShortcutManagerImpl
import com.freetux.PRSMS.feature.conversationinfo.injection.ConversationInfoComponent
import com.freetux.PRSMS.feature.themepicker.injection.ThemePickerComponent
import com.freetux.PRSMS.listener.ContactAddedListener
import com.freetux.PRSMS.listener.ContactAddedListenerImpl
import com.freetux.PRSMS.manager.ActiveConversationManager
import com.freetux.PRSMS.manager.ActiveConversationManagerImpl
import com.freetux.PRSMS.manager.AlarmManager
import com.freetux.PRSMS.manager.AlarmManagerImpl
import com.freetux.PRSMS.manager.AnalyticsManager
import com.freetux.PRSMS.manager.AnalyticsManagerImpl
import com.freetux.PRSMS.manager.BillingManager
import com.freetux.PRSMS.manager.ChangelogManager
import com.freetux.PRSMS.manager.ChangelogManagerImpl
import com.freetux.PRSMS.manager.KeyManager
import com.freetux.PRSMS.manager.KeyManagerImpl
import com.freetux.PRSMS.manager.NotificationManager
import com.freetux.PRSMS.manager.PermissionManager
import com.freetux.PRSMS.manager.PermissionManagerImpl
import com.freetux.PRSMS.manager.RatingManager
import com.freetux.PRSMS.manager.ReferralManager
import com.freetux.PRSMS.manager.ReferralManagerImpl
import com.freetux.PRSMS.manager.ShortcutManager
import com.freetux.PRSMS.manager.WidgetManager
import com.freetux.PRSMS.manager.WidgetManagerImpl
import com.freetux.PRSMS.mapper.CursorToContact
import com.freetux.PRSMS.mapper.CursorToContactGroup
import com.freetux.PRSMS.mapper.CursorToContactGroupImpl
import com.freetux.PRSMS.mapper.CursorToContactGroupMember
import com.freetux.PRSMS.mapper.CursorToContactGroupMemberImpl
import com.freetux.PRSMS.mapper.CursorToContactImpl
import com.freetux.PRSMS.mapper.CursorToConversation
import com.freetux.PRSMS.mapper.CursorToConversationImpl
import com.freetux.PRSMS.mapper.CursorToMessage
import com.freetux.PRSMS.mapper.CursorToMessageImpl
import com.freetux.PRSMS.mapper.CursorToPart
import com.freetux.PRSMS.mapper.CursorToPartImpl
import com.freetux.PRSMS.mapper.CursorToRecipient
import com.freetux.PRSMS.mapper.CursorToRecipientImpl
import com.freetux.PRSMS.mapper.RatingManagerImpl
import com.freetux.PRSMS.repository.BackupRepository
import com.freetux.PRSMS.repository.BackupRepositoryImpl
import com.freetux.PRSMS.repository.BlockingRepository
import com.freetux.PRSMS.repository.BlockingRepositoryImpl
import com.freetux.PRSMS.repository.ContactRepository
import com.freetux.PRSMS.repository.ContactRepositoryImpl
import com.freetux.PRSMS.repository.ConversationRepository
import com.freetux.PRSMS.repository.ConversationRepositoryImpl
import com.freetux.PRSMS.repository.MessageRepository
import com.freetux.PRSMS.repository.MessageRepositoryImpl
import com.freetux.PRSMS.repository.ScheduledMessageRepository
import com.freetux.PRSMS.repository.ScheduledMessageRepositoryImpl
import com.freetux.PRSMS.repository.SyncRepository
import com.freetux.PRSMS.repository.SyncRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    ConversationInfoComponent::class,
    ThemePickerComponent::class])
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideRxPreferences(preferences: SharedPreferences): RxSharedPreferences {
        return RxSharedPreferences.create(preferences)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    @Provides
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    // Listener

    @Provides
    fun provideContactAddedListener(listener: ContactAddedListenerImpl): ContactAddedListener = listener

    // Manager

    @Provides
    fun provideBillingManager(manager: BillingManagerImpl): BillingManager = manager

    @Provides
    fun provideActiveConversationManager(manager: ActiveConversationManagerImpl): ActiveConversationManager = manager

    @Provides
    fun provideAlarmManager(manager: AlarmManagerImpl): AlarmManager = manager

    @Provides
    fun provideAnalyticsManager(manager: AnalyticsManagerImpl): AnalyticsManager = manager

    @Provides
    fun blockingClient(manager: BlockingManager): BlockingClient = manager

    @Provides
    fun changelogManager(manager: ChangelogManagerImpl): ChangelogManager = manager

    @Provides
    fun provideKeyManager(manager: KeyManagerImpl): KeyManager = manager

    @Provides
    fun provideNotificationsManager(manager: NotificationManagerImpl): NotificationManager = manager

    @Provides
    fun providePermissionsManager(manager: PermissionManagerImpl): PermissionManager = manager

    @Provides
    fun provideRatingManager(manager: RatingManagerImpl): RatingManager = manager

    @Provides
    fun provideShortcutManager(manager: ShortcutManagerImpl): ShortcutManager = manager

    @Provides
    fun provideReferralManager(manager: ReferralManagerImpl): ReferralManager = manager

    @Provides
    fun provideWidgetManager(manager: WidgetManagerImpl): WidgetManager = manager

    // Mapper

    @Provides
    fun provideCursorToContact(mapper: CursorToContactImpl): CursorToContact = mapper

    @Provides
    fun provideCursorToContactGroup(mapper: CursorToContactGroupImpl): CursorToContactGroup = mapper

    @Provides
    fun provideCursorToContactGroupMember(mapper: CursorToContactGroupMemberImpl): CursorToContactGroupMember = mapper

    @Provides
    fun provideCursorToConversation(mapper: CursorToConversationImpl): CursorToConversation = mapper

    @Provides
    fun provideCursorToMessage(mapper: CursorToMessageImpl): CursorToMessage = mapper

    @Provides
    fun provideCursorToPart(mapper: CursorToPartImpl): CursorToPart = mapper

    @Provides
    fun provideCursorToRecipient(mapper: CursorToRecipientImpl): CursorToRecipient = mapper

    // Repository

    @Provides
    fun provideBackupRepository(repository: BackupRepositoryImpl): BackupRepository = repository

    @Provides
    fun provideBlockingRepository(repository: BlockingRepositoryImpl): BlockingRepository = repository

    @Provides
    fun provideContactRepository(repository: ContactRepositoryImpl): ContactRepository = repository

    @Provides
    fun provideConversationRepository(repository: ConversationRepositoryImpl): ConversationRepository = repository

    @Provides
    fun provideMessageRepository(repository: MessageRepositoryImpl): MessageRepository = repository

    @Provides
    fun provideScheduledMessagesRepository(repository: ScheduledMessageRepositoryImpl): ScheduledMessageRepository = repository

    @Provides
    fun provideSyncRepository(repository: SyncRepositoryImpl): SyncRepository = repository

}