package com.yellow.api.domain;

import java.util.List;

public class Sanction {
    private String type;
    private String name;
   // @JsonIgnore
    private String nameCodec;
    private List<String> addresses;
    private String id;
    private List<String> program;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProgram() {
        return program;
    }

    public void setProgram(List<String> program) {
        this.program = program;
    }

    public String getNameCodec() {
        return nameCodec;
    }

    public void setNameCodec(String nameCodec) {
        this.nameCodec = nameCodec;
    }
}
