package com.Personify.base;

class User {
    private String userName;
    private String password;

    User(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
    }

    String getPassword() {
        return password;
    }
}
