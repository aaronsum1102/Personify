package com.Personify.base;

class User {
    private String username;
    private String password;

    User(final String userName, final String password) {
        this.username = userName;
        this.password = password;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}
