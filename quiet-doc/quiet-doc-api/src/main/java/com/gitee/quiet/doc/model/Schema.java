/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.doc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Schema {

  @JsonProperty("default")
  protected String _default;
  private String name;
  private String title;
  private BigDecimal multipleOf;
  private BigDecimal maximum;
  private Boolean exclusiveMaximum;
  private BigDecimal minimum;
  private Boolean exclusiveMinimum;
  private Integer maxLength;
  private Integer minLength;
  private String pattern;
  private Integer maxItems;
  private Integer minItems;
  private Boolean uniqueItems;
  private Integer maxProperties;
  private Integer minProperties;
  private List<String> required;
  private String type;
  private Schema not;
  private Map<String, Schema> properties;
  private Object additionalProperties;
  private String description;
  private String format;
  private String $ref;
  private Boolean nullable;
  private Boolean readOnly;
  private Boolean writeOnly;
  private ExternalDocumentation externalDocs;
  private Boolean deprecated;
  private XML xml;
  private java.util.Map<String, Object> extensions;
  @JsonProperty("enum")
  protected List<String> _enum;
  private Discriminator discriminator;
  private boolean exampleSetFlag;
  private List<Schema> prefixItems;
  private List<Schema> allOf;
  private List<Schema> anyOf;
  private List<Schema> oneOf;
  private Schema items;
  private Set<String> types;
  private Map<String, Schema> patternProperties;
  private BigDecimal exclusiveMaximumValue;
  private BigDecimal exclusiveMinimumValue;
  private Schema contains;
  private String $id;
  private String $schema;
  private String $anchor;
  private String contentEncoding;
  private String contentMediaType;
  private Schema contentSchema;
  private Schema propertyNames;
  private Schema unevaluatedProperties;
  private Integer maxContains;
  private Integer minContains;
  private Schema additionalItems;
  private Schema unevaluatedItems;
  @JsonProperty("if")
  private Schema _if;
  @JsonProperty("else")
  private Schema _else;
  private Schema then;
  private Map<String, Schema> dependentSchemas;
  private Map<String, List<String>> dependentRequired;
  private String $comment;
  private Boolean booleanSchemaValue;
}
