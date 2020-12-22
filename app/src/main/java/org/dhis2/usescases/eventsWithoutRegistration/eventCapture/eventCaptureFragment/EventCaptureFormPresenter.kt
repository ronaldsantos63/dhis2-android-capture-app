package org.dhis2.usescases.eventsWithoutRegistration.eventCapture.eventCaptureFragment

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.FlowableProcessor
import org.dhis2.data.forms.dataentry.StoreResult
import org.dhis2.data.forms.dataentry.ValueStore
import org.dhis2.data.forms.dataentry.ValueStoreImpl
import org.dhis2.data.forms.dataentry.fields.ActionType
import org.dhis2.data.forms.dataentry.fields.FieldViewModel
import org.dhis2.data.forms.dataentry.fields.RowAction
import org.dhis2.data.schedulers.SchedulerProvider
import org.dhis2.usescases.eventsWithoutRegistration.eventCapture.EventCaptureContract
import org.hisp.dhis.android.core.D2
import timber.log.Timber

class EventCaptureFormPresenter(
    val view: EventCaptureFormView,
    private val activityPresenter: EventCaptureContract.Presenter,
    val d2: D2,
    val valueStore: ValueStore,
    val schedulerProvider: SchedulerProvider,
    private val onFieldActionProcessor: FlowableProcessor<RowAction>,
    private val focusProcessor: FlowableProcessor<RowAction>
) {
    private var selectedSection: String? = null
    var disposable: CompositeDisposable = CompositeDisposable()
    private var focusedItem = RowAction(id = "", type = ActionType.ON_FOCUS)
    private var itemList: List<FieldViewModel>? = null
    private val itemsWithError = mutableListOf<RowAction>()

    fun init() {
        disposable.add(
            Flowable.merge(
                onFieldActionProcessor.onBackpressureBuffer().distinctUntilChanged(),
                focusProcessor
            )
                .doOnNext { activityPresenter.showProgress() }
                .observeOn(schedulerProvider.io())
                .flatMap { action ->
                    when (action.type) {
                        ActionType.ON_TEXT_CHANGE -> {
                            itemList?.let { list ->
                                list.find { item ->
                                    item.uid() == action.id
                                }?.let { item ->
                                    itemList = list.updated(
                                        list.indexOf(item),
                                        item.withValue(action.value).withEditMode(true)
                                    )
                                }
                            }
                            Flowable.just(
                                StoreResult(
                                    action.id,
                                    null
                                )
                            )
                        }
                        ActionType.ON_FOCUS, ActionType.ON_NEXT -> {
                            this.focusedItem = action
                            Flowable.just(
                                StoreResult(
                                    action.id,
                                    ValueStoreImpl.ValueStoreResult.VALUE_HAS_NOT_CHANGED
                                )
                            )
                        }
                        ActionType.ON_SAVE -> {
                            if (action.error != null) {
                                if (itemsWithError.find { it.id == action.id } == null) {
                                    itemsWithError.add(action)
                                }
                                Flowable.just(
                                    StoreResult(
                                        action.id,
                                        ValueStoreImpl.ValueStoreResult.VALUE_HAS_NOT_CHANGED
                                    )
                                )
                            } else {
                                itemsWithError.find { it.id == action.id }?.let {
                                    itemsWithError.remove(it)
                                }
                                valueStore.save(action.id, action.value)
                            }
                        }
                    }
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { result ->
                        result.valueStoreResult?.let {
                            if (it == ValueStoreImpl.ValueStoreResult.VALUE_CHANGED) {
                                activityPresenter.nextCalculation(true)
                            } else {
                                itemList?.let { fields ->
                                    activityPresenter.formFieldsFlowable()
                                        .onNext(fields.toMutableList())
                                }
                            }
                        } ?: activityPresenter.hideProgress()
                    },
                    {
                        Timber.tag(TAG).e(it)
                    }
                )
        )

        disposable.add(
            activityPresenter.formFieldsFlowable()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { fields ->
                        itemList = fields
                        val list = mergeListWithErrorFields(
                            fields,
                            itemsWithError
                        )
                        view.showFields(setFocusedItem(list).toMutableList())
                        activityPresenter.hideProgress()
                        selectedSection ?: fields
                            .mapNotNull { it.programStageSection() }
                            .firstOrNull()
                            ?.let { selectedSection = it }
                    },
                    { Timber.e(it) }
                )
        )
    }

    private fun mergeListWithErrorFields(
        list: List<FieldViewModel>,
        fieldsWithError: MutableList<RowAction>
    ): List<FieldViewModel> {
        return list.map { item ->
            fieldsWithError.find { it.id == item.uid() }?.let { action ->
                item.withValue(action.value).withError(action.error)
            } ?: item
        }
    }

    fun onDetach() {
        disposable.clear()
    }

    fun onActionButtonClick() {
        activityPresenter.attempFinish()
    }

    private fun getNextItem(currentItemUid: String): String? {
        itemList?.let { fields ->
            val oldItem = fields.find { it.uid() == currentItemUid }
            val pos = fields.indexOf(oldItem)
            if (pos < fields.size - 1) {
                return fields[pos + 1].getUid()
            }
        }

        return null
    }

    private fun setFocusedItem(list: List<FieldViewModel>) = focusedItem.let {
        val uid = if (it.type == ActionType.ON_NEXT) {
            getNextItem(it.id)
        } else {
            it.id
        }

        list.find { item ->
            item.uid() == uid
        }?.let { item ->
            list.updated(list.indexOf(item), item.withFocus(true))
        } ?: list
    }

    fun <E> Iterable<E>.updated(index: Int, elem: E): List<E> =
        mapIndexed { i, existing -> if (i == index) elem else existing }

    companion object {
        private const val TAG = "EventCaptureFormPr"
    }
}