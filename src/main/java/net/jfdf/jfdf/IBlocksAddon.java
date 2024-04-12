package net.jfdf.jfdf;

import java.util.List;
import java.util.Map;
import net.jfdf.jfdf.blocks.CodeHeader;

public interface IBlocksAddon {
   Map onPreGenerateLine(CodeHeader var1, List var2);
}
