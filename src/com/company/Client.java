package com.company;

import java.util.UUID;

public class Client {
    private String id;
    private String name;

    public Client(String name){
        this.id = this.id = UUID.randomUUID().toString().replace("-", "");
        this.name = name;
    }
    public Client(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
