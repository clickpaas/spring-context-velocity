//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.Rule;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.RuleSetBase;
import org.xml.sax.Attributes;

/** @deprecated */
@Deprecated
public class ToolboxRuleSet extends RuleSetBase {
    public ToolboxRuleSet() {
    }

    public void addRuleInstances(Digester digester) {
        this.addToolRules(digester);
        this.addDataRules(digester);
    }

    protected void addToolRules(Digester digester) {
        digester.addObjectCreate("toolbox/tool", this.getToolInfoClass());
        digester.addBeanPropertySetter("toolbox/tool/key", "key");
        digester.addBeanPropertySetter("toolbox/tool/class", "classname");
        digester.addRule("toolbox/tool/parameter", new ToolboxRuleSet.ParameterRule());
        digester.addSetNext("toolbox/tool", "addTool");
    }

    protected void addDataRules(Digester digester) {
        digester.addObjectCreate("toolbox/data", this.getDataInfoClass());
        digester.addSetProperties("toolbox/data");
        digester.addBeanPropertySetter("toolbox/data/key", "key");
        digester.addBeanPropertySetter("toolbox/data/value", "value");
        digester.addSetNext("toolbox/data", "addData");
    }

    protected Class getToolInfoClass() {
        return ViewToolInfo.class;
    }

    protected Class getDataInfoClass() {
        return DataInfo.class;
    }

    protected static class ParameterRule extends Rule {
        protected ParameterRule() {
        }

        public void begin(String ns, String ln, Attributes attributes) throws Exception {
            ViewToolInfo toolinfo = (ViewToolInfo)this.getDigester().peek();
            String name = attributes.getValue("name");
            String value = attributes.getValue("value");
            toolinfo.setParameter(name, value);
        }
    }
}
