package org.dhis2.usescases.form

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.dhis2.usescases.BaseTest
import org.dhis2.usescases.eventsWithoutRegistration.eventCapture.EventCaptureActivity
import org.dhis2.usescases.searchTrackEntity.SearchTEActivity
import org.dhis2.usescases.searchte.robot.searchTeiRobot
import org.dhis2.usescases.teiDashboard.TeiDashboardMobileActivity
import org.dhis2.usescases.teidashboard.robot.enrollmentRobot
import org.dhis2.usescases.teidashboard.robot.eventRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormTest: BaseTest() {

    @get:Rule
    val rule = ActivityTestRule(EventCaptureActivity::class.java, false, false)

    @get:Rule
    val ruleTeiDashboard = ActivityTestRule(TeiDashboardMobileActivity::class.java, false, false)

    @get:Rule
    val ruleSearch = ActivityTestRule(SearchTEActivity::class.java, false, false)

    @Test
    fun shouldSuccessfullyUseForm() {
        //prepareEventProgramRuleIntentAndLaunchActivity(rule)
        startSearchActivity(ruleSearch)

        searchTeiRobot {
            typeAttributeAtPosition("abc", 1)
            clickOnFab()
            clickOnFab()
            selectAnOrgUnit("Ngelehun CHC")
            clickOnAcceptButton()
            acceptDate()
        }

        enrollmentRobot {
            clickOnPersonAttributes("Attributes - Person")
            scrollToBottomProgramForm()
            clickOnDatePicker()
            clickOnAcceptEnrollmentDate()
            clickOnInputDate("DD TEST DATE*")
            clickOnAcceptEnrollmentDate()
            clickOnSaveEnrollment()
        }

        eventRobot {
            clickOnUpdate()
        }

        formRobot {
            //clickOnASpecificSection()
            Thread.sleep(4000)
            clickOnSpinner("ZZ TEST RULE ACTIONS A")
            selectAction(NO_ACTION)
        }

        Thread.sleep(10000)
    }


    companion object {
        const val NO_ACTION = "No Action"
        const val HIDE_FIELD = "Hide Field"
        const val HIDE_SECTION = "Hide Section"
        const val HIDE_OPTION = "Hide Option"
        const val HIDE_OPTION_GROUP = "Hide Option Group"
        const val ASSIGN_VALUE = "Assign Value"
        const val SHOW_WARNING = "Show Warning"
        const val WARNING_COMPLETE = "Warning on Complete"
        const val SHOW_ERROR = "Show Error"
        const val ERROR_COMPLETE = "Error on Complete"
        const val MANDATORY_FIELD = "Make Field Mandatory"
        const val DISPLAY_TEXT = "Display Text"
        const val DISPLAY_KEY = "Display Key/Value Pair"
        const val HIDE_PROGRAM_STAGE = "Hide Program Stage"
        const val SHOW_OPTION_GROUP = "Show Option Group"
    }

}