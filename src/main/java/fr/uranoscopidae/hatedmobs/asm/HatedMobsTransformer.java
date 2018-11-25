package fr.uranoscopidae.hatedmobs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class HatedMobsTransformer implements IClassTransformer
{
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if(name.startsWith("fr.uranoscopidae.hatedmobs.common.Falsified"))
        {
            return patchFalsifiedWorldClasses(name, basicClass);
        }
        return basicClass;
    }

    private byte[] patchFalsifiedWorldClasses(String name, byte[] bytes)
    {
        System.out.println("Patching "+name+"...");
        boolean isFalsifiedChunk = name.contains("Chunk");
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        reader.accept(new Transformer(name, writer, isFalsifiedChunk), 0);
        byte[] result = writer.toByteArray();
        try
        {
            FileOutputStream out = new FileOutputStream("./"+name+".transformed.class");
            out.write(result);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    private class Transformer extends ClassVisitor
    {
        private final String className;
        private final boolean isFalsifiedChunk;
        private String delegateDesc;

        public Transformer(String name, ClassWriter writer, boolean isFalsifiedChunk)
        {
            super(Opcodes.ASM5, writer);
            this.className = name;
            this.isFalsifiedChunk = isFalsifiedChunk;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value)
        {
            if(name.equals("delegate"))
                delegateDesc = desc;
            return super.visitField(access, name, desc, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
            if(name.equals("<init>"))
            {
                return new ConstructorTransformer(className, delegateDesc, isFalsifiedChunk, visitor);
            }
            return visitor;
        }
    }

    private class ConstructorTransformer extends MethodVisitor implements Opcodes
    {
        private final String className;
        private final String worldDesc;
        private final boolean isFalsifiedChunk;

        public ConstructorTransformer(String className, String worldDesc, boolean isFalsifiedChunk, MethodVisitor mv)
        {
            super(Opcodes.ASM5, mv);
            this.className = className;
            this.worldDesc = worldDesc;
            this.isFalsifiedChunk = isFalsifiedChunk;
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
        {
            if(name.equals("<init>")) // call to 'super()'
            {
                injectAssignmentCode();
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        private void injectAssignmentCode()
        {
            visitVarInsn(ALOAD, 0);
            if(isFalsifiedChunk)
            {
                visitVarInsn(ALOAD, 3);
            }
            else
            {
                visitVarInsn(ALOAD, 1);
            }
            visitFieldInsn(PUTFIELD, className.replace(".", "/"), "delegate", worldDesc);
            System.out.println("Injected 'delegate' assignment before call to super() in "+className+" delegate desc is "+worldDesc);
        }

    }
}
