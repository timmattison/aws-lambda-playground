package com.timmattison.unit;

import com.timmattison.ClickTest;
import com.timmattison.button.ClickType;

/**
 * Created by timmattison on 10/16/15.
 */
public class LongClickTest extends ClickTest {
    @Override
    protected ClickType getClickType() {
        return ClickType.LONG;
    }

    @Override
    protected boolean isUnitTest() {
        return true;
    }
}
