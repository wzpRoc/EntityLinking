package org.ailab.entityLinking_old.wim;

/**
 * User: Lu Tingming
 * Date: 15-5-8
 * Time: 上午3:25
 * Desc:
 */
public enum AnnoState {
    notAnnotated(0, "not annotated", "--"),
    machineAnnotated(1, "machine annotated", "Machine"),
    manuallyAnnotated(2, "manually annotated", "Manual")
    ;

    private int value;
    private String name;
    private String abbr;

    private AnnoState(int value, String name, String abbr) {
        this.value = value;
        this.name = name;
        this.abbr = abbr;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    
    public static String getNameByValue(int value) {
        if(value == notAnnotated.value) {
            return notAnnotated.name;
        }
        if(value == machineAnnotated.value) {
            return machineAnnotated.name;
        }
        if(value == manuallyAnnotated.value) {
            return manuallyAnnotated.name;
        }
        
        return String.valueOf(value);
    }
    
    public static String getAbbrByValue(int value) {
        if(value == notAnnotated.value) {
            return notAnnotated.abbr;
        }
        if(value == machineAnnotated.value) {
            return machineAnnotated.abbr;
        }
        if(value == manuallyAnnotated.value) {
            return manuallyAnnotated.abbr;
        }
        
        return String.valueOf(value);
    }

}
