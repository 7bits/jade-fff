package com.recruiters.entities;

import java.util.HashSet;
import java.util.Set;

public class User {

    private Integer id;

    private String name;

    private Set<Address> addresses = new HashSet<Address>();

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(final Address address) {
        this.addresses.add(address);
    }

// other setters and getters

}