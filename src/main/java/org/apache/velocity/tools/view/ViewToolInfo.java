//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** @deprecated */
@Deprecated
public class ViewToolInfo implements ToolInfo {
    protected static final Log LOG = LogFactory.getLog(ViewToolInfo.class);
    private String key;
    private Class clazz;
    private Map parameters;
    private Method init = null;
    private Method configure = null;

    public ViewToolInfo() {
    }

    protected Class getApplicationClass(String name) throws ClassNotFoundException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ViewToolInfo.class.getClassLoader();
        }

        return loader.loadClass(name);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setClassname(String classname) throws Exception {
        if (classname != null && classname.length() != 0) {
            this.clazz = this.getApplicationClass(classname);
            this.clazz.newInstance();

            try {
                this.init = this.clazz.getMethod("init", Object.class);
            } catch (NoSuchMethodException var4) {
            }

            try {
                this.configure = this.clazz.getMethod("configure", Map.class);
            } catch (NoSuchMethodException var3) {
            }
        } else {
            this.clazz = null;
        }

    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public void setParameter(String name, String value) {
        if (this.parameters == null) {
            this.parameters = new HashMap();
        }

        this.parameters.put(name, value);
    }

    public String getKey() {
        return this.key;
    }

    public String getClassname() {
        return this.clazz != null ? this.clazz.getName() : null;
    }

    public Map getParameters() {
        return this.parameters;
    }

    public Object getInstance(Object initData) {
        if (this.clazz == null) {
            LOG.error("Tool " + this.key + " has no Class definition!");
            return null;
        } else {
            Object tool = null;

            try {
                tool = this.clazz.newInstance();
            } catch (IllegalAccessException var8) {
                LOG.error("Exception while instantiating instance of \"" + this.getClassname() + "\"", var8);
            } catch (InstantiationException var9) {
                LOG.error("Exception while instantiating instance of \"" + this.getClassname() + "\"", var9);
            }

            if (this.configure != null && this.parameters != null) {
                try {
                    this.configure.invoke(tool, this.parameters);
                } catch (IllegalAccessException var6) {
                    LOG.error("Exception when calling configure(Map) on " + tool, var6);
                } catch (InvocationTargetException var7) {
                    LOG.error("Exception when calling configure(Map) on " + tool, var7);
                }
            }

            if (this.init != null) {
                try {
                    this.init.invoke(tool, initData);
                } catch (IllegalAccessException var4) {
                    LOG.error("Exception when calling init(Object) on " + tool, var4);
                } catch (InvocationTargetException var5) {
                    LOG.error("Exception when calling init(Object) on " + tool, var5);
                }
            }

            return tool;
        }
    }
}
