package com.qqj.org.controller;

import com.qqj.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerListRequest extends PageRequest {

    private Integer level;

    private String name;

    private String username;

    private Long team;

    private String telephone;

    private Short status;

    private String certificateNumber;
}
