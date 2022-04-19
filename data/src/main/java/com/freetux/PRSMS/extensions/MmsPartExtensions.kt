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
package com.freetux.PRSMS.extensions

import com.google.android.mms.ContentType
import com.freetux.PRSMS.model.MmsPart

fun MmsPart.isSmil() = ContentType.APP_SMIL == type

fun MmsPart.isImage() = ContentType.isImageType(type)

fun MmsPart.isVideo() = ContentType.isVideoType(type)

fun MmsPart.isText() = ContentType.TEXT_PLAIN == type

fun MmsPart.isVCard() = ContentType.TEXT_VCARD == type
