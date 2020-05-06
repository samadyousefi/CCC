/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package mustafaozhan.github.com.mycurrencies.ui.main.calculator.bar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.github.mustafaozhan.basemob.adapter.BaseDBRecyclerViewAdapter
import com.github.mustafaozhan.basemob.viewholder.BaseDBViewHolder
import mustafaozhan.github.com.mycurrencies.databinding.ItemBarBinding
import mustafaozhan.github.com.mycurrencies.model.Currency
import mustafaozhan.github.com.mycurrencies.util.extension.toValidList

class BarAdapter(
    private val barEvent: BarEvent
) : BaseDBRecyclerViewAdapter<Currency, ItemBarBinding>(CalculatorDiffer()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CalculatorDBViewHolder(ItemBarBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false)
    )

    fun submitList(list: MutableList<Currency>?, currentBase: String) =
        submitList(list.toValidList(currentBase))

    inner class CalculatorDBViewHolder(itemBinding: ItemBarBinding) :
        BaseDBViewHolder<Currency, ItemBarBinding>(itemBinding) {

        override fun onItemBind(item: Currency) = with(itemBinding) {
            this.item = item
            this.event = barEvent
        }
    }

    class CalculatorDiffer : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) = false
    }
}
