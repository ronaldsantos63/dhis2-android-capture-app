package org.dhis2.data.forms.dataentry.fields.display;


import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

import org.dhis2.R;
import org.dhis2.data.forms.dataentry.DataEntryViewHolderTypes;
import org.dhis2.data.forms.dataentry.fields.FieldViewModel;
import org.dhis2.data.forms.dataentry.fields.common.FieldUiModel;
import org.hisp.dhis.android.core.common.ObjectStyle;

@AutoValue
public abstract class DisplayViewModel extends FieldViewModel implements FieldUiModel {

    public static DisplayViewModel create(String id, String label, String value, String description) {
        return new AutoValue_DisplayViewModel(id, label, false, value, null, null, false, null, null, null, description, ObjectStyle.builder().build(), null, DataEntryViewHolderTypes.DISPLAY, R.layout.item_indicator);
    }

    @Override
    public FieldUiModel setMandatory() {
        return new AutoValue_DisplayViewModel(uid(), label(), true, value(), null, null, false, null, warning(), error(), description(), objectStyle(), null, DataEntryViewHolderTypes.DISPLAY, R.layout.item_indicator);
    }

    @NonNull
    @Override
    public FieldUiModel withWarning(@NonNull String warning) {
        return new AutoValue_DisplayViewModel(uid(), label(), mandatory(), value(), null, null, false, null, warning, error(), description(), objectStyle(), null, DataEntryViewHolderTypes.DISPLAY, R.layout.item_indicator);
    }

    @NonNull
    @Override
    public FieldUiModel withError(@NonNull String error) {
        return new AutoValue_DisplayViewModel(uid(), label(), mandatory(), value(), null, null, false, null, warning(), error, description(), objectStyle(), null, DataEntryViewHolderTypes.DISPLAY, R.layout.item_indicator);
    }

    @NonNull
    @Override
    public FieldUiModel withValue(String data) {
        return new AutoValue_DisplayViewModel(uid(), label(), mandatory(), data, null, null, false, null, warning(), error(), description(), objectStyle(), null, DataEntryViewHolderTypes.DISPLAY, R.layout.item_indicator);
    }

    @NonNull
    @Override
    public FieldViewModel withEditMode(boolean isEditable) {
        return new AutoValue_DisplayViewModel(uid(), label(), mandatory(), value(), null, null, isEditable, null, warning(), error(), description(), objectStyle(), null, DataEntryViewHolderTypes.DISPLAY, R.layout.item_indicator);
    }
}
