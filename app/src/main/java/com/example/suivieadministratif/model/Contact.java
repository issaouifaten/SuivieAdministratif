package com.example.suivieadministratif.model;

public class Contact {

    private String  id  ;
    private   String  contact  ;


    public Contact(String id, String contact) {
        this.id = id;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
