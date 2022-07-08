//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.velocity.runtime.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.runtime.RuntimeServices;

public class CommonsLogLogChute implements LogChute {
    public static final String LOGCHUTE_COMMONS_LOG_NAME = "runtime.log.logsystem.commons.logging.name";
    public static final String DEFAULT_LOG_NAME = "org.apache.velocity";
    protected Log log;

    public CommonsLogLogChute() {
    }

    public void init(RuntimeServices rs) throws Exception {
        String name = (String)rs.getProperty("runtime.log.logsystem.commons.logging.name");
        if (name == null) {
            name = "org.apache.velocity";
        }

        this.log = LogFactory.getLog(name);
        this.log(0, "CommonsLogLogChute name is '" + name + "'");
    }

    public void log(int level, String message) {
        switch(level) {
        case -1:
            this.log.trace(message);
            break;
        case 0:
        default:
            this.log.debug(message);
            break;
        case 1:
            this.log.info(message);
            break;
        case 2:
            this.log.warn(message);
            break;
        case 3:
            this.log.error(message);
        }

    }

    public void log(int level, String message, Throwable t) {
        switch(level) {
        case -1:
            this.log.trace(message, t);
            break;
        case 0:
        default:
            this.log.debug(message, t);
            break;
        case 1:
            this.log.info(message, t);
            break;
        case 2:
            this.log.warn(message, t);
            break;
        case 3:
            this.log.error(message, t);
        }

    }

    public boolean isLevelEnabled(int level) {
        switch(level) {
        case -1:
            return this.log.isTraceEnabled();
        case 0:
            return this.log.isDebugEnabled();
        case 1:
            return this.log.isInfoEnabled();
        case 2:
            return this.log.isWarnEnabled();
        case 3:
            return this.log.isErrorEnabled();
        default:
            return true;
        }
    }
}
