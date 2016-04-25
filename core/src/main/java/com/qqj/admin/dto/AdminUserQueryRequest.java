package com.qqj.admin.dto;

import lombok.Data;

@Data
public class AdminUserQueryRequest {

    private String username;

    private String realname;

    private String telephone;

    private Boolean enabled;

    private Integer page = 0;

    private Integer pageSize = 100;
}
