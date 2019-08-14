package org.minbox.framework.logging.core.response;

import lombok.Data;

import java.sql.Timestamp;

/**
 * logging service response
 *
 * @author 恒宇少年
 */
@Data
public class ServiceResponse {
    /**
     * service application name
     * service id
     */
    private String id;
    /**
     * service ip address
     */
    private String ip;
    /**
     * service port
     */
    private Integer port;
    /**
     * last report time
     */
    private Timestamp lastReportTime;
    /**
     * first report time
     */
    private Timestamp createTime;
}
