/*
 * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
 *
 * This file is part of QKSMS.
 *
 * QKSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QKSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.moez.QKSMS.common.util

import android.content.Context
import android.text.format.DateFormat
import com.moez.QKSMS.common.util.extensions.isSameDay
import com.moez.QKSMS.common.util.extensions.isSameWeek
import com.moez.QKSMS.common.util.extensions.isSameYear
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import com.moez.QKSMS.common.util.shamsicalendar.PersianDateFormat;
import com.moez.QKSMS.common.util.shamsicalendar.PersianDate;
import com.moez.QKSMS.common.util.shamsicalendar.LanguageUtils;
import com.moez.QKSMS.util.Preferences

@Singleton
class DateFormatter @Inject constructor(val context: Context) {

    /**
     * Formats the [pattern] correctly for the current locale, and replaces 12 hour format with
     * 24 hour format if necessary
     */
    private lateinit var prefs: Preferences
    private fun getFormatter(pattern: String): SimpleDateFormat {
        var formattedPattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), pattern)

        if (DateFormat.is24HourFormat(context)) {
            formattedPattern = formattedPattern
                    .replace("h", "HH")
                    .replace("K", "HH")
                    .replace(" a".toRegex(), "")
        }

        return SimpleDateFormat(formattedPattern, Locale.getDefault())
    }

    private fun getPersianDateFormat(pattern: String): PersianDateFormat {
        return PersianDateFormat(pattern)
    }

    fun getDetailedTimestamp(date: Long): String {
        val pdate = PersianDate(date)
        if (!prefs.shamsiDate.get()) {
            return getFormatter("M/d/y, h:mm:ss a").format(date)
        } else {
            return getPersianDateFormat("y/MM/dd HH:mm:ss").format(pdate)
        }
    }

    fun getTimestamp(date: Long): String {
        val pdate = PersianDate(date)
        if (!prefs.shamsiDate.get()) {
            return getFormatter("h:mm a").format(date)
        } else {
            return getPersianDateFormat("HH:mm").format(pdate)

        }
    }

    fun getMessageTimestamp(date: Long): String {
        val now = Calendar.getInstance()
        val then = Calendar.getInstance()
        then.timeInMillis = date
        val pdate = PersianDate(date)

        if (!prefs.shamsiDate.get()) {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a")
                now.isSameWeek(then) -> getFormatter("E h:mm a")
                now.isSameYear(then) -> getFormatter("MMM d, h:mm a")
                else -> getFormatter("MMM d yyyy, h:mm a")
            }.format(date)
            
        } else {
            return when {
                now.isSameDay(then) -> getPersianDateFormat("HH:mm")
                now.isSameWeek(then) -> getPersianDateFormat("l HH:mm")
                now.isSameYear(then) -> getPersianDateFormat("F dd HH:mm")
                else -> getPersianDateFormat("Y F dd HH:mm")
            }.format(pdate)
            

        }

        
    }

    fun getConversationTimestamp(date: Long): String {
        val now = Calendar.getInstance()
        val then = Calendar.getInstance()
        then.timeInMillis = date
        val pdate = PersianDate(date)
        if (!prefs.shamsiDate.get()) {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a")
                now.isSameWeek(then) -> getFormatter("E")
                now.isSameYear(then) -> getFormatter("MMM d")
                else -> getFormatter("MM/d/yy")
            }.format(date)
        } else {
            return when {
                now.isSameDay(then) -> getPersianDateFormat("HH:mm")
                now.isSameWeek(then) -> getPersianDateFormat("l")
                now.isSameYear(then) -> getPersianDateFormat("dd F")
                else -> getPersianDateFormat("y/MM/dd HH:mm")
            }.format(pdate)
            

        }
        
    }

    fun getScheduledTimestamp(date: Long): String {
        val now = Calendar.getInstance()
        val then = Calendar.getInstance()
        then.timeInMillis = date
        val pdate = PersianDate(date)
        if (!prefs.shamsiDate.get()) {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a")
                now.isSameYear(then) -> getFormatter("MMM d h:mm a")
                else -> getFormatter("MMM d yyyy h:mm a")
            }.format(date)
        } else {
            return when {
                now.isSameDay(then) -> getPersianDateFormat("HH:mm")
                now.isSameYear(then) -> getPersianDateFormat("F dd HH:mm")
                else -> getPersianDateFormat("Y F dd HH:mm")
            }.format(pdate)
            

        }
    }

}
