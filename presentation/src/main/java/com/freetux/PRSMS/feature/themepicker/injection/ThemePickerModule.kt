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
package com.freetux.PRSMS.feature.themepicker.injection

import com.freetux.PRSMS.feature.themepicker.ThemePickerController
import com.freetux.PRSMS.injection.scope.ControllerScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ThemePickerModule(private val controller: ThemePickerController) {

    @Provides
    @ControllerScope
    @Named("recipientId")
    fun provideThreadId(): Long = controller.recipientId

}