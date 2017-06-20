package com.blstream.studybox.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;

public class AuthCredentials {

    @Expose(serialize = false)
    private String id;

    @Expose
    private String email;

    @Expose(deserialize = false)
    private String password;

    private String repeatPassword;

    private AuthCredentials() {
    }

    private AuthCredentials(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.repeatPassword = builder.repeatPassword;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Nullable
    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static class Builder {
        private String id;
        private String email;
        private String password;
        private String repeatPassword;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRepeatPassword(String repeatPassword) {
            this.repeatPassword = repeatPassword;
            return this;
        }

        public AuthCredentials build() {
            return new AuthCredentials(this);
        }
    }
}
