package com.example.notnullserver_semifinal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExchangeInfoMessageExample {

    private HeaderExample header;

    private RequestExample requestExample;

    private EventExample eventExample;
}
