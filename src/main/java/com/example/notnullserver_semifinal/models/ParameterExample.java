package com.example.notnullserver_semifinal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sovcombank.hackaton.proto.ValueRef;

@Getter
@Setter
@ToString
public class ParameterExample {
    private String alias;
    private String caption;
    private String hint;
    private ValueRefExample value;
}
