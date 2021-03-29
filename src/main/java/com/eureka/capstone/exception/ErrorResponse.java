package com.eureka.capstone.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private String summary = "Something went wrong!";
    private String type;
    private String[] details;

    public ErrorResponse(String summary, String... details) {
        super();
        this.summary = summary;
        this.details = details;
    }

}
