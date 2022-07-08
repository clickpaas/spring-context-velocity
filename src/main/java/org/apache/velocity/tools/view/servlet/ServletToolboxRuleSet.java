//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view.servlet;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.Rule;
import org.apache.velocity.tools.view.ToolboxRuleSet;

/** @deprecated */
@Deprecated
public class ServletToolboxRuleSet extends ToolboxRuleSet {
    public ServletToolboxRuleSet() {
    }

    public void addRuleInstances(Digester digester) {
        digester.addRule("toolbox/create-session", new ServletToolboxRuleSet.CreateSessionRule());
        digester.addRule("toolbox/xhtml", new ServletToolboxRuleSet.XhtmlRule());
        super.addRuleInstances(digester);
    }

    protected void addToolRules(Digester digester) {
        super.addToolRules(digester);
        digester.addBeanPropertySetter("toolbox/tool/scope", "scope");
        digester.addBeanPropertySetter("toolbox/tool/request-path", "requestPath");
    }

    protected Class getToolInfoClass() {
        return ServletToolInfo.class;
    }

    protected final class XhtmlRule extends ServletToolboxRuleSet.BooleanConfigRule {
        protected XhtmlRule() {
            super();
        }

        public void setBoolean(Object obj, Boolean b) throws Exception {
            ((ServletToolboxManager)obj).setXhtml(b);
        }
    }

    protected final class CreateSessionRule extends ServletToolboxRuleSet.BooleanConfigRule {
        protected CreateSessionRule() {
            super();
        }

        public void setBoolean(Object obj, Boolean b) throws Exception {
            ((ServletToolboxManager)obj).setCreateSession(b);
        }
    }

    protected abstract class BooleanConfigRule extends Rule {
        protected BooleanConfigRule() {
        }

        public void body(String ns, String name, String text) throws Exception {
            Object parent = this.getDigester().peek();
            if ("yes".equalsIgnoreCase(text)) {
                this.setBoolean(parent, Boolean.TRUE);
            } else {
                this.setBoolean(parent, Boolean.valueOf(text));
            }

        }

        public abstract void setBoolean(Object var1, Boolean var2) throws Exception;
    }
}
