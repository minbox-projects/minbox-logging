package org.minbox.framework.logging.client.tracer.support;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.tracer.LogTraceIdGenerator;
import org.minbox.framework.sequence.Sequence;

/**
 * Use "minbox-sequence" to generate traceId
 * <p>
 * https://github.com/minbox-projects/minbox-sequence
 *
 * @author 恒宇少年
 */
public class MinBoxSequenceLogTraceIdGenerator implements LogTraceIdGenerator {
    private static final int DEFAULT_DATA_CENTER_ID = 1;
    private Sequence sequence;

    public MinBoxSequenceLogTraceIdGenerator() {
        this(DEFAULT_DATA_CENTER_ID);
    }

    public MinBoxSequenceLogTraceIdGenerator(int dataCenterId) {
        this.sequence = new Sequence(dataCenterId);
    }

    @Override
    public String createTraceId() throws MinBoxLoggingException {
        return String.valueOf(sequence.nextId());
    }
}
