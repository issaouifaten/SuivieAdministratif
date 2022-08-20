package com.example.suivieadministratif.model;

public class Machine {

    private   String CodeMachine  ;
    private  String  LibelleMachine ;

    public Machine(String codeMachine, String libelleMachine) {
        CodeMachine = codeMachine;
        LibelleMachine = libelleMachine;
    }

    public String getCodeMachine() {
        return CodeMachine;
    }

    public void setCodeMachine(String codeMachine) {
        CodeMachine = codeMachine;
    }

    public String getLibelleMachine() {
        return LibelleMachine;
    }

    public void setLibelleMachine(String libelleMachine) {
        LibelleMachine = libelleMachine;
    }
}
