package net.jfdf.addon.splitter;

import net.jfdf.jfdf.blocks.CodeBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IfScope extends Scope {
    private int elseIndex = -1;

    public IfScope(CodeBlock ifBlock, CodeBlock ifStart, List<CodeBlock> content, CodeBlock[] elseBlocks, List<CodeBlock> elseContent, CodeBlock ifEnd) {
        super(new ArrayList<>());

        if (elseBlocks != null ^ elseContent != null) {
            throw new IllegalStateException("Both of else blocks and else content should be null or not null.");
        }

        totalLength = 4;
        if(elseBlocks != null) {
            if(elseBlocks.length == 3) {
                totalLength += 4;
            } else {
                throw new IllegalStateException("Else blocks array should have 3 blocks only.");
            }
        }

        this.content.add(ifBlock);
        this.content.add(ifStart);

        this.content.addAll(content);

        if(elseBlocks != null) {
            elseIndex = this.content.size();

            this.content.addAll(Arrays.asList(elseBlocks));
            this.content.addAll(elseContent);
        }

        this.content.add(ifEnd);

        for(CodeBlock block : content) {
            if(block instanceof Scope) {
                totalLength += ((Scope) block).totalLength;
            } else {
                totalLength += 2;
            }
        }

        if(elseContent != null) {
            for (CodeBlock block : elseContent) {
                if (block instanceof Scope) {
                    totalLength += ((Scope) block).totalLength;
                } else {
                    totalLength += 2;
                }
            }
        }
    }

    public IfScope(CodeBlock ifBlock, CodeBlock ifStart, List<CodeBlock> content, CodeBlock ifEnd) {
        this(ifBlock, ifStart, content, null, null, ifEnd);
    }

    @Override
    public List<CodeBlock> getContent() {
        if(elseIndex != -1) {
            return content.subList(2, elseIndex);
        }

        return content.subList(2, content.size() - 1);
    }

    public List<CodeBlock> getElseContent() {
        return content.subList(elseIndex + 3, content.size() - 1);
    }

    public List<CodeBlock> getStart() {
        return content.subList(0, 2);
    }

    public List<CodeBlock> getElse() {
        return content.subList(elseIndex, elseIndex + 3);
    }

    public CodeBlock getEnd() {
        return content.get(content.size() - 1);
    }

    public int getMinimumLength() {
        return elseIndex == -1 ? 6 : 12;
    }

    public boolean hasElse() {
        return elseIndex != -1;
    }
}
