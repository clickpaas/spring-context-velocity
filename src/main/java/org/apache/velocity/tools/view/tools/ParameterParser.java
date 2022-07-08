//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view.tools;

import javax.servlet.ServletRequest;
import org.apache.velocity.tools.view.ParameterTool;
import org.apache.velocity.tools.view.ViewContext;

/** @deprecated */
@Deprecated
public class ParameterParser extends ParameterTool {
    public ParameterParser() {
    }

    public ParameterParser(ServletRequest request) {
        this.setRequest(request);
    }

    /** @deprecated */
    @Deprecated
    public void init(Object obj) {
        if (obj instanceof ViewContext) {
            this.setRequest(((ViewContext)obj).getRequest());
        }

    }
}
