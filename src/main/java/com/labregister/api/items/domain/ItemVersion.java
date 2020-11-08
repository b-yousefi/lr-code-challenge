package com.labregister.api.items.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ItemVersion {

    private final int versionNumber;

    private final String name;

    private final Map<String, String> attributes;

    private final Date versionDate;

    public ItemVersion(Item item) {
        this.versionNumber = item.getVersionNumber();
        this.name = item.getName();
        this.attributes = new HashMap<>(item.getAttributes());
        this.versionDate = (Date) (item.getLastUpdateDate().clone());
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return new HashMap<>(attributes);
    }

    public Date getVersionDate() {
        return (Date) versionDate.clone();
    }

}
