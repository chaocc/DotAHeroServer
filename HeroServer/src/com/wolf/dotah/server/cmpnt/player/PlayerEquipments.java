package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.cardandskill.Card;
import com.wolf.dotah.server.util.l;


public class PlayerEquipments {
    private Card weapon;
    private Card armor;
    private List<EquipChangeListener> changeListeners = new ArrayList<EquipChangeListener>();
    
    public void setWeapon(Card weapon) {
    
        if (weapon.getType() == 'd') {
            removeArmor();
        }
        this.weapon = weapon;
        for (EquipChangeListener lis : changeListeners) {
            lis.onWeaponChanged(armor, false);
        }
    }
    
    public void setArmor(Card armor) {
    
        this.armor = armor;
        for (EquipChangeListener lis : changeListeners) {
            lis.onArmorChanged(armor, false);
        }
    }
    
    public void removeWeapon() {
    
        Card tempWeapon = weapon;
        weapon = null;
        for (EquipChangeListener lis : changeListeners) {
            lis.onWeaponChanged(tempWeapon, true);
        }
    }
    
    public void removeArmor() {
    
        Card tempArmor = armor;
        armor = null;
        for (EquipChangeListener lis : changeListeners) {
            lis.onArmorChanged(tempArmor, true);
        }
        
    }
    
    public void removeEquip(Card equip) {
    
        
        if (equip.getType() == 'a' && armor != null) {
            if (equip.getId() != armor.getId()) {
                l.logger().d("no armor to remove, armor=" + armor + ", removing=" + equip);
                return;
            }
            removeArmor();
        } else if (weapon != null) {
            if (equip.getId() != weapon.getId()) {
                l.logger().d("no weapon to remove, weapon=" + weapon + ", removing=" + equip);
                return;
            }
            removeWeapon();
        }
        l.logger().d("no equip to remove, weapon=" + weapon + ", armor=" + armor + ", removing=" + equip);
    }
    
    public boolean registerEquipChangeListener(EquipChangeListener listener) {
    
        if (changeListeners.contains(listener)) {
            return false;
        } else {
            changeListeners.add(listener);
            return true;
        }
    }
    
    public boolean unregisterEquipChangeListener(EquipChangeListener listener) {
    
        if (changeListeners.contains(listener)) {
            changeListeners.remove(listener);
            return true;
        } else {
            return false;
        }
    }
    
    public interface EquipChangeListener {
        void onWeaponChanged(Card weapon, boolean directionRemove);
        
        void onArmorChanged(Card armor, boolean directionRemove);
    }
}
