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
package com.freetux.PRSMS.feature.blocking

import android.view.View
import com.bluelinelabs.conductor.RouterTransaction
import com.jakewharton.rxbinding2.view.clicks
import com.freetux.PRSMS.R
import com.freetux.PRSMS.common.QkChangeHandler
import com.freetux.PRSMS.common.base.QkController
import com.freetux.PRSMS.common.util.Colors
import com.freetux.PRSMS.common.util.extensions.animateLayoutChanges
import com.freetux.PRSMS.feature.blocking.manager.BlockingManagerController
import com.freetux.PRSMS.feature.blocking.messages.BlockedMessagesController
import com.freetux.PRSMS.feature.blocking.numbers.BlockedNumbersController
import com.freetux.PRSMS.injection.appComponent
import kotlinx.android.synthetic.main.blocking_controller.*
import kotlinx.android.synthetic.main.settings_switch_widget.view.*
import javax.inject.Inject

class BlockingController : QkController<BlockingView, BlockingState, BlockingPresenter>(), BlockingView {

    override val blockingManagerIntent by lazy { blockingManager.clicks() }
    override val blockedNumbersIntent by lazy { blockedNumbers.clicks() }
    override val blockedMessagesIntent by lazy { blockedMessages.clicks() }
    override val dropClickedIntent by lazy { drop.clicks() }

    @Inject lateinit var colors: Colors
    @Inject override lateinit var presenter: BlockingPresenter

    init {
        appComponent.inject(this)
        retainViewMode = RetainViewMode.RETAIN_DETACH
        layoutRes = R.layout.blocking_controller
    }

    override fun onViewCreated() {
        super.onViewCreated()
        parent.postDelayed({ parent?.animateLayoutChanges = true }, 100)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.bindIntents(this)
        setTitle(R.string.blocking_title)
        showBackButton(true)
    }

    override fun render(state: BlockingState) {
        blockingManager.summary = state.blockingManager
        drop.checkbox.isChecked = state.dropEnabled
        blockedMessages.isEnabled = !state.dropEnabled
    }

    override fun openBlockedNumbers() {
        router.pushController(RouterTransaction.with(BlockedNumbersController())
                .pushChangeHandler(QkChangeHandler())
                .popChangeHandler(QkChangeHandler()))
    }

    override fun openBlockedMessages() {
        router.pushController(RouterTransaction.with(BlockedMessagesController())
                .pushChangeHandler(QkChangeHandler())
                .popChangeHandler(QkChangeHandler()))
    }

    override fun openBlockingManager() {
        router.pushController(RouterTransaction.with(BlockingManagerController())
                .pushChangeHandler(QkChangeHandler())
                .popChangeHandler(QkChangeHandler()))
    }

}
