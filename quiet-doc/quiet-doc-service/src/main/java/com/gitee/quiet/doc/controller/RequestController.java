package com.gitee.quiet.doc.controller;

import com.gitee.quiet.common.util.JsonUtils;
import com.gitee.quiet.doc.entity.DocProjectEnvironment;
import com.gitee.quiet.doc.service.DocProjectEnvironmentService;
import com.google.common.io.ByteStreams;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class RequestController {
    
    private static final String DEFAULT_HOSTNAME = "http://127.0.0.1:9363";
    
    private static final String REMOVED_FORMAT = "/request/%s";
    
    private static final Long NONE_SELECTED_ENVIRONMENT_ID = 0L;
    
    private final OkHttpClient client = new OkHttpClient();
    
    private final DocProjectEnvironmentService projectEnvironmentService;
    
    @RequestMapping("/{environmentId}/**")
    public void request(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse,
            @PathVariable Long environmentId) throws IOException {
        RequestBody requestBody;
        String contentType = servletRequest.getContentType();
        MediaType mediaType = null;
        if (contentType != null) {
            mediaType = MediaType.parse(contentType);
        }
        try {
            if (servletRequest instanceof MultipartHttpServletRequest) {
                Collection<Part> parts = servletRequest.getParts();
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
                if (CollectionUtils.isNotEmpty(parts)) {
                    for (Part part : parts) {
                        String partContentType = part.getContentType();
                        InputStream inputStream = part.getInputStream();
                        byte[] requestBodyBytes = ByteStreams.toByteArray(inputStream);
                        MediaType partMediaType;
                        RequestBody partRequestBody;
                        if (partContentType != null) {
                            partMediaType = MediaType.parse(partContentType);
                            partRequestBody = RequestBody.create(requestBodyBytes, partMediaType);
                        } else {
                            partRequestBody = RequestBody.create(requestBodyBytes);
                        }
                        MultipartBody.Part partBody = MultipartBody.Part.createFormData(part.getName(),
                                part.getSubmittedFileName(), partRequestBody);
                        multipartBodyBuilder.addPart(partBody);
                    }
                }
                if (mediaType != null) {
                    multipartBodyBuilder.setType(mediaType);
                }
                requestBody = multipartBodyBuilder.build();
            } else {
                InputStream inputStream = servletRequest.getInputStream();
                byte[] requestBodyBytes = ByteStreams.toByteArray(inputStream);
                requestBody = RequestBody.create(requestBodyBytes, mediaType);
            }
            String hostname;
            if (!NONE_SELECTED_ENVIRONMENT_ID.equals(environmentId)) {
                DocProjectEnvironment environment = projectEnvironmentService.getById(environmentId);
                hostname = environment.getProtocol().getValue() + environment.getBasePath();
            } else {
                hostname = DEFAULT_HOSTNAME;
            }
            String path = servletRequest.getRequestURI()
                    .substring(String.format(REMOVED_FORMAT, environmentId).length());
            if (StringUtils.isNotBlank(servletRequest.getQueryString())) {
                path = path + "?" + servletRequest.getQueryString();
            }
            Request.Builder builder = getBuilderByMethod(hostname + path, servletRequest.getMethod(), requestBody);
            Enumeration<String> headerNames = servletRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                builder.addHeader(name, servletRequest.getHeader(name));
            }
            Request request = builder.build();
            try (Response response = client.newCall(request).execute()) {
                servletResponse.reset();
                InputStream stream = Objects.requireNonNull(response.body()).byteStream();
                Map<String, String> originHeaders = new HashMap<>();
                for (String name : response.headers().names()) {
                    servletResponse.setHeader(name, response.header(name));
                    originHeaders.put(name, response.header(name));
                }
                servletResponse.setHeader("origin-headers", JsonUtils.toJsonString(originHeaders));
                servletResponse.setStatus(response.code());
                servletResponse.getOutputStream().write(stream.readAllBytes());
            }
        } catch (IOException | ServletException exception) {
            log.error("请求出错", exception);
            servletResponse.setStatus(500);
            servletResponse.getOutputStream().write(JsonUtils.toJsonString(exception).getBytes(StandardCharsets.UTF_8));
        }
    }
    
    private Request.Builder getBuilderByMethod(String url, String method, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder().url(url);
        if ("GET".equalsIgnoreCase(method)) {
            builder.get();
        } else if ("POST".equalsIgnoreCase(method)) {
            builder.post(requestBody);
        } else if ("DELETE".equalsIgnoreCase(method)) {
            builder.delete(requestBody);
        } else if ("PATCH".equalsIgnoreCase(method)) {
            builder.patch(requestBody);
        } else if ("PUT".equalsIgnoreCase(method)) {
            builder.put(requestBody);
        } else {
            builder.method(method, requestBody);
        }
        return builder;
    }
    
}
