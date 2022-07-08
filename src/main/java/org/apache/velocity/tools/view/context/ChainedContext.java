//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view.context;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.ViewToolContext;

/** @deprecated */
@Deprecated
public class ChainedContext extends ViewToolContext implements ViewContext {
    private Map<String, Object> oldToolbox;

    public ChainedContext(VelocityEngine velocity, HttpServletRequest request, HttpServletResponse response, ServletContext application) {
        super(velocity, request, response, application);
    }

    public ChainedContext(Context ctx, VelocityEngine velocity, HttpServletRequest request, HttpServletResponse response, ServletContext application) {
        this(velocity, request, response, application);
        if (ctx != null) {
            Object[] arr$ = ctx.getKeys();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Object key = arr$[i$];
                String skey = String.valueOf(key);
                this.put(skey, ctx.get(skey));
            }
        }

    }

    /** @deprecated */
    public void setToolbox(Map<String, Object> box) {
        this.oldToolbox = box;
    }

    public Map<String, Object> getToolbox() {
        if (this.oldToolbox != null) {
            Map<String, Object> box = new HashMap(this.oldToolbox);
            box.putAll(super.getToolbox());
            return box;
        } else {
            return super.getToolbox();
        }
    }

    protected Object internalGet(String key) {
        Object o = null;
        if (this.oldToolbox != null) {
            o = this.oldToolbox.get(key);
            if (o != null) {
                return o;
            }
        }

        return super.internalGet(key);
    }
}
