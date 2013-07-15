package test;

import java.io.File;
import java.io.IOException;

import net.sf.plist.NSArray;
import net.sf.plist.NSObject;
import net.sf.plist.io.PropertyListException;
import net.sf.plist.io.PropertyListParser;

public class PlistReader {
    String source = "doc/HeroCardArray.xml";
    
    public static void main(String... args) throws PropertyListException, IOException {
        new PlistReader().readFile();
    }
    
    public void readFile() throws PropertyListException, IOException {
        NSArray arr = (NSArray) PropertyListParser.parse(new File(source));
        NSObject obj=arr.array()[28];
        System.out.println(obj);
    }
    
}
