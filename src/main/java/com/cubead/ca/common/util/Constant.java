package com.cubead.ca.common.util;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final String CA_COMP_RECV = "receiver";
    public static final String CA_COMP_CTRL = "controller";
    public static final String CA_COMP_CLCT = "collector";
    public static final String CA_COMP_ANLT = "analyst";

    public static enum ComponentEnum {
        RECV(CA_COMP_RECV), CLCT(CA_COMP_CLCT), ANLT(CA_COMP_ANLT), CTRL(CA_COMP_CTRL);

        private String title;

        ComponentEnum(String name) {
            this.title = name;
        }

        public String getTitle() {
            return title;
        }

        public static ComponentEnum match(String title) {
            for (ComponentEnum item : values()) {
                if (item.getTitle().equals(title))
                    return item;
            }
            return null;
        }
    }

    public static enum CompStatusEnum {
        UNKNOWN, ONLINE, OFFLINE, FAILED
    }

    public static final long SCAN_DIR_INTERVAL = 900000;
    
    public static final String CAUID = "CAUID";
    public static final String CAUIDDOMAIN = "CAUIDX";
    
    public static final String OWN_UID = "OWN_UID";
    public static final String OWN_SESSION_ID = "OWN_SESSION_ID";
    public static final String JOIN_KEY = "JOIN_KEY";

    public static final String CA_SERVICE_HOSTNAME = "ca_service";
    public static final String HTTP_SERVER_PROVIDER = "http.server.provider";
    public static final String HTTP_SERVER_IP = "http.server.ip";
    public static final String HTTP_SERVER_PORT = "http.server.port";
    public static final String HTTP_SERVER_THREADPOOL = "http.server.threadpool";
    public static final String HTTP_SERVER_RESOURCE = "http.server.resource";

    public static final String PARAM_SERVICE_CID = "ca.service.cid";
    public static final String PARAM_REGISTER_INTERVAL = "ca.register.interval";

    public static final String PARAM_APP_BASEDIR = "ca.app.basedir";
    public static final String PARAM_RECV_LOG_BASEDIR = "ca.receiver.log.basedir";
    public static final String PARAM_CLCT_LOG_BASEDIR = "ca.collector.log.basedir";

    public static final String PARAM_RECV_TRACE_SERVERIP = "ca.receiver.trace.serverip";
    public static final String PARAM_RECV_TRACE_SERVERPORT = "ca.receiver.trace.serverport";
    public static final String PARAM_RECV_TRACE_CONTEXT = "ca.receiver.trace.context";
    public static final String PARAM_RECV_VISIT_CONTEXT = "ca.receiver.visit.context";
    public static final String PARAM_RECV_TRACE_THREADPOOL = "ca.receiver.trace.threadpool";

    public static final String PARAM_RECV_MANG_SERVERIP = "ca.receiver.manage.serverip";
    public static final String PARAM_RECV_MANG_SERVERPORT = "ca.receiver.manage.serverport";
    public static final String PARAM_RECV_MANG_CONTEXT = "ca.receiver.manage.context";
    public static final String PARAM_RECV_DOWNLOAD_CONTEXT = "ca.receiver.download.context";
    public static final String PARAM_RECV_DOWNLOAD_THREADPOOL = "ca.receiver.download.threadpool";
    public static final String PARAM_RECV_MANG_THREADPOOL = "ca.receiver.manage.threadpool";

    public static final String PARAM_CTRL_SERVERIP = "ca.controller.serverip";
    public static final String PARAM_CTRL_SERVERPORT = "ca.controller.serverport";
    public static final String PARAM_CTRL_REG_CONTEXT = "ca.controller.register.context";
    public static final String PARAM_CTRL_MANG_CONTEXT = "ca.controller.manage.context";
    public static final String PARAM_CTRL_REPORT_CONTEXT = "ca.controller.report.context";
    public static final String PARAM_CTRL_EXPORT_CONTEXT = "ca.controller.export.context";
    public static final String PARAM_CTRL_API_CONTEXT = "ca.controller.api.context";
    public static final String PARAM_CTRL_THREADPOOL = "ca.controller.threadpool";

    public static final String PARAM_CLCT_SERVERIP = "ca.collector.serverip";
    public static final String PARAM_CLCT_SERVERPORT = "ca.collector.serverport";
    public static final String PARAM_CLCT_THREADPOOL = "ca.collector.threadpool";
    public static final String PARAM_CLCT_MANG_CONTEXT = "ca.collector.manage.context";
    public static final String PARAM_CLCT_DOWNLOAD_CONTEXT = "ca.collector.download.context";

    public static final String PARAM_ANLT_SERVERIP = "ca.analyst.serverip";
    public static final String PARAM_ANLT_SERVERPORT = "ca.analyst.serverport";
    public static final String PARAM_ANLT_THREADPOOL = "ca.analyst.threadpool";
    public static final String PARAM_ANLT_MANG_CONTEXT = "ca.analyst.manage.context";
    public static final String PARAM_ANLT_DOWNLOAD_CONTEXT = "ca.analyst.download.context";

    public static final String DEFAULT_RECV_TRACE_CONTEXT = "/recv/trace.do";
    public static final String DEFAULT_RECV_VISIT_CONTEXT = "/recv/visit.do";
    public static final String DEFAULT_RECV_MANG_CONTEXT = "/recv/manage.do";
    public static final String DEFAULT_RECV_DOWNLOAD_CONTEXT = "/recv/download.do";
    public static final String DEFAULT_CTRL_MANG_CONTEXT = "/ctrl/manage.do";
    public static final String DEFAULT_CTRL_REPORT_CONTEXT = "/ctrl/report.do";
    public static final String DEFAULT_CTRL_EXPORT_CONTEXT = "/ctrl/export.do";
    public static final String DEFAULT_CTRL_REG_CONTEXT = "/ctrl/register.do";
    public static final String DEFAULT_CLCT_MANG_CONTEXT = "/clct/manage.do";
    public static final String DEFAULT_CLCT_DOWNLOAD_CONTEXT = "/clct/download.do";
    public static final String DEFAULT_ANLT_MANG_CONTEXT = "/anlt/manage.do";
    public static final String DEFAULT_ANLT_DOWNLOAD_CONTEXT = "/anlt/download.do";
    public static final String DEFAULT_API_CONTEXT="/ctrl/api.do"; //add by yanjun

    public static final String LOG_ONLINE = "online";
    public static final String LOG_SWAP = "swap";
    public static final String LOG_OFFLINE = "offline";
    public static final String LOG_DOWNLOAD = "download";
    public static final String LOG_MERGE = "merge";
    public static String CAUID_COOKIE="";

    public static enum LogTypeEnum {
        ONLINE(0, LOG_ONLINE), SWAP(1, LOG_SWAP), OFFLINE(2, LOG_OFFLINE), DOWNLOAD(3, LOG_DOWNLOAD), MERGE(4,
                LOG_MERGE);

        private int index;
        private String name;

        private LogTypeEnum(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public String toString() {
            return name;
        }
    }

    public static final String REQUEST_NAME_RECVLOG = "recv";
    public static final String REQUEST_NAME_FILE = "file";

    public static final String REQUEST_NAME_COMP = "component";
    public static final String REQUEST_NAME_CID = "cid";
    public static final String REQUEST_NAME_GET = "get";
    public static final String REQUEST_NAME_VIEW = "view";
    public static final String REQUEST_NAME_SEARCH = "search";
    public static final String REQUEST_NAME_QUERY = "query";
    public static final String REQUEST_NAME_ADD = "add";
    public static final String REQUEST_NAME_ADD_TRACKURL = "addTrackUrl";
    public static final String REQUEST_NAME_LIST_TRACKURL = "listTrackUrl";
    public static final String REQUEST_NAME_DEL_TRACKURL = "delTrackUrl";
    public static final String REQUEST_NAME_GET_TRACKLOG = "getTrackLog";
    public static final String REQUEST_NAME_DO_HISTORY = "doKewordId";
    public static final String REQUEST_NAME_QUERYLEADS = "ql";
    public static final String REQUEST_NAME_DEL = "del";
    public static final String REQUEST_NAME_LIST = "list";
    public static final String REQUEST_IP_LIST = "iplist";
    public static final String REQUEST_NAME_ID = "id";
    public static final String REQUEST_NAME_IP = "ip";
    public static final String REQUEST_NAME_CAUID = "cauid";
    public static final String REQUEST_NAME_URL = "url";
    public static final String REQUEST_NAME_TRACKNAME = "trackname";
    public static final String REQUEST_NAME_LOGDATE = "logdate";
    public static final String REQUEST_NAME_URLMATCH = "match";//0,部分匹配，1，全部匹配
    
    public static final String REQUEST_NAME_NOTIFY = "notify";
    public static final String REQUEST_NAME_SYNC = "sync";
    public static final String REQUEST_NAME_REPORT = "cls";
    public static final String REQUEST_NAME_TIMERANGE = "timerange";
    public static final String REQUEST_NAME_GROUP = "group";
    public static final String REQUEST_NAME_OPT = "opt";
    public static final String REQUEST_NAME_OBJ = "obj";
    public static final String REQUEST_NAME_SITE = "site";
    public static final String REQUEST_NAME_TENANTID = "tenantid";
    public static final String REQUEST_NAME_Source = "source";
    
    public static final String REQUEST_VALUE_SUM = "sum";
    
    public static final String REQUEST_VALUE_ONLINE = "online";
    public static final String REQUEST_VALUE_OFFLINE = "offline";
    public static final String REQUEST_VALUE_SWAP = "swap";
    public static final String REQUEST_VALUE_DOWNLOAD = "download";
    public static final String REQUEST_VALUE_MERGE = "merge";
    public static final String REQUEST_VALUE_TYPE = "type";
    public static final String REQUEST_VALUE_SITE = "site";
    public static final String REQUEST_VALUE_CID = "cid";
    public static final String REQUEST_VALUE_DB = "db";
    public static final String REQUEST_VALUE_SITELIST =  "sitelist";

    public static final String PARAM_LOG_TARGET = "ca.acquisition.log.target";

    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_HEADER_CONTENTLEN = "Content-Length";
    public static final String HTTP_HEADER_CONTENTTYPE = "Content-Type";
    public static final String HTTP_HEADER_CONTENTENCODING = "Content-Encoding";
    public static final String HTTP_HEADDER_CONNECTION = "Connection";
    public static final String HTTP_HEADDER_XPATH = "x-Path";

    public static final String CONTENT_TYPE_XML = "text/xml";
    public static final String CONTENT_TYPE_PLAIN = "text/plain";
    public static final String CONTENT_TYPE_AJAX = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_TYPE_STREAM = "application/octet-stream";
    public static final String CONTENT_TYPE_GIF = "image/gif";
    public static final String CONTENT_TYPE_JPEG = "image/jpeg";

    public static final String PARAM_CA_REQUEST = "REQUEST";
    public static final String PARAM_CA_COOKIE = "COOKIE";
    public static final String PARAM_CA_REFERRE = "REFERRE";
    public static final String PARAM_CA_TIMESTAMP = "TIMESTAMP";
    public static final String PARAM_CA_BROWSER = "BROWSER";
    public static final String PARAM_CA_BODY = "BODY";
    public static final String PARAM_CA_CREATIVE = "CA_CREATIVE";

    public static final String LOG_FIELD_UID = "UID";
    public static final String LOG_FIELD_PTYPE = "PTYPE";
    public static final String LOG_FIELD_TENANT = "TENANT";
    public static final String LOG_FIELD_VIEWSITE = "VIEWSITE";
    public static final String LOG_FIELD_TIMERANGE = "TIMERANGE";
    public static final String LOG_FIELD_TIMESTAMP = "TIMESTAMP";
    public static final String LOG_FIELD_CLIENTIP = "CLIENTIP";
    public static final String LOG_FIELD_TERMINAL = "TERMINAL";
    public static final String LOG_FIELD_FROMPAGE = "FROMPAGE";
    public static final String LOG_FIELD_TOPAGE = "TOPAGE";
    public static final String LOG_FIELD_ACTION = "ACTION";
    public static final String LOG_FIELD_TRANSACTION = "TRANS";
    public static final String LOG_FIELD_TRACECODE = "TRACECODE";
    public static final String LOG_FIELD_COOKIE = "COOKIE";
    public static final String LOG_FIELD_SEARCHURL = "SEARCHURL";
    public static final String LOG_FIELD_SEARCHKW = "SEARCHKW";
    public static final String LOG_FIELD_SEARCHPKW = "SEARCHPKW";
    public static final String LOG_FIELD_BATCHNUM = "BATCHNUM";
    public static final String LOG_FIELD_ACTIONTIME = "ACTIONTIME";
    
    public static final String CA_FIELD_FTIME = "CA_FTIME";
    public static final String CA_FIELD_LTIME = "CA_LTIME";
    public static final String CA_FIELD_VTIME = "CA_VTIME";
    public static final String CA_FIELD_VISITOR = "CA_VISITOR";
    public static final String CA_FIELD_SESSION = "CA_SESSION";
    public static final String CA_FIELD_TENANTID = "CA_TENANTID";
    public static final String CA_FIELD_CASOURCE = "CA_SOURCE";
    public static final String CA_FIELD_CREATIVE = "CA_CREATIVE";
    public static final String CA_FIELD_MEDIUM = "CA_MEDIUM";
    public static final String CA_FIELD_CAMPAIGN = "CA_CAMPAIGN";
    public static final String CA_FIELD_GROUP = "CA_GROUP";
    public static final String CA_FIELD_KEYWORD = "CA_KEYWORD";
    public static final String CA_FIELD_SITE = "CA_SITE";
    public static final String CA_FIELD_AREA = "CA_AREA";
    public static final String CA_FIELD_POSITION = "CA_POSITION";
    
    public static final List<String[]> PVFieldMap;
    static {
        PVFieldMap = new ArrayList<String[]>();
        PVFieldMap.add(new String[]{LOG_FIELD_ACTIONTIME, "ActionTime"});
        PVFieldMap.add(new String[]{LOG_FIELD_TIMERANGE, "TimeRange"});
        PVFieldMap.add(new String[]{LOG_FIELD_UID, "UID"});
        PVFieldMap.add(new String[]{LOG_FIELD_TENANT, "TenantID"});
        PVFieldMap.add(new String[]{LOG_FIELD_TERMINAL, "Terminal"});
        PVFieldMap.add(new String[]{LOG_FIELD_CLIENTIP, "IP"});
        PVFieldMap.add(new String[]{CA_FIELD_AREA, "Area"});
        PVFieldMap.add(new String[]{LOG_FIELD_VIEWSITE, "Site"});
        PVFieldMap.add(new String[]{LOG_FIELD_SEARCHURL, "Source"});
        PVFieldMap.add(new String[]{LOG_FIELD_FROMPAGE, "PreviousPage"});
        PVFieldMap.add(new String[]{LOG_FIELD_TOPAGE, "CurrentPage"});
        //FieldMap.add(new String[]{"PageTitle"});
        PVFieldMap.add(new String[]{CA_FIELD_MEDIUM, "Medium"});
        PVFieldMap.add(new String[]{CA_FIELD_CREATIVE, "Creative"});
        PVFieldMap.add(new String[]{CA_FIELD_CAMPAIGN, "Campaign"});
        PVFieldMap.add(new String[]{CA_FIELD_GROUP, "GroupSet"});
        PVFieldMap.add(new String[]{CA_FIELD_KEYWORD, "Keyword"});
        PVFieldMap.add(new String[]{LOG_FIELD_SEARCHKW, "SearchKW"});
        PVFieldMap.add(new String[]{LOG_FIELD_SEARCHPKW, "SearchPKW"});
        PVFieldMap.add(new String[]{LOG_FIELD_PTYPE, "PageType"});
        PVFieldMap.add(new String[]{CA_FIELD_POSITION, "Position"});
        PVFieldMap.add(new String[]{LOG_FIELD_ACTION, "Action"});
        PVFieldMap.add(new String[]{CA_FIELD_FTIME, "FTime"});
        PVFieldMap.add(new String[]{CA_FIELD_LTIME, "LTime"});
        PVFieldMap.add(new String[]{CA_FIELD_VTIME, "VTime"});
        PVFieldMap.add(new String[]{CA_FIELD_VISITOR, "NewVisitor"});
        PVFieldMap.add(new String[]{CA_FIELD_SESSION, "NewSession"});
        PVFieldMap.add(new String[]{LOG_FIELD_TRANSACTION, "TransData"});
        PVFieldMap.add(new String[]{LOG_FIELD_BATCHNUM, "BatchNum"});
    }
}
