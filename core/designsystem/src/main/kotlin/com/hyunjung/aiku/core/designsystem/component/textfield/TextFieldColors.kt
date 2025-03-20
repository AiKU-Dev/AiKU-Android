package com.hyunjung.aiku.core.designsystem.component.textfield

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse

@Immutable
class AikuTextFieldColors(
    val textColor: Color,
    val containerColor: Color,
    val cursorColor: Color,
    val indicatorColor: Color,
    val leadingColor: Color,
    val trailingColor: Color,
    val placeholderColor: Color,
    val supportingColor: Color,
    val errorSupportingColor: Color,
) {
    fun copy(
        textColor: Color = this.textColor,
        containerColor: Color = this.containerColor,
        cursorColor: Color = this.cursorColor,
        indicatorColor: Color = this.indicatorColor,
        leadingColor: Color = this.leadingColor,
        trailingColor: Color = this.trailingColor,
        placeholderColor: Color = this.placeholderColor,
        supportingColor: Color = this.supportingColor,
        errorSupportingColor: Color = this.errorSupportingColor,
    ) =
        AikuTextFieldColors(
            textColor.takeOrElse { this.textColor },
            containerColor.takeOrElse { this.containerColor },
            cursorColor.takeOrElse { this.cursorColor },
            indicatorColor.takeOrElse { this.indicatorColor },
            leadingColor.takeOrElse { this.leadingColor },
            trailingColor.takeOrElse { this.trailingColor },
            placeholderColor.takeOrElse { this.placeholderColor },
            supportingColor.takeOrElse { this.supportingColor },
            errorSupportingColor.takeOrElse { this.errorSupportingColor },
        )

    @Stable
    internal fun supportingColor(
        isError: Boolean,
    ): Color =
        when {
            isError -> errorSupportingColor
            else -> supportingColor
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is AikuTextFieldColors) return false

        if (textColor != other.textColor) return false
        if (containerColor != other.containerColor) return false
        if (cursorColor != other.cursorColor) return false
        if (indicatorColor != other.indicatorColor) return false
        if (leadingColor != other.leadingColor) return false
        if (trailingColor != other.trailingColor) return false
        if (placeholderColor != other.placeholderColor) return false
        if (supportingColor != other.supportingColor) return false
        if (errorSupportingColor != other.errorSupportingColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = textColor.hashCode()
        result = 31 * result + containerColor.hashCode()
        result = 31 * result + cursorColor.hashCode()
        result = 31 * result + indicatorColor.hashCode()
        result = 31 * result + leadingColor.hashCode()
        result = 31 * result + trailingColor.hashCode()
        result = 31 * result + placeholderColor.hashCode()
        result = 31 * result + supportingColor.hashCode()
        result = 31 * result + errorSupportingColor.hashCode()
        return result
    }
}
