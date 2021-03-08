package com.kt289.util;

import java.applet.Applet;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class SignLink implements Runnable {

    public static int uid;
    public static int storeId = 32;
    public static RandomAccessFile cache_dat = null;
    public static final RandomAccessFile[] cache_idx = new RandomAccessFile[5];
    public static boolean sunjava;
    public static final Applet mainapp = null;
    private static boolean active;
    private static int threadliveid;
    private static InetAddress socketip;
    private static int socketreq;
    private static Socket socket = null;
    private static int threadreqpri = 1;
    private static Runnable threadreq = null;
    private static String dnsreq = null;
    public static String dns = null;
    private static String urlreq = null;
    private static DataInputStream urlstream = null;
    private static int savelen;
    private static String savereq = null;
    private static byte[] savebuf = null;
    private static boolean midiplay;
    private static int midipos;
    public static String midi = null;
    public static int midivol;
    public static int midifade;
    private static boolean waveplay;
    private static int wavepos;
    private static String wave = null;
    public static int wavevol;
    public static boolean reporterror = true;
    public static String errorname = "";

    private SignLink() {
    }

    public static void initiate(InetAddress inetaddress) {
        SignLink.threadliveid = (int) (Math.random() * 99999999D);
        if (SignLink.active) {
            try {
                Thread.sleep(500L);
            } catch (Exception exception) {
            }
            SignLink.active = false;
        }
        SignLink.socketreq = 0;
        SignLink.threadreq = null;
        SignLink.dnsreq = null;
        SignLink.savereq = null;
        SignLink.urlreq = null;
        SignLink.socketip = inetaddress;
        Thread thread = new Thread(new SignLink());
        thread.setDaemon(true);
        thread.start();
        while (!SignLink.active) {
            try {
                Thread.sleep(50L);
            } catch (Exception exception) {
            }
        }
    }

    private static String findCacheDirectory() {
        String[] locations = new String[]{"c:/windows/", "c:/winnt/", "d:/windows/", "d:/winnt/", "e:/windows/", "e:/winnt/", "f:/windows/", "f:/winnt/", "c:/", "~/", "/tmp/", ""};
        String fileName = ".file_store_32";

        for (int index = 0; index < locations.length; ++index) {
            try {
                String location = locations[index];
                File file;
                if (location.length() > 0) {
                    file = new File(location);
                    if (!file.exists()) {
                        continue;
                    }
                }

                file = new File(location + fileName);
                if (file.exists() || file.mkdir()) {
                    return location + fileName + "/";
                }
            } catch (Exception exception) {
            }
        }

        return null;
    }

    private static int getUid(String path) {
        try {
            File file = new File(path + "uid.dat");
            if (!file.exists() || file.length() < 4L) {
                DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(path + "uid.dat"));
                dataoutputstream.writeInt((int) (Math.random() * 99999999D));
                dataoutputstream.close();
            }
        } catch (Exception exception) {
        }
        try {
            DataInputStream stream = new DataInputStream(new FileInputStream(path + "uid.dat"));
            int hash = stream.readInt();
            stream.close();
            return hash + 1;
        } catch (Exception exception) {
            return 0;
        }
    }

    public static synchronized Socket openSocket(int port) throws IOException {
        for (SignLink.socketreq = port; SignLink.socketreq != 0; ) {
            try {
                Thread.sleep(50L);
            } catch (Exception exception) {
            }
        }
        if (SignLink.socket == null) {
            throw new IOException("could not open socket");
        } else {
            return SignLink.socket;
        }
    }

    public static synchronized DataInputStream openUrl(String url) throws IOException {
        for (SignLink.urlreq = url; SignLink.urlreq != null; ) {
            try {
                Thread.sleep(50L);
            } catch (Exception exception) {
            }
        }
        if (SignLink.urlstream == null) {
            throw new IOException("could not open: " + url);
        } else {
            return SignLink.urlstream;
        }
    }

    public static synchronized void dnsLookup(String s) {
        SignLink.dns = s;
        SignLink.dnsreq = s;
    }

    public static synchronized void startThread(Runnable runnable, int i) {
        SignLink.threadreqpri = i;
        SignLink.threadreq = runnable;
    }

    public static synchronized boolean requestWaveSave(byte[] abyte0, int i) {
        if (i > 0x1e8480) {
            return false;
        }
        if (SignLink.savereq != null) {
            return false;
        } else {
            SignLink.wavepos = (SignLink.wavepos + 1) % 5;
            SignLink.savelen = i;
            SignLink.savebuf = abyte0;
            SignLink.waveplay = true;
            SignLink.savereq = "sound" + SignLink.wavepos + ".wav";
            return true;
        }
    }

    public static synchronized boolean requestWaveReplay() {
        if (SignLink.savereq != null) {
            return false;
        } else {
            SignLink.savebuf = null;
            SignLink.waveplay = true;
            SignLink.savereq = "sound" + SignLink.wavepos + ".wav";
            return true;
        }
    }

    public static synchronized void saveMidi(byte[] abyte0, int i) {
        if (i > 0x1e8480) {
            return;
        }
        if (SignLink.savereq != null) {
        } else {
            SignLink.midipos = (SignLink.midipos + 1) % 5;
            SignLink.savelen = i;
            SignLink.savebuf = abyte0;
            SignLink.midiplay = true;
            SignLink.savereq = "jingle" + SignLink.midipos + ".mid";
        }
    }

    public static void error(String error) {
        if (!SignLink.reporterror) {
            return;
        }
        if (!SignLink.active) {
            return;
        }
        System.out.println("Error: " + error);
        try {
            error = error.replace(':', '_');
            error = error.replace('@', '_');
            error = error.replace('&', '_');
            error = error.replace('#', '_');
            DataInputStream stream = SignLink.openUrl("reporterror" + 289 + ".cgi?error=" + SignLink.errorname + " " + error);
            stream.readUTF();
            stream.close();
        } catch (IOException exception) {
        }
    }

    @Override
    public void run() {
        SignLink.active = true;
        String cacheDirectory = SignLink.findCacheDirectory();
        SignLink.uid = SignLink.getUid(cacheDirectory);
        try {
            File file = new File(cacheDirectory + "main_file_cache.dat");
            if (file.exists() && file.length() > 0x3200000L) {
                file.delete();
            }
            SignLink.cache_dat = new RandomAccessFile(cacheDirectory + "main_file_cache.dat", "rw");
            for (int index = 0; index < 5; index++) {
                SignLink.cache_idx[index] = new RandomAccessFile(cacheDirectory + "main_file_cache.idx" + index, "rw");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        for (int i = SignLink.threadliveid; SignLink.threadliveid == i; ) {
            if (SignLink.socketreq != 0) {
                try {
                    SignLink.socket = new Socket(SignLink.socketip, SignLink.socketreq);
                } catch (Exception _ex) {
                    SignLink.socket = null;
                }
                SignLink.socketreq = 0;
            } else if (SignLink.threadreq != null) {
                Thread thread = new Thread(SignLink.threadreq);
                thread.setDaemon(true);
                thread.start();
                thread.setPriority(SignLink.threadreqpri);
                SignLink.threadreq = null;
            } else if (SignLink.dnsreq != null) {
                try {
                    SignLink.dns = InetAddress.getByName(SignLink.dnsreq).getHostName();
                } catch (Exception _ex) {
                    SignLink.dns = "unknown";
                }
                SignLink.dnsreq = null;
            } else if (SignLink.savereq != null) {
                if (SignLink.savebuf != null) {
                    try {
                        FileOutputStream fileoutputstream = new FileOutputStream(cacheDirectory + SignLink.savereq);
                        fileoutputstream.write(SignLink.savebuf, 0, SignLink.savelen);
                        fileoutputstream.close();
                    } catch (Exception _ex) {
                    }
                }
                if (SignLink.waveplay) {
                    SignLink.wave = cacheDirectory + SignLink.savereq;
                    SignLink.waveplay = false;
                }
                if (SignLink.midiplay) {
                    SignLink.midi = cacheDirectory + SignLink.savereq;
                    SignLink.midiplay = false;
                }
                SignLink.savereq = null;
            } else if (SignLink.urlreq != null) {
                try {
                    SignLink.urlstream = new DataInputStream((new URL(SignLink.mainapp.getCodeBase(), SignLink.urlreq)).openStream());
                } catch (Exception _ex) {
                    SignLink.urlstream = null;
                }
                SignLink.urlreq = null;
            }
            try {
                Thread.sleep(50L);
            } catch (Exception exception) {
            }
        }
    }

}
