package net.jfdf.compiler.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Scope {
   private final ScopeVariable[] scopeVariables;
   private List unknownScopeVariables;

   public Scope(ScopeVariable[] scopeVariables) {
      this.scopeVariables = scopeVariables;
   }

   public void addUnknownScopeVariable(ScopeVariable scopeVariable) {
      if (this.unknownScopeVariables == null) {
         this.unknownScopeVariables = new ArrayList();
      }

      if (!this.unknownScopeVariables.contains(scopeVariable)) {
         this.unknownScopeVariables.add(scopeVariable);
      }

   }

   public void end() {
      this.collect(Arrays.asList(this.scopeVariables));
      if (this.unknownScopeVariables != null) {
         this.collect(this.unknownScopeVariables);
      }

   }

   private void collect(List scopeVariables) {
      Iterator var2 = scopeVariables.iterator();

      while(true) {
         ScopeVariable scopeVariable;
         do {
            if (!var2.hasNext()) {
               return;
            }

            scopeVariable = (ScopeVariable)var2.next();
         } while(scopeVariable.type.startsWith("L") && !scopeVariable.type.startsWith("Lnet/jfdf/jfdf/values/"));

         if (scopeVariable.type.startsWith("[")) {
         }
      }
   }
}
