package com.blackfat.common.utils.time;

import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by wangfeiyang on 2017/3/31.
 */
public class DateUtilTest {

    @Test
    public void isSameDay(){
        Date date1 = new Date(106, 10, 1);
        Date date2 = new Date(106, 10, 1, 12, 23, 44);

        assertThat(DateUtil.isSameDay(date1, date2)).isTrue();

        Date date3 = new Date(106, 10, 1);
        assertThat(DateUtil.isSameTime(date1, date3)).isTrue();

        assertThat(DateUtil.isSameTime(date1, date3)).isTrue();

        Date date4 = new Date(106, 10, 1, 12, 23, 43);
        assertThat(DateUtil.isBetween(date3, date1, date2)).isTrue();
        assertThat(DateUtil.isBetween(date4, date1, date2)).isTrue();





    }


}