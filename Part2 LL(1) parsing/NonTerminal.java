/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cp2;


import java.util.HashSet;


public class NonTerminal extends Word {


    private HashSet<Terminal> firstSet;
    private HashSet<Terminal> followSet;
    private Boolean isNullable=null;


    public NonTerminal(String name) {
        super(name);
    }

    public HashSet<Terminal> getFirstSet() {
        return firstSet;
    }

    public void setFirstSet(HashSet<Terminal> firstSet) {
        this.firstSet = firstSet;
    }

    public HashSet<Terminal> getFollowSet() {
        return followSet;
    }

    public void setFollowSet(HashSet<Terminal> followSet) {
        this.followSet = followSet;
    }

    public Boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }
}