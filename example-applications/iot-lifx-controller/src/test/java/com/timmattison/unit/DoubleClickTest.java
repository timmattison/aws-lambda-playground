package com.timmattison.unit;

import com.timmattison.ClickTest;
import com.timmattison.button.ClickType;

/**
 * Created by timmattison on 10/16/15.
 */
public class DoubleClickTest extends ClickTest {
    @Override
    protected ClickType getClickType() {
        return ClickType.DOUBLE;
    }

    @Override
    protected boolean isUnitTest() {
        return true;
    }
}
