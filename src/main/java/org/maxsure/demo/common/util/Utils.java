package org.maxsure.demo.common.util;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Slf4j
public final class Utils {

    private Utils() {}

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

}
