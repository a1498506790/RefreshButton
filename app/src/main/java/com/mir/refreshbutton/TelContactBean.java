package com.mir.refreshbutton;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018-03-15
 * @desc
 */

class TelContactBean {

    private String name;
    private String telNumber;

    public TelContactBean(String name, String telNumber) {
        this.name = name;
        this.telNumber = telNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
