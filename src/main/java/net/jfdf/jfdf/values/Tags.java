package net.jfdf.jfdf.values;

public class Tags {
   public static enum RedstonePowerMode {
      DIRECT_POWER("Direct power"),
      INDIRECT_POWER("Indirect power");

      private final String jsonValue;

      private RedstonePowerMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AllowListChanges {
      TRUE("True"),
      FALSE__COPY_LIST_("False (copy list)");

      private final String jsonValue;

      private AllowListChanges(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum PointLocationsInwards {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private PointLocationsInwards(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RotateLocation {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private RotateLocation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IncludeOriginBlock {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IncludeOriginBlock(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ChangeLocationRotation {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ChangeLocationRotation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CampfireSlot {
      VALUE_1("1"),
      VALUE_2("2"),
      VALUE_3("3"),
      VALUE_4("4");

      private final String jsonValue;

      private CampfireSlot(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ShowBottom {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ShowBottom(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TreeType {
      OAK_TREE("Oak Tree"),
      BIG_OAK_TREE("Big Oak Tree"),
      SWAMP_TREE("Swamp Tree"),
      SPRUCE_TREE("Spruce Tree"),
      SLIGHTLY_TALLER_SPRUCE_TREE("Slightly Taller Spruce Tree"),
      BIG_SPRUCE_TREE("Big Spruce Tree"),
      BIRCH_TREE("Birch Tree"),
      TALL_BIRCH_TREE("Tall Birch Tree"),
      JUNGLE_TREE("Jungle Tree"),
      BIG_JUNGLE_TREE("Big Jungle Tree"),
      JUNGLE_BUSH("Jungle Bush"),
      ACACIA_TREE("Acacia Tree"),
      DARK_OAK_TREE("Dark Oak Tree"),
      RED_MUSHROOM("Red Mushroom"),
      BROWN_MUSHROOM("Brown Mushroom"),
      CRIMSON_FUNGUS("Crimson Fungus"),
      WARPED_FUNGUS("Warped Fungus"),
      CHORUS_PLANT("Chorus Plant");

      private final String jsonValue;

      private TreeType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Visibility {
      VISIBLE("Visible"),
      VISIBLE__NO_HITBOX_("Visible (No hitbox)"),
      INVISIBLE("Invisible"),
      INVISIBLE__NO_HITBOX_("Invisible (No hitbox)");

      private final String jsonValue;

      private Visibility(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CloneBlockEntities {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private CloneBlockEntities(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnoreAir {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnoreAir(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ApplyItemMotion {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ApplyItemMotion(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum DelayUnit {
      TICKS("Ticks"),
      SECONDS("Seconds"),
      MINUTES("Minutes");

      private final String jsonValue;

      private DelayUnit(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Movement {
      UPWARDS("Upwards"),
      DIRECTIONAL("Directional");

      private final String jsonValue;

      private Movement(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Instant {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private Instant(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum EndofLifespan {
      DROP_ITEM("Drop item"),
      SHATTER("Shatter"),
      RANDOM("Random");

      private final String jsonValue;

      private EndofLifespan(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ContentType {
      TEXT_PLAIN("text/plain"),
      APPLICATION_JSON("application/json");

      private final String jsonValue;

      private ContentType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RequestMethod {
      POST("Post"),
      GET("Get"),
      PUT("Put"),
      DELETE("Delete");

      private final String jsonValue;

      private RequestMethod(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ReformonImpact {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ReformonImpact(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HurtHitEntities {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private HurtHitEntities(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ShowParticles {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ShowParticles(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum EventTarget {
      DEFAULT("Default"),
      KILLER("Killer"),
      DAMAGER("Damager"),
      VICTIM("Victim"),
      SHOOTER("Shooter"),
      PROJECTILE("Projectile");

      private final String jsonValue;

      private EventTarget(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CompareMode {
      NEAREST("Nearest"),
      FARTHEST("Farthest");

      private final String jsonValue;

      private CompareMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnoreY_Axis {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnoreY_Axis(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ComparisonMode {
      EXACTLY_EQUALS("Exactly equals"),
      IGNORE_STACK_SIZE("Ignore stack size"),
      IGNORE_DURABILITY_AND_STACK_SIZE("Ignore durability and stack size"),
      MATERIAL_ONLY("Material only");

      private final String jsonValue;

      private ComparisonMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum VariableType {
      NUMBER("Number"),
      TEXT("Text"),
      LOCATION("Location"),
      ITEM("Item"),
      LIST("List"),
      POTION_EFFECT("Potion effect"),
      SOUND("Sound"),
      PARTICLE("Particle"),
      VECTOR("Vector"),
      DICTIONARY("Dictionary");

      private final String jsonValue;

      private VariableType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum FoxType {
      RED("Red"),
      SNOW("Snow");

      private final String jsonValue;

      private FoxType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CarryingChest {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private CarryingChest(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Celebrate {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Celebrate(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Persistent {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Persistent(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HasDeathDrops {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private HasDeathDrops(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Angry {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Angry(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Interactions {
      TAKE__SWAP_OR_PLACE_ITEM("Take, swap or place item"),
      TAKE_OR_SWAP_ITEM("Take or swap item"),
      TAKE_ITEM("Take item"),
      PLACE_ITEM("Place item"),
      NONE("None");

      private final String jsonValue;

      private Interactions(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Pumpkin {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Pumpkin(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HasNectar {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private HasNectar(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Silenced {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Silenced(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ArmorStandPart {
      HEAD("Head"),
      BODY("Body"),
      LEFT_ARM("Left Arm"),
      RIGHT_ARM("Right Arm"),
      LEFT_LEG("Left Leg"),
      RIGHT_LEG("Right Leg");

      private final String jsonValue;

      private ArmorStandPart(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Sleeping {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Sleeping(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Screams {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Screams(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Glowing {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Glowing(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Resting {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Resting(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Invisible {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Invisible(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum MooshroomVariant {
      RED("Red"),
      BROWN("Brown");

      private final String jsonValue;

      private MooshroomVariant(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Baby {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Baby(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Biome {
      DESERT("Desert"),
      JUNGLE("Jungle"),
      PLAINS("Plains"),
      SAVANNA("Savanna"),
      SNOW("Snow"),
      SWAMP("Swamp"),
      TAIGA("Taiga");

      private final String jsonValue;

      private Biome(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum LlamaColor {
      BROWN("Brown"),
      CREAMY("Creamy"),
      WHITE("White"),
      GRAY("Gray");

      private final String jsonValue;

      private LlamaColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Phase {
      FLYING("Flying"),
      HOVERING("Hovering"),
      BREATH_ATTACK("Breath attack"),
      DYING("Dying");

      private final String jsonValue;

      private Phase(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Saddle {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Saddle(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SkinType {
      TABBY("Tabby"),
      TUXEDO("Tuxedo"),
      RED("Red"),
      SIAMESE("Siamese"),
      BRITISH_SHORTHAIR("British Shorthair"),
      CALICO("Calico"),
      PERSIAN("Persian"),
      RAGDOLL("Ragdoll"),
      WHITE("White"),
      JELLIE("Jellie"),
      BLACK("Black");

      private final String jsonValue;

      private SkinType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Pattern {
      KOB("Kob"),
      SUNSTREAK("Sunstreak"),
      SNOOPER("Snooper"),
      DASHER("Dasher"),
      BRINELY("Brinely"),
      SPOTTY("Spotty"),
      FLOPPER("Flopper"),
      STRIPEY("Stripey"),
      GLITTER("Glitter"),
      BLOCKFISH("Blockfish"),
      BETTY("Betty"),
      CLAYFISH("Clayfish"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private Pattern(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum BodyColor {
      WHITE("White"),
      ORANGE("Orange"),
      MAGENTA("Magenta"),
      LIGHT_BLUE("Light blue"),
      YELLOW("Yellow"),
      LIME("Lime"),
      PINK("Pink"),
      GRAY("Gray"),
      LIGHT_GRAY("Light gray"),
      CYAN("Cyan"),
      PURPLE("Purple"),
      BLUE("Blue"),
      BROWN("Brown"),
      GREEN("Green"),
      RED("Red"),
      BLACK("Black"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private BodyColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum PatternColor {
      WHITE("White"),
      ORANGE("Orange"),
      MAGENTA("Magenta"),
      LIGHT_BLUE("Light blue"),
      YELLOW("Yellow"),
      LIME("Lime"),
      PINK("Pink"),
      GRAY("Gray"),
      LIGHT_GRAY("Light gray"),
      CYAN("Cyan"),
      PURPLE("Purple"),
      BLUE("Blue"),
      BROWN("Brown"),
      GREEN("Green"),
      RED("Red"),
      BLACK("Black"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private PatternColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideNameTag {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private HideNameTag(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Gravity {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Gravity(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Charged {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Charged(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Rearing {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Rearing(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Pose {
      STANDING("Standing"),
      SLEEPING("Sleeping"),
      SWIMMING("Swimming"),
      SNEAKING("Sneaking");

      private final String jsonValue;

      private Pose(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum NameTagVisible {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private NameTagVisible(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Marker {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Marker(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HasBasePlate {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private HasBasePlate(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HasArms {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private HasArms(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IsSmall {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private IsSmall(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HasPhysics_Updates {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private HasPhysics_Updates(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AllowItemTaking_Adding {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private AllowItemTaking_Adding(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IsMarker_NoHitbox {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private IsMarker_NoHitbox(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IsVisible {
      TRUE("True"),
      FALSE("False"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private IsVisible(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Invulnerable {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Invulnerable(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum BasePlate {
      ENABLE("Enable"),
      DISABLE("Disable"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private BasePlate(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Arms {
      ENABLE("Enable"),
      DISABLE("Disable"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private Arms(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ArmsRaised {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private ArmsRaised(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Profession {
      UNEMPLOYED("Unemployed"),
      ARMORER("Armorer"),
      BUTCHER("Butcher"),
      CARTOGRAPHER("Cartographer"),
      CLERIC("Cleric"),
      FARMER("Farmer"),
      FISHERMAN("Fisherman"),
      FLETCHER("Fletcher"),
      LEATHERWORKER("Leatherworker"),
      LIBRARIAN("Librarian"),
      MASON("Mason"),
      NITWIT("Nitwit"),
      SHEPHERD("Shepherd"),
      TOOLSMITH("Toolsmith"),
      WEAPONSMITH("Weaponsmith");

      private final String jsonValue;

      private Profession(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AgeLock {
      ENABLE("Enable"),
      DISABLE("Disable"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private AgeLock(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HealMobtoMaxHealth {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private HealMobtoMaxHealth(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Dye {
      WHITE("White"),
      ORANGE("Orange"),
      MAGENTA("Magenta"),
      LIGHT_BLUE("Light blue"),
      YELLOW("Yellow"),
      LIME("Lime"),
      PINK("Pink"),
      GRAY("Gray"),
      LIGHT_GRAY("Light gray"),
      CYAN("Cyan"),
      PURPLE("Purple"),
      BLUE("Blue"),
      BROWN("Brown"),
      GREEN("Green"),
      RED("Red"),
      BLACK("Black");

      private final String jsonValue;

      private Dye(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum GeneType {
      AGGRESSIVE("Aggressive"),
      LAZY("Lazy"),
      WEAK("Weak"),
      WORRIED("Worried"),
      PLAYFUL("Playful"),
      NORMAL("Normal"),
      BROWN("Brown");

      private final String jsonValue;

      private GeneType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SetGene {
      MAIN_GENE("Main gene"),
      HIDDEN_GENE("Hidden gene"),
      BOTH("Both");

      private final String jsonValue;

      private SetGene(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Leaping {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Leaping(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Riptiding {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Riptiding(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AI {
      SENTIENT("Sentient"),
      INSENTIENT("Insentient"),
      NONE("None");

      private final String jsonValue;

      private AI(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HorseMarkings {
      NO_MARKINGS("No markings"),
      STOCKINGS_AND_BLAZE("Stockings and blaze"),
      PAINT("Paint"),
      SNOWFLAKE_APPALOOSA("Snowflake appaloosa"),
      SOOTY("Sooty"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private HorseMarkings(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HorseColor {
      WHITE("White"),
      BUCKSKIN("Buckskin"),
      FLAXEN_CHESTNUT("Flaxen chestnut"),
      BAY("Bay"),
      BLACK("Black"),
      DAPPLE_GRAY("Dapple gray"),
      DARK_BAY("Dark bay"),
      DON_T_CHANGE("Don't change");

      private final String jsonValue;

      private HorseColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AxolotlColor {
      PINK("Pink"),
      BROWN("Brown"),
      YELLOW("Yellow"),
      CYAN("Cyan"),
      BLUE("Blue");

      private final String jsonValue;

      private AxolotlColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IsSitting {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private IsSitting(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Sheared {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Sheared(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ParrotColor {
      RED("Red"),
      BLUE("Blue"),
      GREEN("Green"),
      CYAN("Cyan"),
      GRAY("Gray");

      private final String jsonValue;

      private ParrotColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IsHidden {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IsHidden(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum LengthType {
      LENGTH("Length"),
      LENGTH_SQUARED("Length Squared");

      private final String jsonValue;

      private LengthType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum GrowthUnit {
      GROWTH_STAGE_NUMBER("Growth stage number"),
      GROWTH_PERCENTAGE("Growth percentage");

      private final String jsonValue;

      private GrowthUnit(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TranslationType {
      FROM_HEX_TO_COLOR("From hex to color"),
      FROM___TO_COLOR("From & to color"),
      FROM_COLOR_TO__("From color to &"),
      STRIP_COLOR("Strip color");

      private final String jsonValue;

      private TranslationType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RoundMode {
      FLOOR("Floor"),
      NEAREST("Nearest"),
      CEILING("Ceiling");

      private final String jsonValue;

      private RoundMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ShiftDirection {
      ____UPWARDS_______DOWNWARDS("(+) Upwards / (-) Downwards"),
      ____FORWARDS_______BACKWARDS("(+) Forwards / (-) Backwards"),
      ____RIGHT_______LEFT("(+) Right / (-) Left");

      private final String jsonValue;

      private ShiftDirection(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SearchOrder {
      ASCENDING__FIRST_INDEX_("Ascending (first index)"),
      DESCENDING__LAST_INDEX_("Descending (last index)");

      private final String jsonValue;

      private SearchOrder(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnorePitch {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnorePitch(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CosineVariant {
      COSINE("Cosine"),
      INVERSE_COSINE__ARCCOSINE_("Inverse cosine (arccosine)"),
      HYPERBOLIC_COSINE("Hyperbolic cosine");

      private final String jsonValue;

      private CosineVariant(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnoreEmptySlots {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnoreEmptySlots(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HidePotionEffects {
      TRUE("True"),
      FALSE("False"),
      NO_CHANGE("No Change");

      private final String jsonValue;

      private HidePotionEffects(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideCanPlaceOn {
      TRUE("True"),
      FALSE("False"),
      NO_CHANGE("No Change");

      private final String jsonValue;

      private HideCanPlaceOn(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideCanDestroy {
      TRUE("True"),
      FALSE("False"),
      NO_CHANGE("No Change");

      private final String jsonValue;

      private HideCanDestroy(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideUnbreakable {
      TRUE("True"),
      FALSE("False"),
      NO_CHANGE("No Change");

      private final String jsonValue;

      private HideUnbreakable(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideAttributes {
      TRUE("True"),
      FALSE("False"),
      NO_CHANGE("No Change");

      private final String jsonValue;

      private HideAttributes(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideEnchantments {
      TRUE("True"),
      FALSE("False"),
      NO_CHANGE("No Change");

      private final String jsonValue;

      private HideEnchantments(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideColor {
      TRUE("True"),
      FALSE("False"),
      NO_CHANGE("No Change");

      private final String jsonValue;

      private HideColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Format {
      CUSTOM("Custom"),
      VALUE_2020_08_17_17_20_54("2020/08/17 17:20:54"),
      VALUE_2020_08_17("2020/08/17"),
      MON__AUGUST_17("Mon, August 17"),
      MONDAY("Monday"),
      VALUE_17_20_54("17:20:54"),
      VALUE_5_20_PM("5:20 PM"),
      VALUE_17H20M54S("17h20m54s"),
      VALUE_54_229_SECONDS("54.229 seconds");

      private final String jsonValue;

      private Format(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SortOrder {
      ASCENDING("Ascending"),
      DESCENDING("Descending");

      private final String jsonValue;

      private SortOrder(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum DistanceCalculation {
      PRIMARY("Primary"),
      SECONDARY("Secondary"),
      ADDITIVE("Additive"),
      SUBTRACTIVE("Subtractive"),
      MULTIPLICATIVE("Multiplicative"),
      DIVISIVE("Divisive");

      private final String jsonValue;

      private DistanceCalculation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum FractalType {
      BROWNIAN("Brownian"),
      BILLOW__DARK_EDGES_("Billow (Dark edges)"),
      RIGID__LIGHT_EDGES_("Rigid (Light edges)");

      private final String jsonValue;

      private FractalType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum FluidCollision {
      IGNORE_FLUIDS("Ignore fluids"),
      DETECT_FLUIDS("Detect fluids"),
      SOURCE_BLOCKS_ONLY("Source blocks only");

      private final String jsonValue;

      private FluidCollision(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnorePassableBlocks {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnorePassableBlocks(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CellEdgeType {
      EUCLIDEAN("Euclidean"),
      MANHATTAN("Manhattan"),
      NATURAL("Natural");

      private final String jsonValue;

      private CellEdgeType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TangentVariant {
      TANGENT("Tangent"),
      INVERSE_TANGENT__ARCTANGENT_("Inverse tangent (arctangent)"),
      HYPERBOLIC_TANGENT("Hyperbolic tangent");

      private final String jsonValue;

      private TangentVariant(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ColorChannels {
      RGB("RGB"),
      HSB("HSB"),
      HSL("HSL");

      private final String jsonValue;

      private ColorChannels(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TextValue {
      OWNER_NAME("Owner Name"),
      OWNER_UUID("Owner UUID");

      private final String jsonValue;

      private TextValue(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Spread {
      HORIZONTAL("Horizontal"),
      VERTICAL("Vertical");

      private final String jsonValue;

      private Spread(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HideDefault {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private HideDefault(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ReturnValueType {
      ITEM_ID__GOLDEN_APPLE_("Item ID (golden_apple)"),
      ITEM_NAME__GOLDEN_APPLE_("Item Name (Golden Apple)"),
      ITEM("Item");

      private final String jsonValue;

      private ReturnValueType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum DistanceType {
      DISTANCE_2D__X_Z_("Distance 2D (X/Z)"),
      DISTANCE_3D__X_Y_Z_("Distance 3D (X/Y/Z)"),
      ALTITUDE__Y_("Altitude (Y)");

      private final String jsonValue;

      private DistanceType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum LightType {
      COMBINED_LIGHT("Combined light"),
      SKY_LIGHT("Sky light"),
      BLOCK_LIGHT("Block light");

      private final String jsonValue;

      private LightType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CapitalizationType {
      UPPERCASE("UPPERCASE"),
      LOWERCASE("lowercase"),
      PROPER_CASE("Proper Case"),
      INVERT_CASE("iNVERT CASE"),
      RANDOM_CASE("RAnDoM cASe");

      private final String jsonValue;

      private CapitalizationType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RequireLodestoneatLocation {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private RequireLodestoneatLocation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ReplacementType {
      FIRST_OCCURRENCE("First occurrence"),
      ALL_OCCURRENCES("All occurrences");

      private final String jsonValue;

      private ReplacementType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Direction {
      FORWARD("Forward"),
      UPWARD("Upward"),
      SIDEWAYS("Sideways");

      private final String jsonValue;

      private Direction(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Axis {
      X("X"),
      Y("Y"),
      Z("Z");

      private final String jsonValue;

      private Axis(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ReturnType {
      TEXT__3D_("Text (3D)"),
      TEXT__2D_("Text (2D)"),
      VECTOR("Vector");

      private final String jsonValue;

      private ReturnType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CoordinateType {
      PLOT_COORDINATE("Plot coordinate"),
      WORLD_COORDINATE("World coordinate");

      private final String jsonValue;

      private CoordinateType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Breakability {
      BREAKABLE("Breakable"),
      UNBREAKABLE("Unbreakable");

      private final String jsonValue;

      private Breakability(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum DurabilityType {
      SET_DAMAGE("Set Damage"),
      SET_DAMAGE_PERCENTAGE("Set Damage Percentage"),
      SET_REMAINING("Set Remaining"),
      SET_REMAINING_PERCENTAGE("Set Remaining Percentage");

      private final String jsonValue;

      private DurabilityType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Distribution {
      NORMAL("Normal"),
      FOLDED_NORMAL("Folded normal");

      private final String jsonValue;

      private Distribution(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum FaceDirection {
      TOWARD_LOCATION("Toward location"),
      AWAY_FROM_LOCATION("Away from location");

      private final String jsonValue;

      private FaceDirection(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Input {
      DEGREES("Degrees"),
      RADIANS("Radians");

      private final String jsonValue;

      private Input(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SineVariant {
      SINE("Sine"),
      INVERSE_SINE__ARCSINE_("Inverse sine (arcsine)"),
      HYPERBOLIC_SINE("Hyperbolic sine");

      private final String jsonValue;

      private SineVariant(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AngleUnits {
      DEGREES("Degrees"),
      RADIANS("Radians");

      private final String jsonValue;

      private AngleUnits(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum BlockCollision {
      ALL_BLOCKS("All blocks"),
      NON_FLUID_BLOCKS("Non-fluid blocks"),
      SOLID_BLOCKS("Solid blocks"),
      NONE("None");

      private final String jsonValue;

      private BlockCollision(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum EntityCollision {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private EntityCollision(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RoundingMode {
      WHOLE_NUMBER("Whole number"),
      DECIMAL_NUMBER("Decimal number");

      private final String jsonValue;

      private RoundingMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Rotation {
      KEEP_ROTATION("Keep rotation"),
      REMOVE_ROTATION("Remove rotation");

      private final String jsonValue;

      private Rotation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Coordinates {
      ALL_COORDINATES("All coordinates"),
      X_AND_Z("X and Z"),
      ONLY_Y("Only Y");

      private final String jsonValue;

      private Coordinates(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Operation {
      ADD_NUMBER("Add number"),
      ADD_PERCENTAGE_TO_BASE("Add percentage to base"),
      MULTIPLY_MODIFIER_BY_PERCENTAGE("Multiply modifier by percentage");

      private final String jsonValue;

      private Operation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RegularExpressions {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private RegularExpressions(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Component {
      X("X"),
      Y("Y"),
      Z("Z");

      private final String jsonValue;

      private Component(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Coordinate {
      X("X"),
      Y("Y"),
      Z("Z"),
      PITCH("Pitch"),
      YAW("Yaw");

      private final String jsonValue;

      private Coordinate(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Operator {
      OR("|"),
      AND("&"),
      NOT("~"),
      XOR("^"),
      LEFT_SHIFT("<<"),
      RIGHT_SHIFT(">>"),
      UNSIGNED_RIGHT_SHIFT(">>>");

      private final String jsonValue;

      private Operator(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SignLine {
      VALUE_1("1"),
      VALUE_2("2"),
      VALUE_3("3"),
      VALUE_4("4"),
      ALL_LINES("All lines");

      private final String jsonValue;

      private SignLine(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum DivisionMode {
      DEFAULT("Default"),
      FLOOR_RESULT("Floor result");

      private final String jsonValue;

      private DivisionMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RotationAxis {
      PITCH("Pitch"),
      YAW("Yaw");

      private final String jsonValue;

      private RotationAxis(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ActiveEquipmentSlot {
      ANY("Any"),
      MAIN_HAND("Main hand"),
      OFF_HAND("Off hand"),
      HEAD("Head"),
      BODY("Body"),
      LEGS("Legs"),
      FEET("Feet");

      private final String jsonValue;

      private ActiveEquipmentSlot(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Attribute {
      ARMOR("Armor"),
      ARMOR_TOUGHNESS("Armor toughness"),
      ATTACK_DAMAGE("Attack damage"),
      ATTACK_SPEED("Attack speed"),
      MAXIMUM_HEALTH("Maximum health"),
      KNOCKBACK_RESISTANCE("Knockback resistance"),
      MOVEMENT_SPEED("Movement speed"),
      FOLLOW_RANGE("Follow range");

      private final String jsonValue;

      private Attribute(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AddLocationRotation {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private AddLocationRotation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum MatchRequirement {
      ENTIRE_NAME("Entire name"),
      FULL_WORD_S__IN_NAME("Full word(s) in name"),
      ANY_PART_OF_NAME("Any part of name");

      private final String jsonValue;

      private MatchRequirement(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TimeUnit {
      TICKS("Ticks"),
      SECONDS("Seconds"),
      MINUTES("Minutes");

      private final String jsonValue;

      private TimeUnit(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum LocalVariables {
      DON_T_COPY("Don't copy"),
      COPY("Copy"),
      SHARE("Share");

      private final String jsonValue;

      private LocalVariables(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TargetMode {
      WITH_CURRENT_TARGETS("With current targets"),
      WITH_CURRENT_SELECTION("With current selection"),
      WITH_NO_TARGETS("With no targets"),
      FOR_EACH_IN_SELECTION("For each in selection");

      private final String jsonValue;

      private TargetMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum InventoryType {
      ANY_INVENTORY("Any Inventory"),
      PLOT_MENU("Plot Menu"),
      CRAFTING_TABLE("Crafting Table"),
      CHEST("Chest"),
      DOUBLE_CHEST("Double Chest"),
      ENDER_CHEST("Ender Chest"),
      SHULKER_BOX("Shulker Box"),
      BARREL("Barrel"),
      FURNACE__ANY_("Furnace (any)"),
      FURNACE("Furnace"),
      BLAST_FURNACE("Blast Furnace"),
      SMOKER("Smoker"),
      DROPPER("Dropper"),
      DISPENSER("Dispenser"),
      BEACON("Beacon"),
      HOPPER("Hopper"),
      ANVIL("Anvil"),
      BREWING_STAND("Brewing Stand"),
      CARTOGRAPHY_TABLE("Cartography Table"),
      LOOM("Loom"),
      GRINDSTONE("Grindstone"),
      STONECUTTER("Stonecutter"),
      ENCHANTING_TABLE("Enchanting Table"),
      TRADER_MENU__ANY_("Trader Menu (any)"),
      VILLAGER_MENU("Villager Menu"),
      WANDERING_TRADER_MENU("Wandering Trader Menu"),
      HORSE_INVENTORY("Horse Inventory"),
      LLAMA_INVENTORY("Llama Inventory");

      private final String jsonValue;

      private InventoryType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CheckProperties {
      NONE("None"),
      AMPLIFIER("Amplifier"),
      DURATION("Duration"),
      AMPLIFIER_AND_DURATION("Amplifier and duration");

      private final String jsonValue;

      private CheckProperties(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Permission {
      OWNER("Owner"),
      DEVELOPER("Developer"),
      BUILDER("Builder"),
      DEVELOPER_OR_BUILDER("Developer or builder"),
      WHITELISTED("Whitelisted");

      private final String jsonValue;

      private Permission(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnoreCase {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnoreCase(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CompareTextTo {
      ENTITY_TYPE("Entity type"),
      NAME_OR_UUID("Name or UUID");

      private final String jsonValue;

      private CompareTextTo(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Shape {
      SPHERE("Sphere"),
      CIRCLE("Circle"),
      CUBE("Cube"),
      SQUARE("Square");

      private final String jsonValue;

      private Shape(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CheckedSlots {
      ENTIRE_INVENTORY("Entire inventory"),
      MAIN_INVENTORY("Main inventory"),
      UPPER_INVENTORY("Upper inventory"),
      HOTBAR("Hotbar"),
      ARMOR("Armor");

      private final String jsonValue;

      private CheckedSlots(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum CheckMode {
      HAS_ROOM_FOR_ANY_ITEM("Has Room for Any Item"),
      HAS_ROOM_FOR_ALL_ITEMS("Has Room for All Items");

      private final String jsonValue;

      private CheckMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum FluidMode {
      IGNORE_FLUIDS("Ignore fluids"),
      DETECT_FLUIDS("Detect fluids");

      private final String jsonValue;

      private FluidMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum InstantRespawn {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private InstantRespawn(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Hidden {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Hidden(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum OverwritePreviousFracture {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private OverwritePreviousFracture(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SpawnDeathDrops {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private SpawnDeathDrops(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AnimationArm {
      SWING_MAIN_ARM("Swing main arm"),
      SWING_OFF_ARM("Swing off arm");

      private final String jsonValue;

      private AnimationArm(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum EquipmentSlot {
      MAIN_HAND("Main hand"),
      OFF_HAND("Off hand"),
      HEAD("Head"),
      CHEST("Chest"),
      LEGS("Legs"),
      FEET("Feet");

      private final String jsonValue;

      private EquipmentSlot(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum BossBarSlot {
      ALL_BOSS_BARS("All boss bars"),
      VALUE_1("1"),
      VALUE_2("2"),
      VALUE_3("3"),
      VALUE_4("4"),
      VALUE_5("5"),
      VALUE_6("6"),
      VALUE_7("7"),
      VALUE_8("8"),
      VALUE_9("9");

      private final String jsonValue;

      private BossBarSlot(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum LaunchAxis {
      PITCH_AND_YAW("Pitch and Yaw"),
      YAW_ONLY("Yaw Only");

      private final String jsonValue;

      private LaunchAxis(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Collision {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Collision(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SoundSource {
      MASTER("Master"),
      MUSIC("Music"),
      JUKEBOX_NOTE_BLOCKS("Jukebox/Note Blocks"),
      WEATHER("Weather"),
      BLOCKS("Blocks"),
      HOSTILE_CREATURES("Hostile Creatures"),
      FRIENDLY_CREATURES("Friendly Creatures"),
      PLAYERS("Players"),
      AMBIENT_ENVIRONMENT("Ambient/Environment"),
      VOICE_SPEECH("Voice/Speech");

      private final String jsonValue;

      private SoundSource(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum EffectParticles {
      REGULAR("Regular"),
      AMBIENT("Ambient"),
      NONE("None");

      private final String jsonValue;

      private EffectParticles(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum OverwriteEffect {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private OverwriteEffect(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ShowIcon {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ShowIcon(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum FillType {
      WIREFRAME("Wireframe"),
      HOLLOW("Hollow"),
      SOLID("Solid");

      private final String jsonValue;

      private FillType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Weather {
      CLEAR("Clear"),
      DOWNFALL("Downfall"),
      RESET("Reset");

      private final String jsonValue;

      private Weather(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SpeedType {
      GROUND_SPEED("Ground speed"),
      FLIGHT_SPEED("Flight speed"),
      BOTH("Both");

      private final String jsonValue;

      private SpeedType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TextColor {
      WHITE("White"),
      ORANGE("Orange"),
      MAGENTA("Magenta"),
      LIGHT_BLUE("Light blue"),
      YELLOW("Yellow"),
      LIME("Lime"),
      PINK("Pink"),
      GRAY("Gray"),
      LIGHT_GRAY("Light gray"),
      CYAN("Cyan"),
      PURPLE("Purple"),
      BLUE("Blue"),
      BROWN("Brown"),
      GREEN("Green"),
      RED("Red"),
      BLACK("Black");

      private final String jsonValue;

      private TextColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum LocationAlignmentMode {
      BLOCK_CENTER("Block center"),
      LOWER_BLOCK_CORNER("Lower block corner");

      private final String jsonValue;

      private LocationAlignmentMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AlignmentMode {
      REGULAR("Regular"),
      CENTERED("Centered");

      private final String jsonValue;

      private AlignmentMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum TextValueMerging {
      ADD_SPACES("Add spaces"),
      NO_SPACES("No spaces");

      private final String jsonValue;

      private TextValueMerging(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum GiveExperience {
      POINTS("Points"),
      LEVELS("Levels"),
      LEVEL_PERCENTAGE("Level percentage");

      private final String jsonValue;

      private GiveExperience(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum DisguiseVisible {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private DisguiseVisible(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnoreDistance {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnoreDistance(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum PVP {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private PVP(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum KeepCurrentRotation {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private KeepCurrentRotation(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ToastType {
      ADVANCEMENT("Advancement"),
      GOAL("Goal"),
      CHALLENGE("Challenge");

      private final String jsonValue;

      private ToastType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HandSlot {
      MAIN_HAND("Main Hand"),
      OFF_HAND("Off Hand");

      private final String jsonValue;

      private HandSlot(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ContainerState {
      OPEN("Open"),
      CLOSED("Closed");

      private final String jsonValue;

      private ContainerState(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Flying {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Flying(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Gliding {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private Gliding(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ClearCraftingAndCursor {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ClearCraftingAndCursor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ClearMode {
      ENTIRE_INVENTORY("Entire inventory"),
      MAIN_INVENTORY("Main inventory"),
      UPPER_INVENTORY("Upper inventory"),
      HOTBAR("Hotbar"),
      ARMOR("Armor");

      private final String jsonValue;

      private ClearMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HealType {
      REGULAR_HEALTH("Regular Health"),
      ABSORPTION_HEALTH("Absorption Health"),
      COMBINED_HEALTH("Combined Health");

      private final String jsonValue;

      private HealType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum RowtoRemove {
      TOP_ROW("Top row"),
      BOTTOM_ROW("Bottom row");

      private final String jsonValue;

      private RowtoRemove(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum Gamemode {
      SURVIVAL("Survival"),
      CREATIVE("Creative"),
      ADVENTURE("Adventure");

      private final String jsonValue;

      private Gamemode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum PlayerListField {
      HEADER("Header"),
      FOOTER("Footer");

      private final String jsonValue;

      private PlayerListField(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum FlightMode {
      START_FLIGHT("Start Flight"),
      STOP_FLIGHT("Stop Flight");

      private final String jsonValue;

      private FlightMode(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum IgnoreBlocks {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private IgnoreBlocks(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum HealPlayertoMaxHealth {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private HealPlayertoMaxHealth(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AllowFlight {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private AllowFlight(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum InventoryKept {
      ENABLE("Enable"),
      DISABLE("Disable");

      private final String jsonValue;

      private InventoryKept(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AnimationType {
      HURT_ANIMATION("Hurt animation"),
      WAKE_UP__FADE_EFFECT_("Wake up (fade effect)");

      private final String jsonValue;

      private AnimationType(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SetExperience {
      POINTS("Points"),
      LEVEL("Level"),
      LEVEL_PERCENTAGE("Level Percentage");

      private final String jsonValue;

      private SetExperience(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum NewRowPosition {
      TOP_ROW("Top row"),
      BOTTOM_ROW("Bottom row");

      private final String jsonValue;

      private NewRowPosition(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum AddtoCurrentVelocity {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private AddtoCurrentVelocity(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum BarColor {
      RED("Red"),
      PURPLE("Purple"),
      PINK("Pink"),
      BLUE("Blue"),
      GREEN("Green"),
      YELLOW("Yellow"),
      WHITE("White");

      private final String jsonValue;

      private BarColor(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum SkyEffect {
      NONE("None"),
      CREATE_FOG("Create fog"),
      DARKEN_SKY("Darken sky"),
      BOTH("Both");

      private final String jsonValue;

      private SkyEffect(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum BarStyle {
      SOLID("Solid"),
      VALUE_6_SEGMENTS("6 segments"),
      VALUE_10_SEGMENTS("10 segments"),
      VALUE_12_SEGMENTS("12 segments"),
      VALUE_20_SEGMENTS("20 segments");

      private final String jsonValue;

      private BarStyle(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum BarSlot {
      SLOT_1("Slot 1"),
      SLOT_2("Slot 2"),
      SLOT_3("Slot 3"),
      SLOT_4("Slot 4"),
      SLOT_5("Slot 5"),
      SLOT_6("Slot 6"),
      SLOT_7("Slot 7"),
      SLOT_8("Slot 8"),
      SLOT_9("Slot 9");

      private final String jsonValue;

      private BarSlot(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }

   public static enum ReducedDebugInfoEnabled {
      TRUE("True"),
      FALSE("False");

      private final String jsonValue;

      private ReducedDebugInfoEnabled(String jsonValue) {
         this.jsonValue = jsonValue;
      }

      public String getJSONValue() {
         return this.jsonValue;
      }
   }
}
