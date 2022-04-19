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
package com.freetux.PRSMS.injection.android

import com.freetux.PRSMS.feature.backup.BackupActivity
import com.freetux.PRSMS.feature.blocking.BlockingActivity
import com.freetux.PRSMS.feature.compose.ComposeActivity
import com.freetux.PRSMS.feature.compose.ComposeActivityModule
import com.freetux.PRSMS.feature.contacts.ContactsActivity
import com.freetux.PRSMS.feature.contacts.ContactsActivityModule
import com.freetux.PRSMS.feature.conversationinfo.ConversationInfoActivity
import com.freetux.PRSMS.feature.gallery.GalleryActivity
import com.freetux.PRSMS.feature.gallery.GalleryActivityModule
import com.freetux.PRSMS.feature.main.MainActivity
import com.freetux.PRSMS.feature.main.MainActivityModule
import com.freetux.PRSMS.feature.notificationprefs.NotificationPrefsActivity
import com.freetux.PRSMS.feature.notificationprefs.NotificationPrefsActivityModule
import com.freetux.PRSMS.feature.plus.PlusActivity
import com.freetux.PRSMS.feature.plus.PlusActivityModule
import com.freetux.PRSMS.feature.qkreply.QkReplyActivity
import com.freetux.PRSMS.feature.qkreply.QkReplyActivityModule
import com.freetux.PRSMS.feature.scheduled.ScheduledActivity
import com.freetux.PRSMS.feature.scheduled.ScheduledActivityModule
import com.freetux.PRSMS.feature.settings.SettingsActivity
import com.freetux.PRSMS.injection.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PlusActivityModule::class])
    abstract fun bindPlusActivity(): PlusActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindBackupActivity(): BackupActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ComposeActivityModule::class])
    abstract fun bindComposeActivity(): ComposeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ContactsActivityModule::class])
    abstract fun bindContactsActivity(): ContactsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindConversationInfoActivity(): ConversationInfoActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GalleryActivityModule::class])
    abstract fun bindGalleryActivity(): GalleryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [NotificationPrefsActivityModule::class])
    abstract fun bindNotificationPrefsActivity(): NotificationPrefsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [QkReplyActivityModule::class])
    abstract fun bindQkReplyActivity(): QkReplyActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ScheduledActivityModule::class])
    abstract fun bindScheduledActivity(): ScheduledActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindSettingsActivity(): SettingsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindBlockingActivity(): BlockingActivity

}
