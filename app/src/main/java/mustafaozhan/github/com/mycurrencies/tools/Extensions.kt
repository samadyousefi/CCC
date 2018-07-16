package mustafaozhan.github.com.mycurrencies.tools

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.widget.ImageView
import android.widget.TextView
import mustafaozhan.github.com.mycurrencies.R
import mustafaozhan.github.com.mycurrencies.main.fragment.model.Rates
import mustafaozhan.github.com.mycurrencies.room.dao.SettingDao
import mustafaozhan.github.com.mycurrencies.room.model.Setting

/**
 * Created by Mustafa Ozhan on 2018-07-16.
 */
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}

fun getResult(name: Currencies, temp: String, rate: Rates) = when (name) {
    Currencies.EUR -> rate.eUR?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.AUD -> rate.aUD?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.BGN -> rate.bGN?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.BRL -> rate.bRL?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.CAD -> rate.cAD?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.CHF -> rate.cHF?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.CNY -> rate.cNY?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.CZK -> rate.cZK?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.DKK -> rate.dKK?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.GBP -> rate.gBP?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.HKD -> rate.hKD?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.HRK -> rate.hRK?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.HUF -> rate.hUF?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.IDR -> rate.iDR?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.ILS -> rate.iLS?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.INR -> rate.iNR?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.JPY -> rate.jPY?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.KRW -> rate.kRW?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.MXN -> rate.mXN?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.MYR -> rate.mYR?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.NOK -> rate.nOK?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.NZD -> rate.nZD?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.PHP -> rate.pHP?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.PLN -> rate.pLN?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.RON -> rate.rON?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.RUB -> rate.rUB?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.SEK -> rate.sEK?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.SGD -> rate.sGD?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.THB -> rate.tHB?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.TRY -> rate.tRY?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.USD -> rate.uSD?.times(temp.toDouble()) ?: temp.toDouble()
    Currencies.ZAR -> rate.zAR?.times(temp.toDouble()) ?: temp.toDouble()
}

fun SettingDao.insertInitialSettings() {
    this.apply {
        insertSetting(Setting("EUR"))
        insertSetting(Setting("AUD"))
        insertSetting(Setting("BGN"))
        insertSetting(Setting("BRL"))
        insertSetting(Setting("CAD"))
        insertSetting(Setting("CHF"))
        insertSetting(Setting("CNY"))
        insertSetting(Setting("CZK"))
        insertSetting(Setting("DKK"))
        insertSetting(Setting("GBP"))
        insertSetting(Setting("HKD"))
        insertSetting(Setting("HRK"))
        insertSetting(Setting("HUF"))
        insertSetting(Setting("IDR"))
        insertSetting(Setting("ILS"))
        insertSetting(Setting("INR"))
        insertSetting(Setting("JPY"))
        insertSetting(Setting("KRW"))
        insertSetting(Setting("MXN"))
        insertSetting(Setting("MYR"))
        insertSetting(Setting("NOK"))
        insertSetting(Setting("NZD"))
        insertSetting(Setting("PHP"))
        insertSetting(Setting("PLN"))
        insertSetting(Setting("RON"))
        insertSetting(Setting("RUB"))
        insertSetting(Setting("SEK"))
        insertSetting(Setting("SGD"))
        insertSetting(Setting("THB"))
        insertSetting(Setting("TRY"))
        insertSetting(Setting("USD"))
        insertSetting(Setting("ZAR"))
    }
}

fun ImageView.setBackgroundByName(name: String) {
    when (name) {
        "EUR" -> this.setImageResource(R.drawable.eur)
        "AUD" -> this.setImageResource(R.drawable.aud)
        "BGN" -> this.setImageResource(R.drawable.bgn)
        "BRL" -> this.setImageResource(R.drawable.brl)
        "CAD" -> this.setImageResource(R.drawable.cad)
        "CHF" -> this.setImageResource(R.drawable.chf)
        "CNY" -> this.setImageResource(R.drawable.cny)
        "CZK" -> this.setImageResource(R.drawable.czk)
        "DKK" -> this.setImageResource(R.drawable.dkk)
        "GBP" -> this.setImageResource(R.drawable.gbp)
        "HKD" -> this.setImageResource(R.drawable.hkd)
        "HRK" -> this.setImageResource(R.drawable.hrk)
        "HUF" -> this.setImageResource(R.drawable.huf)
        "IDR" -> this.setImageResource(R.drawable.idr)
        "ILS" -> this.setImageResource(R.drawable.ils)
        "INR" -> this.setImageResource(R.drawable.inr)
        "JPY" -> this.setImageResource(R.drawable.jpy)
        "KRW" -> this.setImageResource(R.drawable.krw)
        "MXN" -> this.setImageResource(R.drawable.mxn)
        "MYR" -> this.setImageResource(R.drawable.myr)
        "NOK" -> this.setImageResource(R.drawable.nok)
        "NZD" -> this.setImageResource(R.drawable.nzd)
        "PHP" -> this.setImageResource(R.drawable.php)
        "PLN" -> this.setImageResource(R.drawable.pln)
        "RON" -> this.setImageResource(R.drawable.ron)
        "RUB" -> this.setImageResource(R.drawable.rub)
        "SEK" -> this.setImageResource(R.drawable.sek)
        "SGD" -> this.setImageResource(R.drawable.sgd)
        "THB" -> this.setImageResource(R.drawable.thb)
        "TRY" -> this.setImageResource(R.drawable.tryy)
        "USD" -> this.setImageResource(R.drawable.usd)
        "ZAR" -> this.setImageResource(R.drawable.zar)
        "transparent" -> this.setImageResource(R.drawable.transparent)
    }
}

@SuppressLint("SetTextI18n")
fun TextView.addText(text: String) {
    this.text = this.text.toString() + text
}