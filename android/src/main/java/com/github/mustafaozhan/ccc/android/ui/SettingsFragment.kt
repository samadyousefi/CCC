/*
 * Copyright (c) 2021 Mustafa Ozhan. All rights reserved.
 */
package com.github.mustafaozhan.ccc.android.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.github.mustafaozhan.basemob.fragment.BaseVBFragment
import com.github.mustafaozhan.ccc.android.util.Toast
import com.github.mustafaozhan.ccc.android.util.setAdaptiveBannerAd
import com.github.mustafaozhan.ccc.android.util.showDialog
import com.github.mustafaozhan.ccc.android.util.showSingleChoiceDialog
import com.github.mustafaozhan.ccc.android.util.visibleIf
import com.github.mustafaozhan.ccc.client.log.kermit
import com.github.mustafaozhan.ccc.client.model.AppTheme
import com.github.mustafaozhan.ccc.client.util.toUnit
import com.github.mustafaozhan.ccc.client.viewmodel.SettingsEffect
import com.github.mustafaozhan.ccc.client.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collect
import mustafaozhan.github.com.mycurrencies.R
import mustafaozhan.github.com.mycurrencies.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("TooManyFunctions")
class SettingsFragment : BaseVBFragment<FragmentSettingsBinding>() {

    companion object {
        private const val TEXT_EMAIL_TYPE = "text/email"
        private const val TEXT_TYPE = "text/plain"
    }

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun bind() {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kermit.d { "SettingsFragment onCreate" }
        binding.adViewContainer.setAdaptiveBannerAd(
            getString(R.string.banner_ad_unit_id_settings),
            settingsViewModel.isRewardExpired()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kermit.d { "SettingsFragment onViewCreated" }
        initViews()
        observeStates()
        observeEffect()
        setListeners()
    }

    private fun initViews() = with(binding) {
        with(itemCurrencies) {
            imgSettingsItem.setBackgroundResource(R.drawable.ic_currency)
            settingsItemTitle.text = getString(R.string.settings_item_currencies_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_currencies_sub_title)
        }

        with(itemTheme) {
            root.visibleIf(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            imgSettingsItem.setBackgroundResource(R.drawable.ic_dark_mode)
            settingsItemTitle.text = getString(R.string.settings_item_theme_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_theme_sub_title)
        }
        with(itemDisableAds) {
            imgSettingsItem.setBackgroundResource(R.drawable.ic_disable_ads)
            settingsItemTitle.text = getString(R.string.settings_item_remove_ads_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_remove_ads_sub_title)
        }
        with(itemSync) {
            imgSettingsItem.setBackgroundResource(R.drawable.ic_sync)
            settingsItemTitle.text = getString(R.string.settings_item_sync_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_sync_sub_title)
        }
        with(itemSupportUs) {
            imgSettingsItem.setBackgroundResource(R.drawable.ic_store)
            settingsItemTitle.text = getString(R.string.settings_item_support_us_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_support_us_sub_title)
        }
        with(itemFeedback) {
            imgSettingsItem.setBackgroundResource(R.drawable.ic_email)
            settingsItemTitle.text = getString(R.string.settings_item_feedback_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_feedback_sub_title)
        }
        with(itemShare) {
            imgSettingsItem.setBackgroundResource(R.drawable.ic_share)
            settingsItemTitle.text = getString(R.string.settings_item_share_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_share_sub_title)
        }
        with(itemOnGithub) {
            imgSettingsItem.setBackgroundResource(R.drawable.ic_on_github)
            settingsItemTitle.text = getString(R.string.settings_item_on_github_title)
            settingsItemSubTitle.text = getString(R.string.settings_item_on_github_sub_title)
        }
    }

    private fun observeStates() = lifecycleScope.launchWhenStarted {
        settingsViewModel.state.collect {
            with(it) {
                binding.loadingView.visibleIf(loading)
                binding.itemCurrencies.settingsItemValue.text = requireContext().getString(
                    R.string.settings_item_currencies_value,
                    activeCurrencyCount
                )
                binding.itemTheme.settingsItemValue.text = appThemeType.typeName

                binding.itemDisableAds.settingsItemValue.text =
                    if (settingsViewModel.getAdFreeActivatedDate() == 0.toLong()) "" else {
                        if (settingsViewModel.isRewardExpired()) {
                            getString(R.string.settings_item_remove_ads_value_expired)
                        } else {
                            getString(
                                R.string.settings_item_remove_ads_value_will_expire,
                                addFreeDate
                            )
                        }
                    }
            }
        }
    }

    private fun observeEffect() = lifecycleScope.launchWhenStarted {
        settingsViewModel.effect.collect { viewEffect ->
            kermit.d { "SettingsFragment observeEffect ${viewEffect::class.simpleName}" }
            when (viewEffect) {
                SettingsEffect.Back -> getBaseActivity()?.onBackPressed()
                SettingsEffect.OpenCurrencies -> navigate(
                    R.id.settingsFragment,
                    SettingsFragmentDirections.actionCurrenciesFragmentToCurrenciesFragment()
                )
                SettingsEffect.FeedBack -> sendFeedBack()
                SettingsEffect.Share -> share()
                SettingsEffect.SupportUs -> showDialog(
                    requireActivity(),
                    R.string.support_us,
                    R.string.rate_and_support,
                    R.string.rate
                ) {
                    startIntent(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.app_market_link))
                        )
                    )
                }
                SettingsEffect.OnGitHub -> startIntent(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.github_url))
                    )
                )
                SettingsEffect.RemoveAds -> navigate(
                    R.id.settingsFragment,
                    SettingsFragmentDirections.actionCurrenciesFragmentToRemoveAdsBottomSheet()
                )
                SettingsEffect.ThemeDialog -> changeTheme()
                is SettingsEffect.ChangeTheme -> AppCompatDelegate.setDefaultNightMode(viewEffect.themeValue)
                SettingsEffect.Synchronising -> Toast.show(
                    requireContext(),
                    R.string.txt_synchronising
                )
                SettingsEffect.Synchronised -> Toast.show(requireContext(), R.string.txt_synced)
                SettingsEffect.OnlyOneTimeSync -> Toast.show(
                    requireContext(),
                    R.string.txt_already_synced
                )
            }
        }
    }.toUnit()

    private fun setListeners() = with(binding) {
        with(settingsViewModel.event) {
            backButton.setOnClickListener { onBackClick() }

            itemCurrencies.root.setOnClickListener { onCurrenciesClick() }
            itemTheme.root.setOnClickListener { onThemeClick() }
            itemDisableAds.root.setOnClickListener { onRemoveAdsClick() }
            itemSync.root.setOnClickListener { onSyncClick() }
            itemSupportUs.root.setOnClickListener { onSupportUsClick() }
            itemFeedback.root.setOnClickListener { onFeedBackClick() }
            itemShare.root.setOnClickListener { onShareClick() }
            itemOnGithub.root.setOnClickListener { onOnGitHubClick() }
        }
    }

    override fun onResume() {
        super.onResume()
        kermit.d { "SettingsFragment onResume" }
    }

    private fun changeTheme() {
        AppTheme.getThemeByValue(settingsViewModel.getAppTheme())?.let { currentThemeType ->
            showSingleChoiceDialog(
                requireActivity(),
                getString(R.string.title_dialog_choose_theme),
                AppTheme.values().map { it.typeName }.toTypedArray(),
                currentThemeType.order
            ) { index ->
                AppTheme.getThemeByOrder(index)?.let { settingsViewModel.updateTheme(it) }
            }
        }
    }

    private fun startIntent(intent: Intent) {
        getBaseActivity()?.packageManager?.let {
            intent.resolveActivity(it)?.let { startActivity(intent) }
        }
    }

    private fun share() = Intent(Intent.ACTION_SEND).apply {
        type = TEXT_TYPE
        putExtra(Intent.EXTRA_TEXT, getString(R.string.app_market_link))
        startActivity(Intent.createChooser(this, getString(R.string.settings_item_share_title)))
    }.toUnit()

    private fun sendFeedBack() = Intent(Intent.ACTION_SEND).apply {
        type = TEXT_EMAIL_TYPE
        putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail_developer)))
        putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject))
        putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_extra_text) + "")
        startActivity(Intent.createChooser(this, getString(R.string.mail_intent_title)))
    }.toUnit()
}
