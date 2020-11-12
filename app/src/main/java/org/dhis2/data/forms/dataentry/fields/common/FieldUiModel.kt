package org.dhis2.data.forms.dataentry.fields.common

import androidx.annotation.NonNull
import org.dhis2.data.forms.dataentry.fields.FieldViewModel

interface FieldUiModel{
    fun withValue(data: String?): FieldUiModel
    fun withWarning(warning: String): FieldUiModel
    fun withError(error: String): FieldUiModel
    fun withEditMode(isEditable: Boolean): FieldUiModel
    fun setMandatory(): FieldUiModel
}