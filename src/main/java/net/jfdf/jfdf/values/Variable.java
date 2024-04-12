package net.jfdf.jfdf.values;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Variable implements IItem, ILocation, INumber, IParticle, IPotion, IProjectile, ISound, IText, ISpawnEgg {
   private String name;
   private Scope scope;

   public Variable(Variable variable) {
      this.name = variable.getName();
      this.scope = variable.getScope();
   }

   public Variable(String name, Scope scope) {
      this.name = name;
      this.scope = scope;
   }

   public String getName() {
      return this.name;
   }

   public Scope getScope() {
      return this.scope;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setScope(Scope scope) {
      this.scope = scope;
   }

   public String toString() {
      return "Variable{name='" + this.name + "', scope=" + this.scope + "}";
   }

   public String asJSON() {
      String var10000 = this.name;
      return "{\"id\":\"var\",\"data\":{\"name\":\"" + var10000 + "\",\"scope\":\"" + this.scope.getJSONValue() + "\"}}";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Variable variable = (Variable)o;
         return this.name.equals(variable.name) && this.scope == variable.scope;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.name, this.scope});
   }

   public static enum Scope {
      UNSAVED(0, "unsaved"),
      NORMAL(0, "unsaved"),
      GLOBAL(0, "unsaved"),
      GAME(0, "unsaved"),
      SAVED(1, "saved"),
      LOCAL(2, "local");

      private static final Map values = new HashMap();
      private final int value;
      private final String jsonValue;

      private Scope(int value, String jsonValue) {
         this.value = value;
         this.jsonValue = jsonValue;
      }

      public static Scope valueOf(int type) {
         return (Scope)values.get(type);
      }

      public int getValue() {
         return this.value;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }

      public boolean equals(Scope scope) {
         return this.getValue() == scope.getValue();
      }

      static {
         Scope[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Scope type = var0[var2];
            values.put(type.getValue(), type);
         }

      }
   }
}
