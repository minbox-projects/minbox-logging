package org.minbox.framework.logging.client.global;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.minbox.framework.logging.core.GlobalLog;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Use threadLocal to store all GlobalLogs in a request that need to be saved
 *
 * @author 恒宇少年
 */
public class GlobalLoggingThreadLocal {
    /**
     * GlobalLog {@link ThreadLocal} define
     */
    private static final TransmittableThreadLocal<List<GlobalLog>> GLOBAL_LOGS = new TransmittableThreadLocal();

    /**
     * Get {@link GlobalLog} List from ThreadLocal
     *
     * @return {@link GlobalLog}
     */
    public static List<GlobalLog> getGlobalLogs() {
        return GLOBAL_LOGS.get();
    }

    /**
     * Add {@link GlobalLog} to ThreadLocal
     *
     * @param globalLog {@link GlobalLog}
     */
    public static void addGlobalLogs(GlobalLog globalLog) {
        List<GlobalLog> globalLogs = getGlobalLogs();
        if (ObjectUtils.isEmpty(globalLogs)) {
            globalLogs = new LinkedList();
        }
        globalLogs.add(globalLog);
        GLOBAL_LOGS.set(globalLogs);
    }

    /**
     * Delete {@link GlobalLog} list in ThreadLocal
     */
    public static void remove() {
        GLOBAL_LOGS.remove();
    }
}
