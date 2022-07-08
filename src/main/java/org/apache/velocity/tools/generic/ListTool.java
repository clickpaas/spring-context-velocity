//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.generic;

import java.lang.reflect.Array;
import java.util.List;
import org.apache.velocity.tools.config.DefaultKey;

/** @deprecated */
@Deprecated
@DefaultKey("lists")
public class ListTool {
    public ListTool() {
    }

    public Object get(Object list, int index) {
        if (this.isArray(list)) {
            return this.getFromArray(list, index);
        } else if (!this.isList(list)) {
            return null;
        } else {
            try {
                return ((List)list).get(index);
            } catch (IndexOutOfBoundsException var4) {
                return null;
            }
        }
    }

    private Object getFromArray(Object array, int index) {
        try {
            return Array.get(array, index);
        } catch (IndexOutOfBoundsException var4) {
            return null;
        }
    }

    public Object set(Object list, int index, Object value) {
        if (this.isArray(list)) {
            return this.setToArray(list, index, value);
        } else if (!this.isList(list)) {
            return null;
        } else {
            try {
                ((List)list).set(index, value);
                return "";
            } catch (IndexOutOfBoundsException var5) {
                return null;
            }
        }
    }

    private Object setToArray(Object array, int index, Object value) {
        try {
            Array.set(array, index, value);
            return "";
        } catch (IndexOutOfBoundsException var5) {
            return null;
        }
    }

    public Integer size(Object list) {
        if (this.isArray(list)) {
            return Array.getLength(list);
        } else {
            return !this.isList(list) ? null : ((List)list).size();
        }
    }

    public boolean isArray(Object object) {
        return object == null ? false : object.getClass().isArray();
    }

    public boolean isList(Object object) {
        return object instanceof List;
    }

    public Boolean isEmpty(Object list) {
        Integer size = this.size(list);
        return size == null ? null : size == 0;
    }

    public Boolean contains(Object list, Object element) {
        if (this.isArray(list)) {
            return this.arrayContains(list, element);
        } else {
            return !this.isList(list) ? null : ((List)list).contains(element);
        }
    }

    private Boolean arrayContains(Object array, Object element) {
        int size = this.size(array);

        for(int index = 0; index < size; ++index) {
            if (this.equals(element, this.getFromArray(array, index))) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    private boolean equals(Object what, Object with) {
        if (what == null) {
            return with == null;
        } else {
            return what.equals(with);
        }
    }
}
