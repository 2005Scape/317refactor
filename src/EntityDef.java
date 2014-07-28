// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class EntityDef
{

    public static EntityDef forID(int i)
    {
        for(int j = 0; j < 20; j++)
            if(cache[j].type == (long)i)
                return cache[j];

        anInt56 = (anInt56 + 1) % 20;
        EntityDef entityDef = cache[anInt56] = new EntityDef();
        stream.currentOffset = streamIndices[i];
        entityDef.type = i;
        entityDef.readValues(stream);
        return entityDef;
    }

    public Model method160()
    {
        if(childrenIDs != null)
        {
            EntityDef entityDef = method161();
            if(entityDef == null)
                return null;
            else
                return entityDef.method160();
        }
        if(anIntArray73 == null)
            return null;
        boolean flag1 = false;
        for(int i = 0; i < anIntArray73.length; i++)
            if(!Model.isCached(anIntArray73[i]))
                flag1 = true;

        if(flag1)
            return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray73.length];
        for(int j = 0; j < anIntArray73.length; j++)
            aclass30_sub2_sub4_sub6s[j] = Model.getModel(anIntArray73[j]);

        Model model;
        if(aclass30_sub2_sub4_sub6s.length == 1)
            model = aclass30_sub2_sub4_sub6s[0];
        else
            model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
        if(anIntArray76 != null)
        {
            for(int k = 0; k < anIntArray76.length; k++)
                model.recolour(anIntArray76[k], anIntArray70[k]);

        }
        return model;
    }

    public EntityDef method161()
    {
        int j = -1;
        if(anInt57 != -1)
        {
            VarBit varBit = VarBit.cache[anInt57];
            int k = varBit.configId;
            int l = varBit.leastSignificantBit;
            int i1 = varBit.mostSignificantBit;
            int j1 = client.anIntArray1232[i1 - l];
            j = clientInstance.variousSettings[k] >> l & j1;
        } else
        if(anInt59 != -1)
            j = clientInstance.variousSettings[anInt59];
        if(j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1)
            return null;
        else
            return forID(childrenIDs[j]);
    }

    public static void unpackConfig(StreamLoader streamLoader)
    {
        stream = new Stream(streamLoader.getDataForName("npc.dat"));
        Stream stream2 = new Stream(streamLoader.getDataForName("npc.idx"));
        int totalNPCs = stream2.getUnsignedLEShort();
        streamIndices = new int[totalNPCs];
        int i = 2;
        for(int j = 0; j < totalNPCs; j++)
        {
            streamIndices[j] = i;
            i += stream2.getUnsignedLEShort();
        }

        cache = new EntityDef[20];
        for(int k = 0; k < 20; k++)
            cache[k] = new EntityDef();

    }

    public static void nullLoader()
    {
        mruNodes = null;
        streamIndices = null;
        cache = null;
        stream = null;
    }

    public Model method164(int j, int k, int ai[])
    {
        if(childrenIDs != null)
        {
            EntityDef entityDef = method161();
            if(entityDef == null)
                return null;
            else
                return entityDef.method164(j, k, ai);
        }
        Model model = (Model) mruNodes.insertFromCache(type);
        if(model == null)
        {
            boolean flag = false;
            for(int i1 = 0; i1 < anIntArray94.length; i1++)
                if(!Model.isCached(anIntArray94[i1]))
                    flag = true;

            if(flag)
                return null;
            Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray94.length];
            for(int j1 = 0; j1 < anIntArray94.length; j1++)
                aclass30_sub2_sub4_sub6s[j1] = Model.getModel(anIntArray94[j1]);

            if(aclass30_sub2_sub4_sub6s.length == 1)
                model = aclass30_sub2_sub4_sub6s[0];
            else
                model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
            if(anIntArray76 != null)
            {
                for(int k1 = 0; k1 < anIntArray76.length; k1++)
                    model.recolour(anIntArray76[k1], anIntArray70[k1]);

            }
            model.createBones();
            model.applyLighting(64 + anInt85, 850 + anInt92, -30, -50, -30, true);
            mruNodes.removeFromCache(model, type);
        }
        Model model_1 = Model.aModel_1621;
        model_1.replaceWithModel(model, Class36.isNullFrame(k) & Class36.isNullFrame(j));
        if(k != -1 && j != -1)
            model_1.mixAnimationFrames(ai, j, k);
        else
        if(k != -1)
            model_1.applyTransformation(k);
        if(anInt91 != 128 || anInt86 != 128)
            model_1.scaleT(anInt91, anInt91, anInt86);
        model_1.calculateDiagonals();
        model_1.triangleSkin = null;
        model_1.vertexSkin = null;
        if(boundaryDimension == 1)
            model_1.singleTile = true;
        return model_1;
    }

    private void readValues(Stream stream)
    {
        do
        {
            int i = stream.getUnsignedByte();
            if(i == 0)
                return;
            if(i == 1)
            {
                int j = stream.getUnsignedByte();
                anIntArray94 = new int[j];
                for(int j1 = 0; j1 < j; j1++)
                    anIntArray94[j1] = stream.getUnsignedLEShort();

            } else
            if(i == 2)
                name = stream.getString();
            else
            if(i == 3)
                description = stream.readBytes();
            else
            if(i == 12)
                boundaryDimension = stream.get();
            else
            if(i == 13)
                standAnimationId = stream.getUnsignedLEShort();
            else
            if(i == 14)
                walkAnimationId = stream.getUnsignedLEShort();
            else
            if(i == 17)
            {
                walkAnimationId = stream.getUnsignedLEShort();
                turnAboutAnimationId = stream.getUnsignedLEShort();
                turnRightAnimationId = stream.getUnsignedLEShort();
                turnLeftAnimationId = stream.getUnsignedLEShort();
            } else
            if(i >= 30 && i < 40)
            {
                if(actions == null)
                    actions = new String[5];
                actions[i - 30] = stream.getString();
                if(actions[i - 30].equalsIgnoreCase("hidden"))
                    actions[i - 30] = null;
            } else
            if(i == 40)
            {
                int k = stream.getUnsignedByte();
                anIntArray76 = new int[k];
                anIntArray70 = new int[k];
                for(int k1 = 0; k1 < k; k1++)
                {
                    anIntArray76[k1] = stream.getUnsignedLEShort();
                    anIntArray70[k1] = stream.getUnsignedLEShort();
                }

            } else
            if(i == 60)
            {
                int l = stream.getUnsignedByte();
                anIntArray73 = new int[l];
                for(int l1 = 0; l1 < l; l1++)
                    anIntArray73[l1] = stream.getUnsignedLEShort();

            } else
            if(i == 90)
                stream.getUnsignedLEShort();
            else
            if(i == 91)
                stream.getUnsignedLEShort();
            else
            if(i == 92)
                stream.getUnsignedLEShort();
            else
            if(i == 93)
                aBoolean87 = false;
            else
            if(i == 95)
                combatLevel = stream.getUnsignedLEShort();
            else
            if(i == 97)
                anInt91 = stream.getUnsignedLEShort();
            else
            if(i == 98)
                anInt86 = stream.getUnsignedLEShort();
            else
            if(i == 99)
                aBoolean93 = true;
            else
            if(i == 100)
                anInt85 = stream.get();
            else
            if(i == 101)
                anInt92 = stream.get() * 5;
            else
            if(i == 102)
                anInt75 = stream.getUnsignedLEShort();
            else
            if(i == 103)
                degreesToTurn = stream.getUnsignedLEShort();
            else
            if(i == 106)
            {
                anInt57 = stream.getUnsignedLEShort();
                if(anInt57 == 65535)
                    anInt57 = -1;
                anInt59 = stream.getUnsignedLEShort();
                if(anInt59 == 65535)
                    anInt59 = -1;
                int i1 = stream.getUnsignedByte();
                childrenIDs = new int[i1 + 1];
                for(int i2 = 0; i2 <= i1; i2++)
                {
                    childrenIDs[i2] = stream.getUnsignedLEShort();
                    if(childrenIDs[i2] == 65535)
                        childrenIDs[i2] = -1;
                }

            } else
            if(i == 107)
                aBoolean84 = false;
        } while(true);
    }

    private EntityDef()
    {
        turnLeftAnimationId = -1;
        anInt57 = -1;
        turnAboutAnimationId = -1;
        anInt59 = -1;
        combatLevel = -1;
        anInt64 = 1834;
        walkAnimationId = -1;
        boundaryDimension = 1;
        anInt75 = -1;
        standAnimationId = -1;
        type = -1L;
        degreesToTurn = 32;
        turnRightAnimationId = -1;
        aBoolean84 = true;
        anInt86 = 128;
        aBoolean87 = true;
        anInt91 = 128;
        aBoolean93 = false;
    }

    public int turnLeftAnimationId;
    private static int anInt56;
    private int anInt57;
    public int turnAboutAnimationId;
    private int anInt59;
    private static Stream stream;
    public int combatLevel;
    private final int anInt64;
    public String name;
    public String actions[];
    public int walkAnimationId;
    public byte boundaryDimension;
    private int[] anIntArray70;
    private static int[] streamIndices;
    private int[] anIntArray73;
    public int anInt75;
    private int[] anIntArray76;
    public int standAnimationId;
    public long type;
    public int degreesToTurn;
    private static EntityDef[] cache;
    public static client clientInstance;
    public int turnRightAnimationId;
    public boolean aBoolean84;
    private int anInt85;
    private int anInt86;
    public boolean aBoolean87;
    public int childrenIDs[];
    public byte description[];
    private int anInt91;
    private int anInt92;
    public boolean aBoolean93;
    private int[] anIntArray94;
    public static MRUNodes mruNodes = new MRUNodes(30);

}
