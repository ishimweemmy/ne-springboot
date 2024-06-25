package com.ishimweemmy.templates.springboot.v1.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankingDTO {
    public String sender_id;
    public String receiver_id;
}
