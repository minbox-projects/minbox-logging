package org.minbox.framework.logging.core.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * logging service response
 *
 * @author 恒宇少年
 */
@Data
@Accessors(chain = true)
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
    private LocalDateTime lastReportTime;
    /**
     * first report time
     */
    private LocalDateTime createTime;
}
