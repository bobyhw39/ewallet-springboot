package com.enigma.seventhmarch.exceptions;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
public class ErrorDetails {
    @ApiModelProperty(notes = "timestamp of error")
    private Date timestamp;

    @ApiModelProperty(notes = "message of error")
    private String message;

    @ApiModelProperty(notes = "detail of error")
    private String details;

    @ApiModelProperty(notes = "path of error")
    private String path;

}
