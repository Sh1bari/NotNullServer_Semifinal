package com.example.notnullserver_semifinal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString

public class AdvInfoExample {
    private String caption;
    private List<AdvInfoFieldRefExample> fields = new LinkedList<>();
    private AdvInfoDataExample data;
}
