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

import com.freetux.PRSMS.common.QKApplication
import com.freetux.PRSMS.common.QkDialog
import com.freetux.PRSMS.common.util.QkChooserTargetService
import com.freetux.PRSMS.common.widget.AvatarView
import com.freetux.PRSMS.common.widget.PagerTitleView
import com.freetux.PRSMS.common.widget.PreferenceView
import com.freetux.PRSMS.common.widget.QkEditText
import com.freetux.PRSMS.common.widget.QkSwitch
import com.freetux.PRSMS.common.widget.QkTextView
import com.freetux.PRSMS.common.widget.RadioPreferenceView
import com.freetux.PRSMS.feature.backup.BackupController
import com.freetux.PRSMS.feature.blocking.BlockingController
import com.freetux.PRSMS.feature.blocking.manager.BlockingManagerController
import com.freetux.PRSMS.feature.blocking.messages.BlockedMessagesController
import com.freetux.PRSMS.feature.blocking.numbers.BlockedNumbersController
import com.freetux.PRSMS.feature.compose.editing.DetailedChipView
import com.freetux.PRSMS.feature.conversationinfo.injection.ConversationInfoComponent
import com.freetux.PRSMS.feature.settings.SettingsController
import com.freetux.PRSMS.feature.settings.about.AboutController
import com.freetux.PRSMS.feature.settings.swipe.SwipeActionsController
import com.freetux.PRSMS.feature.themepicker.injection.ThemePickerComponent
import com.freetux.PRSMS.feature.widget.WidgetAdapter
import com.freetux.PRSMS.injection.android.ActivityBuilderModule
import com.freetux.PRSMS.injection.android.BroadcastReceiverBuilderModule
import com.freetux.PRSMS.injection.android.ServiceBuilderModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilderModule::class,
    BroadcastReceiverBuilderModule::class,
    ServiceBuilderModule::class])
interface AppComponent {

    fun conversationInfoBuilder(): ConversationInfoComponent.Builder
    fun themePickerBuilder(): ThemePickerComponent.Builder

    fun inject(application: QKApplication)

    fun inject(controller: AboutController)
    fun inject(controller: BackupController)
    fun inject(controller: BlockedMessagesController)
    fun inject(controller: BlockedNumbersController)
    fun inject(controller: BlockingController)
    fun inject(controller: BlockingManagerController)
    fun inject(controller: SettingsController)
    fun inject(controller: SwipeActionsController)

    fun inject(dialog: QkDialog)

    fun inject(service: WidgetAdapter)

    /**
     * This can't use AndroidInjection, or else it will crash on pre-marshmallow devices
     */
    fun inject(service: QkChooserTargetService)

    fun inject(view: AvatarView)
    fun inject(view: DetailedChipView)
    fun inject(view: PagerTitleView)
    fun inject(view: PreferenceView)
    fun inject(view: RadioPreferenceView)
    fun inject(view: QkEditText)
    fun inject(view: QkSwitch)
    fun inject(view: QkTextView)

}
