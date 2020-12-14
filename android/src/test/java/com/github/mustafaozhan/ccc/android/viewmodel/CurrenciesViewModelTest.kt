/*
 * Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package com.github.mustafaozhan.ccc.android.viewmodel

import com.github.mustafaozhan.ccc.client.repo.SettingsRepository
import com.github.mustafaozhan.ccc.client.ui.currencies.BackEffect
import com.github.mustafaozhan.ccc.client.ui.currencies.CurrenciesViewModel
import com.github.mustafaozhan.ccc.client.ui.currencies.FewCurrencyEffect
import com.github.mustafaozhan.ccc.common.db.CurrencyDao
import com.github.mustafaozhan.ccc.common.model.Currency
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ObsoleteCoroutinesApi
@RunWith(JUnit4::class)
class CurrenciesViewModelTest : BaseViewModelTest<CurrenciesViewModel>() {

    override lateinit var viewModel: CurrenciesViewModel

    @RelaxedMockK
    lateinit var settingsRepository: SettingsRepository

    @RelaxedMockK
    lateinit var currencyDao: CurrencyDao

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CurrenciesViewModel(settingsRepository, currencyDao)
    }

    @Test
    fun `filter list`() {
        val euro = Currency("EUR", "Euro", "€")
        val dollar = Currency("USD", "American Dollar", "$")

        viewModel.data.unFilteredList = mutableListOf<Currency>().apply {
            add(euro)
            add(dollar)
        }

        viewModel.filterList("USD")
        assertEquals(true, viewModel.state.currencyList.value.contains(dollar))

        viewModel.filterList("Euro")
        assertEquals(true, viewModel.state.currencyList.value.contains(euro))

        viewModel.filterList("$")
        assertEquals(true, viewModel.state.currencyList.value.contains(dollar))

        viewModel.filterList("asdasd")
        assertEquals(true, viewModel.state.currencyList.value.isEmpty())

        viewModel.filterList("o")
        assertEquals(2, viewModel.state.currencyList.value.size)
    }

    @Test
    fun `hide selection visibility`() {
        viewModel.hideSelectionVisibility()
        assertEquals(false, viewModel.state.selectionVisibility.value)
    }

    @Test
    fun `query get updated on filtering list`() {
        val query = "query"
        viewModel.filterList(query)
        assertEquals(query, viewModel.data.query)
    }

    // Event
    @Test
    fun `on long click`() = with(viewModel) {
        val currentValue = viewModel.state.selectionVisibility.value
        getEvent().onItemLongClick()
        assertEquals(!currentValue, viewModel.state.selectionVisibility.value)
    }

    @Test
    fun `update all currencies state`() {
        assertEquals(Unit, viewModel.getEvent().updateAllCurrenciesState(true))
        assertEquals(Unit, viewModel.getEvent().updateAllCurrenciesState(false))
    }

    @Test
    fun `on item click`() {
        val currency = Currency("EUR", "Euro", "€")
        assertEquals(Unit, viewModel.getEvent().onItemClick(currency))
    }

    @Test
    fun `on close click`() = runBlockingTest {
        launch {
            viewModel.getEvent().onCloseClick()
            assertEquals(BackEffect, viewModel.effect.single())
            assertEquals("", viewModel.data.query)
        }.cancel()
    }

    @Test
    fun `on done click`() = runBlockingTest {
        launch {
            viewModel.getEvent().onDoneClick()
            assertEquals(FewCurrencyEffect, viewModel.effect.single())
        }.cancel()
    }
}
