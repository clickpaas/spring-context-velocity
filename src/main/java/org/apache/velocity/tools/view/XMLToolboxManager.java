//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.RuleSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** @deprecated */
@Deprecated
public class XMLToolboxManager implements ToolboxManager {
    protected static final Log LOG = LogFactory.getLog(XMLToolboxManager.class);
    private List toolinfo = new ArrayList();
    private Map data = new HashMap();
    private static RuleSet ruleSet = new ToolboxRuleSet();

    public XMLToolboxManager() {
        LOG.warn("XMLToolboxManager has been deprecated. Please use org.apache.velocity.tools.ToolboxFactory instead.");
    }

    public void addTool(ToolInfo info) {
        if (this.validateToolInfo(info)) {
            this.toolinfo.add(info);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Added " + info.getClassname() + " to the toolbox as " + info.getKey());
            }
        }

    }

    public void addData(ToolInfo info) {
        if (this.validateToolInfo(info)) {
            this.data.put(info.getKey(), info.getInstance((Object)null));
            if (LOG.isDebugEnabled()) {
                LOG.debug("Added '" + info.getInstance((Object)null) + "' to the toolbox as " + info.getKey());
            }
        }

    }

    protected boolean validateToolInfo(ToolInfo info) {
        if (info == null) {
            LOG.error("ToolInfo is null!");
            return false;
        } else if (info.getKey() != null && info.getKey().length() != 0) {
            if (info.getClassname() == null) {
                LOG.error("Tool " + info.getKey() + " has no Class definition!");
                return false;
            } else {
                return true;
            }
        } else {
            LOG.error("Tool has no key defined!");
            return false;
        }
    }

    public Map getToolbox(Object initData) {
        Map toolbox = new HashMap(this.data);
        Iterator i = this.toolinfo.iterator();

        while(i.hasNext()) {
            ToolInfo info = (ToolInfo)i.next();
            toolbox.put(info.getKey(), info.getInstance(initData));
        }

        return toolbox;
    }

    public void load(String path) throws Exception {
        if (path == null) {
            throw new IllegalArgumentException("Path value cannot be null");
        } else {
            File file = new File(path);
            if (!file.exists()) {
                throw new IllegalArgumentException("Could not find toolbox config file at: " + path);
            } else {
                this.load((InputStream)(new FileInputStream(file)));
            }
        }
    }

    public void load(InputStream input) throws Exception {
        LOG.trace("Loading toolbox...");
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.setUseContextClassLoader(true);
        digester.push(this);
        digester.addRuleSet(this.getRuleSet());
        digester.parse(input);
        LOG.trace("Toolbox loaded.");
    }

    protected RuleSet getRuleSet() {
        return ruleSet;
    }
}
