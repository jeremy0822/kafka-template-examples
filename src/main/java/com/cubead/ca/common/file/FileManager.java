package com.cubead.ca.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;

import com.cubead.ca.common.util.CommonFunc;
import com.cubead.ca.common.util.Constant.LogTypeEnum;

public class FileManager {
    private String baseDir;
    private IWriter writer;
    public static final int DAY_ONE = 24 * 3600000;
    public static final int DAY_THIRD = 3 * DAY_ONE;

    private static final Logger log = Logger.getLogger(FileManager.class);

    public FileManager(String baseDir) {
        this(baseDir, new DefaultWriter());
    }

    public FileManager(String baseDir, IWriter writer) {
        this.writer = writer;
        this.baseDir = baseDir;
    }

    /**
     * make log file path base on log type
     * 
     * @param logType
     * @return
     */
    public String getPath(LogTypeEnum logType) {
        return baseDir + File.separator + logType.toString() + File.separator;
    }

    /**
     * Find file from file system directory
     * 
     * @param filename
     *            that is only filename, DON'T include path
     * @param logType
     *            indicate log file type, system will make path base on it.
     * @return File object or null if file doesn't exist
     */
    public File getFile(String filename, LogTypeEnum logType) {
        File file = new File(baseDir + File.separator + logType.toString() + File.separator + filename);
        if (file.exists())
            return file;
        else
            return null;
    }

    /**
     * Find files under file system special directory
     * 
     * @param logType
     *            indicate log file type, system will make path base on it.
     * @return null or files array
     */
    public File[] getFileList(LogTypeEnum logType) {
        File file = new File(baseDir + File.separator + logType.toString());
        if (file == null || file.isDirectory() == false)
            return null;
        else
            return file.listFiles();
    }

    public File[] findFile(String name) {
        List<File> list = new ArrayList<File>(4);
        File file = new File(getPath(LogTypeEnum.ONLINE) + name);
        if (file.exists())
            list.add(file);

        file = new File(getPath(LogTypeEnum.OFFLINE) + name);
        if (file.exists())
            list.add(file);

        file = new File(getPath(LogTypeEnum.SWAP) + name);
        if (file.exists())
            list.add(file);

        return list.toArray(new File[] {});
    }

    /**
     * check file last modified time in source directory, found out expired time. if delete flag is true, delete them,
     * otherwise move file to destination directory then delete them from source directory
     * 
     * @param srcPath
     * @param destPath
     * @param expiredTime
     *            unit is millisecond
     * @param del
     *            if flag is true, expired file will be deleted directly, don't move to target path
     */
    public void moveExpiredFile(String srcPath, String destPath, int expiredTime, boolean del) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // move expired file to special directory
        File file = new File(srcPath);
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File one : files) {
                if (one.lastModified() + expiredTime < c.getTimeInMillis()) {
                    if (del)
                        one.delete();
                    else
                        moveFile(one.getAbsolutePath(), destPath + one.getName());
                }
            }
        }
    }

    public static File[] filterFiles(String path, String pattern) {
        File file = new File(path);
        if (file.exists())
            return file.listFiles(new MatchFile(pattern));
        else
            return null;
    }

    /**
     * move file from source path to destination path, if destination path has same name file, delete it first
     * 
     * @param srcPath
     * @param destPath
     * @return
     */
    @SuppressWarnings("resource")
	public boolean moveFile(String srcPath, String destPath) {
        if (srcPath == null || destPath == null)
            return false;

        File srcFile = new File(srcPath);
        FileChannel in = null;
        FileChannel out = null;
        try {
            if (srcFile.exists() == false)
                return false;

            File destFile = new File(destPath);
            if (destFile.exists())
                destFile.delete();

            if (srcFile.renameTo(destFile) == false) {
                destFile.createNewFile();
                in = new FileInputStream(srcFile).getChannel();
                out = new FileOutputStream(destFile).getChannel();
                ByteBuffer buff = ByteBuffer.allocate(512 * 1024);
                while (in.read(buff) > 0) {
                    buff.flip();
                    out.write(buff);
                    buff.clear();
                }
            }
        }
        catch (Exception exp) {
            log.warn("move file meet error:", exp);
            return false;
        }
        finally {
            CommonFunc.closeIO(in);
            CommonFunc.closeIO(out);
        }

        srcFile.delete();
        return true;
    }

    /**
     * create compress file, extension name is gzip
     * 
     * @param srcPath
     * @param destPath
     */
    public void compressFile(String srcPath, String destPath) {
        BufferedInputStream in = null;
        GZIPOutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(srcPath));
            out = new GZIPOutputStream(new FileOutputStream(destPath), 1280 * 1024);
            byte[] buff = new byte[1024 * 1024];
            int len = 0;
            while ((len = in.read(buff)) > 0)
                out.write(buff, 0, len);
            out.finish();
            out.flush();
        }
        catch (Exception exp) {
            log.warn("gzip file meet error:" + exp.getMessage());
        }
        finally {
            CommonFunc.closeIO(in);
            CommonFunc.closeIO(out);
        }
    }

    /**
     * write data to file
     * 
     * @param filename
     *            include path
     * @param data
     *            will be changed to byte array by using utf-8 charset
     * @throws IOException
     */
    public void write(String filename, String data) throws IOException {
        writer.write(filename, data.getBytes("utf-8"));
    }

    private static class MatchFile implements FileFilter {
        private String pattern;

        public MatchFile(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public boolean accept(File file) {
            return file.getName().matches(pattern);
        }
    }

    private static class DefaultWriter implements IWriter {
        @Override
        public void write(File file, byte[] data) throws IOException {
            if (file == null)
                throw new IOException(" file object is null");

            OutputStream out = null;
            try {
                if (file.exists() == false)
                    file.createNewFile();

                out = new BufferedOutputStream(new FileOutputStream(file, true));
                out.write(data);
                out.flush();
            }
            catch (Exception exp) {
                log.warn(" meet error when write data to file " + file.getName() + " because " + exp.getMessage());
                throw new IOException(exp);
            }
            finally {
                CommonFunc.closeIO(out);
            }
        }

        @Override
        public void write(String filename, byte[] data) throws IOException {
            write(new File(filename), data);
        }
    }
}
