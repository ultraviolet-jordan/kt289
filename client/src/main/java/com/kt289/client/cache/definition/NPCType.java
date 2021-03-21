package com.kt289.client.cache.definition;

import com.kt289.cache.Archive;
import com.kt289.client.render.Animation;
import com.kt289.util.buffer.Buffer;
import com.kt289.client.render.Model;
import com.kt289.util.aggregation.Cache;

public class NPCType {

    private static int[] bufferOffsets;
    private static Buffer buffer;
    private static NPCType[] cache;
    private static int bufferIndex;
    public static Cache modelCache = new Cache(30);
    public long id;
    public String name;
    public byte[] description;
    public byte boundaryDimension;
    private int[] modelIds;
    private int[] headModelIds;
    public int standAnimationId;
    public int walkAnimationId;
    public int turnAboutAnimationId;
    public int turnRightAnimationId;
    public int turnLeftAnimationId;
    private int[] modifiedModelColors;
    private int[] originalModelColors;
    public String[] options;
    public boolean visibleMinimap;
    public int combatLevel;
    private int scaleXY;
    private int scaleZ;
    public boolean visible;
    private int brightness;
    private int contrast;
    public int headIcon;
    public int degreesToTurn;

    private NPCType() {
        id = -1L;
        boundaryDimension = 1;
        standAnimationId = -1;
        walkAnimationId = -1;
        turnAboutAnimationId = -1;
        turnRightAnimationId = -1;
        turnLeftAnimationId = -1;
        visibleMinimap = true;
        combatLevel = -1;
        scaleXY = 128;
        scaleZ = 128;
        visible = false;
        headIcon = -1;
        degreesToTurn = 32;
    }

    public static void load(Archive archive) {
        buffer = new Buffer(archive.decompressFile("npc.dat"));
        Buffer buffer = new Buffer(archive.decompressFile("npc.idx"));
        int size = buffer.readUnsignedShort();
        bufferOffsets = new int[size];
        int offset = 2;
        for (int index = 0; index < size; index++) {
            bufferOffsets[index] = offset;
            offset += buffer.readUnsignedShort();
        }
        cache = new NPCType[20];
        for (int index = 0; index < 20; index++) {
            cache[index] = new NPCType();
        }
    }

    public static void nullLoader() {
            modelCache = null;
            bufferOffsets = null;
            cache = null;
            buffer = null;
    }

    public static NPCType getDefinition(int id) {
        for (int index = 0; index < 20; index++) {
            if (cache[index].id == id) {
                return cache[index];
            }
        }
        bufferIndex = (bufferIndex + 1) % 20;
        NPCType definition = cache[bufferIndex] = new NPCType();
        buffer.offset = bufferOffsets[id];
        definition.id = id;
        definition.read(buffer);
        return definition;
    }

    private void read(Buffer buffer) {
        do {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0) {
                return;
            }
            if (opcode == 1) {
                int length = buffer.readUnsignedByte();
                modelIds = new int[length];
                for (int id = 0; id < length; id++) {
                    modelIds[id] = buffer.readUnsignedShort();
                }
            } else if (opcode == 2) {
                name = buffer.readString();
            } else if (opcode == 3) {
                description = buffer.readStringRaw();
            } else if (opcode == 12) {
                boundaryDimension = buffer.readByte();
            } else if (opcode == 13) {
                standAnimationId = buffer.readUnsignedShort();
            } else if (opcode == 14) {
                walkAnimationId = buffer.readUnsignedShort();
            } else if (opcode == 17) {
                walkAnimationId = buffer.readUnsignedShort();
                turnAboutAnimationId = buffer.readUnsignedShort();
                turnRightAnimationId = buffer.readUnsignedShort();
                turnLeftAnimationId = buffer.readUnsignedShort();
            } else if (opcode >= 30 && opcode < 40) {
                if (options == null) {
                    options = new String[5];
                }
                options[opcode - 30] = buffer.readString();
                if (options[opcode - 30].equalsIgnoreCase("hidden")) {
                    options[opcode - 30] = null;
                }
            } else if (opcode == 40) {
                int count = buffer.readUnsignedByte();
                modifiedModelColors = new int[count];
                originalModelColors = new int[count];
                for (int color = 0; color < count; color++) {
                    modifiedModelColors[color] = buffer.readUnsignedShort();
                    originalModelColors[color] = buffer.readUnsignedShort();
                }
            } else if (opcode == 60) {
                int count = buffer.readUnsignedByte();
                headModelIds = new int[count];
                for (int id = 0; id < count; id++) {
                    headModelIds[id] = buffer.readUnsignedShort();
                }
            } else if (opcode == 90) {
                buffer.readUnsignedShort();
            } else if (opcode == 91) {
                buffer.readUnsignedShort();
            } else if (opcode == 92) {
                buffer.readUnsignedShort();
            } else if (opcode == 93) {
                visibleMinimap = false;
            } else if (opcode == 95) {
                combatLevel = buffer.readUnsignedShort();
            } else if (opcode == 97) {
                scaleXY = buffer.readUnsignedShort();
            } else if (opcode == 98) {
                scaleZ = buffer.readUnsignedShort();
            } else if (opcode == 99) {
                visible = true;
            } else if (opcode == 100) {
                brightness = buffer.readByte();
            } else if (opcode == 101) {
                contrast = buffer.readByte() * 5;
            } else if (opcode == 102) {
                headIcon = buffer.readUnsignedShort();
            } else if (opcode == 103) {
                degreesToTurn = buffer.readUnsignedShort();
            }
        } while (true);
    }

    public Model getChildModel(int frameId2, int frameId1, int[] frameId3) {
        Model model = (Model) modelCache.get(id);
        if (model == null) {
            boolean notCached = false;
            for (int i : modelIds) {
                if (!Model.isCached(i)) {
                    notCached = true;
                }
            }
            if (notCached) {
                return null;
            }
            Model[] childModels = new Model[modelIds.length];
            for (int modelId = 0; modelId < modelIds.length; modelId++) {
                childModels[modelId] = Model.getModel(modelIds[modelId]);
            }
            if (childModels.length == 1) {
                model = childModels[0];
            } else {
                model = new Model(childModels, childModels.length);
            }
            if (model != null) {
                if (modifiedModelColors != null) {
                    for (int color = 0; color < modifiedModelColors.length; color++) {
                        model.recolor(modifiedModelColors[color], originalModelColors[color]);
                    }
                }
                model.createBones();
                model.applyLighting(64 + brightness, 850 + contrast, -30, -50, -30, true);
                modelCache.put(id, model);
            }
        }
        Model childModel = Model.aClass44_Sub3_Sub4_Sub4_1530;
        childModel.replaceWithModel(Animation.isNullFrame(frameId1, 0) & Animation.isNullFrame(frameId2, 0), model);
        if (frameId1 != -1 && frameId2 != -1) {
            childModel.mixAnimationFrames(frameId1, frameId2, frameId3);
        } else if (frameId1 != -1) {
            childModel.applyTransformation(frameId1);
        }
        if (scaleXY != 128 || scaleZ != 128) {
            childModel.scaleT(scaleXY, scaleXY, scaleZ);
        }
        childModel.calculateDiagonals();
        childModel.triangleSkin = null;
        childModel.vertexSkin = null;
        if (boundaryDimension == 1) {
            childModel.singleTile = true;
        }
        return childModel;
    }

    public Model getHeadModel() {
        if (headModelIds == null) {
            return null;
        }
        boolean notCached = false;
        for (int headModelId : headModelIds) {
            if (!Model.isCached(headModelId)) {
                notCached = true;
            }
        }
        if (notCached) {
            return null;
        }
        Model[] headModels = new Model[headModelIds.length];
        for (int modelId = 0; modelId < headModelIds.length; modelId++) {
            headModels[modelId] = Model.getModel(headModelIds[modelId]);
        }
        Model model;
        if (headModels.length == 1) {
            model = headModels[0];
        } else {
            model = new Model(headModels, headModels.length);
        }
        if (model != null) {
            if (modifiedModelColors != null) {
                for (int color = 0; color < modifiedModelColors.length; color++) {
                    model.recolor(modifiedModelColors[color], originalModelColors[color]);
                }
            }
        }
        return model;
    }

}
