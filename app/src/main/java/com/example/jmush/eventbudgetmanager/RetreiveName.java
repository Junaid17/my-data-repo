package com.example.jmush.eventbudgetmanager;

/**
 * Created by jmush on 12/27/16.
 */

public class RetreiveName {

    private String memberName;
    private String Ntid;
    private String Phone;
    private int Amount;


    public RetreiveName(String memberName,String Ntid,String Phone, int Amount) {
        this.memberName = memberName;
        this.Ntid=Ntid;
        this.Phone=Phone;
        this.Amount=Amount;

    }

    public RetreiveName() {
    }


    public String getNtid() {
        return Ntid;
    }

    public void setNtid(String ntid) {
        Ntid = ntid;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }



    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
