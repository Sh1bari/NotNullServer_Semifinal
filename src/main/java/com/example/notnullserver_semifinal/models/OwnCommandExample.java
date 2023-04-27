package com.example.notnullserver_semifinal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class OwnCommandExample {
    private String alias;
    private String caption;
    private String description;
    private List<ParameterExample> parameters = new LinkedList<>();
}
