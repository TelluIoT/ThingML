package org.sintef.thingml.helpers;

import org.sintef.thingml.AnnotatedElement;
import org.sintef.thingml.PlatformAnnotation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffl on 10.05.2016.
 */
public class AnnotatedElementHelper {


    public static List<PlatformAnnotation> allAnnotations(AnnotatedElement self) {
        return self.getAnnotations();
    }


    public static boolean isDefined(AnnotatedElement self, String annotation, String value) {
        for (PlatformAnnotation a : allAnnotations(self)) {
            if (a.getName().equals(annotation)) {
                if (a.getValue().equals(value))
                    return true;
            }
        }
        return false;
    }


    public static boolean hasAnnotation(AnnotatedElement self, String name) {
        for (PlatformAnnotation a : allAnnotations(self)) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    public static List<String> annotation(AnnotatedElement self, String name) {
        List<String> result = new ArrayList<String>();
        for (PlatformAnnotation a : self.getAnnotations()) {
            if (a.getName().equals(name)) {
                result.add(a.getValue());
            }
        }
        return result;
    }




}
