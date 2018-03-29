package com.Personify.base;

/**
 * Manage user information of an object.
 */
class User {
    private String username;
    private String password;

    /**
     * Instantiate an user with username and password.
     * @param userName username of an user object.
     * @param password password of an user object
     */
    User(final String userName, final String password) {
        this.username = userName;
        this.password = password;
    }

    /**
     * Provide the username of an user object.
     * @return username of an user object.
     */
    String getUsername() {
        return username;
    }

    /**
     * Provide the password of an user object.
     * @return password of an user object.
     */
    String getPassword() {
        return password;
    }

    /**
     * Set the username of an user object with the specify name.
     * @param username username to be set for an user object.
     */
    void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password of an user object with specify password.
     * @param password password to be set for an user object.
     */
    void setPassword(String password) {
        this.password = password;
    }
}
