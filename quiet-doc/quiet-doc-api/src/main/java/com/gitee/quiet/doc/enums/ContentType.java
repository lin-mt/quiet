/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.doc.enums;

/**
 * ContentType.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum ContentType {
    
    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),
    
    APPLICATION_JSON("application/json"),
    
    APPLICATION_XML("application/xml"),
    
    APPLICATION_JAVASCRIPT("application/javascript"),
    
    MULTIPART_FORM_DATA("multipart/form-data"),
    
    TEXT_HTML("text/html"),
    
    TEXT_PLAIN("text/plain"),
    
    TEXT_XML("text/xml"),
    
    WILDCARD("*/*");
    
    private final String value;
    
    ContentType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
