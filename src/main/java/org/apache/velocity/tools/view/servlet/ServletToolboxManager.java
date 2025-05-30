//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view.servlet;

import org.apache.commons.digester3.RuleSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.view.ServletUtils;
import org.apache.velocity.tools.view.ToolInfo;
import org.apache.velocity.tools.view.XMLToolboxManager;
import org.apache.velocity.tools.view.context.ViewContext;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** @deprecated */
@Deprecated
public class ServletToolboxManager extends XMLToolboxManager {
    public static final String SESSION_TOOLS_KEY = ServletToolboxManager.class.getName() + ":session-tools";
    protected static final Log LOG = LogFactory.getLog(ServletToolboxManager.class);
    private ServletContext servletContext;
    private Map appTools;
    private ArrayList sessionToolInfo;
    private ArrayList requestToolInfo;
    private boolean createSession;
    private static HashMap managersMap = new HashMap();
    private static RuleSet servletRuleSet = new ServletToolboxRuleSet();

    private ServletToolboxManager(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.appTools = new HashMap();
        this.sessionToolInfo = new ArrayList();
        this.requestToolInfo = new ArrayList();
        this.createSession = true;
        LOG.warn("ServletToolboxManager has been deprecated. Please use org.apache.velocity.tools.ToolboxFactory instead.");
    }

    public static synchronized ServletToolboxManager getInstance(ServletContext servletContext, String toolboxFile) {
        if (!toolboxFile.startsWith("/")) {
            toolboxFile = "/" + toolboxFile;
        }

        String uniqueKey = servletContext.hashCode() + 58 + toolboxFile;
        ServletToolboxManager toolboxManager = (ServletToolboxManager)managersMap.get(uniqueKey);
        if (toolboxManager == null) {
            InputStream is = null;

            try {
                is = servletContext.getResourceAsStream(toolboxFile);
                if (is != null) {
                    LOG.info("Using config file '" + toolboxFile + "'");
                    toolboxManager = new ServletToolboxManager(servletContext);
                    toolboxManager.load(is);
                    managersMap.put(uniqueKey, toolboxManager);
                    LOG.debug("Toolbox setup complete.");
                } else {
                    LOG.debug("No toolbox was found at '" + toolboxFile + "'");
                }
            } catch (Exception var14) {
                LOG.error("Problem loading toolbox '" + toolboxFile + "'", var14);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception var13) {
                }

            }
        }

        return toolboxManager;
    }

    public void setCreateSession(boolean b) {
        this.createSession = b;
        LOG.debug("create-session is set to " + b);
    }

    public void setXhtml(Boolean value) {
        this.servletContext.setAttribute("XHTML", value);
        LOG.info("XHTML is set to " + value);
    }

    protected RuleSet getRuleSet() {
        return servletRuleSet;
    }

    protected boolean validateToolInfo(ToolInfo info) {
        if (!super.validateToolInfo(info)) {
            return false;
        } else {
            if (info instanceof ServletToolInfo) {
                ServletToolInfo sti = (ServletToolInfo)info;
                if (sti.getRequestPath() != null && !"request".equalsIgnoreCase(sti.getScope())) {
                    LOG.error(sti.getKey() + " must be a request-scoped tool to have a request path restriction!");
                    return false;
                }
            }

            return true;
        }
    }

    public void addTool(ToolInfo info) {
        if (this.validateToolInfo(info)) {
            if (info instanceof ServletToolInfo) {
                ServletToolInfo sti = (ServletToolInfo)info;
                if ("request".equalsIgnoreCase(sti.getScope())) {
                    this.requestToolInfo.add(sti);
                    return;
                }

                if ("session".equalsIgnoreCase(sti.getScope())) {
                    this.sessionToolInfo.add(sti);
                    return;
                }

                if ("application".equalsIgnoreCase(sti.getScope())) {
                    this.appTools.put(sti.getKey(), sti.getInstance(this.servletContext));
                    return;
                }

                LOG.warn("Unknown scope '" + sti.getScope() + "' - " + sti.getKey() + " will be request scoped.");
                this.requestToolInfo.add(info);
            } else {
                this.requestToolInfo.add(info);
            }
        }

    }

    public void addData(ToolInfo info) {
        if (this.validateToolInfo(info)) {
            this.appTools.put(info.getKey(), info.getInstance((Object)null));
        }

    }

    public Map getToolbox(Object initData) {
        ViewContext ctx = (ViewContext)initData;
        String requestPath = ServletUtils.getPath(ctx.getRequest());
        Map toolbox = new HashMap(this.appTools);
        if (!this.sessionToolInfo.isEmpty()) {
            HttpSession session = ctx.getRequest().getSession(this.createSession);
            if (session != null) {
                synchronized(this.getMutex(session)) {
                    Map stmap = (Map)session.getAttribute(SESSION_TOOLS_KEY);
                    if (stmap == null) {
                        stmap = new HashMap(this.sessionToolInfo.size());
                        Iterator i = this.sessionToolInfo.iterator();

                        while(i.hasNext()) {
                            ServletToolInfo sti = (ServletToolInfo)i.next();
                            ((Map)stmap).put(sti.getKey(), sti.getInstance(ctx));
                        }

                        session.setAttribute(SESSION_TOOLS_KEY, stmap);
                    }

                    toolbox.putAll((Map)stmap);
                }
            }
        }

        Iterator i = this.requestToolInfo.iterator();

        while(true) {
            ToolInfo info;
            ServletToolInfo sti;
            do {
                if (!i.hasNext()) {
                    return toolbox;
                }

                info = (ToolInfo)i.next();
                if (!(info instanceof ServletToolInfo)) {
                    break;
                }

                sti = (ServletToolInfo)info;
            } while(!sti.allowsRequestPath(requestPath));

            toolbox.put(info.getKey(), info.getInstance(ctx));
        }
    }

    protected Object getMutex(HttpSession session) {
        Object lock = session.getAttribute("session.mutex");
        if (lock == null) {
            synchronized(this) {
                lock = session.getAttribute("session.mutex");
                if (lock == null) {
                    lock = new Boolean(true);
                    session.setAttribute("session.mutex", lock);
                }
            }
        }

        return lock;
    }
}
