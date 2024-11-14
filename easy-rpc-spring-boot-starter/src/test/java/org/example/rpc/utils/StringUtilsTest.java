package org.example.rpc.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author Roc
 * @Date 2024/11/14 11:54
 */
public class StringUtilsTest {

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
        assertFalse(StringUtils.isEmpty("abc"));
    }

    @Test
    public void testIsNotEmpty() throws Exception {
        assertFalse(StringUtils.isNotEmpty(null));
        assertFalse(StringUtils.isNotEmpty(""));
        assertTrue(StringUtils.isNotEmpty("abc"));
    }
}
