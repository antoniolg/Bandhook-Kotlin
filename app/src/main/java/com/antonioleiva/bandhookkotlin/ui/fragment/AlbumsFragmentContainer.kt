package com.antonioleiva.bandhookkotlin.ui.fragment

import com.antonioleiva.bandhookkotlin.ui.presenter.AlbumsPresenter

/**
 * @author tpom6oh@gmail.com
 *
 * 05/07/16.
 */
interface AlbumsFragmentContainer {
    fun getAlbumsPresenter(): AlbumsPresenter
}