package com.kt289.client.graphic;

import com.kt289.util.buffer.Buffer;
import com.kt289.client.cache.Requester;
import com.kt289.client.cache.definition.Animation;
import com.kt289.client.render.Renderable;

public class Model extends Renderable {

    public static final Model aClass44_Sub3_Sub4_Sub4_1530 = new Model();
    private static int[] anIntArray1531 = new int[2000];
    private static int[] anIntArray1532 = new int[2000];
    private static int[] anIntArray1533 = new int[2000];
    private static int[] anIntArray1534 = new int[2000];
    private static ModelHeader[] modelHeaders;
    private static Requester requester;
    private static boolean[] restrictEdges = new boolean[4096];
    private static boolean[] aBooleanArray1573 = new boolean[4096];
    private static int[] vertexScreenX = new int[4096];
    private static int[] vertexScreenY = new int[4096];
    private static int[] vertexScreenZ = new int[4096];
    private static int[] vertexMovedX = new int[4096];
    private static int[] vertexMovedY = new int[4096];
    private static int[] vertexMovedZ = new int[4096];
    private static int[] anIntArray1580 = new int[1500];
    private static int[][] anIntArrayArray1581 = new int[1500][512];
    private static int[] anIntArray1582 = new int[12];
    private static int[][] anIntArrayArray1583 = new int[12][2000];
    private static int[] anIntArray1584 = new int[2000];
    private static int[] anIntArray1585 = new int[2000];
    private static int[] anIntArray1586 = new int[12];
    private static final int[] xPosition = new int[10];
    private static final int[] yPosition = new int[10];
    private static final int[] zPosition = new int[10];
    private static int vertexModifierX;
    private static int vertexModifierY;
    private static int vertexModifierZ;
    public static boolean aBoolean1593;
    public static int cursorX;
    public static int cursorY;
    public static int resourceCount;
    public static final int[] resourceIds = new int[1000];
    public static int[] SINE;
    public static int[] COSINE;
    private static int[] HSLtoRGB;
    private static int[] anIntArray1601;

    static {
        Model.SINE = Rasterizer3D.anIntArray1427;
        Model.COSINE = Rasterizer3D.anIntArray1428;
        Model.HSLtoRGB = Rasterizer3D.anIntArray1439;
        Model.anIntArray1601 = Rasterizer3D.anIntArray1426;
    }

    public int vertexCount;
    public int[] verticesX;
    public int[] verticesY;
    public int[] verticesZ;
    public int triangleCount;
    public int[] triangleX;
    public int[] triangleY;
    public int[] triangleZ;
    private int[] triangleHSLA;
    private int[] triangleHSLB;
    private int[] triangleHSLC;
    public int[] triangleDrawType;
    private int[] trianglePriorities;
    private int[] triangleAlpha;
    public int[] triangleColors;
    private int trianglePriority;
    private int texturedTriangleCount;
    private int[] texturedTrianglePointsX;
    private int[] texturedTrianglePointsY;
    private int[] texturedTrianglePointsZ;
    public int minX;
    public int maxX;
    public int maxZ;
    public int minZ;
    public int diagonal2DAboveOrigin;
    public int maxY;
    private int diagonal3D;
    private int diagonal3DAboveOrigin;
    public int anInt1563;
    private int[] vertexSkins;
    private int[] triangleSkins;
    public int[][] vertexSkin;
    public int[][] triangleSkin;
    public boolean singleTile;
    public VertexNormal[] vertexNormalOffset;

    private Model() {
        singleTile = false;
    }

    private Model(int model) {
        singleTile = false;
        ModelHeader modelHeader = Model.modelHeaders[model];
        vertexCount = modelHeader.vertexCount;
        triangleCount = modelHeader.triangleCount;
        texturedTriangleCount = modelHeader.texturedTriangleCount;
        verticesX = new int[vertexCount];
        verticesY = new int[vertexCount];
        verticesZ = new int[vertexCount];
        triangleX = new int[triangleCount];
        triangleY = new int[triangleCount];
        triangleZ = new int[triangleCount];
        texturedTrianglePointsX = new int[texturedTriangleCount];
        texturedTrianglePointsY = new int[texturedTriangleCount];
        texturedTrianglePointsZ = new int[texturedTriangleCount];
        if (modelHeader.vertexSkinOffset >= 0) {
            vertexSkins = new int[vertexCount];
        }
        if (modelHeader.texturePointerOffset >= 0) {
            triangleDrawType = new int[triangleCount];
        }
        if (modelHeader.trianglePriorityOffset >= 0) {
            trianglePriorities = new int[triangleCount];
        } else {
            trianglePriority = -modelHeader.trianglePriorityOffset - 1;
        }
        if (modelHeader.triangleAlphaOffset >= 0) {
            triangleAlpha = new int[triangleCount];
        }
        if (modelHeader.triangleSkinOffset >= 0) {
            triangleSkins = new int[triangleCount];
        }
        triangleColors = new int[triangleCount];
        Buffer vertexDirectionOffsetBuffer = new Buffer(modelHeader.modelData);
        vertexDirectionOffsetBuffer.offset = modelHeader.vertexDirectionOffset;
        Buffer xDataOffsetBuffer = new Buffer(modelHeader.modelData);
        xDataOffsetBuffer.offset = modelHeader.dataOffsetX;
        Buffer yDataOffsetBuffer = new Buffer(modelHeader.modelData);
        yDataOffsetBuffer.offset = modelHeader.dataOffsetY;
        Buffer zDataOffsetBuffer = new Buffer(modelHeader.modelData);
        zDataOffsetBuffer.offset = modelHeader.dataOffsetZ;
        Buffer vertexSkinOffsetBuffer = new Buffer(modelHeader.modelData);
        vertexSkinOffsetBuffer.offset = modelHeader.vertexSkinOffset;
        int baseOffsetX = 0;
        int baseOffsetY = 0;
        int baseOffsetZ = 0;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int flag = vertexDirectionOffsetBuffer.readUnsignedByte();
            int currentOffsetX = 0;
            if ((flag & 1) != 0) {
                currentOffsetX = xDataOffsetBuffer.readUnsignedSmartA();
            }
            int currentOffsetY = 0;
            if ((flag & 2) != 0) {
                currentOffsetY = yDataOffsetBuffer.readUnsignedSmartA();
            }
            int currentOffsetZ = 0;
            if ((flag & 4) != 0) {
                currentOffsetZ = zDataOffsetBuffer.readUnsignedSmartA();
            }
            verticesX[vertex] = baseOffsetX + currentOffsetX;
            verticesY[vertex] = baseOffsetY + currentOffsetY;
            verticesZ[vertex] = baseOffsetZ + currentOffsetZ;
            baseOffsetX = verticesX[vertex];
            baseOffsetY = verticesY[vertex];
            baseOffsetZ = verticesZ[vertex];
            if (vertexSkins != null) {
                vertexSkins[vertex] = vertexSkinOffsetBuffer.readUnsignedByte();
            }
        }
        vertexDirectionOffsetBuffer.offset = modelHeader.colorDataOffset;
        xDataOffsetBuffer.offset = modelHeader.texturePointerOffset;
        yDataOffsetBuffer.offset = modelHeader.trianglePriorityOffset;
        zDataOffsetBuffer.offset = modelHeader.triangleAlphaOffset;
        vertexSkinOffsetBuffer.offset = modelHeader.triangleSkinOffset;
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            triangleColors[triangle] = vertexDirectionOffsetBuffer.readUnsignedShort();
            if (triangleDrawType != null) {
                triangleDrawType[triangle] = xDataOffsetBuffer.readUnsignedByte();
            }
            if (trianglePriorities != null) {
                trianglePriorities[triangle] = yDataOffsetBuffer.readUnsignedByte();
            }
            if (triangleAlpha != null) {
                triangleAlpha[triangle] = zDataOffsetBuffer.readUnsignedByte();
            }
            if (triangleSkins != null) {
                triangleSkins[triangle] = vertexSkinOffsetBuffer.readUnsignedByte();
            }
        }
        vertexDirectionOffsetBuffer.offset = modelHeader.triangleDataOffset;
        xDataOffsetBuffer.offset = modelHeader.triangleTypeOffset;
        int trianglePointOffsetX = 0;
        int trianglePointOffsetY = 0;
        int trianglePointOffsetZ = 0;
        int offset = 0;
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            int type = xDataOffsetBuffer.readUnsignedByte();
            if (type == 1) {
                trianglePointOffsetX = vertexDirectionOffsetBuffer.readUnsignedSmartA() + offset;
                offset = trianglePointOffsetX;
                trianglePointOffsetY = vertexDirectionOffsetBuffer.readUnsignedSmartA() + offset;
                offset = trianglePointOffsetY;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.readUnsignedSmartA() + offset;
                offset = trianglePointOffsetZ;
                triangleX[triangle] = trianglePointOffsetX;
                triangleY[triangle] = trianglePointOffsetY;
                triangleZ[triangle] = trianglePointOffsetZ;
            }
            if (type == 2) {
                trianglePointOffsetY = trianglePointOffsetZ;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.readUnsignedSmartA() + offset;
                offset = trianglePointOffsetZ;
                triangleX[triangle] = trianglePointOffsetX;
                triangleY[triangle] = trianglePointOffsetY;
                triangleZ[triangle] = trianglePointOffsetZ;
            }
            if (type == 3) {
                trianglePointOffsetX = trianglePointOffsetZ;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.readUnsignedSmartA() + offset;
                offset = trianglePointOffsetZ;
                triangleX[triangle] = trianglePointOffsetX;
                triangleY[triangle] = trianglePointOffsetY;
                triangleZ[triangle] = trianglePointOffsetZ;
            }
            if (type == 4) {
                int k4 = trianglePointOffsetX;
                trianglePointOffsetX = trianglePointOffsetY;
                trianglePointOffsetY = k4;
                trianglePointOffsetZ = vertexDirectionOffsetBuffer.readUnsignedSmartA() + offset;
                offset = trianglePointOffsetZ;
                triangleX[triangle] = trianglePointOffsetX;
                triangleY[triangle] = trianglePointOffsetY;
                triangleZ[triangle] = trianglePointOffsetZ;
            }
        }
        vertexDirectionOffsetBuffer.offset = modelHeader.anInt458;
        for (int triangle = 0; triangle < texturedTriangleCount; triangle++) {
            texturedTrianglePointsX[triangle] = vertexDirectionOffsetBuffer.readUnsignedShort();
            texturedTrianglePointsY[triangle] = vertexDirectionOffsetBuffer.readUnsignedShort();
            texturedTrianglePointsZ[triangle] = vertexDirectionOffsetBuffer.readUnsignedShort();
        }
    }

    public Model(Model[] models, int modelCount) {
        singleTile = false;
        boolean setDrawType = false;
        boolean setPriority = false;
        boolean setAlpha = false;
        boolean setSkins = false;
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;
        trianglePriority = -1;
        for (int index = 0; index < modelCount; index++) {
            Model model = models[index];
            if (model != null) {
                vertexCount += model.vertexCount;
                triangleCount += model.triangleCount;
                texturedTriangleCount += model.texturedTriangleCount;
                setDrawType |= model.triangleDrawType != null;
                if (model.trianglePriorities != null) {
                    setPriority = true;
                } else {
                    if (trianglePriority == -1) {
                        trianglePriority = model.trianglePriority;
                    }
                    if (trianglePriority != model.trianglePriority) {
                        setPriority = true;
                    }
                }
                setAlpha |= model.triangleAlpha != null;
                setSkins |= model.triangleSkins != null;
            }
        }
        verticesX = new int[vertexCount];
        verticesY = new int[vertexCount];
        verticesZ = new int[vertexCount];
        vertexSkins = new int[vertexCount];
        triangleX = new int[triangleCount];
        triangleY = new int[triangleCount];
        triangleZ = new int[triangleCount];
        texturedTrianglePointsX = new int[texturedTriangleCount];
        texturedTrianglePointsY = new int[texturedTriangleCount];
        texturedTrianglePointsZ = new int[texturedTriangleCount];
        if (setDrawType) {
            triangleDrawType = new int[triangleCount];
        }
        if (setPriority) {
            trianglePriorities = new int[triangleCount];
        }
        if (setAlpha) {
            triangleAlpha = new int[triangleCount];
        }
        if (setSkins) {
            triangleSkins = new int[triangleCount];
        }
        triangleColors = new int[triangleCount];
        vertexCount = 0;
        triangleCount = 0;
        texturedTriangleCount = 0;
        int count = 0;
        for (int index = 0; index < modelCount; index++) {
            Model model = models[index];
            if (model != null) {
                for (int triangle = 0; triangle < model.triangleCount; triangle++) {
                    if (setDrawType) {
                        if (model.triangleDrawType == null) {
                            triangleDrawType[triangleCount] = 0;
                        } else {
                            int drawType = model.triangleDrawType[triangle];
                            if ((drawType & 2) == 2) {
                                drawType += count << 2;
                            }
                            triangleDrawType[triangleCount] = drawType;
                        }
                    }
                    if (setPriority) {
                        if (model.trianglePriorities == null) {
                            trianglePriorities[triangleCount] = model.trianglePriority;
                        } else {
                            trianglePriorities[triangleCount] = model.trianglePriorities[triangle];
                        }
                    }
                    if (setAlpha) {
                        if (model.triangleAlpha == null) {
                            triangleAlpha[triangleCount] = 0;
                        } else {
                            triangleAlpha[triangleCount] = model.triangleAlpha[triangle];
                        }
                    }
                    if (setSkins && model.triangleSkins != null) {
                        triangleSkins[triangleCount] = model.triangleSkins[triangle];
                    }
                    triangleColors[triangleCount] = model.triangleColors[triangle];
                    triangleX[triangleCount] = getFirstIdenticalVertexId(model, model.triangleX[triangle]);
                    triangleY[triangleCount] = getFirstIdenticalVertexId(model, model.triangleY[triangle]);
                    triangleZ[triangleCount] = getFirstIdenticalVertexId(model, model.triangleZ[triangle]);
                    triangleCount++;
                }
                for (int l1 = 0; l1 < model.texturedTriangleCount; l1++) {
                    texturedTrianglePointsX[texturedTriangleCount] = getFirstIdenticalVertexId(model, model.texturedTrianglePointsX[l1]);
                    texturedTrianglePointsY[texturedTriangleCount] = getFirstIdenticalVertexId(model, model.texturedTrianglePointsY[l1]);
                    texturedTrianglePointsZ[texturedTriangleCount] = getFirstIdenticalVertexId(model, model.texturedTrianglePointsZ[l1]);
                    texturedTriangleCount++;
                }
                count += model.texturedTriangleCount;
            }
        }
    }

    public Model(Model model, boolean flag, boolean flag1, boolean flag2) {
        singleTile = false;
        vertexCount = model.vertexCount;
        triangleCount = model.triangleCount;
        texturedTriangleCount = model.texturedTriangleCount;
        if (flag1) {
            verticesX = model.verticesX;
            verticesY = model.verticesY;
            verticesZ = model.verticesZ;
        } else {
            verticesX = new int[vertexCount];
            verticesY = new int[vertexCount];
            verticesZ = new int[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                verticesX[vertex] = model.verticesX[vertex];
                verticesY[vertex] = model.verticesY[vertex];
                verticesZ[vertex] = model.verticesZ[vertex];
            }
        }
        if (flag2) {
            triangleColors = model.triangleColors;
        } else {
            triangleColors = new int[triangleCount];
            if (triangleCount >= 0) {
                System.arraycopy(model.triangleColors, 0, triangleColors, 0, triangleCount);
            }
        }
        if (flag) {
            triangleAlpha = model.triangleAlpha;
        } else {
            triangleAlpha = new int[triangleCount];
            if (model.triangleAlpha == null) {
                for (int triangle = 0; triangle < triangleCount; triangle++) {
                    triangleAlpha[triangle] = 0;
                }
            } else {
                if (triangleCount >= 0) {
                    System.arraycopy(model.triangleAlpha, 0, triangleAlpha, 0, triangleCount);
                }
            }
        }
        vertexSkins = model.vertexSkins;
        triangleSkins = model.triangleSkins;
        triangleDrawType = model.triangleDrawType;
        triangleX = model.triangleX;
        triangleY = model.triangleY;
        triangleZ = model.triangleZ;
        trianglePriorities = model.trianglePriorities;
        trianglePriority = model.trianglePriority;
        texturedTrianglePointsX = model.texturedTrianglePointsX;
        texturedTrianglePointsY = model.texturedTrianglePointsY;
        texturedTrianglePointsZ = model.texturedTrianglePointsZ;
    }

    public Model(boolean flag, Model model, boolean flag1) {
        singleTile = false;
        vertexCount = model.vertexCount;
        triangleCount = model.triangleCount;
        texturedTriangleCount = model.texturedTriangleCount;
        if (flag1) {
            verticesY = new int[vertexCount];
            if (vertexCount >= 0) {
                System.arraycopy(model.verticesY, 0, verticesY, 0, vertexCount);
            }
        } else {
            verticesY = model.verticesY;
        }
        if (flag) {
            triangleHSLA = new int[triangleCount];
            triangleHSLB = new int[triangleCount];
            triangleHSLC = new int[triangleCount];
            for (int k = 0; k < triangleCount; k++) {
                triangleHSLA[k] = model.triangleHSLA[k];
                triangleHSLB[k] = model.triangleHSLB[k];
                triangleHSLC[k] = model.triangleHSLC[k];
            }
            triangleDrawType = new int[triangleCount];
            if (model.triangleDrawType == null) {
                for (int triangle = 0; triangle < triangleCount; triangle++) {
                    triangleDrawType[triangle] = 0;
                }
            } else {
                if (triangleCount >= 0) {
                    System.arraycopy(model.triangleDrawType, 0, triangleDrawType, 0, triangleCount);
                }
            }
            super.vertexNormals = new VertexNormal[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                VertexNormal vertexNormalNew = super.vertexNormals[vertex] = new VertexNormal();
                VertexNormal vertexNormalOld = model.vertexNormals[vertex];
                vertexNormalNew.x = vertexNormalOld.x;
                vertexNormalNew.y = vertexNormalOld.y;
                vertexNormalNew.z = vertexNormalOld.z;
                vertexNormalNew.magnitude = vertexNormalOld.magnitude;
            }
            vertexNormalOffset = model.vertexNormalOffset;
        } else {
            triangleHSLA = model.triangleHSLA;
            triangleHSLB = model.triangleHSLB;
            triangleHSLC = model.triangleHSLC;
            triangleDrawType = model.triangleDrawType;
        }
        verticesX = model.verticesX;
        verticesZ = model.verticesZ;
        triangleColors = model.triangleColors;
        triangleAlpha = model.triangleAlpha;
        trianglePriorities = model.trianglePriorities;
        trianglePriority = model.trianglePriority;
        triangleX = model.triangleX;
        triangleY = model.triangleY;
        triangleZ = model.triangleZ;
        texturedTrianglePointsX = model.texturedTrianglePointsX;
        texturedTrianglePointsY = model.texturedTrianglePointsY;
        texturedTrianglePointsZ = model.texturedTrianglePointsZ;
        super.modelHeight = model.modelHeight;
        maxY = model.maxY;
        diagonal2DAboveOrigin = model.diagonal2DAboveOrigin;
        diagonal3DAboveOrigin = model.diagonal3DAboveOrigin;
        diagonal3D = model.diagonal3D;
        minX = model.minX;
        maxZ = model.maxZ;
        minZ = model.minZ;
        maxX = model.maxX;
    }

    public static void nullLoader() {
        Model.modelHeaders = null;
        Model.restrictEdges = null;
        Model.aBooleanArray1573 = null;
        Model.vertexScreenX = null;
        Model.vertexScreenY = null;
        Model.vertexScreenZ = null;
        Model.vertexMovedX = null;
        Model.vertexMovedY = null;
        Model.vertexMovedZ = null;
        Model.anIntArray1580 = null;
        Model.anIntArrayArray1581 = null;
        Model.anIntArray1582 = null;
        Model.anIntArrayArray1583 = null;
        Model.anIntArray1584 = null;
        Model.anIntArray1585 = null;
        Model.anIntArray1586 = null;
        Model.SINE = null;
        Model.COSINE = null;
        Model.HSLtoRGB = null;
        Model.anIntArray1601 = null;
    }

    public static void init(int modelCount, Requester requester) {
        Model.modelHeaders = new ModelHeader[modelCount];
        Model.requester = requester;
    }

    public static void load(byte[] data, int modelId) {
        if (data == null) {
            ModelHeader modelHeader = Model.modelHeaders[modelId] = new ModelHeader();
            modelHeader.vertexCount = 0;
            modelHeader.triangleCount = 0;
            modelHeader.texturedTriangleCount = 0;
            return;
        }
        Buffer buffer = new Buffer(data);
        buffer.offset = data.length - 18;
        ModelHeader modelHeader = Model.modelHeaders[modelId] = new ModelHeader();
        modelHeader.modelData = data;
        modelHeader.vertexCount = buffer.readUnsignedShort();
        modelHeader.triangleCount = buffer.readUnsignedShort();
        modelHeader.texturedTriangleCount = buffer.readUnsignedByte();
        int useTextures = buffer.readUnsignedByte();
        int useTrianglePriority = buffer.readUnsignedByte();
        int useAlpha = buffer.readUnsignedByte();
        int useTriangleSkins = buffer.readUnsignedByte();
        int useVertexSkins = buffer.readUnsignedByte();
        int dataLengthX = buffer.readUnsignedShort();
        int dataLengthY = buffer.readUnsignedShort();
        int dataLengthZ = buffer.readUnsignedShort();
        int dataLengthTriangle = buffer.readUnsignedShort();
        int offset = 0;
        modelHeader.vertexDirectionOffset = offset;
        offset += modelHeader.vertexCount;
        modelHeader.triangleTypeOffset = offset;
        offset += modelHeader.triangleCount;
        modelHeader.trianglePriorityOffset = offset;
        if (useTrianglePriority == 255) {
            offset += modelHeader.triangleCount;
        } else {
            modelHeader.trianglePriorityOffset = -useTrianglePriority - 1;
        }
        modelHeader.triangleSkinOffset = offset;
        if (useTriangleSkins == 1) {
            offset += modelHeader.triangleCount;
        } else {
            modelHeader.triangleSkinOffset = -1;
        }
        modelHeader.texturePointerOffset = offset;
        if (useTextures == 1) {
            offset += modelHeader.triangleCount;
        } else {
            modelHeader.texturePointerOffset = -1;
        }
        modelHeader.vertexSkinOffset = offset;
        if (useVertexSkins == 1) {
            offset += modelHeader.vertexCount;
        } else {
            modelHeader.vertexSkinOffset = -1;
        }
        modelHeader.triangleAlphaOffset = offset;
        if (useAlpha == 1) {
            offset += modelHeader.triangleCount;
        } else {
            modelHeader.triangleAlphaOffset = -1;
        }
        modelHeader.triangleDataOffset = offset;
        offset += dataLengthTriangle;
        modelHeader.colorDataOffset = offset;
        offset += modelHeader.triangleCount * 2;
        modelHeader.anInt458 = offset;
        offset += modelHeader.texturedTriangleCount * 6;
        modelHeader.dataOffsetX = offset;
        offset += dataLengthX;
        modelHeader.dataOffsetY = offset;
        offset += dataLengthY;
        modelHeader.dataOffsetZ = offset;
        offset += dataLengthZ;
    }

    public static void resetModel(int modelId) {
        Model.modelHeaders[modelId] = null;
    }

    public static Model getModel(int modelId) {
        if (Model.modelHeaders == null) {
            return null;
        }
        ModelHeader modelHeader = Model.modelHeaders[modelId];
        if (modelHeader == null) {
            Model.requester.request(modelId);
            return null;
        } else {
            return new Model(modelId);
        }
    }

    public static boolean isCached(int modelId) {
        if (Model.modelHeaders == null) {
            return false;
        }
        ModelHeader modelHeader = Model.modelHeaders[modelId];
        if (modelHeader == null) {
            Model.requester.request(modelId);
            return false;
        } else {
            return true;
        }
    }

    private static int mixLightness(int color, int lightness, int drawType) {
        if ((drawType & 2) == 2) {
            if (lightness < 0) {
                lightness = 0;
            } else if (lightness > 127) {
                lightness = 127;
            }
            lightness = 127 - lightness;
            return lightness;
        }
        lightness = lightness * (color & 0x7f) >> 7;
        if (lightness < 2) {
            lightness = 2;
        } else if (lightness > 126) {
            lightness = 126;
        }
        return (color & 0xff80) + lightness;
    }

    public void replaceWithModel(boolean flag, Model model) {
        vertexCount = model.vertexCount;
        triangleCount = model.triangleCount;
        texturedTriangleCount = model.texturedTriangleCount;
        if (Model.anIntArray1531.length < vertexCount) {
            Model.anIntArray1531 = new int[vertexCount + 100];
            Model.anIntArray1532 = new int[vertexCount + 100];
            Model.anIntArray1533 = new int[vertexCount + 100];
        }
        verticesX = Model.anIntArray1531;
        verticesY = Model.anIntArray1532;
        verticesZ = Model.anIntArray1533;
        for (int j = 0; j < vertexCount; j++) {
            verticesX[j] = model.verticesX[j];
            verticesY[j] = model.verticesY[j];
            verticesZ[j] = model.verticesZ[j];
        }
        if (flag) {
            triangleAlpha = model.triangleAlpha;
        } else {
            if (Model.anIntArray1534.length < triangleCount) {
                Model.anIntArray1534 = new int[triangleCount + 100];
            }
            triangleAlpha = Model.anIntArray1534;
            if (model.triangleAlpha == null) {
                for (int k = 0; k < triangleCount; k++) {
                    triangleAlpha[k] = 0;
                }
            } else {
                if (triangleCount >= 0) {
                    System.arraycopy(model.triangleAlpha, 0, triangleAlpha, 0, triangleCount);
                }
            }
        }
        triangleDrawType = model.triangleDrawType;
        triangleColors = model.triangleColors;
        trianglePriorities = model.trianglePriorities;
        trianglePriority = model.trianglePriority;
        triangleSkin = model.triangleSkin;
        vertexSkin = model.vertexSkin;
        triangleX = model.triangleX;
        triangleY = model.triangleY;
        triangleZ = model.triangleZ;
        triangleHSLA = model.triangleHSLA;
        triangleHSLB = model.triangleHSLB;
        triangleHSLC = model.triangleHSLC;
        texturedTrianglePointsX = model.texturedTrianglePointsX;
        texturedTrianglePointsY = model.texturedTrianglePointsY;
        texturedTrianglePointsZ = model.texturedTrianglePointsZ;
    }

    private int getFirstIdenticalVertexId(Model model, int vertex) {
        int vertexId = -1;
        int x = model.verticesX[vertex];
        int y = model.verticesY[vertex];
        int z = model.verticesZ[vertex];
        for (int index = 0; index < vertexCount; index++) {
            if (x != verticesX[index] || y != verticesY[index] || z != verticesZ[index]) {
                continue;
            }
            vertexId = index;
            break;
        }
        if (vertexId == -1) {
            verticesX[vertexCount] = x;
            verticesY[vertexCount] = y;
            verticesZ[vertexCount] = z;
            if (model.vertexSkins != null) {
                vertexSkins[vertexCount] = model.vertexSkins[vertex];
            }
            vertexId = vertexCount++;
        }
        return vertexId;
    }

    public void calculateDiagonals() {
        super.modelHeight = 0;
        diagonal2DAboveOrigin = 0;
        maxY = 0;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int x = verticesX[vertex];
            int y = verticesY[vertex];
            int z = verticesZ[vertex];
            if (-y > super.modelHeight) {
                super.modelHeight = -y;
            }
            if (y > maxY) {
                maxY = y;
            }
            int bounds = x * x + z * z;
            if (bounds > diagonal2DAboveOrigin) {
                diagonal2DAboveOrigin = bounds;
            }
        }
        diagonal2DAboveOrigin = (int) (Math.sqrt(diagonal2DAboveOrigin) + 0.98999999999999999D);
        diagonal3DAboveOrigin = (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        diagonal3D = diagonal3DAboveOrigin + (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + maxY * maxY) + 0.98999999999999999D);
    }

    public void normalise() {
        maxY = 0;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int y = verticesY[vertex];
            if (-y > super.modelHeight) {
                super.modelHeight = -y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }
        diagonal3DAboveOrigin = (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        diagonal3D = diagonal3DAboveOrigin + (int) (Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + maxY * maxY) + 0.98999999999999999D);
    }

    private void calculateDiagonalsAndBounds() {
        super.modelHeight = 0;
        diagonal2DAboveOrigin = 0;
        maxY = 0;
        minX = 0xf423f;
        maxX = 0xfff0bdc1;
        maxZ = 0xfffe7961;
        minZ = 0x1869f;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int x = verticesX[vertex];
            int y = verticesY[vertex];
            int z = verticesZ[vertex];
            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (z < minZ) {
                minZ = z;
            }
            if (z > maxZ) {
                maxZ = z;
            }
            if (-y > super.modelHeight) {
                super.modelHeight = -y;
            }
            if (y > maxY) {
                maxY = y;
            }
            int j1 = x * x + z * z;
            if (j1 > diagonal2DAboveOrigin) {
                diagonal2DAboveOrigin = j1;
            }
        }
        diagonal2DAboveOrigin = (int) Math.sqrt(diagonal2DAboveOrigin);
        diagonal3DAboveOrigin = (int) Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + super.modelHeight * super.modelHeight);
        diagonal3D = diagonal3DAboveOrigin + (int) Math.sqrt(diagonal2DAboveOrigin * diagonal2DAboveOrigin + maxY * maxY);
    }

    public void createBones() {
        if (vertexSkins != null) {
            int[] skins = new int[256];
            int count = 0;
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                int skin = vertexSkins[vertex];
                skins[skin]++;
                if (skin > count) {
                    count = skin;
                }
            }
            vertexSkin = new int[count + 1][];
            for (int vertex = 0; vertex <= count; vertex++) {
                vertexSkin[vertex] = new int[skins[vertex]];
                skins[vertex] = 0;
            }
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                int skin = vertexSkins[vertex];
                vertexSkin[skin][skins[skin]++] = vertex;
            }
            vertexSkins = null;
        }
        if (triangleSkins != null) {
            int[] triangles = new int[256];
            int count = 0;
            for (int triangle = 0; triangle < triangleCount; triangle++) {
                int triangleSkin = triangleSkins[triangle];
                triangles[triangleSkin]++;
                if (triangleSkin > count) {
                    count = triangleSkin;
                }
            }
            triangleSkin = new int[count + 1][];
            for (int triangle = 0; triangle <= count; triangle++) {
                triangleSkin[triangle] = new int[triangles[triangle]];
                triangles[triangle] = 0;
            }
            for (int triangle = 0; triangle < triangleCount; triangle++) {
                int triangleSkin = triangleSkins[triangle];
                this.triangleSkin[triangleSkin][triangles[triangleSkin]++] = triangle;
            }
            triangleSkins = null;
        }
    }

    public void applyTransformation(int frameId) {
        if (vertexSkin == null) {
            return;
        }
        if (frameId == -1) {
            return;
        }
        Animation animation = Animation.forFrameId(frameId);
        if (animation == null) {
            return;
        }
        Skins skins = animation.animationSkins;
        Model.vertexModifierX = 0;
        Model.vertexModifierY = 0;
        Model.vertexModifierZ = 0;
        for (int step = 0; step < animation.frameCount; step++) {
            int opcode = animation.opcodeTable[step];
            transformFrame(skins.opcodes[opcode], skins.skinList[opcode], animation.transformationX[step], animation.transformationY[step], animation.transformationZ[step]);
        }
    }

    public void mixAnimationFrames(int frameId1, int frameId2, int[] frames) {
        if (frameId1 == -1) {
            return;
        }
        if (frames == null || frameId2 == -1) {
            applyTransformation(frameId1);
            return;
        }
        Animation animationFrame1 = Animation.forFrameId(frameId1);
        if (animationFrame1 == null) {
            return;
        }
        Animation animationFrame2 = Animation.forFrameId(frameId2);
        if (animationFrame2 == null) {
            applyTransformation(frameId1);
            return;
        }
        Skins animationSkins = animationFrame1.animationSkins;
        Model.vertexModifierX = 0;
        Model.vertexModifierY = 0;
        Model.vertexModifierZ = 0;
        int count = 0;
        int frameCount = frames[count++];
        for (int frame = 0; frame < animationFrame1.frameCount; frame++) {
            int skin = animationFrame1.opcodeTable[frame];
            while (skin > frameCount) {
                frameCount = frames[count++];
            }
            if (skin != frameCount || animationSkins.opcodes[skin] == 0) {
                transformFrame(animationSkins.opcodes[skin], animationSkins.skinList[skin], animationFrame1.transformationX[frame], animationFrame1.transformationY[frame], animationFrame1.transformationZ[frame]);
            }
        }
        Model.vertexModifierX = 0;
        Model.vertexModifierY = 0;
        Model.vertexModifierZ = 0;
        count = 0;
        frameCount = frames[count++];
        for (int frame = 0; frame < animationFrame2.frameCount; frame++) {
            int skin = animationFrame2.opcodeTable[frame];
            while (skin > frameCount) {
                frameCount = frames[count++];
            }
            if (skin == frameCount || animationSkins.opcodes[skin] == 0) {
                transformFrame(animationSkins.opcodes[skin], animationSkins.skinList[skin], animationFrame2.transformationX[frame], animationFrame2.transformationY[frame], animationFrame2.transformationZ[frame]);
            }
        }
    }

    private void transformFrame(int opcode, int[] skins, int vertexTransformationX, int vertexTransformationY, int vertexTransformationZ) {
        if (opcode == 0) {
            int affectedSkins = 0;
            Model.vertexModifierX = 0;
            Model.vertexModifierY = 0;
            Model.vertexModifierZ = 0;
            for (int skin : skins) {
                if (skin < vertexSkin.length) {
                    int[] vertexSkins = vertexSkin[skin];
                    for (int vertexSkin : vertexSkins) {
                        Model.vertexModifierX += verticesX[vertexSkin];
                        Model.vertexModifierY += verticesY[vertexSkin];
                        Model.vertexModifierZ += verticesZ[vertexSkin];
                        affectedSkins++;
                    }
                }
            }
            if (affectedSkins > 0) {
                Model.vertexModifierX = Model.vertexModifierX / affectedSkins + vertexTransformationX;
                Model.vertexModifierY = Model.vertexModifierY / affectedSkins + vertexTransformationY;
                Model.vertexModifierZ = Model.vertexModifierZ / affectedSkins + vertexTransformationZ;
            } else {
                Model.vertexModifierX = vertexTransformationX;
                Model.vertexModifierY = vertexTransformationY;
                Model.vertexModifierZ = vertexTransformationZ;
            }
            return;
        }
        if (opcode == 1) {
            for (int skin : skins) {
                if (skin < vertexSkin.length) {
                    int[] vertexSkins = vertexSkin[skin];
                    for (int vertexSkin : vertexSkins) {
                        verticesX[vertexSkin] += vertexTransformationX;
                        verticesY[vertexSkin] += vertexTransformationY;
                        verticesZ[vertexSkin] += vertexTransformationZ;
                    }
                }
            }
            return;
        }
        if (opcode == 2) {
            for (int skin : skins) {
                if (skin < vertexSkin.length) {
                    int[] vertexSkins = vertexSkin[skin];
                    for (int vertexSkin : vertexSkins) {
                        verticesX[vertexSkin] -= Model.vertexModifierX;
                        verticesY[vertexSkin] -= Model.vertexModifierY;
                        verticesZ[vertexSkin] -= Model.vertexModifierZ;
                        int rotationX = (vertexTransformationX & 0xff) * 8;
                        int rotationY = (vertexTransformationY & 0xff) * 8;
                        int rotationZ = (vertexTransformationZ & 0xff) * 8;
                        if (rotationZ != 0) {
                            int sine = Model.SINE[rotationZ];
                            int cosine = Model.COSINE[rotationZ];
                            int newVertexX = verticesY[vertexSkin] * sine + verticesX[vertexSkin] * cosine >> 16;
                            verticesY[vertexSkin] = verticesY[vertexSkin] * cosine - verticesX[vertexSkin] * sine >> 16;
                            verticesX[vertexSkin] = newVertexX;
                        }
                        if (rotationX != 0) {
                            int sine = Model.SINE[rotationX];
                            int cosine = Model.COSINE[rotationX];
                            int newVertexY = verticesY[vertexSkin] * cosine - verticesZ[vertexSkin] * sine >> 16;
                            verticesZ[vertexSkin] = verticesY[vertexSkin] * sine + verticesZ[vertexSkin] * cosine >> 16;
                            verticesY[vertexSkin] = newVertexY;
                        }
                        if (rotationY != 0) {
                            int sine = Model.SINE[rotationY];
                            int cosine = Model.COSINE[rotationY];
                            int newVertexZ = verticesZ[vertexSkin] * sine + verticesX[vertexSkin] * cosine >> 16;
                            verticesZ[vertexSkin] = verticesZ[vertexSkin] * cosine - verticesX[vertexSkin] * sine >> 16;
                            verticesX[vertexSkin] = newVertexZ;
                        }
                        verticesX[vertexSkin] += Model.vertexModifierX;
                        verticesY[vertexSkin] += Model.vertexModifierY;
                        verticesZ[vertexSkin] += Model.vertexModifierZ;
                    }
                }
            }
            return;
        }
        if (opcode == 3) {
            for (int skin : skins) {
                if (skin < vertexSkin.length) {
                    int[] vertexSkins = vertexSkin[skin];
                    for (int vertexSkin : vertexSkins) {
                        verticesX[vertexSkin] -= Model.vertexModifierX;
                        verticesY[vertexSkin] -= Model.vertexModifierY;
                        verticesZ[vertexSkin] -= Model.vertexModifierZ;
                        verticesX[vertexSkin] = (verticesX[vertexSkin] * vertexTransformationX) / 128;
                        verticesY[vertexSkin] = (verticesY[vertexSkin] * vertexTransformationY) / 128;
                        verticesZ[vertexSkin] = (verticesZ[vertexSkin] * vertexTransformationZ) / 128;
                        verticesX[vertexSkin] += Model.vertexModifierX;
                        verticesY[vertexSkin] += Model.vertexModifierY;
                        verticesZ[vertexSkin] += Model.vertexModifierZ;
                    }
                }
            }
            return;
        }
        if (opcode == 5 && triangleSkin != null && triangleAlpha != null) {
            for (int skin : skins) {
                if (skin < triangleSkin.length) {
                    int[] triangleSkins = triangleSkin[skin];
                    for (int triangleSkin : triangleSkins) {
                        triangleAlpha[triangleSkin] += vertexTransformationX * 8;
                        if (triangleAlpha[triangleSkin] < 0) {
                            triangleAlpha[triangleSkin] = 0;
                        }
                        if (triangleAlpha[triangleSkin] > 255) {
                            triangleAlpha[triangleSkin] = 255;
                        }
                    }
                }
            }
        }
    }

    public void rotate90Degrees() {
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int verticesX = this.verticesX[vertex];
            this.verticesX[vertex] = verticesZ[vertex];
            verticesZ[vertex] = -verticesX;
        }
    }

    public void rotateX(int j) {
        int sine = Model.SINE[j];
        int cosine = Model.COSINE[j];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int newVertexY = verticesY[vertex] * cosine - verticesZ[vertex] * sine >> 16;
            verticesZ[vertex] = verticesY[vertex] * sine + verticesZ[vertex] * cosine >> 16;
            verticesY[vertex] = newVertexY;
        }
    }

    public void translate(int y, int x, int z) {
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            verticesX[vertex] += x;
            verticesY[vertex] += y;
            verticesZ[vertex] += z;
        }
    }

    public void recolor(int targetColor, int replacementColor) {
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            if (triangleColors[triangle] == targetColor) {
                triangleColors[triangle] = replacementColor;
            }
        }
    }

    public void mirror() {
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            verticesZ[vertex] = -verticesZ[vertex];
        }
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            int newTriangleC = triangleX[triangle];
            triangleX[triangle] = triangleZ[triangle];
            triangleZ[triangle] = newTriangleC;
        }
    }

    public void scaleT(int x, int z, int y) {
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            verticesX[vertex] = (verticesX[vertex] * x) / 128;
            verticesY[vertex] = (verticesY[vertex] * y) / 128;
            verticesZ[vertex] = (verticesZ[vertex] * z) / 128;
        }
    }

    public void applyLighting(int lightMod, int magnitudeMultiplier, int lightX, int lightY, int lightZ, boolean flatShading) {
        int lightMagnitude = (int) Math.sqrt(lightX * lightX + lightY * lightY + lightZ * lightZ);
        int magnitude = magnitudeMultiplier * lightMagnitude >> 8;
        if (triangleHSLA == null) {
            triangleHSLA = new int[triangleCount];
            triangleHSLB = new int[triangleCount];
            triangleHSLC = new int[triangleCount];
        }
        if (super.vertexNormals == null) {
            super.vertexNormals = new VertexNormal[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                super.vertexNormals[vertex] = new VertexNormal();
            }
        }
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            int triangleX = this.triangleX[triangle];
            int triangleY = this.triangleY[triangle];
            int triangleZ = this.triangleZ[triangle];
            int distanceXXY = verticesX[triangleY] - verticesX[triangleX];
            int distanceYXY = verticesY[triangleY] - verticesY[triangleX];
            int distanceZXY = verticesZ[triangleY] - verticesZ[triangleX];
            int distanceXZX = verticesX[triangleZ] - verticesX[triangleX];
            int distanceYZX = verticesY[triangleZ] - verticesY[triangleX];
            int distanceZZX = verticesZ[triangleZ] - verticesZ[triangleX];
            int normalX = distanceYXY * distanceZZX - distanceYZX * distanceZXY;
            int normalY = distanceZXY * distanceXZX - distanceZZX * distanceXXY;
            int normalZ;
            for (normalZ = distanceXXY * distanceYZX - distanceXZX * distanceYXY; normalX > 8192 || normalY > 8192 || normalZ > 8192 || normalX < -8192 || normalY < -8192 || normalZ < -8192; normalZ >>= 1) {
                normalX >>= 1;
                normalY >>= 1;
            }
            int normalLength = (int) Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
            if (normalLength <= 0) {
                normalLength = 1;
            }
            normalX = (normalX * 256) / normalLength;
            normalY = (normalY * 256) / normalLength;
            normalZ = (normalZ * 256) / normalLength;
            if (triangleDrawType == null || (triangleDrawType[triangle] & 1) == 0) {
                VertexNormal vertexNormal = super.vertexNormals[triangleX];
                vertexNormal.x += normalX;
                vertexNormal.y += normalY;
                vertexNormal.z += normalZ;
                vertexNormal.magnitude++;
                vertexNormal = super.vertexNormals[triangleY];
                vertexNormal.x += normalX;
                vertexNormal.y += normalY;
                vertexNormal.z += normalZ;
                vertexNormal.magnitude++;
                vertexNormal = super.vertexNormals[triangleZ];
                vertexNormal.x += normalX;
                vertexNormal.y += normalY;
                vertexNormal.z += normalZ;
                vertexNormal.magnitude++;
            } else {
                int lightness = lightMod + (lightX * normalX + lightY * normalY + lightZ * normalZ) / (magnitude + magnitude / 2);
                triangleHSLA[triangle] = Model.mixLightness(triangleColors[triangle], lightness, triangleDrawType[triangle]);
            }
        }
        if (flatShading) {
            handleShading(lightMod, magnitude, lightX, lightY, lightZ);
        } else {
            vertexNormalOffset = new VertexNormal[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                VertexNormal vertexNormal = super.vertexNormals[vertex];
                VertexNormal shadowVertexNormal = vertexNormalOffset[vertex] = new VertexNormal();
                shadowVertexNormal.x = vertexNormal.x;
                shadowVertexNormal.y = vertexNormal.y;
                shadowVertexNormal.z = vertexNormal.z;
                shadowVertexNormal.magnitude = vertexNormal.magnitude;
            }
        }
        if (flatShading) {
            calculateDiagonals();
        } else {
            calculateDiagonalsAndBounds();
        }
    }

    public void handleShading(int intensity, int j, int lightX, int lightY, int lightZ) {
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            int x = triangleX[triangle];
            int y = triangleY[triangle];
            int z = triangleZ[triangle];
            if (triangleDrawType == null) {
                int color = triangleColors[triangle];
                VertexNormal vertexNormal = super.vertexNormals[x];
                int lightness = intensity + (lightX * vertexNormal.x + lightY * vertexNormal.y + lightZ * vertexNormal.z) / (j * vertexNormal.magnitude);
                triangleHSLA[triangle] = Model.mixLightness(color, lightness, 0);
                vertexNormal = super.vertexNormals[y];
                lightness = intensity + (lightX * vertexNormal.x + lightY * vertexNormal.y + lightZ * vertexNormal.z) / (j * vertexNormal.magnitude);
                triangleHSLB[triangle] = Model.mixLightness(color, lightness, 0);
                vertexNormal = super.vertexNormals[z];
                lightness = intensity + (lightX * vertexNormal.x + lightY * vertexNormal.y + lightZ * vertexNormal.z) / (j * vertexNormal.magnitude);
                triangleHSLC[triangle] = Model.mixLightness(color, lightness, 0);
            } else if ((triangleDrawType[triangle] & 1) == 0) {
                int color = triangleColors[triangle];
                int drawType = triangleDrawType[triangle];
                VertexNormal vertexNormal = super.vertexNormals[x];
                int lightness = intensity + (lightX * vertexNormal.x + lightY * vertexNormal.y + lightZ * vertexNormal.z) / (j * vertexNormal.magnitude);
                triangleHSLA[triangle] = Model.mixLightness(color, lightness, drawType);
                vertexNormal = super.vertexNormals[y];
                lightness = intensity + (lightX * vertexNormal.x + lightY * vertexNormal.y + lightZ * vertexNormal.z) / (j * vertexNormal.magnitude);
                triangleHSLB[triangle] = Model.mixLightness(color, lightness, drawType);
                vertexNormal = super.vertexNormals[z];
                lightness = intensity + (lightX * vertexNormal.x + lightY * vertexNormal.y + lightZ * vertexNormal.z) / (j * vertexNormal.magnitude);
                triangleHSLC[triangle] = Model.mixLightness(color, lightness, drawType);
            }
        }
        super.vertexNormals = null;
        vertexNormalOffset = null;
        vertexSkins = null;
        triangleSkins = null;
        if (triangleDrawType != null) {
            for (int triangle = 0; triangle < triangleCount; triangle++) {
                if ((triangleDrawType[triangle] & 2) == 2) {
                    return;
                }
            }
        }
        triangleColors = null;
    }

    public void renderSingle(int rotationX, int rotationY, int rotationZ, int rotationXW, int i1, int translationY, int translationZ) {
        int centerX = Rasterizer3D.centerX;
        int centerY = Rasterizer3D.centerY;
        int sineX = Model.SINE[rotationX];
        int cosineX = Model.COSINE[rotationX];
        int sineY = Model.SINE[rotationY];
        int cosineY = Model.COSINE[rotationY];
        int sineZ = Model.SINE[rotationZ];
        int cosineZ = Model.COSINE[rotationZ];
        int sineXW = Model.SINE[rotationXW];
        int cosineXW = Model.COSINE[rotationXW];
        int transformation = translationY * sineXW + translationZ * cosineXW >> 16;
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int x = verticesX[vertex];
            int y = verticesY[vertex];
            int z = verticesZ[vertex];
            if (rotationZ != 0) {
                int newX = y * sineZ + x * cosineZ >> 16;
                y = y * cosineZ - x * sineZ >> 16;
                x = newX;
            }
            if (rotationX != 0) {
                int newY = y * cosineX - z * sineX >> 16;
                z = y * sineX + z * cosineX >> 16;
                y = newY;
            }
            if (rotationY != 0) {
                int newZ = z * sineY + x * cosineY >> 16;
                z = z * cosineY - x * sineY >> 16;
                x = newZ;
            }
            x += i1;
            y += translationY;
            z += translationZ;
            int newY = y * cosineXW - z * sineXW >> 16;
            z = y * sineXW + z * cosineXW >> 16;
            y = newY;
            Model.vertexScreenZ[vertex] = z - transformation;
            Model.vertexScreenX[vertex] = centerX + (x << 9) / z;
            Model.vertexScreenY[vertex] = centerY + (y << 9) / z;
            if (texturedTriangleCount > 0) {
                Model.vertexMovedX[vertex] = x;
                Model.vertexMovedY[vertex] = y;
                Model.vertexMovedZ[vertex] = z;
            }
        }
        try {
            method527(false, false, 0);
        } catch (Exception exception) {
        }
    }

    @Override
    public void renderAtPoint(int i, int yCameraSine, int yCameraCosine, int xCameraSine, int xCameraCosine, int x, int y, int z, int i2) {
        int j2 = z * xCameraCosine - x * xCameraSine >> 16;
        int k2 = y * yCameraSine + j2 * yCameraCosine >> 16;
        int l2 = diagonal2DAboveOrigin * yCameraCosine >> 16;
        int i3 = k2 + l2;
        if (i3 <= 50 || k2 >= 3500) {
            return;
        }
        int j3 = z * xCameraSine + x * xCameraCosine >> 16;
        int k3 = j3 - diagonal2DAboveOrigin << 9;
        if (k3 / i3 >= Rasterizer.anInt1377) {
            return;
        }
        int l3 = j3 + diagonal2DAboveOrigin << 9;
        if (l3 / i3 <= -Rasterizer.anInt1377) {
            return;
        }
        int i4 = y * yCameraCosine - j2 * yCameraSine >> 16;
        int j4 = diagonal2DAboveOrigin * yCameraSine >> 16;
        int k4 = i4 + j4 << 9;
        if (k4 / i3 <= -Rasterizer.anInt1378) {
            return;
        }
        int l4 = j4 + (super.modelHeight * yCameraCosine >> 16);
        int i5 = i4 - l4 << 9;
        if (i5 / i3 >= Rasterizer.anInt1378) {
            return;
        }
        int j5 = l2 + (super.modelHeight * yCameraSine >> 16);
        boolean flag = false;
        if (k2 - j5 <= 50) {
            flag = true;
        }
        boolean flag1 = false;
        if (i2 > 0 && Model.aBoolean1593) {
            int k5 = k2 - l2;
            if (k5 <= 50) {
                k5 = 50;
            }
            if (j3 > 0) {
                k3 /= i3;
                l3 /= k5;
            } else {
                l3 /= i3;
                k3 /= k5;
            }
            if (i4 > 0) {
                i5 /= i3;
                k4 /= k5;
            } else {
                k4 /= i3;
                i5 /= k5;
            }
            int i6 = Model.cursorX - Rasterizer3D.centerX;
            int k6 = Model.cursorY - Rasterizer3D.centerY;
            if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4) {
                if (singleTile) {
                    Model.resourceIds[Model.resourceCount++] = i2;
                } else {
                    flag1 = true;
                }
            }
        }
        int centerX = Rasterizer3D.centerX;
        int centerY = Rasterizer3D.centerY;
        int sine = 0;
        int cosine = 0;
        if (i != 0) {
            sine = Model.SINE[i];
            cosine = Model.COSINE[i];
        }
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            int vertexX = verticesX[vertex];
            int vertexY = verticesY[vertex];
            int vertexZ = verticesZ[vertex];
            if (i != 0) {
                int j8 = vertexZ * sine + vertexX * cosine >> 16;
                vertexZ = vertexZ * cosine - vertexX * sine >> 16;
                vertexX = j8;
            }
            vertexX += x;
            vertexY += y;
            vertexZ += z;
            int k8 = vertexZ * xCameraSine + vertexX * xCameraCosine >> 16;
            vertexZ = vertexZ * xCameraCosine - vertexX * xCameraSine >> 16;
            vertexX = k8;
            k8 = vertexY * yCameraCosine - vertexZ * yCameraSine >> 16;
            vertexZ = vertexY * yCameraSine + vertexZ * yCameraCosine >> 16;
            vertexY = k8;
            Model.vertexScreenZ[vertex] = vertexZ - k2;
            if (vertexZ >= 50) {
                Model.vertexScreenX[vertex] = centerX + (vertexX << 9) / vertexZ;
                Model.vertexScreenY[vertex] = centerY + (vertexY << 9) / vertexZ;
            } else {
                Model.vertexScreenX[vertex] = -5000;
                flag = true;
            }
            if (flag || texturedTriangleCount > 0) {
                Model.vertexMovedX[vertex] = vertexX;
                Model.vertexMovedY[vertex] = vertexY;
                Model.vertexMovedZ[vertex] = vertexZ;
            }
        }
        try {
            method527(flag, flag1, i2);
        } catch (Exception exception) {
        }
    }

    private void method527(boolean flag, boolean flag1, int i) {
        for (int diagonal = 0; diagonal < diagonal3D; diagonal++) {
            Model.anIntArray1580[diagonal] = 0;
        }
        for (int triangle = 0; triangle < triangleCount; triangle++) {
            if (triangleDrawType == null || triangleDrawType[triangle] != -1) {
                int x = triangleX[triangle];
                int y = triangleY[triangle];
                int z = triangleZ[triangle];
                int screenXX = Model.vertexScreenX[x];
                int screenXY = Model.vertexScreenX[y];
                int screenXZ = Model.vertexScreenX[z];
                if (flag && (screenXX == -5000 || screenXY == -5000 || screenXZ == -5000)) {
                    Model.aBooleanArray1573[triangle] = true;
                    int j5 = (Model.vertexScreenZ[x] + Model.vertexScreenZ[y] + Model.vertexScreenZ[z]) / 3 + diagonal3DAboveOrigin;
                    Model.anIntArrayArray1581[j5][Model.anIntArray1580[j5]++] = triangle;
                } else {
                    if (flag1 && method530(Model.cursorX, Model.cursorY, Model.vertexScreenY[x], Model.vertexScreenY[y], Model.vertexScreenY[z], screenXX, screenXY, screenXZ)) {
                        Model.resourceIds[Model.resourceCount++] = i;
                        flag1 = false;
                    }
                    if ((screenXX - screenXY) * (Model.vertexScreenY[z] - Model.vertexScreenY[y]) - (Model.vertexScreenY[x] - Model.vertexScreenY[y]) * (screenXZ - screenXY) > 0) {
                        Model.aBooleanArray1573[triangle] = false;
                        Model.restrictEdges[triangle] = screenXX < 0 || screenXY < 0 || screenXZ < 0 || screenXX > Rasterizer.anInt1376 || screenXY > Rasterizer.anInt1376 || screenXZ > Rasterizer.anInt1376;
                        int k5 = (Model.vertexScreenZ[x] + Model.vertexScreenZ[y] + Model.vertexScreenZ[z]) / 3 + diagonal3DAboveOrigin;
                        Model.anIntArrayArray1581[k5][Model.anIntArray1580[k5]++] = triangle;
                    }
                }
            }
        }
        if (trianglePriorities == null) {
            for (int i1 = diagonal3D - 1; i1 >= 0; i1--) {
                int l1 = Model.anIntArray1580[i1];
                if (l1 > 0) {
                    int[] ai = Model.anIntArrayArray1581[i1];
                    for (int j3 = 0; j3 < l1; j3++) {
                        rasterize(ai[j3]);
                    }
                }
            }
            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            Model.anIntArray1582[j1] = 0;
            Model.anIntArray1586[j1] = 0;
        }
        for (int i2 = diagonal3D - 1; i2 >= 0; i2--) {
            int k2 = Model.anIntArray1580[i2];
            if (k2 > 0) {
                int[] ai1 = Model.anIntArrayArray1581[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    int l5 = trianglePriorities[l4];
                    int j6 = Model.anIntArray1582[l5]++;
                    Model.anIntArrayArray1583[l5][j6] = l4;
                    if (l5 < 10) {
                        Model.anIntArray1586[l5] += i2;
                    } else if (l5 == 10) {
                        Model.anIntArray1584[j6] = i2;
                    } else {
                        Model.anIntArray1585[j6] = i2;
                    }
                }
            }
        }
        int l2 = 0;
        if (Model.anIntArray1582[1] > 0 || Model.anIntArray1582[2] > 0) {
            l2 = (Model.anIntArray1586[1] + Model.anIntArray1586[2]) / (Model.anIntArray1582[1] + Model.anIntArray1582[2]);
        }
        int k3 = 0;
        if (Model.anIntArray1582[3] > 0 || Model.anIntArray1582[4] > 0) {
            k3 = (Model.anIntArray1586[3] + Model.anIntArray1586[4]) / (Model.anIntArray1582[3] + Model.anIntArray1582[4]);
        }
        int j4 = 0;
        if (Model.anIntArray1582[6] > 0 || Model.anIntArray1582[8] > 0) {
            j4 = (Model.anIntArray1586[6] + Model.anIntArray1586[8]) / (Model.anIntArray1582[6] + Model.anIntArray1582[8]);
        }
        int i6 = 0;
        int k6 = Model.anIntArray1582[10];
        int[] ai2 = Model.anIntArrayArray1583[10];
        int[] ai3 = Model.anIntArray1584;
        if (i6 == k6) {
            k6 = Model.anIntArray1582[11];
            ai2 = Model.anIntArrayArray1583[11];
            ai3 = Model.anIntArray1585;
        }
        int i5;
        if (i6 < k6) {
            i5 = ai3[i6];
        } else {
            i5 = -1000;
        }
        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                rasterize(ai2[i6++]);
                if (i6 == k6 && ai2 != Model.anIntArrayArray1583[11]) {
                    i6 = 0;
                    k6 = Model.anIntArray1582[11];
                    ai2 = Model.anIntArrayArray1583[11];
                    ai3 = Model.anIntArray1585;
                }
                if (i6 < k6) {
                    i5 = ai3[i6];
                } else {
                    i5 = -1000;
                }
            }
            while (l6 == 3 && i5 > k3) {
                rasterize(ai2[i6++]);
                if (i6 == k6 && ai2 != Model.anIntArrayArray1583[11]) {
                    i6 = 0;
                    k6 = Model.anIntArray1582[11];
                    ai2 = Model.anIntArrayArray1583[11];
                    ai3 = Model.anIntArray1585;
                }
                if (i6 < k6) {
                    i5 = ai3[i6];
                } else {
                    i5 = -1000;
                }
            }
            while (l6 == 5 && i5 > j4) {
                rasterize(ai2[i6++]);
                if (i6 == k6 && ai2 != Model.anIntArrayArray1583[11]) {
                    i6 = 0;
                    k6 = Model.anIntArray1582[11];
                    ai2 = Model.anIntArrayArray1583[11];
                    ai3 = Model.anIntArray1585;
                }
                if (i6 < k6) {
                    i5 = ai3[i6];
                } else {
                    i5 = -1000;
                }
            }
            int i7 = Model.anIntArray1582[l6];
            int[] ai4 = Model.anIntArrayArray1583[l6];
            for (int j7 = 0; j7 < i7; j7++) {
                rasterize(ai4[j7]);
            }
        }
        while (i5 != -1000) {
            rasterize(ai2[i6++]);
            if (i6 == k6 && ai2 != Model.anIntArrayArray1583[11]) {
                i6 = 0;
                ai2 = Model.anIntArrayArray1583[11];
                k6 = Model.anIntArray1582[11];
                ai3 = Model.anIntArray1585;
            }
            if (i6 < k6) {
                i5 = ai3[i6];
            } else {
                i5 = -1000;
            }
        }
    }

    private void rasterize(int i) {
        if (Model.aBooleanArray1573[i]) {
            method529(i);
            return;
        }
        int x = triangleX[i];
        int y = triangleY[i];
        int z = triangleZ[i];
        Rasterizer3D.restrictEdges = Model.restrictEdges[i];
        if (triangleAlpha == null) {
            Rasterizer3D.alpha = 0;
        } else {
            Rasterizer3D.alpha = triangleAlpha[i];
        }
        int drawType;
        if (triangleDrawType == null) {
            drawType = 0;
        } else {
            drawType = triangleDrawType[i] & 3;
        }
        if (drawType == 0) {
            Rasterizer3D.drawShadedTriangle(Model.vertexScreenY[x], Model.vertexScreenY[y], Model.vertexScreenY[z],
                    Model.vertexScreenX[x], Model.vertexScreenX[y], Model.vertexScreenX[z], triangleHSLA[i],
                    triangleHSLB[i], triangleHSLC[i]);
            return;
        }
        if (drawType == 1) {
            Rasterizer3D.drawFlatTriangle(Model.vertexScreenY[x], Model.vertexScreenY[y], Model.vertexScreenY[z],
                    Model.vertexScreenX[x], Model.vertexScreenX[y], Model.vertexScreenX[z],
                    Model.HSLtoRGB[triangleHSLA[i]]);
            return;
        }
        if (drawType == 2) {
            int triangle = triangleDrawType[i] >> 2;
            int texturedTrianglePointsX = this.texturedTrianglePointsX[triangle];
            int texturedTrianglePointsY = this.texturedTrianglePointsY[triangle];
            int texturedTrianglePointsZ = this.texturedTrianglePointsZ[triangle];
            Rasterizer3D.drawTexturedTriangle(Model.vertexScreenY[x], Model.vertexScreenY[y], Model.vertexScreenY[z],
                    Model.vertexScreenX[x], Model.vertexScreenX[y], Model.vertexScreenX[z], triangleHSLA[i],
                    triangleHSLB[i], triangleHSLC[i], Model.vertexMovedX[texturedTrianglePointsX], Model.vertexMovedX[texturedTrianglePointsY],
                    Model.vertexMovedX[texturedTrianglePointsZ], Model.vertexMovedY[texturedTrianglePointsX], Model.vertexMovedY[texturedTrianglePointsY],
                    Model.vertexMovedY[texturedTrianglePointsZ], Model.vertexMovedZ[texturedTrianglePointsX], Model.vertexMovedZ[texturedTrianglePointsY],
                    Model.vertexMovedZ[texturedTrianglePointsZ], triangleColors[i]);
            return;
        }
        int triangle = triangleDrawType[i] >> 2;
        int texturedTrianglePointsX = this.texturedTrianglePointsX[triangle];
        int texturedTrianglePointsY = this.texturedTrianglePointsY[triangle];
        int texturedTrianglePointsZ = this.texturedTrianglePointsZ[triangle];
        Rasterizer3D.drawTexturedTriangle(Model.vertexScreenY[x], Model.vertexScreenY[y], Model.vertexScreenY[z],
                Model.vertexScreenX[x], Model.vertexScreenX[y], Model.vertexScreenX[z], triangleHSLA[i],
                triangleHSLA[i], triangleHSLA[i], Model.vertexMovedX[texturedTrianglePointsX], Model.vertexMovedX[texturedTrianglePointsY],
                Model.vertexMovedX[texturedTrianglePointsZ], Model.vertexMovedY[texturedTrianglePointsX], Model.vertexMovedY[texturedTrianglePointsY],
                Model.vertexMovedY[texturedTrianglePointsZ], Model.vertexMovedZ[texturedTrianglePointsX], Model.vertexMovedZ[texturedTrianglePointsY],
                Model.vertexMovedZ[texturedTrianglePointsZ], triangleColors[i]);
    }

    private void method529(int i) {
        int centerX = Rasterizer3D.centerX;
        int centerY = Rasterizer3D.centerY;
        int count = 0;
        int x = triangleX[i];
        int y = triangleY[i];
        int z = triangleZ[i];
        int movedX = Model.vertexMovedZ[x];
        int movedY = Model.vertexMovedZ[y];
        int movedZ = Model.vertexMovedZ[z];
        if (movedX >= 50) {
            Model.xPosition[count] = Model.vertexScreenX[x];
            Model.yPosition[count] = Model.vertexScreenY[x];
            Model.zPosition[count++] = triangleHSLA[i];
        } else {
            int k2 = Model.vertexMovedX[x];
            int k3 = Model.vertexMovedY[x];
            int k4 = triangleHSLA[i];
            if (movedZ >= 50) {
                int k5 = (50 - movedX) * Model.anIntArray1601[movedZ - movedX];
                Model.xPosition[count] = centerX + (k2 + ((Model.vertexMovedX[z] - k2) * k5 >> 16) << 9) / 50;
                Model.yPosition[count] = centerY + (k3 + ((Model.vertexMovedY[z] - k3) * k5 >> 16) << 9) / 50;
                Model.zPosition[count++] = k4 + ((triangleHSLC[i] - k4) * k5 >> 16);
            }
            if (movedY >= 50) {
                int l5 = (50 - movedX) * Model.anIntArray1601[movedY - movedX];
                Model.xPosition[count] = centerX + (k2 + ((Model.vertexMovedX[y] - k2) * l5 >> 16) << 9) / 50;
                Model.yPosition[count] = centerY + (k3 + ((Model.vertexMovedY[y] - k3) * l5 >> 16) << 9) / 50;
                Model.zPosition[count++] = k4 + ((triangleHSLB[i] - k4) * l5 >> 16);
            }
        }
        if (movedY >= 50) {
            Model.xPosition[count] = Model.vertexScreenX[y];
            Model.yPosition[count] = Model.vertexScreenY[y];
            Model.zPosition[count++] = triangleHSLB[i];
        } else {
            int l2 = Model.vertexMovedX[y];
            int l3 = Model.vertexMovedY[y];
            int l4 = triangleHSLB[i];
            if (movedX >= 50) {
                int i6 = (50 - movedY) * Model.anIntArray1601[movedX - movedY];
                Model.xPosition[count] = centerX + (l2 + ((Model.vertexMovedX[x] - l2) * i6 >> 16) << 9) / 50;
                Model.yPosition[count] = centerY + (l3 + ((Model.vertexMovedY[x] - l3) * i6 >> 16) << 9) / 50;
                Model.zPosition[count++] = l4 + ((triangleHSLA[i] - l4) * i6 >> 16);
            }
            if (movedZ >= 50) {
                int j6 = (50 - movedY) * Model.anIntArray1601[movedZ - movedY];
                Model.xPosition[count] = centerX + (l2 + ((Model.vertexMovedX[z] - l2) * j6 >> 16) << 9) / 50;
                Model.yPosition[count] = centerY + (l3 + ((Model.vertexMovedY[z] - l3) * j6 >> 16) << 9) / 50;
                Model.zPosition[count++] = l4 + ((triangleHSLC[i] - l4) * j6 >> 16);
            }
        }
        if (movedZ >= 50) {
            Model.xPosition[count] = Model.vertexScreenX[z];
            Model.yPosition[count] = Model.vertexScreenY[z];
            Model.zPosition[count++] = triangleHSLC[i];
        } else {
            int i3 = Model.vertexMovedX[z];
            int i4 = Model.vertexMovedY[z];
            int i5 = triangleHSLC[i];
            if (movedY >= 50) {
                int k6 = (50 - movedZ) * Model.anIntArray1601[movedY - movedZ];
                Model.xPosition[count] = centerX + (i3 + ((Model.vertexMovedX[y] - i3) * k6 >> 16) << 9) / 50;
                Model.yPosition[count] = centerY + (i4 + ((Model.vertexMovedY[y] - i4) * k6 >> 16) << 9) / 50;
                Model.zPosition[count++] = i5 + ((triangleHSLB[i] - i5) * k6 >> 16);
            }
            if (movedX >= 50) {
                int l6 = (50 - movedZ) * Model.anIntArray1601[movedX - movedZ];
                Model.xPosition[count] = centerX + (i3 + ((Model.vertexMovedX[x] - i3) * l6 >> 16) << 9) / 50;
                Model.yPosition[count] = centerY + (i4 + ((Model.vertexMovedY[x] - i4) * l6 >> 16) << 9) / 50;
                Model.zPosition[count++] = i5 + ((triangleHSLA[i] - i5) * l6 >> 16);
            }
        }
        int j3 = Model.xPosition[0];
        int j4 = Model.xPosition[1];
        int j5 = Model.xPosition[2];
        int i7 = Model.yPosition[0];
        int j7 = Model.yPosition[1];
        int k7 = Model.yPosition[2];
        if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
            Rasterizer3D.restrictEdges = false;
            if (count == 3) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer.anInt1376 || j4 > Rasterizer.anInt1376 || j5 > Rasterizer.anInt1376) {
                    Rasterizer3D.restrictEdges = true;
                }
                int drawType;
                if (triangleDrawType == null) {
                    drawType = 0;
                } else {
                    drawType = triangleDrawType[i] & 3;
                }
                if (drawType == 0) {
                    Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, Model.zPosition[0], Model.zPosition[1], Model.zPosition[2]);
                } else if (drawType == 1) {
                    Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, Model.HSLtoRGB[triangleHSLA[i]]);
                } else if (drawType == 2) {
                    int j8 = triangleDrawType[i] >> 2;
                    int k9 = texturedTrianglePointsX[j8];
                    int k10 = texturedTrianglePointsY[j8];
                    int k11 = texturedTrianglePointsZ[j8];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, Model.zPosition[0], Model.zPosition[1],
                            Model.zPosition[2], Model.vertexMovedX[k9], Model.vertexMovedX[k10],
                            Model.vertexMovedX[k11], Model.vertexMovedY[k9], Model.vertexMovedY[k10],
                            Model.vertexMovedY[k11], Model.vertexMovedZ[k9], Model.vertexMovedZ[k10],
                            Model.vertexMovedZ[k11], triangleColors[i]);
                } else {
                    int k8 = triangleDrawType[i] >> 2;
                    int l9 = texturedTrianglePointsX[k8];
                    int l10 = texturedTrianglePointsY[k8];
                    int l11 = texturedTrianglePointsZ[k8];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, triangleHSLA[i], triangleHSLA[i],
                            triangleHSLA[i], Model.vertexMovedX[l9], Model.vertexMovedX[l10],
                            Model.vertexMovedX[l11], Model.vertexMovedY[l9], Model.vertexMovedY[l10],
                            Model.vertexMovedY[l11], Model.vertexMovedZ[l9], Model.vertexMovedZ[l10],
                            Model.vertexMovedZ[l11], triangleColors[i]);
                }
            }
            if (count == 4) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer.anInt1376 || j4 > Rasterizer.anInt1376 || j5 > Rasterizer.anInt1376 || Model.xPosition[3] < 0 || Model.xPosition[3] > Rasterizer.anInt1376) {
                    Rasterizer3D.restrictEdges = true;
                }
                int drawType;
                if (triangleDrawType == null) {
                    drawType = 0;
                } else {
                    drawType = triangleDrawType[i] & 3;
                }
                if (drawType == 0) {
                    Rasterizer3D.drawShadedTriangle(i7, j7, k7, j3, j4, j5, Model.zPosition[0], Model.zPosition[1], Model.zPosition[2]);
                    Rasterizer3D.drawShadedTriangle(i7, k7, Model.yPosition[3], j3, j5, Model.xPosition[3], Model.zPosition[0], Model.zPosition[2], Model.zPosition[3]);
                    return;
                }
                if (drawType == 1) {
                    int color = Model.HSLtoRGB[triangleHSLA[i]];
                    Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, color);
                    Rasterizer3D.drawFlatTriangle(i7, k7, Model.yPosition[3], j3, j5, Model.xPosition[3], color);
                    return;
                }
                if (drawType == 2) {
                    int i9 = triangleDrawType[i] >> 2;
                    int i10 = texturedTrianglePointsX[i9];
                    int i11 = texturedTrianglePointsY[i9];
                    int i12 = texturedTrianglePointsZ[i9];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, Model.zPosition[0], Model.zPosition[1],
                            Model.zPosition[2], Model.vertexMovedX[i10], Model.vertexMovedX[i11],
                            Model.vertexMovedX[i12], Model.vertexMovedY[i10], Model.vertexMovedY[i11],
                            Model.vertexMovedY[i12], Model.vertexMovedZ[i10], Model.vertexMovedZ[i11],
                            Model.vertexMovedZ[i12], triangleColors[i]);
                    Rasterizer3D.drawTexturedTriangle(i7, k7, Model.yPosition[3], j3, j5, Model.xPosition[3],
                            Model.zPosition[0], Model.zPosition[2], Model.zPosition[3],
                            Model.vertexMovedX[i10], Model.vertexMovedX[i11], Model.vertexMovedX[i12],
                            Model.vertexMovedY[i10], Model.vertexMovedY[i11], Model.vertexMovedY[i12],
                            Model.vertexMovedZ[i10], Model.vertexMovedZ[i11], Model.vertexMovedZ[i12],
                            triangleColors[i]);
                    return;
                }
                int j9 = triangleDrawType[i] >> 2;
                int j10 = texturedTrianglePointsX[j9];
                int j11 = texturedTrianglePointsY[j9];
                int j12 = texturedTrianglePointsZ[j9];
                Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, triangleHSLA[i], triangleHSLA[i],
                        triangleHSLA[i], Model.vertexMovedX[j10], Model.vertexMovedX[j11],
                        Model.vertexMovedX[j12], Model.vertexMovedY[j10], Model.vertexMovedY[j11],
                        Model.vertexMovedY[j12], Model.vertexMovedZ[j10], Model.vertexMovedZ[j11],
                        Model.vertexMovedZ[j12], triangleColors[i]);
                Rasterizer3D.drawTexturedTriangle(i7, k7, Model.yPosition[3], j3, j5, Model.xPosition[3],
                        triangleHSLA[i], triangleHSLA[i], triangleHSLA[i], Model.vertexMovedX[j10],
                        Model.vertexMovedX[j11], Model.vertexMovedX[j12], Model.vertexMovedY[j10],
                        Model.vertexMovedY[j11], Model.vertexMovedY[j12], Model.vertexMovedZ[j10],
                        Model.vertexMovedZ[j11], Model.vertexMovedZ[j12], triangleColors[i]);
            }
        }
    }

    private boolean method530(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        if (j < k && j < l && j < i1) {
            return false;
        }
        if (j > k && j > l && j > i1) {
            return false;
        }
        if (i < j1 && i < k1 && i < l1) {
            return false;
        }
        return i <= j1 || i <= k1 || i <= l1;
    }
}
