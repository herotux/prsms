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
package com.freetux.PRSMS.feature.gallery

import android.content.Context
import com.freetux.PRSMS.R
import com.freetux.PRSMS.common.Navigator
import com.freetux.PRSMS.common.base.QkViewModel
import com.freetux.PRSMS.common.util.extensions.makeToast
import com.freetux.PRSMS.extensions.mapNotNull
import com.freetux.PRSMS.interactor.SaveImage
import com.freetux.PRSMS.manager.PermissionManager
import com.freetux.PRSMS.repository.ConversationRepository
import com.freetux.PRSMS.repository.MessageRepository
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.Flowable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject
import javax.inject.Named

class GalleryViewModel @Inject constructor(
    conversationRepo: ConversationRepository,
    @Named("partId") private val partId: Long,
    private val context: Context,
    private val messageRepo: MessageRepository,
    private val navigator: Navigator,
    private val saveImage: SaveImage,
    private val permissions: PermissionManager
) : QkViewModel<GalleryView, GalleryState>(GalleryState()) {

    init {
        disposables += Flowable.just(partId)
                .mapNotNull(messageRepo::getMessageForPart)
                .mapNotNull { message -> message.threadId }
                .doOnNext { threadId -> newState { copy(parts = messageRepo.getPartsForConversation(threadId)) } }
                .doOnNext { threadId ->
                    newState {
                        copy(title = conversationRepo.getConversation(threadId)?.getTitle())
                    }
                }
                .subscribe()
    }

    override fun bindView(view: GalleryView) {
        super.bindView(view)

        // When the screen is touched, toggle the visibility of the navigation UI
        view.screenTouched()
                .withLatestFrom(state) { _, state -> state.navigationVisible }
                .map { navigationVisible -> !navigationVisible }
                .autoDisposable(view.scope())
                .subscribe { navigationVisible -> newState { copy(navigationVisible = navigationVisible) } }

        // Save image to device
        view.optionsItemSelected()
                .filter { itemId -> itemId == R.id.save }
                .filter { permissions.hasStorage().also { if (!it) view.requestStoragePermission() } }
                .withLatestFrom(view.pageChanged()) { _, part -> part.id }
                .autoDisposable(view.scope())
                .subscribe { partId -> saveImage.execute(partId) { context.makeToast(R.string.gallery_toast_saved) } }

        // Share image externally
        view.optionsItemSelected()
                .filter { itemId -> itemId == R.id.share }
                .filter { permissions.hasStorage().also { if (!it) view.requestStoragePermission() } }
                .withLatestFrom(view.pageChanged()) { _, part -> part.id }
                .autoDisposable(view.scope())
                .subscribe { partId -> messageRepo.savePart(partId)?.let(navigator::shareFile) }
    }

}
