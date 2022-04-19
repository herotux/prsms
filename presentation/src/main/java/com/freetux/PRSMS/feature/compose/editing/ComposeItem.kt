/*
 * Copyright (C) 2019 Moez Bhatti <freetux.bhatti@gmail.com>
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
package com.freetux.PRSMS.feature.compose.editing

import com.freetux.PRSMS.model.Contact
import com.freetux.PRSMS.model.ContactGroup
import com.freetux.PRSMS.model.Conversation
import com.freetux.PRSMS.model.PhoneNumber
import io.realm.RealmList

sealed class ComposeItem {

    abstract fun getContacts(): List<Contact>

    data class New(val value: Contact) : ComposeItem() {
        override fun getContacts(): List<Contact> = listOf(value)
    }

    data class Recent(val value: Conversation) : ComposeItem() {
        override fun getContacts(): List<Contact> = value.recipients.map { recipient ->
            recipient.contact ?: Contact(numbers = RealmList(PhoneNumber(address = recipient.address)))
        }
    }

    data class Starred(val value: Contact) : ComposeItem() {
        override fun getContacts(): List<Contact> = listOf(value)
    }

    data class Group(val value: ContactGroup) : ComposeItem() {
        override fun getContacts(): List<Contact> = value.contacts
    }

    data class Person(val value: Contact) : ComposeItem() {
        override fun getContacts(): List<Contact> = listOf(value)
    }
}
