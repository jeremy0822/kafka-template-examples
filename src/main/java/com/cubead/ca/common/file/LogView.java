package com.cubead.ca.common.file;

import java.io.File;

public class LogView {
    private String fileName;
    private String filePath;
    private String siteName;
    private String timeRange;
    private long fileSize;
    private long lastModified;

    private LogView() {

    }

    public LogView(File file) {
        this.filePath = file.getAbsolutePath();
        this.fileName = file.getName();
        this.fileSize = file.length();
        this.lastModified = file.lastModified();

        String tmp[] = this.fileName.split("_");
        if (tmp.length >= 3) {
            this.siteName = tmp[tmp.length - 2];
            this.timeRange = tmp[tmp.length - 1].substring(0, tmp[tmp.length - 1].indexOf("."));
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileURL() {
        return filePath;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        sb.append("SiteName=").append(getSiteName());
        sb.append("&FilePath=").append(getFilePath());
        sb.append("&FileName=").append(getFileName());
        sb.append("&TimeRange=").append(getTimeRange());
        sb.append("&FileSize=").append(getFileSize());
        sb.append("&LastModified=").append(getLastModified());
        sb.append("\r\n");
        return null;
    }

    public static LogView decode(String data) {
        String[] fields = data.trim().split("&");
        if (fields.length < 5)
            return null;

        LogView view = new LogView();
        for (String field : fields) {
            String[] item = field.split("=");
            if (item.length != 2)
                return null;

            if (item[0].equals("SiteName"))
                view.siteName = item[1];
            else if (item[0].equals("FilePath"))
                view.filePath = item[1];
            else if (item[0].equals("FileName"))
                view.fileName = item[1];
            else if (item[0].equals("TimeRange"))
                view.timeRange = item[1];
            else if (item[0].equals("FileSize"))
                view.fileSize = Long.parseLong(item[1]);
            else if (item[0].equals("LastModified"))
                view.lastModified = Long.parseLong(item[1]);
        }
        return view;
    }
}
