package net.jfdf.compiler.visitor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import net.jfdf.compiler.JFDFCompiler;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.IfHandler;
import net.jfdf.compiler.annotation.CompileWithExecute;
import net.jfdf.compiler.annotation.MethodFallback;
import net.jfdf.compiler.data.instruction.InstructionData;
import net.jfdf.compiler.data.instruction.InstructionType;
import net.jfdf.compiler.data.instruction.IntegerIncrementInstructionData;
import net.jfdf.compiler.data.instruction.JumpInstructionData;
import net.jfdf.compiler.data.stack.ArrayStackValue;
import net.jfdf.compiler.data.stack.ClassStackValue;
import net.jfdf.compiler.data.stack.CodeArrayStackValue;
import net.jfdf.compiler.data.stack.CodeStackValue;
import net.jfdf.compiler.data.stack.CompareStackValue;
import net.jfdf.compiler.data.stack.EnumStackValue;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.MathFunctionStackValue;
import net.jfdf.compiler.data.stack.MathStackValue;
import net.jfdf.compiler.data.stack.NumberStackValue;
import net.jfdf.compiler.data.stack.ObjectStackValue;
import net.jfdf.compiler.data.stack.ReferencedStackValue;
import net.jfdf.compiler.data.stack.SpecialStackValue;
import net.jfdf.compiler.data.stack.Stack;
import net.jfdf.compiler.data.stack.TextStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.compiler.util.FieldsManager;
import net.jfdf.compiler.util.MethodWrapper;
import net.jfdf.compiler.util.MethodsManager;
import net.jfdf.compiler.util.ReferenceUtils;
import net.jfdf.jfdf.blocks.BracketBlock;
import net.jfdf.jfdf.blocks.EntityEventBlock;
import net.jfdf.jfdf.blocks.FunctionBlock;
import net.jfdf.jfdf.blocks.IfVariableBlock;
import net.jfdf.jfdf.blocks.PlayerEventBlock;
import net.jfdf.jfdf.blocks.ProcessBlock;
import net.jfdf.jfdf.blocks.RepeatBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.Control;
import net.jfdf.jfdf.mangement.Entity;
import net.jfdf.jfdf.mangement.EntityEvent;
import net.jfdf.jfdf.mangement.Function;
import net.jfdf.jfdf.mangement.FunctionWithArgs;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.If;
import net.jfdf.jfdf.mangement.Player;
import net.jfdf.jfdf.mangement.PlayerEvent;
import net.jfdf.jfdf.mangement.Process;
import net.jfdf.jfdf.mangement.Repeat;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.CodeValue;
import net.jfdf.jfdf.values.GameValue;
import net.jfdf.jfdf.values.ILocation;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IPotion;
import net.jfdf.jfdf.values.ISound;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.IVector;
import net.jfdf.jfdf.values.Item;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;
import net.jfdf.jfdf.values.Vector;
import net.jfdf.transformer.Saved;
import net.jfdf.transformer.util.NumberMath;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class CompilerMethodVisitor extends MethodVisitor {
   private final Class class_;
   private final MethodWrapper method;
   private int blockOperationIndex = 0;
   private final Stack stack = new Stack();
   private int lineNumber = -1;
   private int instructionIndex = -1;
   private int labelInstructionIndex = -1;
   private final List instructionDataList;
   private final Map variableDescriptors = new HashMap();
   private final List<Integer> startRepeatBracketIndices = new ArrayList<>();
   private final List<Integer> endBracketIndices = new ArrayList<>();
   private final List<Integer> elseIndices = new ArrayList<>();
   private boolean isThread = false;
   private boolean compileWithExecute = false;
   private final List ifStackLens = new ArrayList();
   private final List elifVars = new ArrayList();
   private Variable labelIfVariable = null;
   private boolean isOrIf = false;
   private int labelLastIfIndex = 0;
   private int labelIfCount = 0;

   public CompilerMethodVisitor(MethodVisitor methodVisitor, Class class_, MethodWrapper method) {
      super(589824, methodVisitor);
      this.class_ = class_;
      this.method = method;
      this.instructionDataList = MethodsManager.getMethodInstructions(class_, method.getName(), method.getDescriptor());
      CompilerAddons.setTempVariableCallback(this::getTempVariable);
      CompilerAddons.setInstructionDataList(this.instructionDataList);
   }

   public void visitCode() {
      Type[] args = Type.getArgumentTypes(this.method.getDescriptor());
      int argsStartIndex = 0;
      int argsEndIndex = args.length;
      if (this.method.isMember()) {
         argsStartIndex = 1;
         ++argsEndIndex;
         this.variableDescriptors.put(0, this.class_.descriptorString());
      }

      int argsCount;
      for(argsCount = argsStartIndex; argsCount < argsEndIndex; ++argsCount) {
         this.variableDescriptors.put(argsCount, args[argsCount - argsStartIndex].getDescriptor());
      }

      if (this.method.getAnnotation(PlayerEvent.class) != null) {
         CodeManager.instance.addCodeBlocks(new PlayerEventBlock(((PlayerEvent)this.method.getAnnotation(PlayerEvent.class)).eventType().getJSONValue()), (ArrayList)(new ArrayList()));
         this.isThread = true;
         if (((PlayerEvent)this.method.getAnnotation(PlayerEvent.class)).eventType() == PlayerEventBlock.Event.JOIN) {
            If.Variable.Equals(new GameValue(GameValue.Value.TOTAL_PLAYER_COUNT), new CodeValue[]{(new Number()).Set(1)}, false);
            Functions.Call("_jfdf>init");
            If.End();
         }
      } else if (this.method.getAnnotation(EntityEvent.class) != null) {
         CodeManager.instance.addCodeBlocks(new EntityEventBlock(((EntityEvent)this.method.getAnnotation(EntityEvent.class)).eventType().getJSONValue()), (ArrayList)(new ArrayList()));
         this.isThread = true;
      } else if (this.method.getAnnotation(Process.class) != null) {
         Process processData = (Process)this.method.getAnnotation(Process.class);
         CodeManager.instance.addCodeBlocks((new ProcessBlock(processData.name())).SetTags(new Tag("Is Hidden", processData.isHidden() ? "True" : "False")), (ArrayList)(new ArrayList()));
         this.isThread = true;
      } else if (this.method.getAnnotation(Function.class) != null) {
         Function functionData = (Function)this.method.getAnnotation(Function.class);
         CodeManager.instance.addCodeBlocks((new FunctionBlock(functionData.name())).SetTags(new Tag("Is Hidden", functionData.isHidden() ? "True" : "False")), (ArrayList)(new ArrayList()));
      } else if (this.method.getAnnotation(FunctionWithArgs.class) != null) {
         FunctionWithArgs functionData = (FunctionWithArgs)this.method.getAnnotation(FunctionWithArgs.class);
         CodeManager.instance.addCodeBlocks((new FunctionBlock(functionData.name())).SetItems(new Item(functionData.iconId(), 1, functionData.iconNbt())).SetTags(new Tag("Is Hidden", functionData.isHidden() ? "True" : "False")), (ArrayList)(new ArrayList()));
      } else {
         boolean isMethodStatic = Modifier.isStatic(this.method.getModifiers());
         CodeManager var10000 = CodeManager.instance;
         String var10003 = this.class_.getName().replace('.', '/');
         var10000.addCodeBlocks((new FunctionBlock("_jfdf>" + var10003 + ">" + this.method.getName() + ">" + this.method.getDescriptor())).SetItems(new Item(this.method.isStaticInitializer() ? "lime_concrete" : (this.method.isConstructor() ? "orange_concrete" : (isMethodStatic ? "blue_concrete" : "red_concrete")), 1, this.method.isStaticInitializer() ? "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"green\",\"text\":\"Static Initializer\"}],\"text\":\"\"}',Lore:['{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"blue\",\"text\":\"  Class:\"}],\"text\":\"\"}','{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"aqua\",\"text\":\"    " + Type.getInternalName(this.class_) + "\"}],\"text\":\"\"}']}" : (!this.method.isConstructor() ? "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"" + (isMethodStatic ? "dark_aqua" : "red") + "\",\"text\":\"" + this.method.getName() + "\"}],\"text\":\"\"}',Lore:['{\"extra\":[{\"text\":\"  \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"blue\",\"text\":\"Location:\"}],\"text\":\"\"}','{\"extra\":[{\"text\":\"    \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"aqua\",\"text\":\"" + Type.getInternalName(this.class_) + " >\"}],\"text\":\"\"}','{\"extra\":[{\"text\":\"      \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"yellow\",\"text\":\"" + this.method.getName() + " >\"}],\"text\":\"\"}','{\"extra\":[{\"text\":\"        \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"gold\",\"text\":\"" + this.method.getDescriptor() + "\"}],\"text\":\"\"}']}" : "display:{Name:'{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"gold\",\"text\":\"Constructor\"}],\"text\":\"\"}',Lore:['{\"extra\":[{\"text\":\"  \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"blue\",\"text\":\"Location:\"}],\"text\":\"\"}','{\"extra\":[{\"text\":\"    \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"aqua\",\"text\":\"" + Type.getInternalName(this.class_) + " >\"}],\"text\":\"\"}','{\"extra\":[{\"text\":\"      \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"yellow\",\"text\":\"" + this.method.getDescriptor() + "\"}],\"text\":\"\"}']}"))), (ArrayList)(new ArrayList()));
      }

      if (this.method.getAnnotation(CompileWithExecute.class) != null && Modifier.isStatic(this.method.getModifiers())) {
         this.compileWithExecute = true;
         this.method.setAccessible(true);

         try {
            argsCount = Type.getArgumentTypes(this.method.getDescriptor()).length;
            if (argsCount > 0) {
               this.method.invoke((Object)null, IntStream.range(0, argsCount).mapToObj((operand) -> {
                  return new net.jfdf.jfdf.values.List(new Variable("_jfdffa>%var(_jfdfFD)>" + operand, Variable.Scope.LOCAL));
               }).toArray());
            } else {
               this.method.invoke((Object)null);
            }

            VariableControl.Decrement(new Variable("_jfdfFD", Variable.Scope.LOCAL));
         } catch (IllegalAccessException | InstantiationException | InvocationTargetException var11) {
            var11.printStackTrace();
         }
      }

      if (this.method.isConstructor()) {
         VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), new Variable("_jfdfRP", Variable.Scope.LOCAL));
      }

      if (this.method.isStaticInitializer()) {
         MethodsManager.enableHasStaticInitializer(this.class_);
         String classInternalName = Type.getInternalName(this.class_);
         Iterator var5 = FieldsManager.getClassDefaultValues(this.class_).entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry defaultValueEntry = (Map.Entry)var5.next();
            String fieldName = (String)defaultValueEntry.getKey();
            Object defaultValue = defaultValueEntry.getValue();
            if (defaultValue != null) {
               try {
                  Object defaultCodeValue;
                  if (defaultValue instanceof String) {
                     defaultCodeValue = (new Text()).Set((String)defaultValue);
                  } else if (defaultValue instanceof java.lang.Number) {
                     defaultCodeValue = (new Number()).Set(((java.lang.Number)defaultValue).doubleValue());
                  } else {
                     if (!(defaultValue instanceof Character)) {
                        throw new IllegalStateException("Unknown field's default value type.");
                     }

                     defaultCodeValue = (new Number()).Set((Integer)defaultValue);
                  }

                  VariableControl.Set(new Variable("_jfdf>" + classInternalName + ">" + fieldName, this.class_.getDeclaredField(fieldName).getAnnotation(Saved.class) == null ? Variable.Scope.NORMAL : Variable.Scope.SAVED), (CodeValue)defaultCodeValue);
               } catch (NoSuchFieldException var10) {
                  throw new RuntimeException("Something went wrong.", var10);
               }
            }
         }
      }

   }

   public void visitLabel(Label label) {
      if (!this.compileWithExecute) {
         this.labelInstructionIndex = ++this.instructionIndex;
         if (this.elseIndices.contains(this.instructionIndex)) {
            if (this.stack.size() == (Integer)this.ifStackLens.get(this.ifStackLens.size() - 1) + 1) {
               Variable ifResultVar = this.getTempVariable();
               this.elifVars.set(this.elifVars.size() - 1, ifResultVar);
               VariableControl.Set(ifResultVar, this.stack.remove(this.stack.size() - 1).getTransformedValue());
            }

            this.elseIndices.remove(this.instructionIndex);
            If.Else();
         }

         this.endBracketIndices.stream().sorted(Collections.reverseOrder()).filter((index) -> {
            return this.instructionIndex == Math.abs(index);
         }).forEach((index) -> {
            if (index < 0) {
               Repeat.End();
            } else {
               if (this.stack.size() == (Integer)this.ifStackLens.remove(this.ifStackLens.size() - 1) + 1) {
                  Variable ifResultVar = (Variable)this.elifVars.remove(this.elifVars.size() - 1);
                  IStackValue ifResult = this.stack.remove(this.stack.size() - 1);
                  VariableControl.Set(ifResultVar, ifResult.getTransformedValue());
                  this.stack.add((IStackValue)(new VariableStackValue(ifResult.getDescriptor(), ifResultVar.getName())));
               }

               If.End();
            }

         });
         this.endBracketIndices.removeIf((index) -> {
            return this.instructionIndex == Math.abs(index);
         });
         boolean pIsOrIf = false;
         boolean isOrIf = false;
         this.labelIfCount = 0;
         int blastLabelInsnIndex = 0;
         int lastLabelInsnIndex = 0;

         for(int i = this.instructionIndex + 1; i < this.instructionDataList.size(); ++i) {
            InstructionData instructionData = (InstructionData)this.instructionDataList.get(i);
            if (instructionData.instructionType == InstructionType.LABEL) {
               this.isOrIf = pIsOrIf;
               if (blastLabelInsnIndex == 0 || lastLabelInsnIndex == blastLabelInsnIndex) {
                  this.isOrIf = false;
               }
               break;
            }

            if (instructionData.instructionType == InstructionType.JUMP && instructionData.instructionOpcode != 167) {
               int jumpTo = ((JumpInstructionData)instructionData).jumpToInstructionIndex;
               pIsOrIf = isOrIf;
               isOrIf = lastLabelInsnIndex == jumpTo || lastLabelInsnIndex == 0;
               blastLabelInsnIndex = lastLabelInsnIndex;
               lastLabelInsnIndex = jumpTo;
               this.labelLastIfIndex = i;
               ++this.labelIfCount;
            }
         }

         this.labelIfVariable = null;
      }
   }

   public void visitLineNumber(int line, Label start) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(-1);
         this.lineNumber = line;
      }
   }

   public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(-1);
      }
   }

   public void visitInsn(int opcode) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(opcode);
         IStackValue dividendStackValue;
         Variable value;
         Variable reference;
         String valueDescriptor;
         Variable valuePointer;
         String variableName;
         switch (opcode) {
            case 1:
               this.stack.add((IStackValue)(new NumberStackValue()));
               break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
               this.stack.add((IStackValue)(new NumberStackValue(opcode - 3)));
               break;
            case 9:
            case 10:
               this.stack.add((IStackValue)(new NumberStackValue((long)(opcode - 9))));
               break;
            case 11:
            case 12:
            case 13:
               this.stack.add((IStackValue)(new NumberStackValue((float)(opcode - 11))));
               break;
            case 14:
            case 15:
               this.stack.add((IStackValue)(new NumberStackValue((double)(opcode - 14))));
               break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 88:
            case 90:
            case 91:
            case 93:
            case 94:
            case 132:
            case 148:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            default:
               throw new IllegalStateException("Unsupported opcode: " + opcode);
            case 46:
            case 47:
            case 48:
            case 49:
            case 51:
            case 52:
            case 53:
               dividendStackValue = this.stack.remove(this.stack.size() - 2);
               if (dividendStackValue instanceof ReferencedStackValue) {
                  value = ((ReferencedStackValue)dividendStackValue).getReference();
               } else {
                  value = new Variable("_jfdfR%var(" + ((Variable)dividendStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
               }

               this.stack.add((IStackValue)(new MathFunctionStackValue(value, NumberMath.add((INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), (new Number()).Set(2)), MathFunctionStackValue.MathFunction.LIST_VALUE)));
               break;
            case 50:
               dividendStackValue = this.stack.remove(this.stack.size() - 2);
               reference = this.getTempVariable();
               Variable reference2;
               if (dividendStackValue instanceof ArrayStackValue) {
                  ArrayStackValue newArray = (ArrayStackValue)dividendStackValue;
                  reference2 = newArray.getReference();
                  variableName = Type.getType(newArray.getDescriptor()).getElementType().getDescriptor();
               } else {
                  reference2 = new Variable("_jfdfR%var(" + ((Variable)dividendStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
                  variableName = Type.getType(dividendStackValue.getDescriptor()).getElementType().getDescriptor();
               }

               VariableControl.GetListValue(reference2, reference2, NumberMath.add((INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), (new Number()).Set(2)));
               ReferenceUtils.incrementIfReference(variableName, reference2);
               this.stack.add((IStackValue)(new VariableStackValue(variableName, reference2.getName())));
               break;
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
               dividendStackValue = this.stack.remove(this.stack.size() - 3);
               IStackValue index = this.stack.remove(this.stack.size() - 2);
               IStackValue value2 = this.stack.remove(this.stack.size() - 1);
               String elementDescriptor = Type.getType(dividendStackValue.getDescriptor()).getElementType().getDescriptor();
               valueDescriptor = value2.getDescriptor();
               if (dividendStackValue instanceof ArrayStackValue) {
                  ArrayStackValue arrayStackValue = (ArrayStackValue)dividendStackValue;
                  if (opcode == 83) {
                     ReferenceUtils.decrementIfReference(elementDescriptor, NumberMath.listValue(arrayStackValue.getReference(), NumberMath.add((INumber)index.getTransformedValue(), (new Number()).Set(2))));
                  }

                  arrayStackValue.set(index, value2.getTransformedValue());
               } else {
                  valuePointer = new Variable("_jfdfR%var(" + ((Variable)dividendStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
                  INumber newIndex = NumberMath.add((INumber)index.getTransformedValue(), (new Number()).Set(2));
                  if (opcode == 83) {
                     ReferenceUtils.decrementIfReference(elementDescriptor, NumberMath.listValue(valuePointer, newIndex));
                  }

                  VariableControl.SetListValue(valuePointer, newIndex, value2.getTransformedValue());
               }

               ReferenceUtils.incrementIfReference(valueDescriptor, value2.getTransformedValue());
               break;
            case 87:
               this.stack.remove(this.stack.size() - 1);
               break;
            case 89:
               this.stack.add((IStackValue)this.stack.get(this.stack.size() - 1));
               break;
            case 92:
               this.stack.add((IStackValue)this.stack.get(this.stack.size() - 2));
               this.stack.add((IStackValue)this.stack.get(this.stack.size() - 2));
               break;
            case 95:
               this.stack.add((IStackValue)this.stack.get(this.stack.size() - 1));
               this.stack.remove(this.stack.size() - 2);
               this.stack.add((IStackValue)this.stack.get(this.stack.size() - 2));
               this.stack.remove(this.stack.get(this.stack.size() - 3));
               break;
            case 96:
            case 97:
            case 98:
               this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.ADD)));
               break;
            case 99:
               if (JFDFCompiler.realDoubles) {
                  variableName = this.getTempVariableName();
                  VariableControl.AddVectors(new Variable(variableName, Variable.Scope.LOCAL), (IVector)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (IVector)this.stack.remove(this.stack.size() - 1).getTransformedValue());
                  this.stack.add((IStackValue)(new VariableStackValue("D", variableName)));
               } else {
                  this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.ADD)));
               }
               break;
            case 100:
            case 101:
            case 102:
               this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.SUBTRACT)));
               break;
            case 103:
               if (JFDFCompiler.realDoubles) {
                  variableName = this.getTempVariableName();
                  VariableControl.SubtractVectors(new Variable(variableName, Variable.Scope.LOCAL), (IVector)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (IVector)this.stack.remove(this.stack.size() - 1).getTransformedValue());
                  this.stack.add((IStackValue)(new VariableStackValue("D", variableName)));
               } else {
                  this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.SUBTRACT)));
               }
               break;
            case 104:
            case 105:
            case 106:
               this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.MULTIPLY)));
               break;
            case 107:
               if (JFDFCompiler.realDoubles) {
                  variableName = this.getTempVariableName();
                  VariableControl.SetToProduct(new Variable(variableName, Variable.Scope.LOCAL), this.stack.remove(this.stack.size() - 2).getTransformedValue(), this.stack.remove(this.stack.size() - 1).getTransformedValue());
                  this.stack.add((IStackValue)(new VariableStackValue("D", variableName)));
               } else {
                  this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.MULTIPLY)));
               }
               break;
            case 108:
            case 109:
               this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.DIVIDE_INTEGER)));
               break;
            case 110:
               this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.DIVIDE)));
               break;
            case 111:
               if (JFDFCompiler.realDoubles) {
                  dividendStackValue = this.stack.remove(this.stack.size() - 1);
                  if (!(dividendStackValue instanceof CodeArrayStackValue)) {
                     throw new UnsupportedOperationException("Trying to divide double by a variable while real double mode is active.");
                  }

                  variableName = this.getTempVariableName();
                  VariableControl.SetToProduct(new Variable(variableName, Variable.Scope.LOCAL), this.stack.remove(this.stack.size() - 1).getTransformedValue(), new Vector(1.0 / ((Vector)dividendStackValue.getTransformedValue()).x, 0.0, 0.0));
                  this.stack.add((IStackValue)(new VariableStackValue("D", variableName)));
               } else {
                  this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.DIVIDE)));
               }
               break;
            case 112:
            case 113:
            case 114:
               this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.REMAINDER)));
               break;
            case 115:
               if (JFDFCompiler.realDoubles) {
                  throw new UnsupportedOperationException("Trying to get remainder of double while real double mode is active.");
               }

               this.stack.add((IStackValue)(new MathStackValue((INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathStackValue.MathOperation.REMAINDER)));
               break;
            case 116:
            case 117:
            case 118:
               this.stack.add((IStackValue)(new MathFunctionStackValue((INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathFunctionStackValue.MathFunction.NEGATIVE)));
               break;
            case 119:
               if (JFDFCompiler.realDoubles) {
                  variableName = this.getTempVariableName();
                  VariableControl.SetToProduct(new Variable(variableName, Variable.Scope.LOCAL), this.stack.remove(this.stack.size() - 1).getTransformedValue(), new Vector(-1.0, 0.0, 0.0));
                  this.stack.add((IStackValue)(new VariableStackValue("D", variableName)));
               } else {
                  this.stack.add((IStackValue)(new MathFunctionStackValue((INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), MathFunctionStackValue.MathFunction.NEGATIVE)));
               }
               break;
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
               Variable value3 = this.getTempVariable();
               Tags.Operator var10000;
               switch (opcode) {
                  case 120:
                  case 121:
                     var10000 = Tags.Operator.LEFT_SHIFT;
                     break;
                  case 122:
                  case 123:
                     var10000 = Tags.Operator.RIGHT_SHIFT;
                     break;
                  case 124:
                  case 125:
                     var10000 = Tags.Operator.UNSIGNED_RIGHT_SHIFT;
                     break;
                  case 126:
                  case 127:
                     var10000 = Tags.Operator.AND;
                     break;
                  case 128:
                  case 129:
                     var10000 = Tags.Operator.OR;
                     break;
                  case 130:
                  case 131:
                     var10000 = Tags.Operator.XOR;
                     break;
                  default:
                     throw new IllegalStateException("Unknown bitwise operator opcode: " + opcode);
               }

               Tags.Operator operatorType = var10000;
               VariableControl.Bitwise(value3, (INumber)this.stack.remove(this.stack.size() - 2).getTransformedValue(), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), operatorType);
               this.stack.add((IStackValue)(new VariableStackValue("J", value3.getName())));
               break;
            case 133:
            case 140:
               dividendStackValue = (IStackValue)this.stack.get(this.stack.size() - 1);
               if (dividendStackValue instanceof NumberStackValue) {
                  this.stack.set(this.stack.size() - 1, (IStackValue)(new NumberStackValue(((NumberStackValue)dividendStackValue).getJavaValue().longValue())));
               }
               break;
            case 134:
            case 137:
               dividendStackValue = (IStackValue)this.stack.get(this.stack.size() - 1);
               if (dividendStackValue instanceof NumberStackValue) {
                  this.stack.set(this.stack.size() - 1, (IStackValue)(new NumberStackValue(((NumberStackValue)dividendStackValue).getJavaValue().floatValue())));
               }
               break;
            case 135:
            case 138:
            case 141:
               variableName = this.getTempVariableName();
               VariableControl.Vector(new Variable(variableName, Variable.Scope.LOCAL), (INumber)this.stack.remove(this.stack.size() - 1).getTransformedValue(), (new Number()).Set(0), (new Number()).Set(0));
               this.stack.add((IStackValue)(new VariableStackValue("D", variableName)));
               break;
            case 136:
            case 139:
               dividendStackValue = (IStackValue)this.stack.get(this.stack.size() - 1);
               if (dividendStackValue instanceof NumberStackValue) {
                  this.stack.set(this.stack.size() - 1, (IStackValue)(new NumberStackValue(((NumberStackValue)dividendStackValue).getJavaValue().intValue())));
               }
               break;
            case 142:
               variableName = this.getTempVariableName();
               VariableControl.GetVectorComp(new Variable(variableName, Variable.Scope.LOCAL), (IVector)this.stack.remove(this.stack.size() - 1).getTransformedValue(), Tags.Component.X);
               this.stack.add((IStackValue)(new VariableStackValue("I", variableName)));
               break;
            case 143:
               variableName = this.getTempVariableName();
               VariableControl.GetVectorComp(new Variable(variableName, Variable.Scope.LOCAL), (IVector)this.stack.remove(this.stack.size() - 1).getTransformedValue(), Tags.Component.X);
               this.stack.add((IStackValue)(new VariableStackValue("J", variableName)));
               break;
            case 144:
               variableName = this.getTempVariableName();
               VariableControl.GetVectorComp(new Variable(variableName, Variable.Scope.LOCAL), (IVector)this.stack.remove(this.stack.size() - 1).getTransformedValue(), Tags.Component.X);
               this.stack.add((IStackValue)(new VariableStackValue("F", variableName)));
               break;
            case 145:
               this.stack.set(this.stack.size() - 1, (IStackValue)(new MathFunctionStackValue((INumber)((IStackValue)this.stack.get(this.stack.size() - 1)).getTransformedValue(), MathFunctionStackValue.MathFunction.CAST_TO_BYTE, this.method.getName(), this.blockOperationIndex++)));
               break;
            case 146:
               this.stack.set(this.stack.size() - 1, (IStackValue)(new MathFunctionStackValue((INumber)((IStackValue)this.stack.get(this.stack.size() - 1)).getTransformedValue(), MathFunctionStackValue.MathFunction.CAST_TO_CHAR, this.method.getName(), this.blockOperationIndex++)));
               break;
            case 147:
               this.stack.set(this.stack.size() - 1, (IStackValue)(new MathFunctionStackValue((INumber)((IStackValue)this.stack.get(this.stack.size() - 1)).getTransformedValue(), MathFunctionStackValue.MathFunction.CAST_TO_SHORT, this.method.getName(), this.blockOperationIndex++)));
               break;
            case 149:
            case 150:
            case 151:
            case 152:
               this.stack.add((IStackValue)(new CompareStackValue(CompareStackValue.CompareType.NORMAL, this.stack.remove(this.stack.size() - 2), this.stack.remove(this.stack.size() - 1))));
               break;
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
               dividendStackValue = this.stack.remove(this.stack.size() - 1);
               if (dividendStackValue instanceof ReferencedStackValue) {
                  ((ReferencedStackValue)dividendStackValue).setAllocationVariable("_jfdfrv", Variable.Scope.LOCAL);
               } else {
                  VariableControl.Set(new Variable("_jfdfrv", Variable.Scope.LOCAL), dividendStackValue.getTransformedValue());
                  ReferenceUtils.incrementIfReference(dividendStackValue.getDescriptor(), new Variable("_jfdfrv", Variable.Scope.LOCAL));
               }

               int firstLocalVar = (this.method.isMember() ? 1 : 0) + Type.getArgumentTypes(this.method.getDescriptor()).length;
               Iterator var18 = this.variableDescriptors.entrySet().iterator();

               while(var18.hasNext()) {
                  Map.Entry descriptorEntry = (Map.Entry)var18.next();
                  int var = (Integer)descriptorEntry.getKey();
                  String descriptor = (String)descriptorEntry.getValue();
                  if (var >= firstLocalVar) {
                     Variable valuePointer2 = new Variable("_jfdffv>%var(_jfdfFD)>" + var, Variable.Scope.LOCAL);
                     ReferenceUtils.decrementIfReference(descriptor, valuePointer2);
                  }
               }

               VariableControl.Decrement(new Variable("_jfdfFD", Variable.Scope.LOCAL), (new Number()).Set(1));
               Control.Return();
               break;
            case 177:
               int firstLocalVar2 = (this.method.isMember() ? 1 : 0) + Type.getArgumentTypes(this.method.getDescriptor()).length;
               Iterator var11 = this.variableDescriptors.entrySet().iterator();

               while(var11.hasNext()) {
                  Map.Entry descriptorEntry = (Map.Entry)var11.next();
                  int var = (Integer)descriptorEntry.getKey();
                  valueDescriptor = (String)descriptorEntry.getValue();
                  if (var >= firstLocalVar2) {
                     valuePointer = new Variable("_jfdffv>%var(_jfdfFD)>" + var, Variable.Scope.LOCAL);
                     ReferenceUtils.decrementIfReference(valueDescriptor, valuePointer);
                  }
               }

               if (this.isThread) {
                  Functions.Call("_jfdf>std>procEnd");
               } else {
                  VariableControl.Decrement(new Variable("_jfdfFD", Variable.Scope.LOCAL), (new Number()).Set(1));
                  Control.Return();
               }
               break;
            case 190:
               dividendStackValue = this.stack.remove(this.stack.size() - 1);
               value3 = this.getTempVariable();
               if (dividendStackValue instanceof ReferencedStackValue) {
                  reference2 = ((ReferencedStackValue)dividendStackValue).getReference();
               } else {
                  reference2 = new Variable("_jfdfR%var(" + ((Variable)dividendStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
               }

               VariableControl.ListLength(value3, reference2);
               this.stack.add((IStackValue)(new MathStackValue(value3, (new Number()).Set(1), MathStackValue.MathOperation.SUBTRACT)));
         }

         super.visitInsn(opcode);
      }
   }

   public void visitVarInsn(int opcode, int var) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(opcode);
         if (opcode >= 54 && opcode <= 58) {
            IStackValue stackValue = this.stack.remove(this.stack.size() - 1);
            String variableName;
            if (Type.getArgumentTypes(this.method.getDescriptor()).length + (this.method.isMember() ? 1 : 0) > var) {
               variableName = "_jfdffa>%var(_jfdfFD)>" + var;
            } else {
               variableName = "_jfdffv>%var(_jfdfFD)>" + var;
            }

            Variable valuePointer = new Variable(variableName, Variable.Scope.NORMAL);
            String variableDescriptor = (String)this.variableDescriptors.get(var);
            ReferenceUtils.decrementIfReference(variableDescriptor, valuePointer);
            if (stackValue instanceof ReferencedStackValue) {
               ReferencedStackValue referencedStackValue = (ReferencedStackValue)stackValue;
               if (ReferenceUtils.isReferenceDescriptor(stackValue.getDescriptor()) && !ReferenceUtils.isReferenceDescriptor(variableDescriptor) && variableDescriptor != null) {
                  referencedStackValue.resetBeforeAllocate();
               }

               referencedStackValue.setAllocationVariable(variableName, Variable.Scope.LOCAL);
               this.variableDescriptors.put(var, stackValue.getDescriptor());
            } else {
               VariableControl.Set(new Variable(variableName, Variable.Scope.LOCAL), stackValue.getTransformedValue());
               this.variableDescriptors.put(var, stackValue.getDescriptor());
               ReferenceUtils.incrementIfReference(stackValue.getDescriptor(), valuePointer);
            }
         } else {
            if (opcode < 21 || opcode > 25) {
               throw new IllegalStateException("Unsupported opcode: " + opcode);
            }

            if (Type.getArgumentTypes(this.method.getDescriptor()).length + (this.method.isMember() ? 1 : 0) > var) {
               this.stack.add((IStackValue)(new VariableStackValue((String)this.variableDescriptors.get(var), "_jfdffa>%var(_jfdfFD)>" + var)));
            } else {
               this.stack.add((IStackValue)(new VariableStackValue((String)this.variableDescriptors.get(var), "_jfdffv>%var(_jfdfFD)>" + var)));
            }
         }

         super.visitVarInsn(opcode, var);
      }
   }

   public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(opcode);
         int argsCount;
         IStackValue stackValue;
         String returnVariableName;
         MethodWrapper invokeMethod;
         int i;
         CodeValue arg;
         Class invokeClass;
         String returnDescriptor;
         Object[] methodArgs;
         switch (opcode) {
            case 182:
            case 185:
               if (CompilerAddons.publishInvokeMemberEvent(owner, name, descriptor, this.stack)) {
                  return;
               } else {
                  argsCount = Type.getArgumentTypes(descriptor).length;
                  stackValue = (IStackValue)this.stack.get(this.stack.size() - (argsCount + 1));
                  if (stackValue instanceof CodeStackValue && (!name.equals("equals") || !descriptor.equals("(Ljava/lang/Object;)Z"))) {
                     methodArgs = new Object[argsCount];
                     Type[] argumentTypes = Type.getArgumentTypes(descriptor);

                     for(i = argsCount - 1; i >= 0; --i) {
                        IStackValue argStackValue = this.stack.remove(this.stack.size() - 1);
                        if (argStackValue instanceof TextStackValue) {
                           methodArgs[i] = ((TextStackValue)argStackValue).getValue().Get();
                        } else {
                           if (!(argStackValue instanceof NumberStackValue)) {
                              throw new IllegalStateException("Passing non-constant value is not allowed in a code value method.");
                           }

                           if (argumentTypes[i] == Type.BOOLEAN_TYPE) {
                              methodArgs[i] = ((NumberStackValue)argStackValue).getJavaValue().intValue() == 1;
                           } else {
                              methodArgs[i] = ((NumberStackValue)argStackValue).getJavaValue();
                           }
                        }
                     }

                     ((CodeStackValue)stackValue).invokeMethod(name, descriptor, methodArgs);
                     this.stack.remove(this.stack.size() - 1);
                     if (descriptor.endsWith(")L" + owner + ";")) {
                        this.stack.add(stackValue);
                     }

                     return;
                  } else if (stackValue instanceof CodeArrayStackValue) {
                     if (name.equals("next")) {
                        IStackValue stackValue2 = this.stack.remove(this.stack.size() - 1);
                        ((CodeArrayStackValue)stackValue2).next(stackValue2.getTransformedValue());
                     } else if (!name.equals("getArray")) {
                        throw new IllegalStateException("Unknown CodeArrayStackValue method: " + name + descriptor + ".");
                     }

                     return;
                  } else {
                     if (name.equals("valueOf") && descriptor.equals("(Ljava/lang/String;)L" + owner + ";")) {
                        try {
                           invokeClass = Class.forName(owner);
                           if (invokeClass.getSuperclass() == Enum.class) {
                              arg = this.stack.remove(this.stack.size() - 1).getTransformedValue();
                              returnVariableName = "";
                              if (arg instanceof Text) {
                                 returnVariableName = ((Text)arg).Get();
                              } else if (arg instanceof Variable) {
                                 returnVariableName = "%var(" + ((Variable)arg).getName() + ")";
                              }

                              this.stack.remove(this.stack.size() - 1);
                              this.stack.add((IStackValue)(new VariableStackValue("L" + owner + ";", "_jfdf>" + owner + ">" + returnVariableName, Variable.Scope.NORMAL)));
                           }
                        } catch (ClassNotFoundException var29) {
                           throw new RuntimeException("Something went wrong.", var29);
                        }
                     }

                     if (name.equals("equals") && descriptor.equals("(Ljava/lang/Object;)Z") && (owner.equals("java/lang/Object") || owner.equals("java/lang/String"))) {
                        this.stack.add((IStackValue)(new CompareStackValue(CompareStackValue.CompareType.NORMAL, this.stack.remove(this.stack.size() - 2), this.stack.remove(this.stack.size() - 1))));
                        return;
                     } else if (name.equals("clone") && descriptor.equals("()Ljava/lang/Object;") && owner.startsWith("[")) {
                        returnDescriptor = this.getTempVariableName();
                        Variable reference;
                        if (stackValue instanceof ReferencedStackValue) {
                           reference = ((ReferencedStackValue)stackValue).getReference();
                        } else {
                           reference = new Variable("_jfdfR%var(" + ((Variable)stackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
                        }

                        VariableControl.Set(new Variable(returnDescriptor, Variable.Scope.LOCAL), reference);
                        this.stack.remove(this.stack.size() - 1);
                        this.stack.add((IStackValue)(new VariableStackValue("L" + owner + ";", returnDescriptor)));
                        return;
                     } else if (owner.startsWith("net/jfdf/jfdf/mangement/")) {
                        methodArgs = new Object[argsCount];
                        if (!(stackValue instanceof SpecialStackValue)) {
                           throw new IllegalStateException("Given stack value should be a special stack value.");
                        } else {
                           SpecialStackValue specialStackValue = (SpecialStackValue)stackValue;
                           Object object;
                           Class class_;
                           switch (owner) {
                              case "net/jfdf/jfdf/mangement/Player":
                                 class_ = Player.class;
                                 Player var68;
                                 switch (specialStackValue.getType()) {
                                    case PLAYER_CURRENT_SELECTION:
                                       var68 = Player.getCurrentSelection();
                                       break;
                                    case PLAYER_DEFAULT:
                                       var68 = Player.getDefaultPlayer();
                                       break;
                                    case PLAYER_KILLER:
                                       var68 = Player.getKiller();
                                       break;
                                    case PLAYER_DAMAGER:
                                       var68 = Player.getDamager();
                                       break;
                                    case PLAYER_SHOOTER:
                                       var68 = Player.getShooter();
                                       break;
                                    case PLAYER_VICTIM:
                                       var68 = Player.getVictim();
                                       break;
                                    case PLAYER_ALL_PLAYERS:
                                       var68 = Player.getAllPlayers();
                                       break;
                                    default:
                                       throw new IllegalStateException("Non-player selection special type \"" + specialStackValue.getType() + "\".");
                                 }

                                 object = var68;
                                 break;
                              case "net/jfdf/jfdf/mangement/Entity":
                                 class_ = Entity.class;
                                 Entity var67;
                                 switch (specialStackValue.getType()) {
                                    case ENTITY_CURRENT_SELECTION:
                                       var67 = Entity.getCurrentSelection();
                                       break;
                                    case ENTITY_DEFAULT:
                                       var67 = Entity.getDefaultEntity();
                                       break;
                                    case ENTITY_KILLER:
                                       var67 = Entity.getKiller();
                                       break;
                                    case ENTITY_DAMAGER:
                                       var67 = Entity.getDamager();
                                       break;
                                    case ENTITY_SHOOTER:
                                       var67 = Entity.getShooter();
                                       break;
                                    case ENTITY_VICTIM:
                                       var67 = Entity.getVictim();
                                       break;
                                    case ENTITY_PROJECTILE:
                                       var67 = Entity.getProjectile();
                                       break;
                                    case ENTITY_ALL_ENTITIES:
                                       var67 = Entity.getAllEntities();
                                       break;
                                    case ENTITY_ALL_MOBS:
                                       var67 = Entity.getAllMobs();
                                       break;
                                    case ENTITY_LAST_ENTITY_SPAWNED:
                                       var67 = Entity.getLastEntitySpawned();
                                       break;
                                    case ENTITY_LAST_MOB_SPAWNED:
                                       var67 = Entity.getLastMobSpawned();
                                       break;
                                    default:
                                       throw new IllegalStateException("Non-entity selection special type \"" + specialStackValue.getType() + "\".");
                                 }

                                 object = var67;
                                 break;
                              case "net/jfdf/jfdf/mangement/If$Player":
                                 class_ = If.Player.class;
                                 If.Player var66;
                                 switch (specialStackValue.getType()) {
                                    case PLAYER_CURRENT_SELECTION:
                                       var66 = If.Player.getCurrentSelection();
                                       break;
                                    case PLAYER_DEFAULT:
                                       var66 = If.Player.getDefaultPlayer();
                                       break;
                                    case PLAYER_KILLER:
                                       var66 = If.Player.getKiller();
                                       break;
                                    case PLAYER_DAMAGER:
                                       var66 = If.Player.getDamager();
                                       break;
                                    case PLAYER_SHOOTER:
                                       var66 = If.Player.getShooter();
                                       break;
                                    case PLAYER_VICTIM:
                                       var66 = If.Player.getVictim();
                                       break;
                                    case PLAYER_ALL_PLAYERS:
                                       var66 = If.Player.getAllPlayers();
                                       break;
                                    default:
                                       throw new IllegalStateException("Non-player selection special type \"" + specialStackValue.getType() + "\".");
                                 }

                                 object = var66;
                                 break;
                              case "net/jfdf/jfdf/mangement/If$Entity":
                                 class_ = If.Entity.class;
                                 If.Entity var65;
                                 switch (specialStackValue.getType()) {
                                    case ENTITY_CURRENT_SELECTION:
                                       var65 = If.Entity.getCurrentSelection();
                                       break;
                                    case ENTITY_DEFAULT:
                                       var65 = If.Entity.getDefaultEntity();
                                       break;
                                    case ENTITY_KILLER:
                                       var65 = If.Entity.getKiller();
                                       break;
                                    case ENTITY_DAMAGER:
                                       var65 = If.Entity.getDamager();
                                       break;
                                    case ENTITY_SHOOTER:
                                       var65 = If.Entity.getShooter();
                                       break;
                                    case ENTITY_VICTIM:
                                       var65 = If.Entity.getVictim();
                                       break;
                                    case ENTITY_PROJECTILE:
                                       var65 = If.Entity.getProjectile();
                                       break;
                                    case ENTITY_ALL_ENTITIES:
                                       var65 = If.Entity.getAllEntities();
                                       break;
                                    case ENTITY_ALL_MOBS:
                                       var65 = If.Entity.getAllMobs();
                                       break;
                                    case ENTITY_LAST_ENTITY_SPAWNED:
                                       var65 = If.Entity.getLastEntitySpawned();
                                       break;
                                    case ENTITY_LAST_MOB_SPAWNED:
                                       var65 = If.Entity.getLastMobSpawned();
                                       break;
                                    default:
                                       throw new IllegalStateException("Non-entity selection special type \"" + specialStackValue.getType() + "\".");
                                 }

                                 object = var65;
                                 break;
                              default:
                                 throw new IllegalStateException("Unknown class type during invoking virtual method from management api: " + owner + ".");
                           }

                           Type[] argumentTypes = Type.getArgumentTypes(descriptor);

                           for(i = argsCount - 1; i >= 0; --i) {
                              IStackValue stackValue2 = this.stack.remove(this.stack.size() - 1);
                              if (stackValue2 instanceof CodeArrayStackValue) {
                                 try {
                                    Class arrayClass = Class.forName(argumentTypes[i].getElementType().getClassName()).arrayType();
                                    CodeValue[] array = ((CodeArrayStackValue)stackValue2).getValues();
                                    methodArgs[i] = Arrays.copyOf(array, array.length, arrayClass);
                                 } catch (ClassNotFoundException var27) {
                                    throw new RuntimeException("Something went wrong.", var27);
                                 }
                              } else if (stackValue2 instanceof EnumStackValue) {
                                 methodArgs[i] = ((EnumStackValue)stackValue2).getEnumValue();
                              } else {
                                 if (stackValue2 instanceof NumberStackValue) {
                                    if (((NumberStackValue)stackValue2).getJavaValue() == null) {
                                       methodArgs[i] = null;
                                       continue;
                                    }

                                    if (argumentTypes[i] == Type.BOOLEAN_TYPE) {
                                       methodArgs[i] = ((NumberStackValue)stackValue2).getJavaValue().intValue() == 1;
                                       continue;
                                    }
                                 }

                                 methodArgs[i] = stackValue2.getTransformedValue();
                              }
                           }

                           try {
                              MethodWrapper.getWrapper(class_, name, descriptor).invoke(object, methodArgs);
                           } catch (IllegalAccessException | NoSuchMethodException var25) {
                              throw new RuntimeException("Something went wrong.", var25);
                           } catch (InstantiationException | IllegalArgumentException | InvocationTargetException var26) {
                              throw new RuntimeException("Invalid value types during invoking \"" + name + descriptor + "\" of class \"" + owner + "\".\nPassed arguments: " + Arrays.deepToString(methodArgs) + "\nInvoked by : " + Type.getInternalName(this.class_) + ">" + this.method.getName() + this.method.getDescriptor() + ":" + this.lineNumber, var26);
                           }

                           this.stack.remove(this.stack.size() - 1);
                           return;
                        }
                     } else {
                        if (owner.equals("java/io/PrintStream") && name.equals("println")) {
                           SpecialStackValue.SpecialValueType streamType = ((SpecialStackValue)this.stack.remove(this.stack.size() - 2)).getType();
                           if (streamType == SpecialStackValue.SpecialValueType.SYSTEM_OUT) {
                              Player.getCurrentSelection().SendMessage(new CodeValue[]{this.stack.remove(this.stack.size() - 1).getTransformedValue()}, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
                           } else {
                              if (streamType != SpecialStackValue.SpecialValueType.SYSTEM_ERR) {
                                 throw new IllegalStateException("Unknown special value type: " + streamType);
                              }

                              Player.getCurrentSelection().SendMessage(new CodeValue[]{(new Text()).Set("آ§c"), this.stack.remove(this.stack.size() - 1).getTransformedValue()}, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
                           }
                        } else {
                           try {
                              JFDFCompiler.addUsedClass(Class.forName(owner.replace('/', '.')));
                           } catch (ClassNotFoundException var28) {
                              throw new RuntimeException("Something went wrong.", var28);
                           }

                           CodeValue[] invokeVirtualArgs = new CodeValue[3 + argsCount];
                           invokeVirtualArgs[0] = stackValue.getTransformedValue();
                           if (JFDFCompiler.useNextPatchFeatures) {
                              ((Variable)invokeVirtualArgs[0]).setName(((Variable)invokeVirtualArgs[0]).getName().replace("%var(_jfdfFD)", "%math(%var(_jfdfFD) - 1)"));
                           } else {
                              ((Variable)invokeVirtualArgs[0]).setName(((Variable)invokeVirtualArgs[0]).getName().replace("%var(_jfdfFD)", "%var(_jfdfPFD)"));
                           }

                           invokeVirtualArgs[1] = (new Text()).Set(name);
                           invokeVirtualArgs[2] = (new Text()).Set(descriptor);

                           for(i = argsCount - 1; i >= 0; --i) {
                              CodeValue arg2 = this.stack.remove(this.stack.size() - 1).getTransformedValue();
                              String replaceValue = JFDFCompiler.useNextPatchFeatures ? "%math(%var(_jfdfFD) - 1)" : "%var(_jfdfPFD)";
                              if (arg2 instanceof Variable && ((Variable)arg2).getName().contains("%var(_jfdfFD)")) {
                                 ((Variable)arg2).setName(((Variable)arg2).getName().replace("%var(_jfdfFD)", replaceValue));
                              } else if (arg2 instanceof Number) {
                                 ((Number)arg2).SetToString(((Number)arg2).getValue().replace("%var(_jfdfFD)", replaceValue));
                              } else if (arg2 instanceof Text) {
                                 ((Text)arg2).Set(((Text)arg2).Get().replace("%var(_jfdfFD)", replaceValue));
                              }

                              invokeVirtualArgs[i + 3] = arg2;
                           }

                           if (!JFDFCompiler.useNextPatchFeatures) {
                              VariableControl.Set(new Variable("_jfdfPFD", Variable.Scope.LOCAL), new Variable("_jfdfFD", Variable.Scope.LOCAL));
                           }

                           VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
                           VariableControl.CreateList(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), invokeVirtualArgs);
                           Functions.Call("_jfdf>std>invokeVirtual");
                           this.stack.remove(this.stack.size() - 1);
                           returnVariableName = Type.getReturnType(descriptor).getDescriptor();
                           if (!returnVariableName.equals("V")) {
                              returnVariableName = this.getTempVariableName();
                              VariableControl.Set(new Variable(returnVariableName, Variable.Scope.LOCAL), new Variable("_jfdfrv", Variable.Scope.LOCAL));
                              this.stack.add((IStackValue)(new VariableStackValue(returnVariableName, returnVariableName)));
                           }
                        }

                        return;
                     }
                  }
               }
            case 183:
               argsCount = Type.getArgumentTypes(descriptor).length;
               stackValue = (IStackValue)this.stack.get(this.stack.size() - (argsCount + 1));
               if (name.equals("<init>")) {
                  if (stackValue instanceof CodeStackValue) {
                     if (CompilerAddons.publishInvokeConstructorEvent(owner, descriptor, this.stack)) {
                        return;
                     } else {
                        methodArgs = new Object[argsCount];

                        for(i = argsCount - 1; i >= 0; --i) {
                           stackValue = this.stack.remove(this.stack.size() - 1);
                           if (stackValue instanceof TextStackValue) {
                              methodArgs[i] = ((TextStackValue)stackValue).getValue().Get();
                           } else if (stackValue instanceof NumberStackValue) {
                              methodArgs[i] = ((NumberStackValue)stackValue).getJavaValue();
                           } else {
                              if (!(stackValue instanceof EnumStackValue)) {
                                 throw new IllegalStateException("Passing non-constant value is not allowed in a code value constructor.");
                              }

                              methodArgs[i] = ((EnumStackValue)stackValue).getEnumValue();
                           }
                        }

                        ((CodeStackValue)stackValue).callConstructor(descriptor, methodArgs);
                        this.stack.remove(this.stack.size() - 1);
                        return;
                     }
                  } else if (this.method.isConstructor() && stackValue instanceof VariableStackValue && ((Variable)stackValue.getTransformedValue()).getName().equals("_jfdffa>%var(_jfdfFD)>0")) {
                     i = (int)Arrays.stream(this.class_.getDeclaredFields()).filter((field) -> {
                        return !Modifier.isStatic(field.getModifiers());
                     }).count();
                     CodeValue[] objectDataAddition;
                     if (!owner.equals("java/lang/Object") && !owner.equals("java/lang/Enum")) {
                        objectDataAddition = new CodeValue[i];
                        Arrays.fill(objectDataAddition, (new Number()).Set(0));

                        try {
                           JFDFCompiler.addUsedClass(Class.forName(owner.replace('/', '.')));
                        } catch (ClassNotFoundException var22) {
                           throw new RuntimeException("Something went wrong.", var22);
                        }

                        if (!JFDFCompiler.useNextPatchFeatures) {
                           VariableControl.Set(new Variable("_jfdfPFD", Variable.Scope.LOCAL), new Variable("_jfdfFD", Variable.Scope.LOCAL));
                        }

                        VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));

                        for(i = 1; i <= argsCount; ++i) {
                           CodeValue arg2 = this.stack.remove(this.stack.size() - (argsCount - i) - 1).getTransformedValue();
                           String replaceValue = JFDFCompiler.useNextPatchFeatures ? "%math(%var(_jfdfFD) - 1)" : "%var(_jfdfPFD)";
                           if (arg2 instanceof Variable && ((Variable)arg2).getName().contains("%var(_jfdfFD)")) {
                              ((Variable)arg2).setName(((Variable)arg2).getName().replace("%var(_jfdfFD)", replaceValue));
                           } else if (arg2 instanceof Number) {
                              ((Number)arg2).SetToString(((Number)arg2).getValue().replace("%var(_jfdfFD)", replaceValue));
                           } else if (arg2 instanceof Text) {
                              ((Text)arg2).Set(((Text)arg2).Get().replace("%var(_jfdfFD)", replaceValue));
                           }

                           VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>" + i, Variable.Scope.LOCAL), arg2);
                        }

                        returnVariableName = descriptor;

                        try {
                           MethodWrapper invokeMethod2 = MethodWrapper.getWrapper(Class.forName(owner.replace('/', '.')), name, returnVariableName);
                           if (invokeMethod2.getAnnotation(MethodFallback.class) != null) {
                              returnVariableName = ((MethodFallback)invokeMethod2.getAnnotation(MethodFallback.class)).descriptor();
                           }
                        } catch (NoSuchMethodException | ClassNotFoundException var31) {
                           throw new RuntimeException("Something went wrong.", var31);
                        }

                        Functions.Call("_jfdf>" + owner + ">" + name + ">" + returnVariableName);
                        VariableControl.SetListValue(new Variable("_jfdfR%var(_jfdffa>%var(_jfdfFD)>0)", Variable.Scope.NORMAL), (new Number()).Set(1), (new Text()).Set(this.class_.getName().replace('.', '/')));
                        VariableControl.AppendValue(new Variable("_jfdfR%var(_jfdffa>%var(_jfdfFD)>0)", Variable.Scope.NORMAL), objectDataAddition);
                     } else {
                        objectDataAddition = new CodeValue[i + 1];
                        Arrays.fill(objectDataAddition, (new Number()).Set(0));
                        objectDataAddition[0] = (new Text()).Set(this.class_.getName().replace('.', '/'));
                        VariableControl.CreateList(new Variable("_jfdfR%var(_jfdffa>%var(_jfdfFD)>0)", Variable.Scope.NORMAL), objectDataAddition);
                     }

                     this.stack.remove(this.stack.size() - 1);
                     return;
                  } else if (CompilerAddons.publishInvokeConstructorEvent(owner, descriptor, this.stack)) {
                     return;
                  } else {
                     try {
                        JFDFCompiler.addUsedClass(Class.forName(owner.replace('/', '.')));
                     } catch (ClassNotFoundException var23) {
                        throw new RuntimeException("Something went wrong.", var23);
                     }

                     this.stack.remove(this.stack.size() - argsCount - 1);
                     if (!JFDFCompiler.useNextPatchFeatures) {
                        VariableControl.Set(new Variable("_jfdfPFD", Variable.Scope.LOCAL), new Variable("_jfdfFD", Variable.Scope.LOCAL));
                     }

                     VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));

                     for(i = 1; i <= argsCount; ++i) {
                        arg = this.stack.remove(this.stack.size() - (argsCount - i) - 1).getTransformedValue();
                        returnVariableName = JFDFCompiler.useNextPatchFeatures ? "%math(%var(_jfdfFD) - 1)" : "%var(_jfdfPFD)";
                        if (arg instanceof Variable && ((Variable)arg).getName().contains("%var(_jfdfFD)")) {
                           ((Variable)arg).setName(((Variable)arg).getName().replace("%var(_jfdfFD)", returnVariableName));
                        } else if (arg instanceof Number) {
                           ((Number)arg).SetToString(((Number)arg).getValue().replace("%var(_jfdfFD)", returnVariableName));
                        } else if (arg instanceof Text) {
                           ((Text)arg).Set(((Text)arg).Get().replace("%var(_jfdfFD)", returnVariableName));
                        }

                        VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>" + i, Variable.Scope.LOCAL), arg);
                     }

                     returnDescriptor = descriptor;

                     try {
                        invokeMethod = MethodWrapper.getWrapper(Class.forName(owner.replace('/', '.')), name, returnDescriptor);
                        if (invokeMethod.getAnnotation(MethodFallback.class) != null) {
                           returnDescriptor = ((MethodFallback)invokeMethod.getAnnotation(MethodFallback.class)).descriptor();
                        }
                     } catch (NoSuchMethodException | ClassNotFoundException var32) {
                        throw new RuntimeException("Something went wrong.", var32);
                     }

                     Functions.Call("_jfdf>" + owner + "><init>>" + returnDescriptor);
                     return;
                  }
               } else {
                  try {
                     JFDFCompiler.addUsedClass(Class.forName(owner.replace('/', '.')));
                  } catch (ClassNotFoundException var24) {
                     throw new RuntimeException("Something went wrong.", var24);
                  }

                  if (!JFDFCompiler.useNextPatchFeatures) {
                     VariableControl.Set(new Variable("_jfdfPFD", Variable.Scope.LOCAL), new Variable("_jfdfFD", Variable.Scope.LOCAL));
                  }

                  VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));

                  for(i = 0; i <= argsCount; ++i) {
                     arg = this.stack.remove(this.stack.size() - (argsCount - i) - 1).getTransformedValue();
                     returnVariableName = JFDFCompiler.useNextPatchFeatures ? "%math(%var(_jfdfFD) - 1)" : "%var(_jfdfPFD)";
                     if (arg instanceof Variable && ((Variable)arg).getName().contains("%var(_jfdfFD)")) {
                        ((Variable)arg).setName(((Variable)arg).getName().replace("%var(_jfdfFD)", returnVariableName));
                     } else if (arg instanceof Number) {
                        ((Number)arg).SetToString(((Number)arg).getValue().replace("%var(_jfdfFD)", returnVariableName));
                     } else if (arg instanceof Text) {
                        ((Text)arg).Set(((Text)arg).Get().replace("%var(_jfdfFD)", returnVariableName));
                     }

                     VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>" + i, Variable.Scope.LOCAL), arg);
                  }

                  returnDescriptor = descriptor;

                  try {
                     invokeMethod = MethodWrapper.getWrapper(Class.forName(owner.replace('/', '.')), name, returnDescriptor);
                     if (invokeMethod.getAnnotation(MethodFallback.class) != null) {
                        returnDescriptor = ((MethodFallback)invokeMethod.getAnnotation(MethodFallback.class)).descriptor();
                     }
                  } catch (NoSuchMethodException | ClassNotFoundException var33) {
                     throw new RuntimeException("Something went wrong.", var33);
                  }

                  Functions.Call("_jfdf>" + owner + ">" + name + ">" + returnDescriptor);
                  returnVariableName = Type.getReturnType(descriptor).getDescriptor();
                  if (!returnVariableName.equals("V")) {
                     returnVariableName = this.getTempVariableName();
                     VariableControl.Set(new Variable(returnVariableName, Variable.Scope.LOCAL), new Variable("_jfdfrv", Variable.Scope.LOCAL));
                     this.stack.add((IStackValue)(new VariableStackValue(returnVariableName, returnVariableName)));
                  }

                  return;
               }
            case 184:
               if (CompilerAddons.publishInvokeStaticEvent(owner, name, descriptor, this.stack)) {
                  return;
               } else {
                  argsCount = Type.getArgumentTypes(descriptor).length;
                  int i2;
                  if (owner.equals("net/jfdf/compiler/util/CodeValueArrayBuilder") && name.equals("start")) {
                     i2 = ((NumberStackValue)this.stack.remove(this.stack.size() - 1)).getJavaValue().intValue();
                     this.stack.add((IStackValue)(new CodeArrayStackValue(i2)));
                  } else {
                     Stack stack;
                     SpecialStackValue specialStackValue;
                     SpecialStackValue.SpecialValueType valueType;
                     if (!owner.equals("net/jfdf/jfdf/mangement/Player") && !owner.equals("net/jfdf/jfdf/mangement/If$Player")) {
                        if (!owner.equals("net/jfdf/jfdf/mangement/Entity") && !owner.equals("net/jfdf/jfdf/mangement/If$Entity")) {
                           if (owner.startsWith("net/jfdf/jfdf/mangement/")) {
                              if (this.method.isStaticInitializer() && owner.equals("net/jfdf/jfdf/mangement/Control")) {
                                 if (name.equals("Wait")) {
                                    throw new IllegalStateException("Control: Wait is not allowed inside static initializer.");
                                 }

                                 if (name.equals("End")) {
                                    throw new IllegalStateException("Control: End is not allowed inside static initializer.");
                                 }
                              }

                              Object[] methodArgs2 = new Object[argsCount];
                              Type[] argumentTypes = Type.getArgumentTypes(descriptor);

                              for(i2 = argsCount - 1; i2 >= 0; --i2) {
                                 stackValue = this.stack.remove(this.stack.size() - 1);
                                 if (stackValue instanceof CodeArrayStackValue) {
                                    try {
                                       Class arrayClass = Class.forName(argumentTypes[i2].getElementType().getClassName()).arrayType();
                                       CodeValue[] array = ((CodeArrayStackValue)stackValue).getValues();
                                       methodArgs2[i2] = Arrays.copyOf(array, array.length, arrayClass);
                                    } catch (ClassNotFoundException var19) {
                                       throw new RuntimeException("Something went wrong.", var19);
                                    }
                                 } else if (stackValue instanceof EnumStackValue) {
                                    methodArgs2[i2] = ((EnumStackValue)stackValue).getEnumValue();
                                 } else if ((name.equals("Call") || name.equals("CallWithArgs")) && owner.equals("net/jfdf/jfdf/mangement/Functions") && stackValue instanceof TextStackValue) {
                                    methodArgs2[i2] = ((Text)stackValue.getTransformedValue()).Get();
                                 } else {
                                    if (stackValue instanceof NumberStackValue) {
                                       if (((NumberStackValue)stackValue).getJavaValue() == null) {
                                          methodArgs2[i2] = null;
                                          continue;
                                       }

                                       if (argumentTypes[i2] == Type.BOOLEAN_TYPE) {
                                          methodArgs2[i2] = ((NumberStackValue)stackValue).getJavaValue().intValue() == 1;
                                          continue;
                                       }
                                    }

                                    methodArgs2[i2] = stackValue.getTransformedValue();
                                 }
                              }

                              try {
                                 MethodWrapper.getWrapper(Class.forName(owner.replace('/', '.')), name, descriptor).invoke((Object)null, methodArgs2);
                                 return;
                              } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException var17) {
                                 throw new RuntimeException("Something went wrong.", var17);
                              } catch (InstantiationException | IllegalArgumentException | InvocationTargetException var18) {
                                 throw new RuntimeException("Invalid value types during invoking \"" + name + descriptor + "\" of class \"" + owner + "\".\nPassed arguments: " + Arrays.deepToString(methodArgs2) + "\nInvoked by : " + Type.getInternalName(this.class_) + ">" + this.method.getName() + this.method.getDescriptor() + ":" + this.lineNumber, var18);
                              }
                           }

                           if (owner.equals("net/jfdf/compiler/util/ValueUtils")) {
                              if (!name.equals("asReference")) {
                                 return;
                              }

                              stackValue = this.stack.remove(this.stack.size() - 1);
                              Variable reference;
                              if (stackValue instanceof ReferencedStackValue) {
                                 reference = ((ReferencedStackValue)stackValue).getReference();
                              } else {
                                 reference = new Variable("_jfdfR%var(" + ((Variable)stackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
                              }

                              this.stack.add((IStackValue)(new VariableStackValue("Ljava/lang/Object;", reference.getName(), Variable.Scope.NORMAL)));
                           } else {
                              if ((name.equals("valueOf") || name.endsWith("Value")) && owner.matches("java/lang/(Boolean|Byte|Short|Integer|Long|Character)")) {
                                 if (name.equals("valueOf")) {
                                    Variable value = this.getTempVariable();
                                    VariableControl.CreateList(value, this.stack.remove(this.stack.size() - 1).getTransformedValue());
                                    this.stack.add((IStackValue)(new VariableStackValue("J", value.getName())));
                                 } else {
                                    this.stack.add((IStackValue)(new MathFunctionStackValue((Variable)this.stack.remove(this.stack.size() - 1), (new Number()).Set(1), MathFunctionStackValue.MathFunction.LIST_VALUE)));
                                 }

                                 return;
                              }

                              if (owner.equals("java/util/Arrays")) {
                                 if (name.equals("toString")) {
                                    stackValue = this.stack.remove(this.stack.size() - 1);
                                    this.stack.add((IStackValue)(new TextStackValue("%var(_jfdfR%var(" + ((Variable)stackValue.getTransformedValue()).getName() + "))")));
                                 } else if (name.equals("asList")) {
                                    return;
                                 }
                              } else {
                                 try {
                                    JFDFCompiler.addUsedClass(Class.forName(owner.replace('/', '.')));
                                 } catch (ClassNotFoundException var21) {
                                    throw new RuntimeException("Something went wrong.", var21);
                                 }

                                 if (!JFDFCompiler.useNextPatchFeatures) {
                                    VariableControl.Set(new Variable("_jfdfPFD", Variable.Scope.LOCAL), new Variable("_jfdfFD", Variable.Scope.LOCAL));
                                 }

                                 VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));

                                 for(i2 = 0; i2 < argsCount; ++i2) {
                                    CodeValue arg2 = this.stack.remove(this.stack.size() - (argsCount - i2)).getTransformedValue();
                                    returnVariableName = JFDFCompiler.useNextPatchFeatures ? "%math(%var(_jfdfFD) - 1)" : "%var(_jfdfPFD)";
                                    if (arg2 instanceof Variable && ((Variable)arg2).getName().contains("%var(_jfdfFD)")) {
                                       ((Variable)arg2).setName(((Variable)arg2).getName().replace("%var(_jfdfFD)", returnVariableName));
                                    } else if (arg2 instanceof Number) {
                                       ((Number)arg2).SetToString(((Number)arg2).getValue().replace("%var(_jfdfFD)", returnVariableName));
                                    } else if (arg2 instanceof Text) {
                                       ((Text)arg2).Set(((Text)arg2).Get().replace("%var(_jfdfFD)", returnVariableName));
                                    }

                                    VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>" + i2, Variable.Scope.LOCAL), arg2);
                                 }

                                 String callDescriptor = descriptor;

                                 try {
                                    invokeClass = Class.forName(owner.replace('/', '.'));
                                    invokeMethod = MethodWrapper.getWrapper(invokeClass, name, callDescriptor);
                                    if (invokeMethod.getAnnotation(MethodFallback.class) != null) {
                                       callDescriptor = ((MethodFallback)invokeMethod.getAnnotation(MethodFallback.class)).descriptor();

                                       try {
                                          MethodWrapper.getWrapper(invokeClass, name, callDescriptor);
                                       } catch (NoSuchMethodException var20) {
                                          throw new IllegalStateException("Couldn't find " + name + callDescriptor + ".");
                                       }
                                    }
                                 } catch (NoSuchMethodException | ClassNotFoundException var30) {
                                    throw new RuntimeException("Something went wrong.", var30);
                                 }

                                 Functions.Call("_jfdf>" + owner + ">" + name + ">" + callDescriptor);
                                 returnDescriptor = Type.getReturnType(descriptor).getDescriptor();
                                 if (!returnDescriptor.equals("V")) {
                                    returnVariableName = this.getTempVariableName();
                                    VariableControl.Set(new Variable(returnVariableName, Variable.Scope.LOCAL), new Variable("_jfdfrv", Variable.Scope.LOCAL));
                                    this.stack.add((IStackValue)(new VariableStackValue(returnDescriptor, returnVariableName)));
                                 }
                              }
                           }
                        } else {
                           stack = this.stack;
                           switch (name) {
                              case "getCurrentSelection":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_CURRENT_SELECTION;
                                 break;
                              case "getDefaultEntity":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_DEFAULT;
                                 break;
                              case "getKiller":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_KILLER;
                                 break;
                              case "getDamager":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_DAMAGER;
                                 break;
                              case "getShooter":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_SHOOTER;
                                 break;
                              case "getVictim":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_VICTIM;
                                 break;
                              case "getProjectile":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_PROJECTILE;
                                 break;
                              case "getAllEntities":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_ALL_ENTITIES;
                                 break;
                              case "getAllMobs":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_ALL_MOBS;
                                 break;
                              case "getLastEntitySpawned":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_LAST_ENTITY_SPAWNED;
                                 break;
                              case "getLastMobSpawned":
                                 valueType = SpecialStackValue.SpecialValueType.ENTITY_LAST_MOB_SPAWNED;
                                 break;
                              default:
                                 throw new IllegalStateException("Unknown Entity class static method \"" + name + "\".");
                           }

                           specialStackValue = new SpecialStackValue(valueType);
                           stack.add((IStackValue)specialStackValue);
                        }
                     } else {
                        stack = this.stack;
                        switch (name) {
                           case "getCurrentSelection":
                              valueType = SpecialStackValue.SpecialValueType.PLAYER_CURRENT_SELECTION;
                              break;
                           case "getDefaultPlayer":
                              valueType = SpecialStackValue.SpecialValueType.PLAYER_DEFAULT;
                              break;
                           case "getKiller":
                              valueType = SpecialStackValue.SpecialValueType.PLAYER_KILLER;
                              break;
                           case "getDamager":
                              valueType = SpecialStackValue.SpecialValueType.PLAYER_DAMAGER;
                              break;
                           case "getShooter":
                              valueType = SpecialStackValue.SpecialValueType.PLAYER_SHOOTER;
                              break;
                           case "getVictim":
                              valueType = SpecialStackValue.SpecialValueType.PLAYER_VICTIM;
                              break;
                           case "getAllPlayers":
                              valueType = SpecialStackValue.SpecialValueType.PLAYER_ALL_PLAYERS;
                              break;
                           default:
                              throw new IllegalStateException("Unknown Player class static method \"" + name + "\".");
                        }

                        specialStackValue = new SpecialStackValue(valueType);
                        stack.add((IStackValue)specialStackValue);
                     }
                  }

                  return;
               }
            default:
               throw new IllegalStateException("Trying to call not supported method !\nInvoked Class: " + owner.replace('/', '.') + "\nInvoked Method: " + name + descriptor + "\nInvoke Opcode: " + opcode);
         }
      }
   }

   public void visitTypeInsn(int opcode, String type) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(opcode);
         if (opcode == 187) {
            if (CompilerAddons.publishInitClassEvent(type, this.stack)) {
               return;
            }

            if (type.startsWith("net/jfdf/jfdf/values/")) {
               this.stack.add((IStackValue)(new CodeStackValue(type)));
            } else {
               this.stack.add((IStackValue)(new ObjectStackValue(type, this.method.getName(), this.blockOperationIndex++)));
            }
         } else if (opcode == 189) {
            IStackValue arrayLength = this.stack.remove(this.stack.size() - 1);
            this.stack.add((IStackValue)(new ArrayStackValue("L" + type + ";", arrayLength, this.method.getName(), this.blockOperationIndex++)));
         } else if (opcode == 192 && this.stack.get(this.stack.size() - 1) instanceof VariableStackValue) {
            VariableStackValue variableStackValue = (VariableStackValue)this.stack.get(this.stack.size() - 1);
            Variable variable = (Variable)variableStackValue.getTransformedValue();
            this.stack.set(this.stack.size() - 1, (IStackValue)(new VariableStackValue(type, variable.getName(), variable.getScope())));
         }

         super.visitTypeInsn(opcode, type);
      }
   }

   public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(opcode);
         IStackValue objectStackValue;
         Variable reference;
         Class fieldClass;
         int fieldIndex;
         Stack var10000;
         Field field;
         Variable value;
         switch (opcode) {
            case 178:
               if (CompilerAddons.publishGetFieldEvent(true, owner, name, descriptor, this.stack)) {
                  return;
               }

               if (!owner.startsWith("net/jfdf/jfdf/values/") && !owner.startsWith("net/jfdf/jfdf/blocks")) {
                  if (owner.equals("java/lang/System")) {
                     if (name.equals("out")) {
                        this.stack.add((IStackValue)(new SpecialStackValue(SpecialStackValue.SpecialValueType.SYSTEM_OUT)));
                     } else {
                        if (!name.equals("err")) {
                           throw new IllegalStateException("You can't access any fields from java.lang.System except \"out\" and \"err\".");
                        }

                        this.stack.add((IStackValue)(new SpecialStackValue(SpecialStackValue.SpecialValueType.SYSTEM_ERR)));
                     }
                  } else {
                     try {
                        fieldClass = Class.forName(owner.replace('/', '.'));
                        JFDFCompiler.addUsedClass(fieldClass);
                        field = null;

                        while(field == null && fieldClass != Object.class) {
                           try {
                              field = fieldClass.getDeclaredField(name);
                           } catch (NoSuchFieldException var15) {
                              fieldClass = fieldClass.getSuperclass();
                           }
                        }

                        if (field == null) {
                           throw new NoSuchFieldException("Couldn't find static field \"" + name + "\" inside \"" + owner + "\".");
                        }

                        var10000 = this.stack;
                        String var10004 = Type.getInternalName(fieldClass);
                        var10000.add((IStackValue)(new VariableStackValue(descriptor, "_jfdf>" + var10004 + ">" + name, field.getAnnotation(Saved.class) == null ? Variable.Scope.NORMAL : Variable.Scope.SAVED)));
                     } catch (NoSuchFieldException | ClassNotFoundException var17) {
                        throw new RuntimeException("Something went wrong.", var17);
                     }
                  }
               } else {
                  try {
                     Object tag = Class.forName(owner.replace('/', '.')).getField(name).get((Object)null);
                     this.stack.add((IStackValue)(new EnumStackValue(tag)));
                  } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException var16) {
                     throw new RuntimeException("Something went wrong.", var16);
                  }
               }
               break;
            case 179:
               try {
                  fieldClass = Class.forName(owner.replace('/', '.'));
                  field = fieldClass.getDeclaredField(name);
                  IStackValue value2 = this.stack.remove(this.stack.size() - 1);
                  boolean isNewValue = value2 instanceof ReferencedStackValue;
                  if (isNewValue) {
                     ((ReferencedStackValue)value2).setAllocationVariable("_jfdf>" + owner + ">" + name, Variable.Scope.NORMAL);
                  } else {
                     var value3 = new Variable("_jfdf>" + owner + ">" + name, field.getAnnotation(Saved.class) == null ? Variable.Scope.NORMAL : Variable.Scope.SAVED);
                     ReferenceUtils.decrementIfReference(descriptor, value3);
                     VariableControl.Set(value3, ((IStackValue) value3).getTransformedValue());
                     ReferenceUtils.incrementIfReference(descriptor, value3);
                  }
                  break;
               } catch (NoSuchFieldException | ClassNotFoundException var13) {
                  throw new RuntimeException("Something went wrong.", var13);
               }
            case 180:
               if (CompilerAddons.publishGetFieldEvent(false, owner, name, descriptor, this.stack)) {
                  return;
               }

               try {
                  objectStackValue = this.stack.remove(this.stack.size() - 1);
                  if (objectStackValue instanceof ReferencedStackValue) {
                     reference = ((ReferencedStackValue)objectStackValue).getReference();
                  } else {
                     reference = new Variable("_jfdfR%var(" + ((Variable)objectStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
                  }

                  fieldClass = Class.forName(owner.replace('/', '.'));
                  fieldIndex = FieldsManager.getFieldIndex(fieldClass, name);
                  JFDFCompiler.addUsedClass(fieldClass);
                  if (descriptor.equals("Ljava/lang/String;")) {
                     var10000 = this.stack;
                     String var10003 = reference.getName();
                     var10000.add((IStackValue)(new TextStackValue("%index(" + var10003 + ", " + fieldIndex + ")")));
                  } else if (descriptor.matches("[ZBSIJFDC]")) {
                     this.stack.add((IStackValue)(new MathFunctionStackValue(reference, (new Number()).Set(fieldIndex), MathFunctionStackValue.MathFunction.LIST_VALUE)));
                  } else {
                     value = this.getTempVariable();
                     VariableControl.GetListValue(value, reference, (new Number()).Set(fieldIndex));
                     ReferenceUtils.incrementIfReference(descriptor, value);
                     this.stack.add((IStackValue)(new VariableStackValue(descriptor, value.getName())));
                  }
                  break;
               } catch (ClassNotFoundException var14) {
                  throw new RuntimeException("Something went wrong.", var14);
               }
            case 181:
               if (owner.startsWith("net/jfdf/jfdf/values/")) {
                  CodeValue variableValue = this.stack.remove(this.stack.size() - 2).getTransformedValue();
                  if (variableValue instanceof Variable) {
                     reference = (Variable)variableValue;
                     CodeValue fieldNewValue = this.stack.remove(this.stack.size() - 1).getTransformedValue();
                     String[] ownerSplitted = owner.split("/");
                     String var26 = ownerSplitted[ownerSplitted.length - 1];
                     switch (var26 + "." + name) {
                        case "Location.x":
                           VariableControl.SetCoord(reference, (ILocation)null, (INumber)fieldNewValue, Tags.Coordinate.X, Tags.CoordinateType.PLOT_COORDINATE);
                           break;
                        case "Location.y":
                           VariableControl.SetCoord(reference, (ILocation)null, (INumber)fieldNewValue, Tags.Coordinate.Y, Tags.CoordinateType.PLOT_COORDINATE);
                           break;
                        case "Location.z":
                           VariableControl.SetCoord(reference, (ILocation)null, (INumber)fieldNewValue, Tags.Coordinate.Z, Tags.CoordinateType.PLOT_COORDINATE);
                           break;
                        case "Location.yaw":
                           VariableControl.SetCoord(reference, (ILocation)null, (INumber)fieldNewValue, Tags.Coordinate.YAW, Tags.CoordinateType.PLOT_COORDINATE);
                           break;
                        case "Location.pitch":
                           VariableControl.SetCoord(reference, (ILocation)null, (INumber)fieldNewValue, Tags.Coordinate.PITCH, Tags.CoordinateType.PLOT_COORDINATE);
                           break;
                        case "Vector.x":
                           VariableControl.SetVectorComp(reference, (IVector)null, (INumber)fieldNewValue, Tags.Component.X);
                           break;
                        case "Vector.y":
                           VariableControl.SetVectorComp(reference, (IVector)null, (INumber)fieldNewValue, Tags.Component.Y);
                           break;
                        case "Vector.z":
                           VariableControl.SetVectorComp(reference, (IVector)null, (INumber)fieldNewValue, Tags.Component.Z);
                           break;
                        case "Sound.name":
                           VariableControl.SetSoundType(reference, (ISound)null, (IText)fieldNewValue);
                           break;
                        case "Sound.pitch":
                           VariableControl.SetSoundPitch(reference, (ISound)null, (INumber)fieldNewValue);
                           break;
                        case "Sound.volume":
                           VariableControl.SetSoundVolume(reference, (ISound)null, (INumber)fieldNewValue);
                           break;
                        case "Potion.potionType":
                           VariableControl.SetPotionType(reference, (IPotion)null, (IText)fieldNewValue);
                           break;
                        case "Potion.duration":
                           VariableControl.SetPotionDur(reference, (IPotion)null, (INumber)fieldNewValue);
                           break;
                        case "Potion.amplifier":
                           VariableControl.SetPotionAmp(reference, (IPotion)null, (INumber)fieldNewValue);
                           break;
                        default:
                           throw new IllegalStateException("Setting field \"" + name + "\" for type \"" + owner + "\" is not supported.");
                     }

                     return;
                  }

                  throw new IllegalStateException("Trying to set a field that does not belong to variable.");
               }

               try {
                  objectStackValue = this.stack.remove(this.stack.size() - 2);
                  if (objectStackValue instanceof ReferencedStackValue) {
                     reference = ((ReferencedStackValue)objectStackValue).getReference();
                  } else {
                     reference = new Variable("_jfdfR%var(" + ((Variable)objectStackValue.getTransformedValue()).getName() + ")", Variable.Scope.NORMAL);
                  }

                  fieldClass = Class.forName(owner.replace('/', '.'));
                  fieldIndex = FieldsManager.getFieldIndex(fieldClass, name);
                  IStackValue newValue = this.stack.remove(this.stack.size() - 1);
                  Number valuePointer = Number.AsListValue(reference, (new Number()).Set(fieldIndex));
                  ReferenceUtils.decrementIfReference(descriptor, valuePointer);
                  VariableControl.SetListValue(reference, (new Number()).Set(fieldIndex), newValue.getTransformedValue());
                  if (!objectStackValue.getTransformedValue().equals(newValue.getTransformedValue())) {
                     ReferenceUtils.incrementIfReference(descriptor, valuePointer);
                  }
               } catch (ClassNotFoundException var12) {
                  throw new RuntimeException("Something went wrong.", var12);
               }
         }

      }
   }

   public void visitLdcInsn(Object value) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(18);
         if (value.getClass() == String.class) {
            this.stack.add((IStackValue)(new TextStackValue((String)value)));
         } else if (value.getClass() == Byte.class) {
            this.stack.add((IStackValue)(new NumberStackValue((Byte)value)));
         } else if (value.getClass() == Short.class) {
            this.stack.add((IStackValue)(new NumberStackValue((Short)value)));
         } else if (value.getClass() == Integer.class) {
            this.stack.add((IStackValue)(new NumberStackValue((Integer)value)));
         } else if (value.getClass() == Long.class) {
            this.stack.add((IStackValue)(new NumberStackValue((Long)value)));
         } else if (value.getClass() == Float.class) {
            this.stack.add((IStackValue)(new NumberStackValue((Float)value)));
         } else if (value.getClass() == Double.class) {
            if (JFDFCompiler.realDoubles) {
               this.stack.add((IStackValue)(new CodeStackValue(new Vector((Double)value, 0.0, 0.0))));
            } else {
               this.stack.add((IStackValue)(new NumberStackValue((Double)value)));
            }
         } else if (value.getClass() == Character.class) {
            this.stack.add((IStackValue)(new NumberStackValue((Character)value)));
         } else {
            if (value.getClass() != Type.class) {
               throw new IllegalStateException("Unsupported constant value type: " + value.getClass().getName());
            }

            try {
               String classFullName = ((Type)value).getClassName();
               this.stack.add((IStackValue)(new ClassStackValue(Class.forName(classFullName))));
            } catch (ClassNotFoundException var3) {
               throw new RuntimeException(var3);
            }
         }

         super.visitLdcInsn(value);
      }
   }

   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(10000);
         throw new IllegalStateException("Try-catch statements are not supported !");
      }
   }

   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(171);
         throw new IllegalStateException("Lookup switches are not supported !");
      }
   }

   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(170);
         throw new IllegalStateException("Table switches are not supported !");
      }
   }

   public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(186);
         if (!CompilerAddons.publishInvokeDynamicEvent(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments, this.stack)) {
            throw new IllegalStateException("Invoke dynamic is not supported !");
         }
      }
   }

   public void visitIincInsn(int var, int increment) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(132);
         String variableName;
         if (Type.getArgumentTypes(this.method.getDescriptor()).length + (this.method.isMember() ? 1 : 0) > var) {
            variableName = "_jfdffa>%var(_jfdfFD)>" + var;
         } else {
            variableName = "_jfdffv>%var(_jfdfFD)>" + var;
         }

         IStackValue newVariableValue = null;

         for(int i = 0; i < this.stack.size(); ++i) {
            IStackValue stackValue = (IStackValue)this.stack.get(i);
            if (stackValue instanceof VariableStackValue) {
               VariableStackValue varStackValue = (VariableStackValue)stackValue;
               Variable variable = (Variable)varStackValue.getTransformedValue();
               if (variable.getName().equals(variableName)) {
                  if (newVariableValue == null) {
                     String newVariableName = this.getTempVariableName();
                     newVariableValue = new VariableStackValue("J", newVariableName);
                     VariableControl.Set(new Variable(newVariableName, Variable.Scope.LOCAL), variable);
                  }

                  this.stack.set(i, (IStackValue)newVariableValue);
               }
            }
         }

         VariableControl.Increment(new Variable(variableName, Variable.Scope.LOCAL), (new Number()).Set(increment));
      }
   }

   public void visitIntInsn(int opcode, int operand) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(opcode);
         switch (opcode) {
            case 16:
            case 17:
               this.stack.add((IStackValue)(new NumberStackValue(operand)));
               break;
            case 188:
               IStackValue arrayLength = this.stack.remove(this.stack.size() - 1);
               String var10000;
               switch (operand) {
                  case 4:
                     var10000 = "Z";
                     break;
                  case 5:
                     var10000 = "C";
                     break;
                  case 6:
                     var10000 = "F";
                     break;
                  case 7:
                     var10000 = "D";
                     break;
                  case 8:
                     var10000 = "B";
                     break;
                  case 9:
                     var10000 = "S";
                     break;
                  case 10:
                     var10000 = "I";
                     break;
                  case 11:
                     var10000 = "J";
                     break;
                  default:
                     throw new IllegalStateException("Unknown primitive array type: " + operand);
               }

               String descriptor = var10000;
               this.stack.add((IStackValue)(new ArrayStackValue(descriptor, arrayLength, this.method.getName(), this.blockOperationIndex++)));
               break;
            default:
               throw new IllegalStateException("Unknown opcode: " + opcode);
         }

         super.visitIntInsn(opcode, operand);
      }
   }

   public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
      if (!this.compileWithExecute) {
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         this.stack.onVisitInsn(197);
         throw new IllegalStateException("Multi-dimensional arrays are not supported !");
      }
   }

   public void visitJumpInsn(int opcode, Label label) {
      if (!this.compileWithExecute) {
         this.stack.onVisitInsn(opcode);
         CompilerAddons.setInstructionIndex(++this.instructionIndex);
         boolean elseAfterIf = false;
         boolean whileRepeat = false;
         boolean invertIf = false;
         boolean continueIf = false;
         boolean breakIf = false;
         int jumpToIndex = ((JumpInstructionData)this.instructionDataList.get(this.instructionIndex)).jumpToInstructionIndex;
         boolean isIf = opcode >= 153 && opcode <= 166 || opcode == 198 || opcode == 199;
         if (isIf) {
            if (this.endBracketIndices.contains(-jumpToIndex)) {
               if ((Integer)this.endBracketIndices.stream().filter((integer) -> {
                  return integer < 0;
               }).max(Comparator.naturalOrder()).orElse(0) != -jumpToIndex) {
                  throw new IllegalStateException("break label is not supported !");
               }

               breakIf = true;
               invertIf = true;
            } else {
               InstructionData beforeLabelInsn = (InstructionData)this.instructionDataList.get(jumpToIndex - 1);
               if (beforeLabelInsn.instructionOpcode == 167) {
                  JumpInstructionData gotoInsn = (JumpInstructionData)beforeLabelInsn;
                  if (gotoInsn.jumpToInstructionIndex == this.labelInstructionIndex) {
                     whileRepeat = true;
                  } else if (gotoInsn.jumpToInstructionIndex >= jumpToIndex && !this.endBracketIndices.contains(-gotoInsn.jumpToInstructionIndex)) {
                     elseAfterIf = true;
                     if (this.isOrIf || this.labelLastIfIndex == this.instructionIndex) {
                        this.endBracketIndices.add(gotoInsn.jumpToInstructionIndex);
                     }
                  }
               }
            }

            if (this.startRepeatBracketIndices.contains(jumpToIndex)) {
               if ((Integer)this.startRepeatBracketIndices.get(this.startRepeatBracketIndices.size() - 1) != jumpToIndex) {
                  throw new IllegalStateException("continue label is not supported !");
               }

               continueIf = true;
               invertIf = true;
            }
         }

         if (this.isOrIf) {
            if (this.labelIfVariable == null) {
               this.labelIfVariable = this.getTempVariable();
               VariableControl.Set(this.labelIfVariable, (new Number()).Set(0));
            }

            if (this.labelLastIfIndex != this.instructionIndex) {
               invertIf = !invertIf;
            }
         } else if (elseAfterIf && this.labelIfVariable == null) {
            this.labelIfVariable = this.getTempVariable();
            VariableControl.Set(this.labelIfVariable, (new Number()).Set(0));
         }

         String var10000;
         int i;
         switch (opcode) {
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 198:
            case 199:
               IStackValue stackValue = (IStackValue)this.stack.get(this.stack.size() - 1);
               boolean compareEqInvert = invertIf;
               switch (opcode) {
                  case 153:
                  case 198:
                     compareEqInvert = !invertIf;
                     var10000 = "!=";
                     break;
                  case 154:
                  case 199:
                     compareEqInvert = !invertIf;
                     var10000 = "=";
                     break;
                  case 155:
                     var10000 = ">=";
                     break;
                  case 156:
                     var10000 = "<";
                     break;
                  case 157:
                     var10000 = "<=";
                     break;
                  case 158:
                     var10000 = ">";
                     break;
                  default:
                     throw new IllegalStateException("Unexpected opcode: " + opcode);
               }

               String ifType = var10000;
               CodeValue[] items;
               if (stackValue instanceof CompareStackValue) {
                  CompareStackValue.CompareType compareType = ((CompareStackValue)stackValue).getCompareType();
                  items = new CodeValue[]{((CompareStackValue)stackValue).getStackValue1().getTransformedValue(), ((CompareStackValue)stackValue).getStackValue2().getTransformedValue()};
                  if (compareType == CompareStackValue.CompareType.NORMAL) {
                     invertIf = compareEqInvert;
                  } else if (compareType == CompareStackValue.CompareType.LIST_CONTAINS) {
                     ifType = "ListContains";
                     invertIf = opcode == 154 != invertIf;
                  }
               } else {
                  items = null;
               }

               IfHandler eventIfHandler = CompilerAddons.publishIfEvent(ifType, invertIf, this.stack);
               IfHandler ifHandler;
               if (eventIfHandler != null) {
                  ifHandler = eventIfHandler;
               } else {
                  if (items == null) {
                     items = new CodeValue[]{stackValue.getTransformedValue(), (new Number()).Set(0)};
                  }

                  String finalIfType = ifType;
                  boolean finalInvertIf = invertIf;
                  CodeValue[] finalItems = items;
                  ifHandler = new IfHandler() {
                     public void onIf() {
                        CodeManager.instance.addCodeBlock((new IfVariableBlock(finalIfType, finalInvertIf)).SetItems(finalItems));
                        CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
                     }

                     public void onRepeat() {
                        CodeManager.instance.addCodeBlock((new RepeatBlock(finalIfType, finalInvertIf)).SetItems(finalItems));
                        CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
                     }
                  };
                  this.stack.remove(this.stack.size() - 1);
               }

               if (whileRepeat) {
                  ifHandler.onRepeat();
               } else {
                  ifHandler.onIf();
               }
               break;
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
               switch (opcode) {
                  case 159:
                  case 165:
                     var10000 = "!=";
                     break;
                  case 160:
                  case 166:
                     var10000 = "=";
                     break;
                  case 161:
                     var10000 = ">=";
                     break;
                  case 162:
                     var10000 = "<";
                     break;
                  case 163:
                     var10000 = "<=";
                     break;
                  case 164:
                     var10000 = ">";
                     break;
                  default:
                     throw new IllegalStateException("Unexpected opcode: " + opcode);
               }

               String ifType2 = var10000;
               if (whileRepeat) {
                  CodeManager.instance.addCodeBlock((new RepeatBlock(ifType2, invertIf)).SetItems(this.stack.remove(this.stack.size() - 2).getTransformedValue(), this.stack.remove(this.stack.size() - 1).getTransformedValue()));
               } else {
                  CodeManager.instance.addCodeBlock((new IfVariableBlock(ifType2, invertIf)).SetItems(this.stack.remove(this.stack.size() - 2).getTransformedValue(), this.stack.remove(this.stack.size() - 1).getTransformedValue()));
               }

               CodeManager.instance.addCodeBlock(new BracketBlock(false, whileRepeat));
               break;
            case 167:
               if (this.startRepeatBracketIndices.contains(jumpToIndex) && this.endBracketIndices.contains(-(this.instructionIndex + 1))) {
                  this.startRepeatBracketIndices.remove(jumpToIndex);
                  return;
               }

               if (this.startRepeatBracketIndices.contains(jumpToIndex)) {
                  if ((Integer)this.startRepeatBracketIndices.get(this.startRepeatBracketIndices.size() - 1) == jumpToIndex) {
                     Control.Skip();
                     return;
                  }

                  throw new IllegalStateException("continue label is not supported !");
               }

               if (this.endBracketIndices.contains(-jumpToIndex)) {
                  if ((Integer)this.endBracketIndices.stream().filter((integer) -> {
                     return integer < 0;
                  }).max(Comparator.naturalOrder()).orElse(0) == -jumpToIndex) {
                     Control.StopRepeat();
                     return;
                  }

                  throw new IllegalStateException("break label is not supported !");
               }

               if (this.endBracketIndices.contains(jumpToIndex)) {
                  return;
               }

               i = -1;
               int incrementAmount = 0;
               boolean isContinue = false;

               for(int i2 = jumpToIndex; i2 < this.instructionDataList.size(); ++i2) {
                  InstructionData instructionData = (InstructionData)this.instructionDataList.get(i2);
                  InstructionType instructionType = instructionData.instructionType;
                  if (instructionType != InstructionType.LABEL && instructionType != InstructionType.FRAME && instructionType != InstructionType.LINE_NUMBER) {
                     if (i2 != -1) {
                        if (instructionData.instructionOpcode == 167) {
                           JumpInstructionData jumpInstructionData = (JumpInstructionData)instructionData;
                           if ((Integer)this.startRepeatBracketIndices.get(this.startRepeatBracketIndices.size() - 1) == jumpInstructionData.jumpToInstructionIndex) {
                              isContinue = true;
                           }
                        }
                        break;
                     }

                     if (instructionType != InstructionType.INTEGER_INCREMENT) {
                        break;
                     }

                     IntegerIncrementInstructionData incrementInsnData = (IntegerIncrementInstructionData)instructionData;
                     i2 = incrementInsnData.var;
                     incrementAmount = incrementInsnData.increment;
                  }
               }

               if (isContinue) {
                  --this.instructionIndex;
                  this.visitIincInsn(i, incrementAmount);
                  Control.Skip();
                  return;
               }
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
         }

         if (!isIf) {
            throw new IllegalStateException("Jump instructions are not supported ! (Opcode: " + opcode + ")\nMethod: " + Type.getInternalName(this.class_) + ">" + this.method.getName() + this.method.getDescriptor() + ":" + this.lineNumber);
         } else {
            if (breakIf) {
               Control.StopRepeat();
               If.End();
            } else if (continueIf) {
               Control.Skip();
               If.End();
            } else if (this.isOrIf) {
               if (whileRepeat) {
                  throw new IllegalStateException("Using or if inside a loop is not supported yet !");
               }

               VariableControl.Set(this.labelIfVariable, (new Number()).Set(1));
               If.End();
               if (this.labelLastIfIndex == this.instructionIndex) {
                  this.ifStackLens.add(this.stack.size());
                  this.elifVars.add((Object)null);
                  If.Variable.Equals(this.labelIfVariable, new CodeValue[]{(new Number()).Set(1)}, false);
                  if (!elseAfterIf || this.isOrIf && this.labelLastIfIndex != this.instructionIndex) {
                     this.endBracketIndices.add(jumpToIndex);
                  } else {
                     this.elseIndices.add(jumpToIndex);
                  }
               }
            } else {
               if (whileRepeat) {
                  this.startRepeatBracketIndices.add(this.labelInstructionIndex);
               } else {
                  this.ifStackLens.add(this.stack.size());
                  this.elifVars.add((Object)null);
               }

               if (elseAfterIf) {
                  if (this.labelLastIfIndex == this.instructionIndex) {
                     VariableControl.Set(this.labelIfVariable, (new Number()).Set(1));

                     for(i = 0; i < this.labelIfCount; ++i) {
                        If.End();
                     }

                     If.Variable.Equals(this.labelIfVariable, new CodeValue[]{(new Number()).Set(1)}, false);
                     this.elseIndices.add(jumpToIndex);
                  }
               } else {
                  this.endBracketIndices.add(whileRepeat ? -jumpToIndex : jumpToIndex);
               }
            }

         }
      }
   }

   public void visitEnd() {
      CompilerAddons.setTempVariableCallback((CompilerAddons.TempVariableCallback)null);
      CompilerAddons.setInstructionDataList((List)null);
      CompilerAddons.setInstructionIndex(-1);
      super.visitEnd();
   }

   private Variable getTempVariable() {
      String var10002 = this.method.getName();
      return new Variable("_jco>" + var10002 + ">" + this.blockOperationIndex++, Variable.Scope.LOCAL);
   }

   private String getTempVariableName() {
      String var10000 = this.method.getName();
      return "_jco>" + var10000 + ">" + this.blockOperationIndex++;
   }
}
