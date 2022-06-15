/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.quiet.service.vo.BaseVO;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

/**
 * 客户端 vo
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietClientVO extends BaseVO {

    @NotBlank
    @Length(max = 20)
    private String clientId;

    @NotBlank
    @Length(max = 30)
    private String clientName;

    @NotBlank
    @Length(max = 60)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clientSecret;

    private Set<String> resourceIds;

    @NotNull
    private Boolean secretRequired;

    @NotNull
    private Boolean scoped;

    private Set<String> scope;

    private Set<String> authorizedGrantTypes;

    private Set<String> registeredRedirectUri;

    @Min(0)
    @NotNull
    private Integer accessTokenValiditySeconds;

    @Min(0)
    @NotNull
    private Integer refreshTokenValiditySeconds;

    @NotNull
    @ColumnDefault("0")
    private Boolean autoApprove;

    @Length(max = 100)
    private String remark;
}
