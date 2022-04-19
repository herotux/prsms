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
package com.freetux.PRSMS.common.util

import android.content.Context
import android.text.format.DateFormat
import com.freetux.PRSMS.common.util.extensions.isSameDay
import com.freetux.PRSMS.common.util.extensions.isSameWeek
import com.freetux.PRSMS.common.util.extensions.isSameYear
import com.freetux.PRSMS.common.util.shamsicalendar.PersianDate
import com.freetux.PRSMS.common.util.shamsicalendar.PersianDateFormat
import com.freetux.PRSMS.util.Preferences
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormatter @Inject constructor(val context: Context, private val prefs: Preferences) {

    /**
     * Formats the [pattern] correctly for the current locale, and replaces 12 hour format with
     * 24 hour format if necessary
     */
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
        var newpattern = pattern
        return PersianDateFormat(newpattern,PersianDateFormat.PersianDateNumberCharacter.FARSI)
    }

    fun getDetailedTimestamp(date: Long): String {
        val pdate = PersianDate(date)
        if (!prefs.shamsiDate.get()) {
            return getFormatter("M/d/y, h:mm:ss a").format(date)
        } else {
            return getPersianDateFormat("Y/m/d").format(pdate) + " در " + getFormatter("h:mm:ss a").format(date)
        }
    }

    fun getTimestamp(date: Long): String {
        return getFormatter("h:mm a").format(date)
    }

    fun isSameShamsiYear(thenyear: Int): Boolean {
        val pdatenow = PersianDate()
        return thenyear == pdatenow.getShYear()
    }

    fun getMessageTimestamp(date: Long): String {
        val now = Calendar.getInstance()
        val then = Calendar.getInstance()
        then.timeInMillis = date
        val pdate = PersianDate(date)
        val pdatenow = PersianDate()
        val nowyear = pdatenow.getShYear()
        val thenyear = pdate.getShYear()



        if (!prefs.shamsiDate.get()) {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a")
                now.isSameWeek(then) -> getFormatter("E h:mm a")
                now.isSameYear(then) -> getFormatter("MMM d, h:mm a")
                else -> getFormatter("MMM d yyyy, h:mm a")
            }.format(date)

        } else {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a").format(date)
                now.isSameWeek(then) -> getFormatter("E h:mm a").format(date)
                isSameShamsiYear(thenyear) -> getPersianDateFormat("d F").format(pdate) + " در " + getFormatter("h:mm a").format(date)
                else -> getPersianDateFormat("d F Y").format(pdate) + " در " +  getFormatter("h:mm a").format(date)
            }

        }


    }

    fun getConversationTimestamp(date: Long): String {
        val now = Calendar.getInstance()
        val then = Calendar.getInstance()
        then.timeInMillis = date
        val pdate = PersianDate(date)
        val pdatenow = PersianDate()
        val nowyear = pdatenow.getShYear()
        val thenyear = pdate.getShYear()
        if (!prefs.shamsiDate.get()) {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a")
                now.isSameWeek(then) -> getFormatter("E")
                now.isSameYear(then) -> getFormatter("MMM d")
                else -> getFormatter("MM/d/yy")
            }.format(date)
        } else {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a").format(date)
                now.isSameWeek(then) -> getPersianDateFormat("l").format(pdate)
                isSameShamsiYear(thenyear) -> getPersianDateFormat("F").format(pdate) + " " +  getPersianDateFormat("d").format(pdate)
                else -> getPersianDateFormat("Y/m/d").format(pdate)
            }


        }

    }

    fun getScheduledTimestamp(date: Long): String {
        val now = Calendar.getInstance()
        val then = Calendar.getInstance()
        then.timeInMillis = date
        val pdate = PersianDate(date)
        val pdatenow = PersianDate()
        val nowyear = pdatenow.getShYear()
        val thenyear = pdate.getShYear()
        if (!prefs.shamsiDate.get()) {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a")
                now.isSameYear(then) -> getFormatter("MMM d h:mm a")
                else -> getFormatter("MMM d yyyy h:mm a")
            }.format(date)
        } else {
            return when {
                now.isSameDay(then) -> getFormatter("h:mm a").format(date)
                isSameShamsiYear(thenyear) -> getPersianDateFormat("d F ").format(pdate) + " در " + getFormatter("h:mm a").format(date)
                else -> getPersianDateFormat("d F Y ").format(pdate) + " در " + getFormatter("h:mm a").format(date)
            }


        }
    }

}
