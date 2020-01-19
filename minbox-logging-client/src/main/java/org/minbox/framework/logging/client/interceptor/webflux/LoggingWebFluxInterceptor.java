package org.minbox.framework.logging.client.interceptor.webflux;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.interceptor.LoggingAbstractInterceptor;
import org.minbox.framework.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * {@link org.springframework.web.server.WebFilter}
 *
 * @author 恒宇少年
 */
public class LoggingWebFluxInterceptor extends LoggingAbstractInterceptor implements WebFilter {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingWebFluxInterceptor.class);
    /**
     * {@link LoggingFactoryBean}
     */
    private LoggingFactoryBean factoryBean;

    public LoggingWebFluxInterceptor(LoggingFactoryBean factoryBean) {
        super(factoryBean);
        this.factoryBean = factoryBean;
    }

    /**
     * Intercept requests and generate logs
     *
     * @param exchange {@link ServerWebExchange}
     * @param chain    {@link WebFilterChain}
     * @return {@link Mono}
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        boolean isIgnore = checkUriIsIgnore(request);
        if (isIgnore) {
            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }

    /**
     * check request uri is ignore {@link UrlUtils#isIgnore(List, String)}
     *
     * @param request {@link ServerHttpRequest}
     * @return uri is ignore
     */
    private boolean checkUriIsIgnore(ServerHttpRequest request) {
        String uri = request.getURI().getPath();
        return UrlUtils.isIgnore(factoryBean.getIgnorePaths(), uri);
    }
}
