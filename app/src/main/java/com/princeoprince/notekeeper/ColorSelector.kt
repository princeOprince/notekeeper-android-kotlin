package com.princeoprince.notekeeper

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.princeoprince.notekeeper.databinding.ColorSelectorBinding

class ColorSelector @JvmOverloads

    constructor(context: Context, attributeSet: AttributeSet? = null,
                defStyle: Int = 0, defRes: Int = 0)
            : LinearLayout(context, attributeSet, defStyle, defRes) {

        private var listOfColors = listOf(Color.BLUE, Color.RED, Color.GREEN)
        private var selectedColorIndex = 0
        private var _binding : ColorSelectorBinding? = null
        private val binding get() = _binding!!

        init {
            val typedArray = context.obtainStyledAttributes(
                attributeSet, R.styleable.ColorSelector
            )
            typedArray.recycle()
            orientation = HORIZONTAL
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
            inflater.inflate(R.layout.color_selector, this)
            _binding = ColorSelectorBinding.inflate(inflater, this)
            binding.selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

            binding.colorSelectorArrowLeft.setOnClickListener { selectPreviousColor() }
            binding.colorSelectorArrowRight.setOnClickListener { selectNextColor() }
        }

    private fun selectPreviousColor() {
        if (selectedColorIndex == 0) {
            selectedColorIndex = listOfColors.lastIndex
        } else {
            selectedColorIndex--
        }
        binding.selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
    }

    private fun selectNextColor() {
        if (selectedColorIndex == listOfColors.lastIndex) {
            selectedColorIndex = 0
        } else {
            selectedColorIndex++
        }
        binding.selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
    }
}
