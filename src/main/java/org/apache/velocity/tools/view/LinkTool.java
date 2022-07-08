//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.tools.view;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.tools.generic.ValueParser;

public class LinkTool extends org.apache.velocity.tools.generic.LinkTool {
    public static final String INCLUDE_REQUEST_PARAMS_KEY = "includeRequestParams";
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected boolean includeRequestParams = false;

    public LinkTool() {
    }

    protected void configure(ValueParser props) {
        super.configure(props);
        this.request = (HttpServletRequest)props.getValue("request");
        Boolean incParams = props.getBoolean("includeRequestParams");
        if (incParams != null) {
            this.setIncludeRequestParams(incParams);
        }

        this.response = (HttpServletResponse)props.getValue("response");
        this.setCharacterEncoding(this.response.getCharacterEncoding());
        this.setFromRequest(this.request);
    }

    protected void setFromRequest(HttpServletRequest request) {
        this.setScheme(request.getScheme());
        this.setPort(request.getServerPort());
        this.setHost(request.getServerName());
        String ctx = request.getContextPath();
        String pth = ServletUtils.getPath(request);
        this.setPath(this.combinePath(ctx, pth));
        if (this.includeRequestParams) {
            this.setQuery(request.getParameterMap());
        }

    }

    public void setIncludeRequestParams(boolean includeRequestParams) {
        this.includeRequestParams = includeRequestParams;
    }

    public LinkTool addRequestParams(String... butOnlyThese) {
        return this.addRequestParams(false, butOnlyThese);
    }

    public LinkTool addRequestParamsExcept(String... ignoreThese) {
        return this.addRequestParams(true, ignoreThese);
    }

    public LinkTool addMissingRequestParams(String... ignoreThese) {
        String[] these;
        if (this.query != null && !this.query.isEmpty()) {
            Set keys = this.query.keySet();
            these = new String[keys.size() + ignoreThese.length];

            int i;
            for(i = 0; i < ignoreThese.length; ++i) {
                these[i] = ignoreThese[i];
            }

            for(Iterator iter = keys.iterator(); iter.hasNext(); ++i) {
                these[i] = String.valueOf(iter.next());
            }
        } else {
            these = ignoreThese;
        }

        return this.addRequestParams(true, these);
    }

    private LinkTool addRequestParams(boolean ignore, String... special) {
        LinkTool copy = (LinkTool)this.duplicate(true);
        Map reqParams = this.request.getParameterMap();
        boolean noSpecial = special == null || special.length == 0;
        Iterator i$ = reqParams.entrySet().iterator();

        while(true) {
            Entry entry;
            String key;
            boolean isSpecial;
            do {
                if (!i$.hasNext()) {
                    return copy;
                }

                Object e = i$.next();
                entry = (Entry)e;
                key = String.valueOf(entry.getKey());
                isSpecial = !noSpecial && this.contains(special, key);
            } while(!noSpecial && (!ignore || isSpecial) && (ignore || !isSpecial));

            copy.setParam(key, entry.getValue(), this.appendParams);
        }
    }

    private boolean contains(String[] set, String name) {
        String[] arr$ = set;
        int len$ = set.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String i = arr$[i$];
            if (name.equals(i)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isPathChanged() {
        if (this.path == this.self.getPath()) {
            return false;
        } else if (this.path == null) {
            return true;
        } else {
            return !this.path.equals(this.self.getPath());
        }
    }

    public String getContextPath() {
        if (!this.isPathChanged()) {
            return this.request.getContextPath();
        } else if (this.path != null && !this.opaque) {
            int firstInternalSlash = this.path.indexOf(47, 1);
            return firstInternalSlash <= 0 ? this.path : this.path.substring(0, firstInternalSlash);
        } else {
            return null;
        }
    }

    public String getRequestPath() {
        if (this.path != null && !this.opaque) {
            if (!this.isPathChanged()) {
                return ServletUtils.getPath(this.request);
            } else {
                int firstInternalSlash = this.path.indexOf(47, 1);
                return firstInternalSlash <= 0 ? this.path : this.path.substring(firstInternalSlash, this.path.length());
            }
        } else {
            return null;
        }
    }

    public String getContextURL() {
        LinkTool copy = (LinkTool)this.duplicate();
        copy.setQuery((Object)null);
        copy.setFragment((Object)null);
        copy.setPath(this.getContextPath());
        return copy.toString();
    }

    public String toString() {
        String str = super.toString();
        return str.length() == 0 ? str : this.response.encodeURL(str);
    }
}
