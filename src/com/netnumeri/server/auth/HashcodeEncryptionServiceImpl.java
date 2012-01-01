package com.netnumeri.server.auth;


public class HashcodeEncryptionServiceImpl implements OneWayEncryptionService {


    @Override
    public String hexMD5(String toEncrypt) {

        String ret = Integer.toString(toEncrypt.hashCode());
        return (ret);
    }
}
