//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view;

/** @deprecated */
@Deprecated
public class DataInfo implements ToolInfo {
    public static final String TYPE_STRING = "string";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_BOOLEAN = "boolean";
    private static final int TYPE_ID_STRING = 0;
    private static final int TYPE_ID_NUMBER = 1;
    private static final int TYPE_ID_BOOLEAN = 2;
    private String key = null;
    private int type_id = 0;
    private Object data = null;

    public DataInfo() {
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        if ("boolean".equalsIgnoreCase(type)) {
            this.type_id = 2;
        } else if ("number".equalsIgnoreCase(type)) {
            this.type_id = 1;
        } else {
            this.type_id = 0;
        }

    }

    public void setValue(String value) {
        if (this.type_id == 2) {
            this.data = Boolean.valueOf(value);
        } else if (this.type_id == 1) {
            if (value.indexOf(46) >= 0) {
                this.data = new Double(value);
            } else {
                this.data = new Integer(value);
            }
        } else {
            this.data = value;
        }

    }

    public String getKey() {
        return this.key;
    }

    public String getClassname() {
        return this.data != null ? this.data.getClass().getName() : null;
    }

    public Object getInstance(Object initData) {
        return this.data;
    }
}
