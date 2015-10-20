package com.timmattison.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by timmattison on 10/16/15.
 */
public class SharedInjector {
    private static final Injector injector = Guice.createInjector(new LambdaModule());

    public static Injector getInjector() {
        return injector;
    }
}
