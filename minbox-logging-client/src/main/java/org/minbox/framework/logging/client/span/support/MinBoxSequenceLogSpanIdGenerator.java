package org.minbox.framework.logging.client.span.support;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.span.LogSpanIdGenerator;
import org.minbox.framework.sequence.Sequence;

/**
 * Use "minbox-sequence" to generate spanId
 * <p>
 * https://github.com/minbox-projects/minbox-sequence
 *
 * @author 恒宇少年
 */
public class MinBoxSequenceLogSpanIdGenerator implements LogSpanIdGenerator {
    private static final int DEFAULT_DATA_CENTER_ID = 1;
    private Sequence sequence;

    public MinBoxSequenceLogSpanIdGenerator() {
        this(DEFAULT_DATA_CENTER_ID);
    }

    public MinBoxSequenceLogSpanIdGenerator(int dataCenterId) {
        this.sequence = new Sequence(dataCenterId);
    }

    @Override
    public String createSpanId() throws MinBoxLoggingException {
        return String.valueOf(sequence.nextId());
    }
}
