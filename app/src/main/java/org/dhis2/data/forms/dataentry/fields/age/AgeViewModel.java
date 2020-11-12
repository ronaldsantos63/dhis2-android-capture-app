package org.dhis2.data.forms.dataentry.fields.age;

import com.google.auto.value.AutoValue;

import org.dhis2.R;
import org.dhis2.data.forms.dataentry.DataEntryViewHolderTypes;
import org.dhis2.data.forms.dataentry.fields.FieldViewModel;
import org.dhis2.data.forms.dataentry.fields.common.FieldUiModel;
import org.hisp.dhis.android.core.common.ObjectStyle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * QUADRAM. Created by frodriguez on 20/03/2018.
 */

@AutoValue
public abstract class AgeViewModel extends FieldViewModel implements FieldUiModel {

    @NonNull
    public abstract Boolean mandatory();

    @Nullable
    public abstract String value();

    public static FieldUiModel create(String id, String label, Boolean mandatory, String value, String section, Boolean editable, String description, ObjectStyle objectStyle) {
        return new AutoValue_AgeViewModel(id, label, section, null, editable, null, null, null, description, objectStyle, null, DataEntryViewHolderTypes.AGE_VIEW, R.layout.form_age_custom, mandatory, value);
    }

    @Override
    public FieldUiModel setMandatory() {
        return new AutoValue_AgeViewModel(uid(), label(), programStageSection(), allowFutureDate(), editable(), optionSet(), warning(), error(), description(), objectStyle(), null, DataEntryViewHolderTypes.AGE_VIEW, R.layout.form_age_custom, true, value());
    }

    @NonNull
    @Override
    public FieldUiModel withError(@NonNull String error) {
        return new AutoValue_AgeViewModel(uid(), label(), programStageSection(), allowFutureDate(), editable(), optionSet(), warning(), error, description(), objectStyle(), null, DataEntryViewHolderTypes.AGE_VIEW, R.layout.form_age_custom, mandatory(), value());
    }

    @NonNull
    @Override
    public FieldUiModel withWarning(@NonNull String warning) {
        return new AutoValue_AgeViewModel(uid(), label(), programStageSection(), allowFutureDate(), editable(), optionSet(), warning, error(), description(), objectStyle(), null, DataEntryViewHolderTypes.AGE_VIEW, R.layout.form_age_custom, mandatory(), value());
    }

    @NonNull
    @Override
    public FieldUiModel withValue(String data) {
        return new AutoValue_AgeViewModel(uid(), label(), programStageSection(), allowFutureDate(), false, optionSet(), warning(), error(), description(), objectStyle(), null, DataEntryViewHolderTypes.AGE_VIEW, R.layout.form_age_custom, mandatory(), data);
    }

    @NonNull
    @Override
    public FieldUiModel withEditMode(boolean isEditable) {
        return new AutoValue_AgeViewModel(uid(), label(), programStageSection(), allowFutureDate(), isEditable, optionSet(), warning(), error(), description(), objectStyle(), null, DataEntryViewHolderTypes.AGE_VIEW, R.layout.form_age_custom, mandatory(), value());
    }
}
