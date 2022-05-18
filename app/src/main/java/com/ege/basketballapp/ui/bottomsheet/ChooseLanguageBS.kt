package com.ege.basketballapp.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.ege.basketballapp.MainActivity
import com.ege.basketballapp.R
import com.ege.basketballapp.utils.GlobalPreferences
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_display_languages.*

class ChooseLanguageBS() : BottomSheetDialogFragment() {
    lateinit var globalPreferences: GlobalPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_display_languages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        globalPreferences = GlobalPreferences(requireContext())
        if (globalPreferences.locale == "tr") {
            changeLanguage(turkishCon, turkish)
        } else if (globalPreferences.locale == "en") {
            changeLanguage(englishcon, english)
        }
        saveBTN.setOnClickListener {
            restartActivity()
            dismiss()
        }

        closeIc.setOnClickListener {
            dismiss()
        }
        turkishCon.setOnClickListener {
            changeLanguage(turkishCon, turkish)
            globalPreferences.storeLocale("tr")
        }

        turkish.setOnClickListener({ turkishCon.performClick() })
        englishcon.setOnClickListener {
            changeLanguage(englishcon, english)
            globalPreferences.storeLocale("en")

        }
        english.setOnClickListener { englishcon.performClick() }


    }

    private fun restartActivity() {

        (activity as MainActivity).recreateActivity()
        dismiss()

    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }


    private fun changeLanguage(constraintLayout: ConstraintLayout, radioButton: RadioButton) {
        englishcon.setBackgroundColor(resources.getColor(R.color.white))
        turkishCon.setBackgroundColor(resources.getColor(R.color.white))
        turkish.isChecked = false
        english.isChecked = false
        constraintLayout.background = resources.getDrawable(R.drawable.con_round_lavandar)
        radioButton.isChecked = true
    }
}