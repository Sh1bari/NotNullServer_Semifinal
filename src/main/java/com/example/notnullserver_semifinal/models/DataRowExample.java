package com.example.notnullserver_semifinal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class DataRowExample {
    private String rowIdent;
    private boolean incrementDelete;
    private List<DataFieldValueExample> values = new LinkedList();

}
