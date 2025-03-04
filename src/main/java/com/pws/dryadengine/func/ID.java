package com.pws.dryadengine.func;

import java.util.HashMap;

public class ID {
    private final int serial;
    private static HashMap<Integer, Integer> serialIndex = new HashMap<>();

    public ID(int typeId){
        if(!serialIndex.containsKey(typeId)) 
            serialIndex.put(typeId, 1);

        int sip = serialIndex.get(typeId).intValue();

        this.serial = (1000 + typeId) * (int)Math.pow(10,(int)(Math.log10(sip)+1)) + sip;

        sip++;
        serialIndex.put(typeId, sip);
    }

    public int getSerial() {
        return serial;
    }
}
