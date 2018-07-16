package mustafaozhan.github.com.mycurrencies.room.dao

import android.arch.persistence.room.*
import mustafaozhan.github.com.mycurrencies.room.model.Currency

/**
 * Created by Mustafa Ozhan on 2018-07-16.
 */
@Dao
abstract class CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrency(currency: Currency)

    @Update
    abstract fun updateCurrency(currency: Currency)

    @Query("SELECT * FROM currency")
    abstract fun getAllCurrencies(): MutableList<Currency>

}