package com.wolf.dotah.server.cmpnt;

import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.Number;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.u;

public class Data extends EsObject {
    
    private static final long serialVersionUID = -1791116800338703971L;
    
    //    public Data() {
    //        this.setInteger(client_const.param_key.kParamRemainingCardCount, value);
    //    }
    
    public Data addBoolean(String name, boolean value) {
        
        super.setBoolean(name, value);
        return this;
    }
    
    public Data addBooleanArray(String name, boolean[] value) {
        
        super.setBooleanArray(name, value);
        return this;
    }
    
    public Data addByte(String name, byte value) {
        
        super.setByte(name, value);
        return this;
    }
    
    public Data addByteArray(String name, byte[] value) {
        
        super.setByteArray(name, value);
        return this;
    }
    
    public Data addChar(String name, char value) {
        
        super.setChar(name, value);
        return this;
    }
    
    public Data addCharArray(String name, char[] value) {
        
        super.setCharArray(name, value);
        return this;
    }
    
    public Data addDouble(String name, double value) {
        
        super.setDouble(name, value);
        return this;
    }
    
    public Data addDoubleArray(String name, double[] value) {
        
        super.setDoubleArray(name, value);
        return this;
    }
    
    public Data addEsObject(String name, EsObject value) {
        
        super.setEsObject(name, value);
        return this;
    }
    
    public Data addEsObjectArray(String arg0, EsObject[] arg1) {
        
        super.setEsObjectArray(arg0, arg1);
        return this;
    }
    
    public Data addFloat(String name, float value) {
        
        super.setFloat(name, value);
        return this;
    }
    
    public Data addFloatArray(String name, float[] value) {
        
        super.setFloatArray(name, value);
        return this;
    }
    
    public Data addInteger(String name, int value) {
        
        super.setInteger(name, value);
        return this;
    }
    
    public Data addIntegerArray(String name, int[] value) {
        
        super.setIntegerArray(name, value);
        return this;
    }
    
    public Data addLong(String name, long value) {
        
        super.setLong(name, value);
        return this;
    }
    
    public Data addLongArray(String name, long[] value) {
        
        super.setLongArray(name, value);
        return this;
    }
    
    public Data addNumber(String name, double value) {
        
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, float value) {
        
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, int value) {
        
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, long value) {
        
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, Number value) {
        
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumberArray(String arg0, Number[] arg1) {
        
        super.setNumberArray(arg0, arg1);
        return this;
    }
    
    public Data addShort(String name, short value) {
        
        super.setShort(name, value);
        return this;
    }
    
    public Data addShortArray(String name, short[] value) {
        
        super.setShortArray(name, value);
        return this;
    }
    
    public Data addString(String name, String value) {
        
        super.setString(name, value);
        return this;
    }
    
    public Data addStringArray(String name, String[] value) {
        
        super.setStringArray(name, value);
        return this;
    }
    
    public void setAction(String serverAction) {
        this.setInteger(c.action, u.actionMapping(serverAction));
        this.setString(c.action_description, serverAction);
        
    }
    
}
