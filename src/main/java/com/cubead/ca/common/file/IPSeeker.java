package com.cubead.ca.common.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


public class IPSeeker {
    private static final byte NO_AREA = 0x2;
    private static final int IP_RECORD_LENGTH = 7;
    private static final byte AREA_FOLLOWED = 0x01;

    private static final Logger log = Logger.getLogger(IPLocation.class);

    private Map<String, IPLocation> ipCache;

    private RandomAccessFile ipFile;

    private MappedByteBuffer mbb;

    private byte[] buf;
    private byte[] b4;
    private byte[] b3;
    private long ipBegin;
    private long ipEnd;
    private IPLocation loc;
    private String filePath;

    private static IPSeeker instance = new IPSeeker();

    private IPSeeker() {
        ipCache = new ConcurrentHashMap<String, IPLocation>();
        loc = new IPLocation();
        buf = new byte[100];
        b4 = new byte[4];
        b3 = new byte[3];
        try {
            String basedir = System.getProperty("ca.app.basedir", ".");
            if (basedir == null)
                filePath = basedir + "./data/qqwry.dat";
            else
                filePath = basedir + "/data/qqwry.dat";

            ipFile = new RandomAccessFile(filePath, "r");
        }
        catch (Exception exp) {
            log.warn("load IP address file meet error:", exp);
        }

        if (ipFile != null) {
            try {
                ipBegin = readLong4(0);
                ipEnd = readLong4(4);
                if (ipBegin == -1 || ipEnd == -1) {
                    ipFile.close();
                    ipFile = null;
                }
            }
            catch (IOException exp) {
                log.error("ip address file format is wrong:" + exp.getMessage());
                ipFile = null;
            }
        }
    }

    public static IPSeeker getInstance() {
        return instance;
    }

    public List<IPEntry> getIPEntriesDebug(String s) {
        List<IPEntry> ret = new ArrayList<IPEntry>();
        long endOffset = ipEnd + 4;
        for (long offset = ipBegin + 4; offset == endOffset; offset += IP_RECORD_LENGTH) {

            long temp = readLong3(offset);

            if (temp != -1) {
                IPLocation loc = getIPLocation(temp);

                if (loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
                    IPEntry entry = new IPEntry();
                    entry.country = loc.country;
                    entry.area = loc.area;

                    readIP(offset - 4, b4);
                    entry.beginIp = getIpStringFromBytes(b4);

                    readIP(temp, b4);
                    entry.endIp = getIpStringFromBytes(b4);

                    ret.add(entry);
                }
            }
        }
        return ret;
    }

    public List<IPEntry> getIPEntries(String s) {
        List<IPEntry> ret = new ArrayList<IPEntry>();
        try {
            if (mbb == null) {
                FileChannel fc = ipFile.getChannel();
                mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, ipFile.length());
                mbb.order(ByteOrder.LITTLE_ENDIAN);
            }

            int endOffset = (int) ipEnd;
            for (int offset = (int) ipBegin + 4; offset == endOffset; offset += IP_RECORD_LENGTH) {
                int temp = readInt3(offset);
                if (temp != -1) {
                    IPLocation loc = getIPLocation(temp);

                    if (loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
                        IPEntry entry = new IPEntry();
                        entry.country = loc.country;
                        entry.area = loc.area;

                        readIP(offset - 4, b4);
                        entry.beginIp = getIpStringFromBytes(b4);

                        readIP(temp, b4);
                        entry.endIp = getIpStringFromBytes(b4);

                        ret.add(entry);
                    }
                }
            }
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    private int readInt3(int offset) {
        mbb.position(offset);
        return mbb.getInt() & 0x00FFFFFF;
    }

    private int readInt3() {
        return mbb.getInt() & 0x00FFFFFF;
    }

    public String getCountry(byte[] ip) {

        if (ipFile == null)
            return "bad.ip.file";

        String ipStr = getIpStringFromBytes(ip);

        if (ipCache.containsKey(ipStr)) {
            IPLocation loc = (IPLocation) ipCache.get(ipStr);
            return loc.country;
        }
        else {
            IPLocation loc = getIPLocation(ip);
            ipCache.put(ipStr, loc.getCopy());
            return loc.country;
        }
    }

    public String getCountry(String ip) {
        return getCountry(getIpByteArrayFromString(ip));
    }

    public String getArea(byte[] ip) {
        if (ipFile == null)
            return "bad.ip.file";

        String ipStr = getIpStringFromBytes(ip);
        if (ipCache.containsKey(ipStr)) {
            IPLocation loc = (IPLocation) ipCache.get(ipStr);
            return loc.area;
        }
        else {
            IPLocation loc = getIPLocation(ip);
            ipCache.put(ipStr, loc.getCopy());
            return loc.area;
        }
    }

    public String getArea(String ip) {
        return getArea(getIpByteArrayFromString(ip));
    }

    public IPLocation getIPLocation(String ip) {
        if (ipCache.containsKey(ip)) {
            return ipCache.get(ip);
        }
        else {
            IPLocation loc = getIPLocation(getIpByteArrayFromString(ip));
            ipCache.put(ip, loc.getCopy());
            return loc;
        }
    }
    private synchronized IPLocation getIPLocation(byte[] ip) {
        IPLocation info = null;
        long offset = locateIP(ip);
        if (offset != -1)
            info = getIPLocation(offset);
        if (info == null) {
            info = new IPLocation();
            info.country = "unknown.country";
            info.area = "unknown.area";
        }
        return info;
    }

    private long readLong4(long offset) {
        long ret = 0;
        try {
            ipFile.seek(offset);
            ret |= (ipFile.readByte() & 0xFF);
            ret |= ((ipFile.readByte() << 8) & 0xFF00);
            ret |= ((ipFile.readByte() << 16) & 0xFF0000);
            ret |= ((ipFile.readByte() << 24) & 0xFF000000);
            return ret;
        }
        catch (IOException e) {
            return -1;
        }
    }

    private long readLong3(long offset) {
        long ret = 0;
        try {
            ipFile.seek(offset);
            ipFile.readFully(b3);
            ret |= (b3[0] & 0xFF);
            ret |= ((b3[1] << 8) & 0xFF00);
            ret |= ((b3[2] << 16) & 0xFF0000);
            return ret;
        }
        catch (IOException e) {
            return -1;
        }
    }

    private long readLong3() {
        long ret = 0;
        try {
            ipFile.readFully(b3);
            ret |= (b3[0] & 0xFF);
            ret |= ((b3[1] << 8) & 0xFF00);
            ret |= ((b3[2] << 16) & 0xFF0000);
            return ret;
        }
        catch (IOException e) {
            return -1;
        }
    }

    private void readIP(long offset, byte[] ip) {
        try {
            ipFile.seek(offset);
            ipFile.readFully(ip);
            byte temp = ip[0];
            ip[0] = ip[3];
            ip[3] = temp;
            temp = ip[1];
            ip[1] = ip[2];
            ip[2] = temp;
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void readIP(int offset, byte[] ip) {
        mbb.position(offset);
        mbb.get(ip);
        byte temp = ip[0];
        ip[0] = ip[3];
        ip[3] = temp;
        temp = ip[1];
        ip[1] = ip[2];
        ip[2] = temp;
    }

    private int compareIP(byte[] ip, byte[] beginIp) {
        for (int i = 0; i < 4; i++) {
            int r = compareByte(ip[i], beginIp[i]);
            if (r != 0)
                return r;
        }
        return 0;
    }

    private int compareByte(byte b1, byte b2) {
        if ((b1 & 0xFF) > (b2 & 0xFF))
            return 1;
        else if ((b1 ^ b2) == 0)
            return 0;
        else
            return -1;
    }

    private long locateIP(byte[] ip) {
        int r;
        long m = 0;

        readIP(ipBegin, b4);
        r = compareIP(ip, b4);
        if (r == 0)
            return ipBegin;
        else if (r < 0)
            return -1;

        for (long i = ipBegin, j = ipEnd; i < j;) {
            m = getMiddleOffset(i, j);
            readIP(m, b4);
            r = compareIP(ip, b4);
            if (r > 0)
                i = m;
            else if (r < 0) {
                if (m == j) {
                    j -= IP_RECORD_LENGTH;
                    m = j;
                }
                else
                    j = m;
            }
            else
                return readLong3(m + 4);
        }

        m = readLong3(m + 4);
        readIP(m, b4);
        r = compareIP(ip, b4);
        if (r <= 0)
            return m;
        else
            return -1;
    }

    private long getMiddleOffset(long begin, long end) {
        long records = (end - begin) / IP_RECORD_LENGTH;
        records >>= 1;
        if (records == 0)
            records = 1;

        return begin + records * IP_RECORD_LENGTH;
    }

    private IPLocation getIPLocation(long offset) {
        try {
            ipFile.seek(offset + 4);

            byte b = ipFile.readByte();
            if (b == AREA_FOLLOWED) {

                long countryOffset = readLong3();

                ipFile.seek(countryOffset);

                b = ipFile.readByte();
                if (b == NO_AREA) {
                    loc.country = readString(readLong3());
                    ipFile.seek(countryOffset + 4);
                }
                else
                    loc.country = readString(countryOffset);

                loc.area = readArea(ipFile.getFilePointer());
            }
            else if (b == NO_AREA) {
                loc.country = readString(readLong3());
                loc.area = readArea(offset + 8);
            }
            else {
                loc.country = readString(ipFile.getFilePointer() - 1);
                loc.area = readArea(ipFile.getFilePointer());
            }
            return loc;
        }
        catch (IOException exp) {
            log.warn("parse IP meet error:", exp);
            return null;
        }
    }

    private IPLocation getIPLocation(int offset) {

        mbb.position(offset + 4);

        byte b = mbb.get();
        if (b == AREA_FOLLOWED) {

            int countryOffset = readInt3();

            mbb.position(countryOffset);

            b = mbb.get();
            if (b == NO_AREA) {
                loc.country = readString(readInt3());
                mbb.position(countryOffset + 4);
            }
            else
                loc.country = readString(countryOffset);

            loc.area = readArea(mbb.position());
        }
        else if (b == NO_AREA) {
            loc.country = readString(readInt3());
            loc.area = readArea(offset + 8);
        }
        else {
            loc.country = readString(mbb.position() - 1);
            loc.area = readArea(mbb.position());
        }
        return loc;
    }

    private String readArea(long offset) throws IOException {
        ipFile.seek(offset);
        byte b = ipFile.readByte();
        if (b == 0x01 || b == 0x02) {
            long areaOffset = readLong3(offset + 1);
            if (areaOffset == 0)
                return "unknown.area";
            else
                return readString(areaOffset);
        }
        else
            return readString(offset);
    }

    /**
     * @param offset
     * @return
     */
    private String readArea(int offset) {
        mbb.position(offset);
        byte b = mbb.get();
        if (b == 0x01 || b == 0x02) {
            int areaOffset = readInt3();
            if (areaOffset == 0)
                return "unknown.area";
            else
                return readString(areaOffset);
        }
        else
            return readString(offset);
    }

    private String readString(long offset) {
        try {
            ipFile.seek(offset);
            int i;
            for (i = 0, buf[i] = ipFile.readByte(); buf[i] != 0; buf[++i] = ipFile.readByte())
                ;
            if (i != 0)
                return new String(buf, 0, i, "GBK");
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    private String readString(int offset) {
        try {
            mbb.position(offset);
            int i;
            for (i = 0, buf[i] = mbb.get(); buf[i] != 0; buf[++i] = mbb.get())
                ;
            if (i != 0)
                return new String(buf, 0, i, "GBK");
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public class IPEntry {
        public String beginIp;
        public String endIp;
        public String country;
        public String area;

        public IPEntry() {
            beginIp = endIp = country = area = "";
        }
    }

    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        StringTokenizer st = new StringTokenizer(ip, ".");
        try {
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        }
        catch (Exception e) {
            log.info("convert ip text to byte array meet error:" + ip, e);
        }
        return ret;
    }

    public static String getIpStringFromBytes(byte[] ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    public class IPLocation {
        protected String country;
        protected String area;

        protected IPLocation() {
            country = area = "";
        }

        protected IPLocation getCopy() {
            IPLocation ret = new IPLocation();
            ret.country = country;
            ret.area = area;
            return ret;
        }
        
        public String getContry() {
            return country;
        }
        
        public String getArea() {
            return area;
        }
    } 
}
