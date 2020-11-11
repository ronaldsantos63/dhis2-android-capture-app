package org.dhis2.data.forms.dataentry.fields.common

sealed class FieldUiModelSealed {
    data class Spinner(var displayName: String) : FieldUiModelSealed()
    object  Picture: FieldUiModelSealed() {
        var displayName: String = "Whatever..."
    }
    fun hola(): String{
        return "hello"
    }
}