package com.java.yandifei.network;

import java.io.Serializable;

public class Author implements Serializable {
    public String name;

    public Author() {}

    @Override
    public String toString() {
        return "Author(name: " + name + ")";
    }
}
