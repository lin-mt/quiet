package com.gitee.quiet.doc.model;

import java.util.Map;
import java.util.Set;
import lombok.Data;

/**
 * Schema.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Data
public class Schema {

    private String title;

    private String type;

    private Map<String, Schema> properties;

    private Set<String> required;

    private String description;

    private Long minLength;

    private Long maxLength;

    private Long minimum;

    private Long maximum;

    private Boolean exclusiveMinimum;

    private Boolean exclusiveMaximum;

    private Schema items;

    private Long minItems;

    private Long maxItems;

}
