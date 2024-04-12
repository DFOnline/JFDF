package net.jfdf.addon.string;

import java.util.List;
import net.jfdf.compiler.addon.CompilerAddons;
import net.jfdf.compiler.addon.ICompilerAddon;
import net.jfdf.compiler.addon.IfHandler;
import net.jfdf.compiler.data.stack.IStackValue;
import net.jfdf.compiler.data.stack.Stack;
import net.jfdf.compiler.data.stack.TextStackValue;
import net.jfdf.compiler.data.stack.VariableStackValue;
import net.jfdf.jfdf.blocks.BracketBlock;
import net.jfdf.jfdf.blocks.IfVariableBlock;
import net.jfdf.jfdf.blocks.RepeatBlock;
import net.jfdf.jfdf.mangement.CodeManager;
import net.jfdf.jfdf.mangement.Functions;
import net.jfdf.jfdf.mangement.VariableControl;
import net.jfdf.jfdf.values.INumber;
import net.jfdf.jfdf.values.IText;
import net.jfdf.jfdf.values.Number;
import net.jfdf.jfdf.values.Tag;
import net.jfdf.jfdf.values.Tags;
import net.jfdf.jfdf.values.Text;
import net.jfdf.jfdf.values.Variable;
import net.jfdf.transformer.util.NumberMath;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

public class StringAddon implements ICompilerAddon {
   public boolean onInitClass(String type, Stack stack) {
      if (type.equals("java/lang/StringBuilder")) {
         Variable stringBuilder = CompilerAddons.getTempVariable();
         Variable reference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);
         VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
         VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), (new Text()).Set(stringBuilder.getName()));
         Functions.Call("_jfdf>std>sallocl");
         VariableControl.CreateList(reference, (new Text()).Set("\u0000p"));
         stack.add(new VariableStackValue("Ljava/lang/StringBuilder;", stringBuilder.getName()));
         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeConstructor(String type, String descriptor, Stack stack) {
      if (type.equals("java/lang/StringBuilder")) {
         switch (descriptor) {
            case "()V":
               stack.remove(stack.size() - 1);
               break;
            case "(Ljava/lang/String;)V":
               IStackValue builderValue = (IStackValue)stack.remove(stack.size() - 2);
               Variable stringBuilder = (Variable)builderValue.getTransformedValue();
               Variable reference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);
               VariableControl.AppendValue(reference, ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               break;
            default:
               throw new UnsupportedOperationException("StringBuilder" + descriptor + " is not supported !");
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeDynamic(String name, String descriptor, Handle methodHandle, Object[] methodArgs, Stack stack) {
      if (name.equals("makeConcatWithConstants") && methodHandle.getOwner().equals("java/lang/invoke/StringConcatFactory")) {
         String result = (String)methodArgs[0];
         int argsCount = Type.getArgumentTypes(descriptor).length;

         for(int i = 0; i < argsCount; ++i) {
            IStackValue argument = (IStackValue)stack.remove(stack.size() - (argsCount - i));
            int replaceIndex = result.indexOf(1);
            String var10000 = result.substring(0, replaceIndex);
            result = var10000 + TextUtils.toTextCode(argument.getTransformedValue()) + result.substring(replaceIndex + 1);
         }

         stack.add(new TextStackValue(result));
         return true;
      } else {
         return false;
      }
   }

   public boolean onInvokeMember(String owner, String name, String descriptor, Stack stack) {
      Variable stringBuilder;
      Variable builderReference;
      switch (owner) {
         case "java/lang/String":
            switch (name + descriptor) {
               case "concat(Ljava/lang/String;)Ljava/lang/String;":
                  String var18 = TextUtils.toTextCode(((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue());
                  stack.add(new TextStackValue(var18 + TextUtils.toTextCode(((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue())));
                  return true;
               case "toLowerCase()Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.SetCase(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.CapitalizationType.LOWERCASE);
                  stack.add(new VariableStackValue("Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "toUpperCase()Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.SetCase(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.CapitalizationType.UPPERCASE);
                  stack.add(new VariableStackValue("Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.ReplaceText(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.RegularExpressions.DISABLE, Tags.ReplacementType.ALL_OCCURRENCES);
                  stack.add(new VariableStackValue("Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.ReplaceText(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.RegularExpressions.ENABLE, Tags.ReplacementType.ALL_OCCURRENCES);
                  stack.add(new VariableStackValue("Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.ReplaceText(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue(), Tags.RegularExpressions.ENABLE, Tags.ReplacementType.FIRST_OCCURRENCE);
                  stack.add(new VariableStackValue("Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "repeat(I)Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.RepeatText(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
                  stack.add(new VariableStackValue("Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "substring(II)Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.TrimText(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 3)).getTransformedValue(), NumberMath.add((INumber)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue(), (new Number()).Set(1)), (INumber)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
                  stack.add(new VariableStackValue("Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "length()I":
                  stringBuilder = CompilerAddons.getTempVariable();
                  VariableControl.TextLength(stringBuilder, (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
                  stack.add(new VariableStackValue("I", stringBuilder.getName()));
                  return true;
               case "split(Ljava/lang/String;)[Ljava/lang/String;":
                  stringBuilder = CompilerAddons.getTempVariable();
                  builderReference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);
                  IText splitText = (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
                  IText splitByText = (IText)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
                  VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
                  VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), (new Text()).Set(stringBuilder.getName()));
                  Functions.Call("_jfdf>std>sallocl");
                  VariableControl.ReplaceText(builderReference, splitText, splitByText, (new Text()).Set("\u0001"), Tags.RegularExpressions.ENABLE, Tags.ReplacementType.ALL_OCCURRENCES);
                  VariableControl.SplitText(builderReference, builderReference, (new Text()).Set("\u0001"));
                  VariableControl.InsertListValue(builderReference, (new Number()).Set(1), (new Text()).Set("\u0000p"));
                  stack.add(new VariableStackValue("[Ljava/lang/String;", stringBuilder.getName()));
                  return true;
               case "equalsIgnoreCase(Ljava/lang/String;)Z":
               case "contains(Ljava/lang/CharSequence;)Z":
               case "matches(Ljava/lang/String;)Z":
               case "startsWith(Ljava/lang/String;)Z":
               case "endsWith(Ljava/lang/String;)Z":
                  IfStringStackValue.IfType ifType;
                  switch (name) {
                     case "equalsIgnoreCase":
                        ifType = IfStringStackValue.IfType.EQUALS_IGNORE_CASE;
                        break;
                     case "matches":
                        ifType = IfStringStackValue.IfType.EQUALS_REGEX;
                        break;
                     case "contains":
                        ifType = IfStringStackValue.IfType.CONTAINS;
                        break;
                     case "startsWith":
                        ifType = IfStringStackValue.IfType.STARTS_WITH;
                        break;
                     case "endsWith":
                        ifType = IfStringStackValue.IfType.ENDS_WITH;
                        break;
                     default:
                        throw new IllegalStateException("Unexpected value: " + name);
                  }

                  IfStringStackValue ifStringStackValue = new IfStringStackValue(ifType, new IStackValue[]{(IStackValue)stack.remove(stack.size() - 2), (IStackValue)stack.remove(stack.size() - 1)});
                  stack.add(ifStringStackValue);
                  return true;
               default:
                  throw new UnsupportedOperationException("String." + name + descriptor + " is not supported !");
            }
         case "java/lang/StringBuilder":
            if (name.equals("append") && descriptor.matches("\\(([IJDFZ]|Ljava/lang/String;)\\)Ljava/lang/StringBuilder;")) {
               IStackValue builderValue = (IStackValue)stack.get(stack.size() - 2);
               Variable stringBuilder2 = (Variable)builderValue.getTransformedValue();
               stringBuilder2 = new Variable("_jfdfR%var(" + stringBuilder2.getName() + ")", Variable.Scope.NORMAL);
               VariableControl.AppendValue(stringBuilder2, ((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue());
               stack.add(new VariableStackValue("Ljava/lang/StringBuilder;", stringBuilder2.getName()));
            } else {
               if (!name.equals("toString")) {
                  throw new UnsupportedOperationException("StringBuilder." + name + descriptor + " is not supported !");
               }

               Variable result = CompilerAddons.getTempVariable();
               IStackValue builderValue = (IStackValue)stack.remove(stack.size() - 1);
               stringBuilder = (Variable)builderValue.getTransformedValue();
               builderReference = new Variable("_jfdfR%var(" + stringBuilder.getName() + ")", Variable.Scope.NORMAL);
               VariableControl.SetListValue(builderReference, (new Number()).Set(1), (new Text()).Set(""));
               VariableControl.JoinText(result, builderReference, (new Text()).Set(""), (IText)null);
               VariableControl.SetListValue(builderReference, (new Number()).Set(1), (new Text()).Set("\u0000p"));
               stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
            }
            break;
         default:
            return false;
      }

      return true;
   }

   public boolean onInvokeStatic(String owner, String name, String descriptor, Stack stack) {
      if (owner.equals("java/lang/String")) {
         if (name.equals("join") && descriptor.equals("(Ljava/lang/CharSequence;[Ljava/lang/CharSequence;)Ljava/lang/String;")) {
            Variable result = CompilerAddons.getTempVariable();
            Variable temp = new Variable("tmp0", Variable.Scope.LOCAL);
            IText delimiter = (IText)((IStackValue)stack.remove(stack.size() - 2)).getTransformedValue();
            Variable arrayPointer = (Variable)((IStackValue)stack.remove(stack.size() - 1)).getTransformedValue();
            Variable arrayReference = new Variable("_jfdfR%var(" + arrayPointer.getName() + ")", Variable.Scope.NORMAL);
            VariableControl.Increment(new Variable("_jfdfFD", Variable.Scope.LOCAL));
            VariableControl.Set(new Variable("_jfdffa>%var(_jfdfFD)>0", Variable.Scope.LOCAL), (new Text()).Set(result.getName()));
            Functions.Call("_jfdf>std>sallocl");
            VariableControl.GetListValue(temp, arrayReference, (new Number()).Set(1));
            VariableControl.RemoveListIndex(arrayReference, (new Number()).Set(1));
            VariableControl.JoinText(result, arrayReference, delimiter, (IText)null);
            VariableControl.InsertListValue(arrayReference, (new Number()).Set(1), temp);
            stack.add(new VariableStackValue("Ljava/lang/String;", result.getName()));
            return true;
         } else {
            throw new UnsupportedOperationException("String." + name + descriptor + " is not supported !");
         }
      } else {
         return false;
      }
   }

   public IfHandler onIf(String defaultType, final boolean invert, Stack stack) {
      final IStackValue ifValue = (IStackValue)stack.get(stack.size() - 1);
      if (ifValue instanceof IfStringStackValue) {
         IfStringStackValue ifStringData = (IfStringStackValue)ifValue;
         final IStackValue[] stackValues = ifStringData.getStackValues();
         return new IfHandler() {
            public void onIf() {
               CodeManager.instance.addCodeBlock((new IfVariableBlock(this.getIfAction(), invert)).SetItems(stackValues[0].getTransformedValue(), stackValues[1].getTransformedValue()).SetTags(this.getIfTags()));
               CodeManager.instance.addCodeBlock(new BracketBlock(false, false));
            }

            public void onRepeat() {
               CodeManager.instance.addCodeBlock((new RepeatBlock(this.getIfAction(), invert)).SetItems(stackValues[0].getTransformedValue(), stackValues[1].getTransformedValue()).SetTags(this.getIfTags()));
               CodeManager.instance.addCodeBlock(new BracketBlock(false, true));
            }

            String getIfAction() {
               String var10000;
               switch (((IfStringStackValue)ifValue).getIfType()) {
                  case EQUALS_IGNORE_CASE:
                  case EQUALS_REGEX:
                     var10000 = "TextMatches";
                     break;
                  case CONTAINS:
                     var10000 = "Contains";
                     break;
                  case STARTS_WITH:
                     var10000 = "StartsWith";
                     break;
                  case ENDS_WITH:
                     var10000 = "EndsWith";
                     break;
                  default:
                     throw new IncompatibleClassChangeError();
               }

               return var10000;
            }

            Tag[] getIfTags() {
               Tag[] var10000;
               switch (((IfStringStackValue)ifValue).getIfType()) {
                  case EQUALS_IGNORE_CASE:
                     var10000 = new Tag[]{new Tag("Regular Expressions", "Disable"), new Tag("Ignore Case", "True")};
                     break;
                  case EQUALS_REGEX:
                     var10000 = new Tag[]{new Tag("Regular Expressions", "Enable"), new Tag("Ignore Case", "False")};
                     break;
                  case CONTAINS:
                  case STARTS_WITH:
                  case ENDS_WITH:
                     var10000 = new Tag[]{new Tag("Ignore Case", "False")};
                     break;
                  default:
                     throw new IncompatibleClassChangeError();
               }

               return var10000;
            }
         };
      } else {
         return null;
      }
   }
}
