package com.blackfat.common.utils.time;

import org.junit.Test;
import com.blackfat.common.utils.time.ClockUtil.DummyClock;

import static org.assertj.core.api.Assertions.*;



public class ClockUtilTest {

    @Test
    public void testDummyClock(){

        DummyClock dummyClock = new DummyClock();

        dummyClock.updateNow(111);

        assertThat(dummyClock.currentTimeMillis()).isEqualTo(111);

        assertThat(dummyClock.currentDate().getTime()).isEqualTo(111);
    }

}