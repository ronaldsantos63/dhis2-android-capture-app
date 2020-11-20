package org.dhis2.usescases.form

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.dhis2.R
import org.dhis2.common.BaseRobot
import org.dhis2.common.viewactions.clickChildViewWithId
import org.dhis2.data.forms.dataentry.fields.edittext.EditTextCustomHolder
import org.hamcrest.Matchers.allOf

fun formRobot(formRobot: FormRobot.() -> Unit) {
    FormRobot().apply {
        formRobot()
    }
}

class FormRobot : BaseRobot() {

    fun clickOnASpecificSection(sectionLabel: String) {
        onView(withId(R.id.formRecycler))
            .perform(actionOnItem<EditTextCustomHolder>(allOf(hasDescendant(withText(sectionLabel)), hasDescendant(
                withId(R.id.openIndicator))), click()))
    }

    fun clickOnSpinner(label: String) {
        onView(withId(R.id.formRecycler))
            .perform(actionOnItem<EditTextCustomHolder>(hasDescendant(withText(label)), clickChildViewWithId(R.id.input_editText)))
    }

    fun typeOnSearchInput(searchWord: String) {
        onView(withId(R.id.txtSearch)).perform(typeText(searchWord))
    }

    fun selectAction(action: String) {
        onView(allOf(withId(R.id.spinner_text), withText(action))).perform(click())
    }



}