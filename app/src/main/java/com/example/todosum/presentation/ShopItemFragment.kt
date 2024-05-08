package com.example.todosum.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todosum.R
import com.example.todosum.databinding.FragmentShopItemBinding


class ShopItemFragment : Fragment() {
    private lateinit var binding: FragmentShopItemBinding
    private lateinit var shopItemViewModel: ShopItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener
    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parseParams()
        binding = FragmentShopItemBinding.inflate(inflater, container, false)
        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRightMode()
        addTextChangeListener()
        observeViewModel()
    }

    private fun observeViewModel() = with(binding) {
        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        shopItemViewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun addTextChangeListener() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilName.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilCount.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun launchAddMode() = with(binding) {
        saveButton.setOnClickListener {
            shopItemViewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchEditMode() = with(binding) {
        shopItemViewModel.getShopItem(shopItemId)
        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        saveButton.setOnClickListener {
            shopItemViewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())

        }
    }


    private fun parseParams() {
        if (arguments == null) {
            throw RuntimeException("Param screen mode is absent $screenMode")
        }
        arguments?.let {
            screenMode = it.getString(EXTRA_MODE, MODE_UNKNOWN)
            if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
                throw RuntimeException("Unknown screen mode $screenMode")
            }
            if(screenMode == MODE_EDIT && it.containsKey(EXTRA_SHOP_ITEM_ID)){
                shopItemId = it.getInt(EXTRA_SHOP_ITEM_ID, shopItemId)
            }
            if(screenMode == MODE_EDIT && !it.containsKey(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param shop item id is absent")
            }
        }
    }

    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }


    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}

