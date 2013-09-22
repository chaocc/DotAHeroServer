package com.wolf.dotah.server.cmpnt;

import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.Number;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

public class Data extends EsObject {
    
    private static final long serialVersionUID = -1791116800338703971L;
    
    
    public Data() {
    
        super();
    }
    
    
    public Data addBoolean(String name, boolean value) {
    
        debug(name, value + "");
        super.setBoolean(name, value);
        return this;
    }
    
    public Data addBooleanArray(String name, boolean[] value) {
    
        debug(name, value + "");
        super.setBooleanArray(name, value);
        return this;
    }
    
    public Data addByte(String name, byte value) {
    
        debug(name, value + "");
        super.setByte(name, value);
        return this;
    }
    
    public Data addByteArray(String name, byte[] value) {
    
        debug(name, value + "");
        super.setByteArray(name, value);
        return this;
    }
    
    public Data addChar(String name, char value) {
    
        debug(name, value + "");
        super.setChar(name, value);
        return this;
    }
    
    public Data addCharArray(String name, char[] value) {
    
        debug(name, value.toString());
        super.setCharArray(name, value);
        return this;
    }
    
    public Data addDouble(String name, double value) {
    
        debug(name, value + "");
        super.setDouble(name, value);
        return this;
    }
    
    public Data addDoubleArray(String name, double[] value) {
    
        debug(name, value + "");
        super.setDoubleArray(name, value);
        return this;
    }
    
    public Data addEsObject(String name, EsObject value) {
    
        debug(name, value + "");
        super.setEsObject(name, value);
        return this;
    }
    
    public Data addEsObjectArray(String name, EsObject[] objs) {
    
        debug(name, objs.toString());
        super.setEsObjectArray(name, objs);
        return this;
    }
    
    public Data addFloat(String name, float value) {
    
        debug(name, value + "");
        super.setFloat(name, value);
        return this;
    }
    
    public Data addFloatArray(String name, float[] value) {
    
        debug(name, value + "");
        super.setFloatArray(name, value);
        return this;
    }
    
    public Data addInteger(String name, int value) {
    
        debug(name, value + "");
        super.setInteger(name, value);
        return this;
    }
    
    public Data addIntegerArray(String name, int[] value) {
    
        debug(name, value + "");
        super.setIntegerArray(name, value);
        return this;
    }
    
    public Data addLong(String name, long value) {
    
        debug(name, value + "");
        super.setLong(name, value);
        return this;
    }
    
    public Data addLongArray(String name, long[] value) {
    
        debug(name, value + "");
        super.setLongArray(name, value);
        return this;
    }
    
    public Data addNumber(String name, double value) {
    
        debug(name, value + "");
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, float value) {
    
        debug(name, value + "");
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, int value) {
    
        debug(name, value + "");
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, long value) {
    
        debug(name, value + "");
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumber(String name, Number value) {
    
        debug(name, value + "");
        super.setNumber(name, value);
        return this;
    }
    
    public Data addNumberArray(String name, Number[] arg1) {
    
        debug(name, arg1.toString());
        super.setNumberArray(name, arg1);
        return this;
    }
    
    public Data addShort(String name, short value) {
    
        debug(name, value + "");
        super.setShort(name, value);
        return this;
    }
    
    public Data addShortArray(String name, short[] value) {
    
        debug(name, value + "");
        super.setShortArray(name, value);
        return this;
    }
    
    public Data addString(String name, String value) {
    
        debug(name, value + "");
        super.setString(name, value);
        return this;
    }
    
    public Data addStringArray(String name, String[] value) {
    
        debug(name, value + "");
        super.setStringArray(name, value);
        return this;
    }
    
    public Data setAction(String serverAction) {
    
        debug("action", serverAction + "");
        this.setInteger(c.a, u.actionMapping(serverAction));
        this.setString(c.action_description, serverAction);
        return this;
    }
    
    public Data setAction(int clientAction) {
    
        debug("function id ", clientAction + "");
        this.setInteger(c.a, clientAction);
        return this;
    }
    
    private void debug(String name, String value) {
    
        l.logger().d("adding data to esobj ===>> ", value);
        
        
    }
    
    
    public Data addHandCardSize(int original_size, int new_size) {
    
        this.setInteger(c.param_key.hand_card_change_amount, new_size - original_size);
        //        this.setInteger(c.param_key.hand_card_count, new_size);
        return this;
    }
    
    
    public Data addHandCardState(List<Integer> input, List<Integer> cards) {
    
        int[] toBeChange = u.intArrayMapping(input.toArray(new Integer[] {}));
        this.setIntegerArray(c.param_key.id_list, toBeChange);
        //        this.setIntegerArray(c.param_key.hand_card_to_be_change, toBeChange);
        //        this.setIntegerArray(c.param_key.hand_card_after_change, u.intArrayMapping(cards.toArray(new Integer[] {})));
        return this;
    }
    
    
    public Data isCancelEnabled(boolean b) {
    
        return this.addBoolean(c.param_key.is_optional_discard, b);
    }
    
    
}
