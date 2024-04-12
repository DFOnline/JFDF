package net.jfdf.compiler.data.instruction;

public enum InstructionType {
   NO_ARGUMENTS,
   JUMP,
   LABEL,
   FRAME,
   LINE_NUMBER,
   FIELD,
   VARIABLE,
   INTEGER,
   INTEGER_INCREMENT,
   INVOKE,
   INVOKE_DYNAMIC,
   LDC,
   TYPE,
   MULTI_ANEW_ARRAY,
   TRY_CATCH,
   TABLE_SWITCH,
   LOOKUP_SWITCH,
   MAXS,
   LOCAL_VARIABLE;
}
