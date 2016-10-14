package fi.zalando.core.ui.presenter;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fi.zalando.core.domain.helper.SubscriptionHelper;
import fi.zalando.core.ui.view.BaseView;
import fi.zalando.core.utils.Preconditions;

/**
 * Class responsible of holding common methods for all Presenters in the App.
 *
 * Created by jduran on 24/11/15.
 */
public abstract class BasePresenter<T extends BaseView> {

    protected T view;
    private boolean isViewSet;
    private boolean isPresenterInitialised;

    /**
     * Injected objects
     */
    protected SubscriptionHelper subscriptionHelper;

    /**
     * Constructor
     */
    protected BasePresenter(SubscriptionHelper subscriptionHelper) {

        this.subscriptionHelper = subscriptionHelper;
    }

    /**
     * Provides the {@link SubscriptionHelper} instance linked with the {@link BasePresenter}
     *
     * @return {@link SubscriptionHelper} instance
     */
    public SubscriptionHelper getSubscriptionHelper() {

        return subscriptionHelper;
    }

    /**
     * Initialises the presenter. Linked to Activity onCreate() and Fragment onActivityCreated
     * lifecycle methods
     *
     * @param state {@link Bundle} to restore or start the instance of the presenter
     */
    @CallSuper
    public void initialise(@Nullable Bundle state) {

        Preconditions.checkState(isViewSet, "Call setView before initialising presenter");
        isPresenterInitialised = true;
        // Init View
        view.initView();
    }

    /**
     * Saves the instance of the {@link BasePresenter}
     *
     * @param outState {@link Bundle} to save the state
     */
    @SuppressWarnings("UnusedParameters")
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * Gets a callback when the Fragment/Activity where the presenter is linked appears on the
     * screen
     */
    @CallSuper
    public void resume() {

        Preconditions.checkState(isViewSet, "Call setView before resuming presenter");
        Preconditions.checkState(isPresenterInitialised, "Call initialise before resuming " +
                "presenter");
    }

    /**
     * Release the {@link BasePresenter}
     */
    @CallSuper
    public void destroy() {

        subscriptionHelper.unsubscribeAll();
    }

    /**
     * Sets the {@link T} instance to link the {@link BasePresenter} with the UI so that the
     * presenter can hold a reference to the view it will be interacting with.
     *
     * @param view {@link T} to link the {@link BasePresenter} with the UI
     */
    public final void setView(@NonNull T view) {

        Preconditions.checkNotNull(view);
        isViewSet = true;
        this.view = view;
    }

}
