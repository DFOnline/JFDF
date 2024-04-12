package net.jfdf.addon.splitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.IBlocksAddon;
import net.jfdf.jfdf.blocks.BracketBlock;
import net.jfdf.jfdf.blocks.CallFunctionBlock;
import net.jfdf.jfdf.blocks.CodeBlock;
import net.jfdf.jfdf.blocks.CodeHeader;
import net.jfdf.jfdf.blocks.ControlBlock;
import net.jfdf.jfdf.blocks.ElseBlock;
import net.jfdf.jfdf.blocks.FunctionBlock;
import net.jfdf.jfdf.blocks.IfEntityBlock;
import net.jfdf.jfdf.blocks.IfGameBlock;
import net.jfdf.jfdf.blocks.IfPlayerBlock;
import net.jfdf.jfdf.blocks.IfVariableBlock;
import net.jfdf.jfdf.blocks.RepeatBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tag;

public class CodeSplitter implements IBlocksAddon {
   public static CodeManager.PLOT_SIZE plotSize;

   public Map onPreGenerateLine(CodeHeader codeHeader, List blocks) {
      short var10000;
      switch (plotSize) {
         case BASIC:
            var10000 = 48;
            break;
         case LARGE:
            var10000 = 98;
            break;
         case MASSIVE:
            var10000 = 298;
            break;
         default:
            throw new IncompatibleClassChangeError();
      }

      int maxLength = var10000;
      if (CodeManager.checksumFunction) {
         maxLength -= 8;
      }

      String codeHeaderName = "<Unknown>";
      if (codeHeader instanceof FunctionBlock) {
         codeHeaderName = ((FunctionBlock)codeHeader).getName();
      }

      int blocksTotalLength = 0;
      Iterator var6 = blocks.iterator();

      while(true) {
         while(var6.hasNext()) {
            CodeBlock block = (CodeBlock)var6.next();
            if (!(block instanceof RepeatBlock) && !(block instanceof IfPlayerBlock) && !(block instanceof IfEntityBlock) && !(block instanceof IfGameBlock) && !(block instanceof IfVariableBlock)) {
               if (block instanceof BracketBlock) {
                  blocksTotalLength += ((BracketBlock)block).isClose ? 2 : 1;
               } else {
                  blocksTotalLength += 2;
               }
            } else {
               ++blocksTotalLength;
            }
         }

         if (blocksTotalLength <= maxLength) {
            return null;
         }

         List scopeCodeBlocksStack = new ArrayList();
         List scopeElseCodeBlocksStack = new ArrayList();
         List scopeStartStack = new ArrayList();
         List scopeElseStack = new ArrayList();
         scopeCodeBlocksStack.add(new ArrayList());
         scopeElseCodeBlocksStack.add((Object)null);
         List parent = (List)scopeCodeBlocksStack.get(0);

         for(int i = 0; i < blocks.size(); ++i) {
            CodeBlock block = (CodeBlock)blocks.get(i);
            if (block instanceof BracketBlock) {
               if (((BracketBlock)block).isClose) {
                  if (blocks.size() > i + 1 && blocks.get(i + 1) instanceof ElseBlock) {
                     scopeElseStack.set(scopeElseStack.size() - 1, i + 1);
                     scopeElseCodeBlocksStack.set(scopeElseCodeBlocksStack.size() - 1, new ArrayList());
                     parent = (List)scopeElseCodeBlocksStack.get(scopeElseCodeBlocksStack.size() - 1);
                  } else {
                     int startBracketIndex = (Integer)scopeStartStack.remove(scopeStartStack.size() - 1);
                     int elseIndex = (Integer)scopeElseStack.remove(scopeElseStack.size() - 1);
                     List scopeContent = (List)scopeCodeBlocksStack.remove(scopeCodeBlocksStack.size() - 1);
                     List scopeElseContent = (List)scopeElseCodeBlocksStack.remove(scopeElseCodeBlocksStack.size() - 1);
                     parent = (List)scopeElseCodeBlocksStack.get(scopeElseCodeBlocksStack.size() - 1);
                     if (parent == null) {
                        parent = (List)scopeCodeBlocksStack.get(scopeCodeBlocksStack.size() - 1);
                     }

                     if (elseIndex == -1) {
                        parent.add(new IfScope((CodeBlock)blocks.get(startBracketIndex - 1), (CodeBlock)blocks.get(startBracketIndex), scopeContent, block));
                     } else {
                        parent.add(new IfScope((CodeBlock)blocks.get(startBracketIndex - 1), (CodeBlock)blocks.get(startBracketIndex), scopeContent, new CodeBlock[]{(CodeBlock)blocks.get(elseIndex - 1), (CodeBlock)blocks.get(elseIndex), (CodeBlock)blocks.get(elseIndex + 1)}, scopeElseContent, block));
                     }
                  }
               } else if (!(blocks.get(i - 1) instanceof ElseBlock)) {
                  parent.remove(parent.size() - 1);
                  scopeStartStack.add(i);
                  scopeElseStack.add(-1);
                  scopeCodeBlocksStack.add(new ArrayList());
                  scopeElseCodeBlocksStack.add((Object)null);
                  parent = (List)scopeCodeBlocksStack.get(scopeCodeBlocksStack.size() - 1);
               }
            } else if (!(block instanceof ElseBlock)) {
               parent.add(block);
            }
         }

         Scope lineScope = new Scope((List)scopeCodeBlocksStack.get(0));
         Map splittedCode = new LinkedHashMap();
         blocks.clear();
         blocks.addAll(unwrapAndUpdateReturn(new Scope(splitScope(lineScope, codeHeaderName, splittedCode, maxLength, maxLength, 0)), 0));
         Map result = new LinkedHashMap();
         List codeHeaderItems = codeHeader instanceof FunctionBlock ? ((FunctionBlock)codeHeader).GetItems() : new ArrayList();
         int i = 0;

         for(Iterator var25 = splittedCode.entrySet().iterator(); var25.hasNext(); ++i) {
            Map.Entry entry = (Map.Entry)var25.next();
            result.put((new FunctionBlock(codeHeaderName + "_" + (i + 2))).SetItems((List)codeHeaderItems).SetTags(new Tag("Is Hidden", "True")), unwrapAndUpdateReturn(new Scope((List)entry.getKey()), (Integer)entry.getValue()));
         }

         return result;
      }
   }

   private static List splitScope(Scope scope, String codeHeaderName, Map splittedCode, int maxLength, int functionMaxLength, int depth) {
      List result = new ArrayList();
      boolean isIfScope = scope instanceof IfScope;
      if (isIfScope) {
         IfScope ifScope = (IfScope)scope;
         if (maxLength < ifScope.getMinimumLength()) {
            splittedCode.put(splitScope(scope, codeHeaderName, splittedCode, functionMaxLength, functionMaxLength, depth + 1), depth);
            result.add(new CallFunctionBlock(codeHeaderName + "_" + (splittedCode.size() + 1)));
            return result;
         }

         maxLength -= ifScope.getMinimumLength();
         result.addAll(ifScope.getStart());
      }

      maxLength -= 2;

      for(int ci = 0; ci < 2; ++ci) {
         List lastCodeLine = null;
         List content;
         if (ci == 0) {
            content = scope.getContent();
         } else {
            if (!isIfScope || !((IfScope)scope).hasElse()) {
               break;
            }

            IfScope ifScope = (IfScope)scope;
            result.addAll(ifScope.getElse());
            content = ifScope.getElseContent();
         }

         int iterationStartIndex = 0;
         int iterationBlocksLen = 0;
         int i = 0;

         for(Iterator var14 = content.iterator(); var14.hasNext(); ++i) {
            CodeBlock block = (CodeBlock)var14.next();
            boolean isBlockScope = block instanceof Scope;
            if (isBlockScope) {
               iterationBlocksLen += ((Scope)block).totalLength;
            } else {
               iterationBlocksLen += 2;
            }

            if (iterationBlocksLen >= maxLength) {
               List codeLine = new ArrayList(content.subList(iterationStartIndex, i));
               iterationStartIndex = i;
               if (isBlockScope) {
                  Scope scopeBlock = (Scope)block;
                  int scopeMaxLen = maxLength - (iterationBlocksLen - scopeBlock.totalLength);
                  codeLine.addAll(splitScope(scopeBlock, codeHeaderName, splittedCode, scopeMaxLen, functionMaxLength, depth));
                  iterationStartIndex = i + 1;
               }

               maxLength = functionMaxLength;
               iterationBlocksLen = 0;
               if (lastCodeLine == null) {
                  int startIndex = result.size();
                  result.addAll(codeLine);
                  lastCodeLine = result.subList(startIndex, result.size());
                  ++depth;
               } else if (codeLine.size() == 1) {
                  ((List)lastCodeLine).addAll(codeLine);
               } else {
                  splittedCode.put(codeLine, depth++);
                  ((List)lastCodeLine).add(new CallFunctionBlock(codeHeaderName + "_" + (splittedCode.size() + 1)));
                  lastCodeLine = codeLine;
               }
            }
         }

         List codeLine = new ArrayList(content.subList(iterationStartIndex, content.size()));
         if (codeLine.size() != 0) {
            if (lastCodeLine == null) {
               result.addAll(codeLine);
               ++depth;
            } else if (codeLine.size() == 1) {
               ((List)lastCodeLine).addAll(codeLine);
            } else {
               splittedCode.put(codeLine, depth++);
               ((List)lastCodeLine).add(new CallFunctionBlock(codeHeaderName + "_" + (splittedCode.size() + 1)));
            }
         }
      }

      if (isIfScope) {
         result.add(((IfScope)scope).getEnd());
      }

      return result;
   }

   private static List unwrapScope(Scope scope) {
      List scopeContent = new ArrayList();
      Iterator var2 = scope.content.iterator();

      while(var2.hasNext()) {
         CodeBlock block = (CodeBlock)var2.next();
         if (block instanceof Scope) {
            scopeContent.addAll(unwrapScope((Scope)block));
         } else {
            scopeContent.add(block);
         }
      }

      return scopeContent;
   }

   private static List unwrapAndUpdateReturn(Scope scope, int depth) {
      List unwrappedBlocks = unwrapScope(scope);
      if (depth != 0) {
         List updatedBlocks = new ArrayList(unwrappedBlocks);

         for(int i = 0; i < unwrappedBlocks.size(); ++i) {
            CodeBlock block = (CodeBlock)unwrappedBlocks.get(i);
            if (block instanceof ControlBlock && ((ControlBlock)block).getAction().equals("Return")) {
               updatedBlocks.set(i, (new ControlBlock("ReturnNTimes")).SetItems((new Number()).Set(depth + 1)));
            }
         }

         return updatedBlocks;
      } else {
         return unwrappedBlocks;
      }
   }

   static {
      plotSize = CodeManager.PLOT_SIZE.BASIC;
   }
}
