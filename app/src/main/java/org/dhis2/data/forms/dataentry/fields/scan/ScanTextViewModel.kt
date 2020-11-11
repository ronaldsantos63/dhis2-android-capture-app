package org.dhis2.data.forms.dataentry.fields.scan

import com.google.auto.value.AutoValue
import org.dhis2.R
import org.dhis2.data.forms.dataentry.DataEntryViewHolderTypes
import org.dhis2.data.forms.dataentry.fields.FieldViewModel
import org.dhis2.data.forms.dataentry.fields.common.FieldUiModel
import org.hisp.dhis.android.core.common.ObjectStyle
import org.hisp.dhis.android.core.common.ValueTypeDeviceRendering

@AutoValue
abstract class ScanTextViewModel : FieldViewModel(), FieldUiModel {

    abstract val fieldRendering: ValueTypeDeviceRendering?

    abstract val hint: String?

    companion object {
        @JvmStatic
        fun create(
            id: String,
            label: String?,
            mandatory: Boolean?,
            value: String?,
            section: String?,
            editable: Boolean?,
            optionSet: String?,
            description: String?,
            objectStyle: ObjectStyle?,
            fieldRendering: ValueTypeDeviceRendering?,
            hint: String?
        ): FieldViewModel =
            AutoValue_ScanTextViewModel(
                id,
                label,
                mandatory,
                value,
                section,
                null,
                editable,
                optionSet,
                null,
                null,
                description,
                objectStyle,
                null,
                DataEntryViewHolderTypes.SCAN_CODE,
                R.layout.form_scan,
                fieldRendering,
                hint
            )
    }

    override fun setMandatory(): FieldUiModel =
        AutoValue_ScanTextViewModel(
            uid(),
            label(),
            true,
            value(),
            programStageSection(),
            allowFutureDate(),
            editable(),
            optionSet(),
            warning(),
            error(),
            description(),
            objectStyle(),
            null,
            DataEntryViewHolderTypes.SCAN_CODE,
            R.layout.form_scan,
            fieldRendering,
            hint
        )

    override fun withError(error: String): FieldUiModel =
        AutoValue_ScanTextViewModel(
            uid(),
            label(),
            mandatory(),
            value(),
            programStageSection(),
            allowFutureDate(),
            editable(),
            optionSet(),
            warning(),
            error,
            description(),
            objectStyle(),
            null,
            DataEntryViewHolderTypes.SCAN_CODE,
            R.layout.form_scan,
            fieldRendering,
            hint
        )

    override fun withWarning(warning: String): FieldUiModel =
        AutoValue_ScanTextViewModel(
            uid(),
            label(),
            mandatory(),
            value(),
            programStageSection(),
            allowFutureDate(),
            editable(),
            optionSet(),
            warning,
            error(),
            description(),
            objectStyle(),
            null,
            DataEntryViewHolderTypes.SCAN_CODE,
            R.layout.form_scan,
            fieldRendering,
            hint
        )

    override fun withValue(data: String?): FieldUiModel =
        AutoValue_ScanTextViewModel(
            uid(),
            label(),
            mandatory(),
            data,
            programStageSection(),
            allowFutureDate(),
            false,
            optionSet(),
            warning(),
            error(),
            description(),
            objectStyle(),
            null,
            DataEntryViewHolderTypes.SCAN_CODE,
            R.layout.form_scan,
            fieldRendering,
            hint
        )

    override fun withEditMode(isEditable: Boolean): FieldViewModel =
        AutoValue_ScanTextViewModel(
            uid(),
            label(),
            mandatory(),
            value(),
            programStageSection(),
            allowFutureDate(),
            isEditable,
            optionSet(),
            warning(),
            error(),
            description(),
            objectStyle(),
            null,
            DataEntryViewHolderTypes.SCAN_CODE,
            R.layout.form_scan,
            fieldRendering,
            hint
        )
}
