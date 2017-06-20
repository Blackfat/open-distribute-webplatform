package com.blackfat.common.utils.base;

import com.blackfat.common.utils.number.RandomUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SystemPropertiesUtilTest {
    @Test
    public void registerSystemPropertiesListener() throws Exception {
        String name = "test" + RandomUtil.nextInt();

        TestPropertiesListener listener = new TestPropertiesListener(name);
        SystemPropertiesUtil.registerSystemPropertiesListener(listener);

        System.setProperty(name, "haha");
        assertThat(listener.newValue).isEqualTo("haha");
    }

    public static class TestPropertiesListener extends SystemPropertiesUtil.PropertiesListener{
        public TestPropertiesListener(String propertyName) {
            super(propertyName);
        }

        public String newValue;

        @Override
        public void onChange(String propertyName, String value) {
            newValue = value;
        }
    };

}