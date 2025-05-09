//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view.tools;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.velocity.tools.generic.ValueParser;
import org.apache.velocity.tools.view.context.ViewContext;

/** @deprecated */
@Deprecated
public class LinkTool extends org.apache.velocity.tools.view.LinkTool {
    /** @deprecated */
    @Deprecated
    public static final String SELF_ABSOLUTE_KEY = "self-absolute";
    /** @deprecated */
    @Deprecated
    public static final String SELF_INCLUDE_PARAMETERS_KEY = "self-include-parameters";
    /** @deprecated */
    @Deprecated
    public static final String AUTO_IGNORE_PARAMETERS_KEY = "auto-ignore-parameters";
    /** @deprecated */
    @Deprecated
    protected ServletContext application;
    private HashSet<String> parametersToIgnore;
    private boolean autoIgnore = true;

    public LinkTool() {
    }

    protected void configure(ValueParser parser) {
        Boolean selfAbsolute = parser.getBoolean("self-absolute");
        if (selfAbsolute != null) {
            this.setForceRelative(!selfAbsolute);
        }

        Boolean selfParams = parser.getBoolean("self-include-parameters");
        if (selfParams != null) {
            this.setIncludeRequestParams(selfParams);
        }

        Boolean autoIgnoreParams = parser.getBoolean("auto-ignore-parameters");
        if (autoIgnoreParams != null) {
            this.setAutoIgnoreParameters(autoIgnoreParams);
        }

        super.configure(parser);
    }

    /** @deprecated */
    @Deprecated
    public void init(Object obj) {
        if (obj instanceof ViewContext) {
            ViewContext ctx = (ViewContext)obj;
            this.setRequest(ctx.getRequest());
            this.setResponse(ctx.getResponse());
            this.application = ctx.getServletContext();
            if (ctx.getVelocityEngine() != null) {
                this.log = ctx.getVelocityEngine().getLog();
            }
        }

    }

    /** @deprecated */
    @Deprecated
    public void setXhtml(boolean useXhtml) {
        this.setXHTML(useXhtml);
    }

    /** @deprecated */
    @Deprecated
    public void setSelfAbsolute(boolean selfAbsolute) {
        this.setForceRelative(!selfAbsolute);
    }

    /** @deprecated */
    @Deprecated
    public void setSelfIncludeParameters(boolean selfParams) {
        this.setIncludeRequestParams(selfParams);
    }

    /** @deprecated */
    @Deprecated
    public void setAutoIgnoreParameters(boolean autoIgnore) {
        this.autoIgnore = autoIgnore;
    }

    /** @deprecated */
    @Deprecated
    public void setRequest(HttpServletRequest request) {
        this.request = request;
        this.setFromRequest(request);
    }

    /** @deprecated */
    @Deprecated
    public void setResponse(HttpServletResponse response) {
        this.response = response;
        if (response != null) {
            this.setCharacterEncoding(response.getCharacterEncoding());
        }

    }

    /** @deprecated */
    @Deprecated
    public LinkTool setAnchor(String anchor) {
        return (LinkTool)this.anchor(anchor);
    }

    /** @deprecated */
    @Deprecated
    public LinkTool setRelative(String uri) {
        return (LinkTool)this.relative(uri);
    }

    /** @deprecated */
    @Deprecated
    public LinkTool setAbsolute(String uri) {
        return (LinkTool)this.absolute(uri);
    }

    /** @deprecated */
    @Deprecated
    public LinkTool setURI(String uri) {
        return (LinkTool)this.uri(uri);
    }

    /** @deprecated */
    @Deprecated
    public String getURI() {
        return this.path;
    }

    /** @deprecated */
    @Deprecated
    public LinkTool addQueryData(String key, Object value) {
        return (LinkTool)this.append(key, value);
    }

    /** @deprecated */
    @Deprecated
    public LinkTool addQueryData(Map parameters) {
        return (LinkTool)this.params(parameters);
    }

    /** @deprecated */
    @Deprecated
    public String getQueryData() {
        return this.getQuery();
    }

    /** @deprecated */
    @Deprecated
    public String encodeURL(String url) {
        return this.encode(url);
    }

    /** @deprecated */
    @Deprecated
    public LinkTool addIgnore(String parameterName) {
        LinkTool copy = (LinkTool)this.duplicate();
        if (copy.parametersToIgnore == null) {
            copy.parametersToIgnore = new HashSet(1);
        }

        copy.parametersToIgnore.add(parameterName);
        return copy;
    }

    /** @deprecated */
    @Deprecated
    public LinkTool addAllParameters() {
        if (this.parametersToIgnore != null) {
            String[] ignoreThese = new String[this.parametersToIgnore.size()];
            return (LinkTool)this.addRequestParamsExcept((String[])this.parametersToIgnore.toArray(ignoreThese));
        } else {
            return this.autoIgnore ? (LinkTool)this.addMissingRequestParams(new String[0]) : (LinkTool)this.addRequestParams(new String[0]);
        }
    }

    public void setParam(Object key, Object value, boolean append) {
        super.setParam(key, value, append);
        if (this.autoIgnore) {
            if (this.parametersToIgnore == null) {
                this.parametersToIgnore = new HashSet(1);
            }

            this.parametersToIgnore.add(String.valueOf(key));
        }

    }

    public void setParams(Object obj, boolean append) {
        super.setParams(obj, append);
        if (this.autoIgnore && obj instanceof Map) {
            Map params = (Map)obj;
            if (!params.isEmpty()) {
                if (this.parametersToIgnore == null) {
                    this.parametersToIgnore = new HashSet(params.size());
                }

                Iterator i$ = ((Map)obj).entrySet().iterator();

                while(i$.hasNext()) {
                    Object e = i$.next();
                    Entry entry = (Entry)e;
                    String key = String.valueOf(entry.getKey());
                    this.parametersToIgnore.add(key);
                }
            }
        }

    }
}
