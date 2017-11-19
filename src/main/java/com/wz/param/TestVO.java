package com.wz.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TestVO {

    @NotBlank
    private String msg;

    @NotNull
    private String id;

//    @NotEmpty
//    private List<String> str;

}
