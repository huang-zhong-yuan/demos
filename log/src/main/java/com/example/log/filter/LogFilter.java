package com.example.log.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class LogFilter extends OncePerRequestFilter {

    public static final String TRACE_ID = "traceId";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put(TRACE_ID, traceId);
        StringBuffer path = httpServletRequest.getRequestURL();
        String method = httpServletRequest.getMethod();

        Map<String, Object> paramMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(httpServletRequest.getParameterMap())) {
            paramMap.putAll(httpServletRequest.getParameterMap());
        }

        String s = objectMapper.writeValueAsString(paramMap);
        log.info("requestUrl: {}, method:{}, params:{}", path, method, s);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        log.info("request process finished: {}", path);

//        Stopwatch stopwatch = Stopwatch.createStarted();
//        String s = JSONObject.toJSONString(paramMap);
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
//
//        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
//        if (elapsed <= 3000) {
//            log.info("traceId: {}, requestUrl: {}, method:{}, params:{}, time: {}ms", traceId, path, method, s, elapsed);
//        } else {
//            log.error("traceId: {}, requestUrl: {}, method:{}, params:{}, time: {}ms", traceId, path, method, s, elapsed);
//        }
        ThreadContext.remove(TRACE_ID);


        /*String traceId = UUID.randomUUID().toString();
        StringBuffer path = httpServletRequest.getRequestURL();
        String method = httpServletRequest.getMethod();

        Map<String, Object> paramMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(httpServletRequest.getParameterMap())) {
            paramMap.putAll(httpServletRequest.getParameterMap());
        }

        String s = objectMapper.writeValueAsString(paramMap);
        log.info("requestUrl: {}, method:{}, params:{}", path, method, s);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        log.info("request process finished: {}", path);*/
    }
}
