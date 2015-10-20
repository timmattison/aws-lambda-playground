package com.timmattison.integration;

import com.timmattison.unit.SingleClickTest;

/**
 * Created by timmattison on 10/16/15.
 */
public class SingleClickIT extends SingleClickTest {
    @Override
    protected boolean isUnitTest() {
        return false;
    }
}
