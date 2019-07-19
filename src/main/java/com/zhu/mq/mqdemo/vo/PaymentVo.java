package com.zhu.mq.mqdemo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentVo {
    private String no;

    private Double money;

    private String comment;
}
