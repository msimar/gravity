package fi.zalando.core.module;

import android.content.Context;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import fi.zalando.core.data.helper.GooglePlayServicesHelper;
import fi.zalando.core.data.helper.LocationHelper;
import fi.zalando.core.domain.helper.SubscriptionHelper;

/**
 * Module that provides the implementations of all the injected helpers
 *
 * Created by jduran on 19/11/15.
 */
@Module
public class BaseHelperModule {

    /**
     * Provides a {@link GooglePlayServicesHelper} instance dependency
     *
     * @return {@link GooglePlayServicesHelper} instance
     */
    @Provides
    public GooglePlayServicesHelper provideGooglePlayServicesHelper() {

        return new GooglePlayServicesHelper();
    }

    /**
     * Provides a {@link LocationHelper} instance dependency
     *
     * @param applicationContext {@link Context} of the app
     * @param locationManager    {@link LocationManager} to inject
     * @return {@link LocationHelper} instance
     */
    @Provides
    public LocationHelper provideLocationHelper(Context applicationContext,
                                                LocationManager locationManager) {

        return new LocationHelper(applicationContext, locationManager);
    }

    /**
     * Provides a {@link SubscriptionHelper} instance dependency
     *
     * @return {@link SubscriptionHelper} instance
     */
    @Provides
    public SubscriptionHelper provideSubscriptionHelper() {

        return new SubscriptionHelper();
    }

}
