package com.brotherj.brotherjserver.GenerateController;

import com.brotherj.brotherjserver.validator.AssertCode;
import com.brotherj.brotherjutil.util.ResultCodeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * description：
 *
 * @author wangjie
 */
@Data
public class BillDetailVo {

    @NotNull
    @AssertCode
    private Long houseBillId;

    private String billNo;
    private Integer billType;

}
