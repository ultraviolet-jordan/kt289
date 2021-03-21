package com.kt289.client.cache.definition;

import com.kt289.client.graphic.ImageRGB;
import com.kt289.client.graphic.TypeFace;
import com.kt289.client.render.Animation;
import com.kt289.client.render.Model;
import com.kt289.util.buffer.Buffer;
import com.kt289.cache.Archive;
import com.kt289.client.Client;
import com.kt289.util.SignLink;
import com.kt289.util.TextUtils;
import com.kt289.util.aggregation.Cache;

public class Widget {

    public static Widget[] aClass5Array100;
    private static final Cache aClass39_162 = new Cache(30);
    private static Cache spriteCache;
    private final byte aByte98;
    private int anInt99;
    public int[] anIntArray101;
    public int[] anIntArray102;
    public int anInt103;
    public int anInt104;
    public int anInt105;
    public int anInt106;
    public int anInt107;
    public int anInt108;
    public int anInt109;
    public int anInt110;
    public int anInt111;
    public byte aByte112;
    public int anInt113;
    public int anInt114;
    public int[][] anIntArrayArray115;
    public int[] anIntArray116;
    public int[] anIntArray117;
    public int anInt118;
    public int anInt119;
    public int anInt120;
    public boolean aBoolean121;
    public int[] anIntArray122;
    public int[] anIntArray123;
    public int[] anIntArray124;
    private int anInt125;
    private boolean aBoolean126;
    public boolean aBoolean127;
    public boolean aBoolean128;
    public boolean aBoolean129;
    public boolean aBoolean130;
    public int anInt131;
    public int anInt132;
    public ImageRGB[] aClass44_Sub3_Sub1_Sub2Array133;
    public int[] anIntArray134;
    public int[] anIntArray135;
    public String[] aStringArray136;
    public boolean aBoolean137;
    public boolean aBoolean138;
    public boolean aBoolean139;
    public TypeFace aClass44_Sub3_Sub1_Sub4_140;
    public String aString141;
    public String aString142;
    public int anInt143;
    public int anInt144;
    public int anInt145;
    public int anInt146;
    public ImageRGB aClass44_Sub3_Sub1_Sub2_147;
    public ImageRGB aClass44_Sub3_Sub1_Sub2_148;
    public int anInt149;
    public int anInt150;
    private int anInt151;
    private int anInt152;
    public int anInt153;
    public int anInt154;
    public int anInt155;
    public int anInt156;
    public int anInt157;
    public String aString158;
    public String aString159;
    public int anInt160;
    public String aString161;

    public Widget() {
        aByte98 = 4;
    }

    public static void method181(TypeFace[] aclass44_sub3_sub1_sub4, Archive mediaArchive, int i, Archive widgetArchive) {
        try {
            Widget.spriteCache = new Cache(50000);
            Buffer class44_sub3_sub2 = new Buffer(widgetArchive.decompressFile("data"));
            int j = -1;
            int k = class44_sub3_sub2.readUnsignedShort();
            if (i != 0) {
                return;
            }
            Widget.aClass5Array100 = new Widget[k];
            while (class44_sub3_sub2.offset < class44_sub3_sub2.payload.length) {
                int l = class44_sub3_sub2.readUnsignedShort();
                if (l == 65535) {
                    j = class44_sub3_sub2.readUnsignedShort();
                    l = class44_sub3_sub2.readUnsignedShort();
                }
                Widget class5 = Widget.aClass5Array100[l] = new Widget();
                class5.anInt105 = l;
                class5.anInt106 = j;
                class5.anInt107 = class44_sub3_sub2.readUnsignedByte();
                class5.anInt108 = class44_sub3_sub2.readUnsignedByte();
                class5.anInt109 = class44_sub3_sub2.readUnsignedShort();
                class5.anInt110 = class44_sub3_sub2.readUnsignedShort();
                class5.anInt111 = class44_sub3_sub2.readUnsignedShort();
                class5.aByte112 = (byte) class44_sub3_sub2.readUnsignedByte();
                class5.anInt118 = class44_sub3_sub2.readUnsignedByte();
                if (class5.anInt118 != 0) {
                    class5.anInt118 = (class5.anInt118 - 1 << 8) + class44_sub3_sub2.readUnsignedByte();
                } else {
                    class5.anInt118 = -1;
                }
                int j1 = class44_sub3_sub2.readUnsignedByte();
                if (j1 > 0) {
                    class5.anIntArray116 = new int[j1];
                    class5.anIntArray117 = new int[j1];
                    for (int k1 = 0; k1 < j1; k1++) {
                        class5.anIntArray116[k1] = class44_sub3_sub2.readUnsignedByte();
                        class5.anIntArray117[k1] = class44_sub3_sub2.readUnsignedShort();
                    }
                }
                int l1 = class44_sub3_sub2.readUnsignedByte();
                if (l1 > 0) {
                    class5.anIntArrayArray115 = new int[l1][];
                    for (int i2 = 0; i2 < l1; i2++) {
                        int j3 = class44_sub3_sub2.readUnsignedShort();
                        class5.anIntArrayArray115[i2] = new int[j3];
                        for (int i5 = 0; i5 < j3; i5++) {
                            class5.anIntArrayArray115[i2][i5] = class44_sub3_sub2.readUnsignedShort();
                        }
                    }
                }
                if (class5.anInt107 == 0) {
                    class5.anInt119 = class44_sub3_sub2.readUnsignedShort();
                    class5.aBoolean121 = class44_sub3_sub2.readUnsignedByte() == 1;
                    int j2 = class44_sub3_sub2.readUnsignedShort();
                    class5.anIntArray122 = new int[j2];
                    class5.anIntArray123 = new int[j2];
                    class5.anIntArray124 = new int[j2];
                    for (int k3 = 0; k3 < j2; k3++) {
                        class5.anIntArray122[k3] = class44_sub3_sub2.readUnsignedShort();
                        class5.anIntArray123[k3] = class44_sub3_sub2.readShort();
                        class5.anIntArray124[k3] = class44_sub3_sub2.readShort();
                    }
                }
                if (class5.anInt107 == 1) {
                    class5.anInt125 = class44_sub3_sub2.readUnsignedShort();
                    class5.aBoolean126 = class44_sub3_sub2.readUnsignedByte() == 1;
                }
                if (class5.anInt107 == 2) {
                    class5.anIntArray101 = new int[class5.anInt110 * class5.anInt111];
                    class5.anIntArray102 = new int[class5.anInt110 * class5.anInt111];
                    class5.aBoolean127 = class44_sub3_sub2.readUnsignedByte() == 1;
                    class5.aBoolean128 = class44_sub3_sub2.readUnsignedByte() == 1;
                    class5.aBoolean129 = class44_sub3_sub2.readUnsignedByte() == 1;
                    class5.aBoolean130 = class44_sub3_sub2.readUnsignedByte() == 1;
                    class5.anInt131 = class44_sub3_sub2.readUnsignedByte();
                    class5.anInt132 = class44_sub3_sub2.readUnsignedByte();
                    class5.anIntArray134 = new int[20];
                    class5.anIntArray135 = new int[20];
                    class5.aClass44_Sub3_Sub1_Sub2Array133 = new ImageRGB[20];
                    for (int k2 = 0; k2 < 20; k2++) {
                        int l3 = class44_sub3_sub2.readUnsignedByte();
                        if (l3 == 1) {
                            class5.anIntArray134[k2] = class44_sub3_sub2.readShort();
                            class5.anIntArray135[k2] = class44_sub3_sub2.readShort();
                            String s1 = class44_sub3_sub2.readString();
                            if (mediaArchive != null && s1.length() > 0) {
                                int j5 = s1.lastIndexOf(",");
                                class5.aClass44_Sub3_Sub1_Sub2Array133[k2] = Widget.method186(false,
                                        Integer.parseInt(s1.substring(j5 + 1)), mediaArchive, s1.substring(0, j5));
                            }
                        }
                    }
                    class5.aStringArray136 = new String[5];
                    for (int i4 = 0; i4 < 5; i4++) {
                        class5.aStringArray136[i4] = class44_sub3_sub2.readString();
                        if (class5.aStringArray136[i4].length() == 0) {
                            class5.aStringArray136[i4] = null;
                        }
                    }
                }
                if (class5.anInt107 == 3) {
                    class5.aBoolean137 = class44_sub3_sub2.readUnsignedByte() == 1;
                }
                if (class5.anInt107 == 4 || class5.anInt107 == 1) {
                    class5.aBoolean138 = class44_sub3_sub2.readUnsignedByte() == 1;
                    int l2 = class44_sub3_sub2.readUnsignedByte();
                    if (aclass44_sub3_sub1_sub4 != null) {
                        class5.aClass44_Sub3_Sub1_Sub4_140 = aclass44_sub3_sub1_sub4[l2];
                    }
                    class5.aBoolean139 = class44_sub3_sub2.readUnsignedByte() == 1;
                }
                if (class5.anInt107 == 4) {
                    class5.aString141 = class44_sub3_sub2.readString();
                    class5.aString142 = class44_sub3_sub2.readString();
                }
                if (class5.anInt107 == 1 || class5.anInt107 == 3 || class5.anInt107 == 4) {
                    class5.anInt143 = class44_sub3_sub2.readInt();
                }
                if (class5.anInt107 == 3 || class5.anInt107 == 4) {
                    class5.anInt144 = class44_sub3_sub2.readInt();
                    class5.anInt145 = class44_sub3_sub2.readInt();
                    class5.anInt146 = class44_sub3_sub2.readInt();
                }
                if (class5.anInt107 == 5) {
                    String s = class44_sub3_sub2.readString();
                    if (mediaArchive != null && s.length() > 0) {
                        int j4 = s.lastIndexOf(",");
                        class5.aClass44_Sub3_Sub1_Sub2_147 = Widget.method186(false,
                                Integer.parseInt(s.substring(j4 + 1)), mediaArchive, s.substring(0, j4));
                    }
                    s = class44_sub3_sub2.readString();
                    if (mediaArchive != null && s.length() > 0) {
                        int k4 = s.lastIndexOf(",");
                        class5.aClass44_Sub3_Sub1_Sub2_148 = Widget.method186(false,
                                Integer.parseInt(s.substring(k4 + 1)), mediaArchive, s.substring(0, k4));
                    }
                }
                if (class5.anInt107 == 6) {
                    int i1 = class44_sub3_sub2.readUnsignedByte();
                    if (i1 != 0) {
                        class5.anInt149 = 1;
                        class5.anInt150 = (i1 - 1 << 8) + class44_sub3_sub2.readUnsignedByte();
                    }
                    i1 = class44_sub3_sub2.readUnsignedByte();
                    if (i1 != 0) {
                        class5.anInt151 = 1;
                        class5.anInt152 = (i1 - 1 << 8) + class44_sub3_sub2.readUnsignedByte();
                    }
                    i1 = class44_sub3_sub2.readUnsignedByte();
                    if (i1 != 0) {
                        class5.anInt153 = (i1 - 1 << 8) + class44_sub3_sub2.readUnsignedByte();
                    } else {
                        class5.anInt153 = -1;
                    }
                    i1 = class44_sub3_sub2.readUnsignedByte();
                    if (i1 != 0) {
                        class5.anInt154 = (i1 - 1 << 8) + class44_sub3_sub2.readUnsignedByte();
                    } else {
                        class5.anInt154 = -1;
                    }
                    class5.anInt155 = class44_sub3_sub2.readUnsignedShort();
                    class5.anInt156 = class44_sub3_sub2.readUnsignedShort();
                    class5.anInt157 = class44_sub3_sub2.readUnsignedShort();
                }
                if (class5.anInt107 == 7) {
                    class5.anIntArray101 = new int[class5.anInt110 * class5.anInt111];
                    class5.anIntArray102 = new int[class5.anInt110 * class5.anInt111];
                    class5.aBoolean138 = class44_sub3_sub2.readUnsignedByte() == 1;
                    int i3 = class44_sub3_sub2.readUnsignedByte();
                    if (aclass44_sub3_sub1_sub4 != null) {
                        class5.aClass44_Sub3_Sub1_Sub4_140 = aclass44_sub3_sub1_sub4[i3];
                    }
                    class5.aBoolean139 = class44_sub3_sub2.readUnsignedByte() == 1;
                    class5.anInt143 = class44_sub3_sub2.readInt();
                    class5.anInt131 = class44_sub3_sub2.readShort();
                    class5.anInt132 = class44_sub3_sub2.readShort();
                    class5.aBoolean128 = class44_sub3_sub2.readUnsignedByte() == 1;
                    class5.aStringArray136 = new String[5];
                    for (int l4 = 0; l4 < 5; l4++) {
                        class5.aStringArray136[l4] = class44_sub3_sub2.readString();
                        if (class5.aStringArray136[l4].length() == 0) {
                            class5.aStringArray136[l4] = null;
                        }
                    }
                }
                if (class5.anInt108 == 2 || class5.anInt107 == 2) {
                    class5.aString158 = class44_sub3_sub2.readString();
                    class5.aString159 = class44_sub3_sub2.readString();
                    class5.anInt160 = class44_sub3_sub2.readUnsignedShort();
                }
                if (class5.anInt108 == 1 || class5.anInt108 == 4 || class5.anInt108 == 5 || class5.anInt108 == 6) {
                    class5.aString161 = class44_sub3_sub2.readString();
                    if (class5.aString161.length() == 0) {
                        if (class5.anInt108 == 1) {
                            class5.aString161 = "Ok";
                        }
                        if (class5.anInt108 == 4) {
                            class5.aString161 = "Select";
                        }
                        if (class5.anInt108 == 5) {
                            class5.aString161 = "Select";
                        }
                        if (class5.anInt108 == 6) {
                            class5.aString161 = "Continue";
                        }
                    }
                }
            }
            Widget.spriteCache = null;
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("87101, " + aclass44_sub3_sub1_sub4 + ", " + mediaArchive + ", " + i + ", "
                    + widgetArchive + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public static void method185(int i, Model class44_sub3_sub4_sub4, int j, int k) {
        try {
            Widget.aClass39_162.clear();
            if (j != 9) {
                for (int l = 1; l > 0; l++) {
                }
            }
            if (class44_sub3_sub4_sub4 != null && i != 4) {
                Widget.aClass39_162.put((i << 16) + k, class44_sub3_sub4_sub4);
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("27428, " + i + ", " + class44_sub3_sub4_sub4 + ", " + j + ", " + k + ", "
                    + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    private static ImageRGB method186(boolean flag, int i, Archive class47, String s) {
        try {
            if (flag) {
                throw new NullPointerException();
            }
            long l = (TextUtils.method552(-5562, s) << 8) + i;
            ImageRGB class44_sub3_sub1_sub2 = (ImageRGB) Widget.spriteCache.get(l);
            if (class44_sub3_sub1_sub2 != null) {
                return class44_sub3_sub1_sub2;
            }
            try {
                class44_sub3_sub1_sub2 = new ImageRGB(class47, s, i);
                Widget.spriteCache.put(l, class44_sub3_sub1_sub2);
            } catch (Exception _ex) {
                return null;
            }
            return class44_sub3_sub1_sub2;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("90047, " + flag + ", " + i + ", " + class47 + ", " + s + ", "
                    + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public void method182(int i, int j, int k) {
        try {
            int l = anIntArray101[k];
            anIntArray101[k] = anIntArray101[j];
            if (i != 0) {
                return;
            } else {
                anIntArray101[j] = l;
                l = anIntArray102[k];
                anIntArray102[k] = anIntArray102[j];
                anIntArray102[j] = l;
                return;
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("35687, " + i + ", " + j + ", " + k + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public Model method183(int i, int j, int k, boolean flag) {
        try {
            if (i != 0) {
                for (int l = 1; l > 0; l++) {
                }
            }
            Model class44_sub3_sub4_sub4;
            if (flag) {
                class44_sub3_sub4_sub4 = method184(anInt151, anInt152);
            } else {
                class44_sub3_sub4_sub4 = method184(anInt149, anInt150);
            }
            if (class44_sub3_sub4_sub4 == null) {
                return null;
            }
            if (k == -1 && j == -1 && class44_sub3_sub4_sub4.triangleColors == null) {
                return class44_sub3_sub4_sub4;
            }
            Model class44_sub3_sub4_sub4_1 = new Model(class44_sub3_sub4_sub4, Animation.isNullFrame(k, 0)
                    & Animation.isNullFrame(j, 0), false, true);
            if (k != -1 || j != -1) {
                class44_sub3_sub4_sub4_1.createBones();
            }
            if (k != -1) {
                class44_sub3_sub4_sub4_1.applyTransformation(k);
            }
            if (j != -1) {
                class44_sub3_sub4_sub4_1.applyTransformation(j);
            }
            class44_sub3_sub4_sub4_1.applyLighting(64, 768, -50, -10, -50, true);
            return class44_sub3_sub4_sub4_1;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("4323, " + i + ", " + j + ", " + k + ", " + flag + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private Model method184(int i, int j) {
        Model class44_sub3_sub4_sub4 = (Model) Widget.aClass39_162.get((i << 16) + j);
        if (class44_sub3_sub4_sub4 != null) {
            return class44_sub3_sub4_sub4;
        }
        if (i == 1) {
            class44_sub3_sub4_sub4 = Model.getModel(j);
        }
        if (i == 2) {
            class44_sub3_sub4_sub4 = NPCType.getDefinition(j).getHeadModel();
        }
        if (i == 3) {
            class44_sub3_sub4_sub4 = Client.localPlayer.method539(false);
        }
        if (i == 4) {
            class44_sub3_sub4_sub4 = ItemDefinition.method220(j).method225(true, 50);
        }
        if (i == 5) {
            class44_sub3_sub4_sub4 = null;
        }
        if (class44_sub3_sub4_sub4 != null) {
            Widget.aClass39_162.put((i << 16) + j, class44_sub3_sub4_sub4);
        }
        return class44_sub3_sub4_sub4;
    }

}
