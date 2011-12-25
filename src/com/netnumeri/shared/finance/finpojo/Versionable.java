package com.netnumeri.shared.finance.finpojo;

public abstract class Versionable extends Persistable {

    private Integer concurrencyVersion;

    public Versionable(String name) {
        super(name);
    }

    public Versionable() {
    }

    public Integer getConcurrencyVersion() {
        return concurrencyVersion;
    }

    public void setConcurrencyVersion(Integer concurrencyVersion) {
        this.concurrencyVersion = concurrencyVersion;
    }
}
