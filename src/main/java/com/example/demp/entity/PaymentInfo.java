package com.example.demp.entity;
import java.io.Serializable;

import lombok.Data;
/**
 * 収支情報 Entity
 */
@Data
public class PaymentInfo implements Serializable {

    private Long userid;
    private Long seq;
    private int paymoney;
    private String paymokuteki;
    private String paydate;
}