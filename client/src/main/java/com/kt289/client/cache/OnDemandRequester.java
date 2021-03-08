package com.kt289.client.cache;

import com.kt289.util.buffer.Buffer;
import com.kt289.client.Client;
import com.kt289.util.SignLink;
import com.kt289.util.aggregation.LinkedList;
import com.kt289.util.aggregation.Queue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;

public class OnDemandRequester extends Requester implements Runnable {

    private final int[][] versions;
    private final int[][] crcs;
    private final byte[][] filePriorities;
    private int highestPriority;
    private byte[] modelIndices;
    private int[] mapIndices1;
    private int[] mapIndices2;
    private int[] mapIndices3;
    private int[] mapIndices4;
    private int[] frames;
    private int[] musicPriorities;
    private boolean running;
    private Client clientInstance;
    private final CRC32 crc32;
    private boolean waiting;
    private int uncompletedCount;
    private int completedCount;
    private final Queue nodeSubList;
    private final LinkedList mandatoryRequests;
    private final LinkedList unrequested;
    private final LinkedList requested;
    private final LinkedList complete;
    private final LinkedList passiveRequests;
    public String status;
    private int filesLoaded;
    private int totalFiles;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    public int failedRequests;
    private OnDemandNode current;
    private int completedSize;
    private int expectedSize;
    private final byte[] payload;
    private final byte[] aByteArray1318;
    private int loopCycle;
    private int writeLoopCycle;
    private long lastRequestTime;
    public int onDemandCycle;

    public OnDemandRequester() {
        versions = new int[4][];
        crcs = new int[4][];
        filePriorities = new byte[4][];
        running = true;
        crc32 = new CRC32();
        waiting = false;
        nodeSubList = new Queue();
        mandatoryRequests = new LinkedList();
        unrequested = new LinkedList();
        requested = new LinkedList();
        complete = new LinkedList();
        passiveRequests = new LinkedList();
        status = "";
        payload = new byte[500];
        aByteArray1318 = new byte[65000];
    }

    public void start(Archive loader, Client client) {
        String[] versions = {"model_version", "anim_version", "midi_version", "map_version"};
        for (int index = 0; index < 4; index++) {
            byte[] decompressed = loader.decompressFile(versions[index]);
            int length = decompressed.length / 2;
            Buffer buffer = new Buffer(decompressed);
            this.versions[index] = new int[length];
            filePriorities[index] = new byte[length];
            for (int part = 0; part < length; part++) {
                this.versions[index][part] = buffer.readUnsignedShort();
            }
        }
        String[] crcs = {"model_crc", "anim_crc", "midi_crc", "map_crc"};
        for (int index = 0; index < 4; index++) {
            byte[] decompressed = loader.decompressFile(crcs[index]);
            int length = decompressed.length / 4;
            Buffer buffer = new Buffer(decompressed);
            this.crcs[index] = new int[length];
            for (int part = 0; part < length; part++) {
                this.crcs[index][part] = buffer.readUnsignedInt();
            }
        }
        byte[] indices = loader.decompressFile("model_index");
        int length = this.versions[0].length;
        this.modelIndices = new byte[length];
        for (int part = 0; part < length; part++) {
            if (part < indices.length) {
                this.modelIndices[part] = indices[part];
            } else {
                this.modelIndices[part] = 0;
            }
        }
        indices = loader.decompressFile("map_index");
        Buffer buffer = new Buffer(indices);
        length = indices.length / 7;
        mapIndices1 = new int[length];
        mapIndices2 = new int[length];
        mapIndices3 = new int[length];
        mapIndices4 = new int[length];
        for (int part = 0; part < length; part++) {
            mapIndices1[part] = buffer.readUnsignedShort();
            mapIndices2[part] = buffer.readUnsignedShort();
            mapIndices3[part] = buffer.readUnsignedShort();
            mapIndices4[part] = buffer.readUnsignedByte();
        }
        indices = loader.decompressFile("anim_index");
        buffer = new Buffer(indices);
        length = indices.length / 2;
        frames = new int[length];
        for (int part = 0; part < length; part++) {
            frames[part] = buffer.readUnsignedShort();
        }
        indices = loader.decompressFile("midi_index");
        buffer = new Buffer(indices);
        length = indices.length;
        musicPriorities = new int[length];
        for (int k2 = 0; k2 < length; k2++) {
            musicPriorities[k2] = buffer.readUnsignedByte();
        }
        clientInstance = client;
        running = true;
        clientInstance.startRunnable(this, 2);
    }

    public void disable() {
        running = false;
    }

    public int getFileCount(int index) {
        return versions[index].length;
    }

    public int getAnimationCount() {
        return frames.length;
    }

    public int getMapId(int mapY, int mapX, int type) {
        int coord = (mapX << 8) + mapY;
        for (int indice = 0; indice < mapIndices1.length; indice++) {
            if (mapIndices1[indice] == coord) {
                if (type == 0) {
                    return mapIndices2[indice];
                } else {
                    return mapIndices3[indice];
                }
            }
        }
        return -1;
    }

    public void preloadRegions(boolean force) {
        int length = mapIndices1.length;
        for (int index = 0; index < length; index++) {
            if (force || mapIndices4[index] != 0) {
                setPriority(3, (byte) 2, mapIndices3[index]);
                setPriority(3, (byte) 2, mapIndices2[index]);
            }
        }
    }

    public boolean method389(int i) {
        for (int index = 0; index < mapIndices1.length; index++) {
            if (mapIndices3[index] == i) {
                return true;
            }
        }
        return false;
    }

    public int getModelId(int i) {
        return modelIndices[i] & 0xff;
    }

    public boolean isMusicPriority(int i) {
        return musicPriorities[i] == 1;
    }

    @Override
    public void request(int index) {
        request(0, index);
    }

    public void request(int type, int index) {
        if (type < 0 || type > versions.length || index < 0 || index > versions[type].length) {
            return;
        }
        if (versions[type][index] == 0) {
            return;
        }
        synchronized (nodeSubList) {
            for (OnDemandNode onDemandNode = (OnDemandNode) nodeSubList.peek(); onDemandNode != null; onDemandNode = (OnDemandNode) nodeSubList.getNext()) {
                if (onDemandNode.dataType == type && onDemandNode.index == index) {
                    return;
                }
            }
            OnDemandNode onDemandNode = new OnDemandNode();
            onDemandNode.dataType = type;
            onDemandNode.index = index;
            onDemandNode.incomplete = true;
            synchronized (mandatoryRequests) {
                mandatoryRequests.pushBack(onDemandNode);
            }
            nodeSubList.push(onDemandNode);
        }
    }

    public int getImmediateRequestCount() {
        synchronized (nodeSubList) {
            return nodeSubList.getSize();
        }
    }

    public OnDemandNode getNext() {
        OnDemandNode onDemandNode;
        synchronized (complete) {
            onDemandNode = (OnDemandNode) complete.pop();
        }
        if (onDemandNode == null) {
            return null;
        }
        synchronized (nodeSubList) {
            onDemandNode.removeCacheable();
        }
        if (onDemandNode.buffer == null) {
            return onDemandNode;
        }
        int offset = 0;
        try {
            GZIPInputStream gzipinputstream = new GZIPInputStream(new ByteArrayInputStream(onDemandNode.buffer));
            do {
                if (offset == aByteArray1318.length) {
                    throw new RuntimeException("buffer overflow!");
                }
                int read = gzipinputstream.read(aByteArray1318, offset, aByteArray1318.length - offset);
                if (read == -1) {
                    break;
                }
                offset += read;
            } while (true);
        } catch (IOException exception) {
            throw new RuntimeException("error unzipping");
        }
        onDemandNode.buffer = new byte[offset];
        for (int index = 0; index < offset; index++) {
            onDemandNode.buffer[index] = aByteArray1318[index];
        }
        return onDemandNode;
    }

    public void setPriority(int id, byte data, int index) {
        if (clientInstance.caches[0] == null) {
            return;
        }
        if (versions[id][index] == 0) {
            return;
        }
        byte[] decompress = clientInstance.caches[id + 1].decompress(index);
        if (crcMatches(decompress, versions[id][index], crcs[id][index])) {
            return;
        }
        filePriorities[id][index] = data;
        if (data > highestPriority) {
            highestPriority = data;
        }
        totalFiles++;
    }

    public void clearPassiveRequests() {
        synchronized (passiveRequests) {
            passiveRequests.clear();
        }
    }

    public void passiveRequest(int index, int type) {
        if (clientInstance.caches[0] == null) {
            return;
        }
        if (versions[type][index] == 0) {
            return;
        }
        if (filePriorities[type][index] == 0) {
            return;
        }
        if (highestPriority == 0) {
            return;
        }
        OnDemandNode onDemandNode = new OnDemandNode();
        onDemandNode.dataType = type;
        onDemandNode.index = index;
        onDemandNode.incomplete = false;
        synchronized (passiveRequests) {
            passiveRequests.pushBack(onDemandNode);
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                onDemandCycle++;
                int delay = 20;
                if (highestPriority == 0 && clientInstance.caches[0] != null) {
                    delay = 50;
                }
                try {
                    Thread.sleep(delay);
                } catch (Exception exception) {
                }
                waiting = true;
                for (int j = 0; j < 100; j++) {
                    if (!waiting) {
                        break;
                    }
                    waiting = false;
                    checkReceived();
                    handleFailed((byte) -3);
                    if (uncompletedCount == 0 && j >= 5) {
                        break;
                    }
                    loadExtra(0);
                    if (inputStream != null) {
                        read();
                    }
                }
                boolean requested = false;
                for (OnDemandNode onDemandNode = (OnDemandNode) this.requested.peekFront(); onDemandNode != null; onDemandNode = (OnDemandNode) this.requested.getNext()) {
                    if (onDemandNode.incomplete) {
                        requested = true;
                        onDemandNode.loopCycle++;
                        if (onDemandNode.loopCycle > 50) {
                            onDemandNode.loopCycle = 0;
                            closeRequest(onDemandNode, 409);
                        }
                    }
                }
                if (!requested) {
                    for (OnDemandNode onDemandNode = (OnDemandNode) this.requested.peekFront(); onDemandNode != null; onDemandNode = (OnDemandNode) this.requested.getNext()) {
                        requested = true;
                        onDemandNode.loopCycle++;
                        if (onDemandNode.loopCycle > 50) {
                            onDemandNode.loopCycle = 0;
                            closeRequest(onDemandNode, 409);
                        }
                    }
                }
                if (requested) {
                    loopCycle++;
                    if (loopCycle > 750) {
                        try {
                            socket.close();
                        } catch (Exception exception) {
                        }
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        expectedSize = 0;
                    }
                } else {
                    loopCycle = 0;
                    status = "";
                }
                if (clientInstance.loggedIn && socket != null && outputStream != null && (highestPriority > 0 || clientInstance.caches[0] == null)) {
                    writeLoopCycle++;
                    if (writeLoopCycle > 500) {
                        writeLoopCycle = 0;
                        payload[0] = 0;
                        payload[1] = 0;
                        payload[2] = 0;
                        payload[3] = 10;
                        try {
                            outputStream.write(payload, 0, 4);
                        } catch (IOException _ex) {
                            loopCycle = 5000;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            SignLink.error("od_ex " + exception.getMessage());
        }
    }

    private void checkReceived() {
        OnDemandNode request;
        synchronized (mandatoryRequests) {
            request = (OnDemandNode) mandatoryRequests.pop();
        }
        while (request != null) {
            waiting = true;
            byte[] data = null;
            if (clientInstance.caches[0] != null) {
                data = clientInstance.caches[request.dataType + 1].decompress(request.index);
            }
            if (!crcMatches(data, versions[request.dataType][request.index], crcs[request.dataType][request.index])) {
                data = null;
            }
            synchronized (mandatoryRequests) {
                if (data == null) {
                    unrequested.pushBack(request);
                } else {
                    request.buffer = data;
                    synchronized (complete) {
                        complete.pushBack(request);
                    }
                }
                request = (OnDemandNode) mandatoryRequests.pop();
            }
        }
    }

    private void handleFailed(byte byte0) {
        uncompletedCount = 0;
        completedCount = 0;
        for (OnDemandNode onDemandNode = (OnDemandNode) requested.peekFront(); onDemandNode != null; onDemandNode = (OnDemandNode) requested.getNext()) {
            if (onDemandNode.incomplete) {
                uncompletedCount++;
            } else {
                completedCount++;
            }
        }
        while (uncompletedCount < 10) {
            OnDemandNode onDemandNode = (OnDemandNode) unrequested.pop();
            if (onDemandNode == null) {
                break;
            }
            if (filePriorities[onDemandNode.dataType][onDemandNode.index] != 0) {
                filesLoaded++;
            }
            filePriorities[onDemandNode.dataType][onDemandNode.index] = 0;
            requested.pushBack(onDemandNode);
            uncompletedCount++;
            closeRequest(onDemandNode, 409);
            waiting = true;
        }
    }

    private void loadExtra(int i) {
        uncomplete:
        while (true) {
            while (true) {
                if (uncompletedCount != 0) {
                    break uncomplete;
                }
                if (completedCount >= 10) {
                    break;
                }
                if (highestPriority == 0) {
                    break;
                }
                OnDemandNode onDemandNode;
                synchronized (passiveRequests) {
                    onDemandNode = (OnDemandNode) passiveRequests.pop();
                }
                while (onDemandNode != null) {
                    if (filePriorities[onDemandNode.dataType][onDemandNode.index] != 0) {
                        filePriorities[onDemandNode.dataType][onDemandNode.index] = (byte) 0;
                        requested.pushBack(onDemandNode);
                        closeRequest(onDemandNode, 409);
                        waiting = true;
                        if (filesLoaded < totalFiles) {
                            filesLoaded++;
                        }
                        status = "Loading extra files - " + (filesLoaded * 100 / totalFiles) + "%";
                        completedCount++;
                        if (completedCount == 10) {
                            return;
                        }
                    }
                    synchronized (passiveRequests) {
                        onDemandNode = ((OnDemandNode) passiveRequests.pop());
                    }
                }
                for (int type = 0; type < 4; type++) {
                    byte[] data = filePriorities[type];
                    int length = data.length;
                    for (int index = 0; index < length; index++) {
                        if (data[index] == highestPriority) {
                            data[index] = (byte) 0;
                            onDemandNode = new OnDemandNode();
                            onDemandNode.dataType = type;
                            onDemandNode.index = index;
                            onDemandNode.incomplete = false;
                            requested.pushBack(onDemandNode);
                            closeRequest(onDemandNode, 409);
                            waiting = true;
                            if (filesLoaded < totalFiles) {
                                filesLoaded++;
                            }
                            status = "Loading extra files - " + (filesLoaded * 100 / totalFiles) + "%";
                            completedCount++;
                            if (completedCount == 10) {
                                return;
                            }
                        }
                    }
                }
                highestPriority--;
            }
            break;
        }
    }

    private void read() {
        try {
            int available = inputStream.available();
            if (expectedSize == 0 && available >= 6) {
                waiting = true;
                for (int offset = 0; offset < 6; offset += inputStream.read(payload, offset, 6 - offset)) {
                }
                int dataType = payload[0] & 0xff;
                int index = ((payload[1] & 0xff) << 8) + (payload[2] & 0xff);
                int r = ((payload[3] & 0xff) << 8) + (payload[4] & 0xff);
                int header = payload[5] & 0xff;

                current = null;
                for (OnDemandNode onDemandNode = (OnDemandNode) requested.peekFront(); onDemandNode != null; onDemandNode = (OnDemandNode) requested.getNext()) {
                    if (onDemandNode.dataType == dataType && onDemandNode.index == index) {
                        current = onDemandNode;
                    }
                    if (current != null) {
                        onDemandNode.loopCycle = 0;
                    }
                }
                if (current != null) {
                    loopCycle = 0;
                    if (r == 0) {
                        SignLink.error("Rej: " + dataType + "," + index);
                        current.buffer = null;
                        if (current.incomplete) {
                            synchronized (complete) {
                                complete.pushBack(current);
                            }
                        } else {
                            current.remove();
                        }
                        current = null;
                    } else {
                        if (current.buffer == null && header == 0) {
                            current.buffer = new byte[r];
                        }
                        if (current.buffer == null && header != 0) {
                            throw new IOException("missing start of file");
                        }
                    }
                }
                completedSize = header * 500;
                expectedSize = 500;
                if (expectedSize > r - header * 500) {
                    expectedSize = r - header * 500;
                }
            }
            if (expectedSize > 0 && available >= expectedSize) {
                waiting = true;
                byte[] payload = this.payload;
                int size = 0;
                if (current != null) {
                    payload = current.buffer;
                    size = completedSize;
                }
                for (int offset = 0; offset < expectedSize; offset += inputStream.read(payload, offset + size, expectedSize - offset)) {
                }
                if (expectedSize + completedSize >= payload.length && current != null) {
                    if (clientInstance.caches[0] != null) {
                        clientInstance.caches[current.dataType + 1].put(payload, payload.length, current.index);
                    }
                    if (!current.incomplete && current.dataType == 3) {
                        current.incomplete = true;
                        current.dataType = 93;
                    }
                    if (current.incomplete) {
                        synchronized (complete) {
                            complete.pushBack(current);
                        }
                    } else {
                        current.remove();
                    }
                }
                expectedSize = 0;
            }
        } catch (IOException ioexception) {
            try {
                socket.close();
            } catch (Exception exception) {
            }
            socket = null;
            inputStream = null;
            outputStream = null;
            expectedSize = 0;
        }
    }

    private boolean crcMatches(byte[] data, int cacheVersion, int cacheChecksum) {
        if (data == null || data.length < 2) {
            return false;
        }
        int length = data.length - 2;
        int version = ((data[length] & 0xff) << 8) + (data[length + 1] & 0xff);
        crc32.reset();
        crc32.update(data, 0, length);
        int checksum = (int) crc32.getValue();
        if (version != cacheVersion) {
            return false;
        }
        return checksum == cacheChecksum;
    }

    private void closeRequest(OnDemandNode request, int i) {
        try {
            if (socket == null) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastRequestTime < 4000L) {
                    return;
                }
                lastRequestTime = currentTime;
                socket = clientInstance.openSocket(43594 + Client.portOffset);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                outputStream.write(15);
                for (int j = 0; j < 8; j++) {
                    inputStream.read();
                }
                loopCycle = 0;
            }
            payload[0] = (byte) request.dataType;
            payload[1] = (byte) (request.index >> 8);
            payload[2] = (byte) request.index;
            if (request.incomplete) {
                payload[3] = 2;
            } else if (!clientInstance.loggedIn) {
                payload[3] = 1;
            } else {
                payload[3] = 0;
            }
            outputStream.write(payload, 0, 4);
            writeLoopCycle = 0;
            failedRequests = -10000;
            return;
        } catch (IOException ioexception) {
        }
        try {
            socket.close();
        } catch (Exception exception) {
        }
        socket = null;
        inputStream = null;
        outputStream = null;
        expectedSize = 0;
        failedRequests++;
    }
}
