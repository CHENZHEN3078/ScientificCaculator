package com.example.caculator02.entity;

public class equation {


    public String str;
    public double ans;
    public equation(){

    }

    public equation(String str, double ans) {
        this.str = str;
        this.ans = ans;
    }

    @Override
    public String toString() {
        return "equation{" +
                "str=" + str +
                ", ans=" + ans +
                '}';
    }
}
