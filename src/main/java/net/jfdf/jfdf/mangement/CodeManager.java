package net.jfdf.jfdf.mangement;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

import net.jfdf.jfdf.AddonsManager;
import net.jfdf.jfdf.JFDFAddon;
import net.jfdf.jfdf.blocks.CodeBlock;
import net.jfdf.jfdf.blocks.CodeHeader;
import net.jfdf.jfdf.blocks.FunctionBlock;
import net.jfdf.jfdf.values.*;
import net.jfdf.jfdf.values.Variable.Scope;
import org.objectweb.asm.Type;

public class CodeManager {
	public static boolean checksumFunction = false;

	private final List<Variable> variables = new ArrayList<>();
	private final List<Method> functionsWithArgs = new ArrayList<>();
	private final LinkedHashMap<CodeHeader, List<CodeBlock>> codeBlocks = new LinkedHashMap<>();
	private String author = "net.jfdf.jfdf";

	public PLOT_SIZE plotSize;
	public PLAYER_RANK playerRank;

	public final static CodeManager instance = new CodeManager();

	public Variable getVariable(String variableName, Scope scope) {
		for (Variable variable : variables) {
			if (variable.getName().equals(variableName) && variable.getScope().equals(scope))
				return variable;
		}

		Variable variable = new Variable(variableName, scope);
		variables.add(variable);
		return variable;
	}
	
	public void addCodeBlock(CodeBlock codeBlock) {
		@SuppressWarnings("unchecked")
		List<CodeBlock>[] array = (List<CodeBlock>[]) new List[codeBlocks.size()];
		this.codeBlocks.values().toArray(array)[codeBlocks.size() - 1].add(codeBlock);

		try {
			JFDFAddon.class.getMethod("onAddCodeBlock", CodeBlock.class).invoke(null, codeBlock);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ignored) {}
	}

	public List<CodeBlock> getActiveCodeBlocks() {
		@SuppressWarnings("unchecked")
		List<CodeBlock>[] array = (List<CodeBlock>[]) new List[codeBlocks.size()];
		return this.codeBlocks.values().toArray(array)[codeBlocks.size() - 1];
	}
	
	public void addCodeBlocks(CodeHeader codeHeader, CodeBlock... codeBlocks) {
		this.codeBlocks.put(codeHeader, Arrays.asList(codeBlocks));
	}
	
	public void addCodeBlocks(CodeHeader codeHeader, ArrayList<CodeBlock> codeBlocks) {
		this.codeBlocks.put(codeHeader, codeBlocks);
	}

	public void addFunctionWithArgs(Method functionMethod) {
		functionsWithArgs.add(functionMethod);
	}

	public Method[] getFunctionsWithArgs() {
		return functionsWithArgs.toArray(new Method[0]);
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public List<String> getCodeValuesAsList() {
		Map<CodeHeader, List<CodeBlock>> addonCodeBlocks = new HashMap<>();
		List<String> codeValues = new ArrayList<>();

		List<String> checksumFunctions = new ArrayList<>();
		List<String> checksumValues = new ArrayList<>();

		for (Entry<CodeHeader, List<CodeBlock>> codeBlocksData : codeBlocks.entrySet()) {
			CodeHeader codeHeader = codeBlocksData.getKey();
			List<CodeBlock> codeBlocks = new ArrayList<>(codeBlocksData.getValue());

			addonCodeBlocks.putAll(AddonsManager.publishPreGenerateLine(codeHeader, codeBlocks));

			String codeNBT = "";
			List<String> codeBlocksJSON = new ArrayList<>();

			for (CodeBlock codeBlock : codeBlocks) {
				codeBlocksJSON.add(codeBlock.asJSON());
			}

			codeNBT += String.join(",", codeBlocksJSON) + "]}";

			if(checksumFunction && codeHeader instanceof FunctionBlock) {
				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));

					String checksum = String.format("%064x", new BigInteger(1, digest.digest()));
					checksumFunctions.add(((FunctionBlock) codeHeader).getName());
					checksumValues.add(checksum);

					codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" +
						(codeBlocks.size() == 0 ? "" : ",") + codeNBT;
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			codeNBT = "{\"blocks\":[" + codeHeader.asJSON() + (codeBlocks.size() != 0 || checksumFunction ? "," : "") + codeNBT;
			
	        try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(codeNBT.length());
				GZIPOutputStream gzip = new GZIPOutputStream(bos);

				gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
				gzip.close();
				bos.close();

				String compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
				codeValues.add(compressed);
				
				try {
					JFDFAddon.class.getMethod("onGetCodeHeaderValue", String.class).invoke(null, compressed);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {}
	        } catch (Exception t) {
	        	t.printStackTrace();
	        	throw new RuntimeException("Error while compressing");
			}
		}

		for (Entry<CodeHeader, List<CodeBlock>> codeBlocksData : addonCodeBlocks.entrySet()) {
			CodeHeader codeHeader = codeBlocksData.getKey();
			List<CodeBlock> codeBlocks = codeBlocksData.getValue();

			String codeNBT = "";
			List<String> codeBlocksJSON = new ArrayList<>();

			for (CodeBlock codeBlock : codeBlocks) {
				codeBlocksJSON.add(codeBlock.asJSON());
			}

			codeNBT += String.join(",", codeBlocksJSON) + "]}";

			if(checksumFunction && codeHeader instanceof FunctionBlock) {
				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));

					String checksum = String.format("%064x", new BigInteger(1, digest.digest()));
					checksumFunctions.add(((FunctionBlock) codeHeader).getName());
					checksumValues.add(checksum);

					codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" +
							(codeBlocks.size() == 0 ? "" : ",") + codeNBT;
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			codeNBT = "{\"blocks\":[" + codeHeader.asJSON() + (codeBlocks.size() != 0 || checksumFunction ? "," : "") + codeNBT;

			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(codeNBT.length());
				GZIPOutputStream gzip = new GZIPOutputStream(bos);

				gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
				gzip.close();
				bos.close();

				String compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
				codeValues.add(compressed);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error while compressing");
			}
		}

		if(checksumFunction) {
			FunctionBlock checksumCodeHeader = new FunctionBlock("_jfdf>checksum");
			addCodeBlocks(checksumCodeHeader, new ArrayList<>());

			SimpleDateFormat currentTime = new SimpleDateFormat("YYYY/MM/dd hh:mm:s a z", Locale.US);
			currentTime.setTimeZone(TimeZone.getTimeZone("GMT"));
			Player.getCurrentSelection().SendMessage(new CodeValue[] { new Text().Set("\u00A7bCode Last Updated: \u00A7e" + currentTime.format(new Date())) }, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);

			for (int i = 0; i < checksumFunctions.size(); i += 26) {
				if(i == 0) {
					VariableControl.CreateList(
							new Variable("_jco0", Variable.Scope.LOCAL),
							checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				} else {
					VariableControl.AppendValue(
							new Variable("_jco0", Variable.Scope.LOCAL),
							checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				}
			}

			for (int i = 0; i < checksumValues.size(); i += 26) {
				if(i == 0) {
					VariableControl.CreateList(
							new Variable("_jco1", Variable.Scope.LOCAL),
							checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				} else {
					VariableControl.AppendValue(
							new Variable("_jco1", Variable.Scope.LOCAL),
							checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				}
			}

			VariableControl.CreateDict(
					new Variable("tmp0", Variable.Scope.LOCAL),
					new Variable("_jco0", Variable.Scope.LOCAL),
					new Variable("_jco1", Variable.Scope.LOCAL)
			);

			VariableControl.Set(new Variable("_jfdfCSM", Scope.LOCAL), new net.jfdf.jfdf.values.Number().Set(1));
			Repeat.ForEachEntry(new Variable("tmp1", Variable.Scope.LOCAL), new Variable("tmp2", Variable.Scope.LOCAL), new Variable("tmp0", Variable.Scope.LOCAL));
				Functions.Call("%var(tmp1)");

				If.Variable.NotEquals(new Variable("_jfdfCS", Scope.LOCAL), new CodeValue[] { new Variable("tmp2", Variable.Scope.LOCAL) }, false);
					Player.getCurrentSelection().SendMessage(new CodeValue[] { new Text().Set("\u00A7cFound not updated function: \u00A74"), new Variable("tmp1", Variable.Scope.LOCAL) }, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
				If.End();
			Repeat.End();
			VariableControl.Set(new Variable("_jfdfCSM", Scope.LOCAL), new net.jfdf.jfdf.values.Number().Set(0));

			CodeHeader codeHeader = this.codeBlocks.keySet().toArray(CodeHeader[]::new)[codeBlocks.size() - 1];
			List<CodeBlock> codeBlocks = getActiveCodeBlocks();

			String codeNBT = "{\"blocks\":[";
			List<String> codeBlocksJSON = new ArrayList<>();

			codeBlocksJSON.add(codeHeader.asJSON());

			for (CodeBlock codeBlock : codeBlocks) {
				codeBlocksJSON.add(codeBlock.asJSON());
			}

			codeNBT += String.join(",", codeBlocksJSON) + "]}";

			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(codeNBT.length());
				GZIPOutputStream gzip = new GZIPOutputStream(bos);

				gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
				gzip.close();
				bos.close();

				String compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
				codeValues.add(compressed);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error while compressing");
			}

			this.codeBlocks.remove(checksumCodeHeader);
		}
        
        return codeValues;
	}

	public Map<CodeHeader, String> getCodeValuesAsMap() {
		Map<CodeHeader, List<CodeBlock>> addonCodeBlocks = new LinkedHashMap<>();
		Map<CodeHeader, String> codeValues = new LinkedHashMap<>();

		List<String> checksumFunctions = new ArrayList<>();
		List<String> checksumValues = new ArrayList<>();

		for (Entry<CodeHeader, List<CodeBlock>> codeBlocksData : codeBlocks.entrySet()) {
			CodeHeader codeHeader = codeBlocksData.getKey();
			List<CodeBlock> codeBlocks = new ArrayList<>(codeBlocksData.getValue());

			addonCodeBlocks.putAll(AddonsManager.publishPreGenerateLine(codeHeader, codeBlocks));

			String codeNBT = "";
			List<String> codeBlocksJSON = new ArrayList<>();

			for (CodeBlock codeBlock : codeBlocks) {
				codeBlocksJSON.add(codeBlock.asJSON());
			}

			codeNBT += String.join(",", codeBlocksJSON) + "]}";

			if(checksumFunction && codeHeader instanceof FunctionBlock) {
				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));

					String checksum = String.format("%064x", new BigInteger(1, digest.digest()));
					checksumFunctions.add(((FunctionBlock) codeHeader).getName());
					checksumValues.add(checksum);

					codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" +
							(codeBlocks.size() == 0 ? "" : ",") + codeNBT;
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			codeNBT = "{\"blocks\":[" + codeHeader.asJSON() + (codeBlocks.size() != 0 || checksumFunction ? "," : "") + codeNBT;

			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(codeNBT.length());
				GZIPOutputStream gzip = new GZIPOutputStream(bos);

				gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
				gzip.close();
				bos.close();

				String compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
				codeValues.put(codeHeader, compressed);

				try {
					JFDFAddon.class.getMethod("onGetCodeHeaderValue", String.class).invoke(null, compressed);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {}
			} catch (Exception t) {
				t.printStackTrace();
				throw new RuntimeException("Error while compressing");
			}
		}

		for (Entry<CodeHeader, List<CodeBlock>> codeBlocksData : addonCodeBlocks.entrySet()) {
			CodeHeader codeHeader = codeBlocksData.getKey();
			List<CodeBlock> codeBlocks = codeBlocksData.getValue();

			String codeNBT = "";
			List<String> codeBlocksJSON = new ArrayList<>();

			for (CodeBlock codeBlock : codeBlocks) {
				codeBlocksJSON.add(codeBlock.asJSON());
			}

			codeNBT += String.join(",", codeBlocksJSON) + "]}";

			if(checksumFunction && codeHeader instanceof FunctionBlock) {
				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					digest.update(("{\"blocks\":[" + codeNBT).getBytes(StandardCharsets.UTF_8));

					String checksum = String.format("%064x", new BigInteger(1, digest.digest()));
					checksumFunctions.add(((FunctionBlock) codeHeader).getName());
					checksumValues.add(checksum);

					codeNBT = "{\"id\":\"block\",\"block\":\"if_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCSM\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"num\",\"data\":{\"name\":\"1\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"bracket\",\"direct\":\"open\",\"type\":\"norm\"},{\"id\":\"block\",\"block\":\"set_var\",\"args\":{\"items\":[{\"item\":{\"id\":\"var\",\"data\":{\"name\":\"_jfdfCS\",\"scope\":\"local\"}},\"slot\":0},{\"item\":{\"id\":\"txt\",\"data\":{\"name\":\"" + checksum + "\"}},\"slot\":1}]},\"action\":\"=\"},{\"id\":\"block\",\"block\":\"control\",\"args\":{\"items\":[]},\"action\":\"Return\"},{\"id\":\"bracket\",\"direct\":\"close\",\"type\":\"norm\"}" +
							(codeBlocks.size() == 0 ? "" : ",") + codeNBT;
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}

			codeNBT = "{\"blocks\":[" + codeHeader.asJSON() + (codeBlocks.size() != 0 || checksumFunction ? "," : "") + codeNBT;

			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(codeNBT.length());
				GZIPOutputStream gzip = new GZIPOutputStream(bos);

				gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
				gzip.close();
				bos.close();

				String compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
				codeValues.put(codeHeader, compressed);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error while compressing");
			}
		}

		if(checksumFunction) {
			FunctionBlock checksumCodeHeader = new FunctionBlock("_jfdf>checksum");
			addCodeBlocks(checksumCodeHeader, new ArrayList<>());

			SimpleDateFormat currentTime = new SimpleDateFormat("YYYY/MM/dd hh:mm:s a z", Locale.US);
			currentTime.setTimeZone(TimeZone.getTimeZone("GMT"));
			Player.getCurrentSelection().SendMessage(new CodeValue[] { new Text().Set("\u00A7bCode Last Updated: \u00A7e" + currentTime.format(new Date())) }, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);

			for (int i = 0; i < checksumFunctions.size(); i += 26) {
				if(i == 0) {
					VariableControl.CreateList(
							new Variable("_jco0", Variable.Scope.LOCAL),
							checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				} else {
					VariableControl.AppendValue(
							new Variable("_jco0", Variable.Scope.LOCAL),
							checksumFunctions.subList(i, Math.min(i + 26, checksumFunctions.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				}
			}

			for (int i = 0; i < checksumValues.size(); i += 26) {
				if(i == 0) {
					VariableControl.CreateList(
							new Variable("_jco1", Variable.Scope.LOCAL),
							checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				} else {
					VariableControl.AppendValue(
							new Variable("_jco1", Variable.Scope.LOCAL),
							checksumValues.subList(i, Math.min(i + 26, checksumValues.size())).stream().map(s -> new Text().Set(s)).toArray(Text[]::new)
					);
				}
			}

			VariableControl.CreateDict(
					new Variable("tmp0", Variable.Scope.LOCAL),
					new Variable("_jco0", Variable.Scope.LOCAL),
					new Variable("_jco1", Variable.Scope.LOCAL)
			);

			VariableControl.Set(new Variable("_jfdfCSM", Scope.LOCAL), new net.jfdf.jfdf.values.Number().Set(1));
			Repeat.ForEachEntry(new Variable("tmp1", Variable.Scope.LOCAL), new Variable("tmp2", Variable.Scope.LOCAL), new Variable("tmp0", Variable.Scope.LOCAL));
			Functions.Call("%var(tmp1)");

			If.Variable.NotEquals(new Variable("_jfdfCS", Scope.LOCAL), new CodeValue[] { new Variable("tmp2", Variable.Scope.LOCAL) }, false);
			Player.getCurrentSelection().SendMessage(new CodeValue[] { new Text().Set("\u00A7cFound not updated function: \u00A74"), new Variable("tmp1", Variable.Scope.LOCAL) }, Tags.AlignmentMode.REGULAR, Tags.TextValueMerging.NO_SPACES);
			If.End();
			Repeat.End();
			VariableControl.Set(new Variable("_jfdfCSM", Scope.LOCAL), new net.jfdf.jfdf.values.Number().Set(0));

			CodeHeader codeHeader = this.codeBlocks.keySet().toArray(CodeHeader[]::new)[codeBlocks.size() - 1];
			List<CodeBlock> codeBlocks = getActiveCodeBlocks();

			String codeNBT = "{\"blocks\":[";
			List<String> codeBlocksJSON = new ArrayList<>();

			codeBlocksJSON.add(codeHeader.asJSON());

			for (CodeBlock codeBlock : codeBlocks) {
				codeBlocksJSON.add(codeBlock.asJSON());
			}

			codeNBT += String.join(",", codeBlocksJSON) + "]}";

			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(codeNBT.length());
				GZIPOutputStream gzip = new GZIPOutputStream(bos);

				gzip.write(codeNBT.getBytes(StandardCharsets.UTF_8));
				gzip.close();
				bos.close();

				String compressed = new String(Base64.getEncoder().encode(bos.toByteArray()), StandardCharsets.UTF_8);
				codeValues.put(checksumCodeHeader, compressed);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error while compressing");
			}

			this.codeBlocks.remove(checksumCodeHeader);
		}

		return codeValues;
	}
	
	public String[] getCodeValues() {
		List<String> codeValuesList = getCodeValuesAsList();
		String[] codeValues = new String[codeValuesList.size()];
		return codeValuesList.toArray(codeValues);
	}

	public List<String> getCodeValuesJSONAsList() {
		List<String> jsons = new ArrayList<>();

		for (Entry<CodeHeader, List<CodeBlock>> codeBlocksData : codeBlocks.entrySet()) {
			CodeHeader codeHeader = codeBlocksData.getKey();
			List<CodeBlock> codeBlocks = codeBlocksData.getValue();

			String codeNBT = "{\"blocks\":[";

			List<String> codeBlocksJSON = new ArrayList<>();

			codeBlocksJSON.add(codeHeader.asJSON());

			for (CodeBlock codeBlock : codeBlocks) {
				codeBlocksJSON.add(codeBlock.asJSON());
			}

			codeNBT += String.join(",", codeBlocksJSON) + "]}";
			jsons.add(codeNBT);
		}

		return jsons;
	}

	public String[] getCodeValuesJSON() {
		List<String> codeValuesJsonList = getCodeValuesJSONAsList();
		String[] codeValues = new String[codeValuesJsonList.size()];
		return codeValuesJsonList.toArray(codeValues);
	}
	
	public List<String> getGiveCommandsAsList() {
		ArrayList<String> giveCommands = new ArrayList<>();
		List<String> codeValues = getCodeValuesAsList();
		
		int i = 0;
		for (Entry<CodeHeader, List<CodeBlock>> codeBlocksPair : codeBlocks.entrySet()) {
			CodeHeader codeHeader = codeBlocksPair.getKey();
			String codeValue = codeValues.get(i);
			
			String giveCommand = "/give @p ender_chest{PublicBukkitValues: {\"hypercube:codetemplatedata\": '{\"author\":\"" + author + "" + "\",\"name\":\"" + codeHeader.getTemplateName() + "\",\"version\":1,\"code\":\"" + codeValue  + "\"}'}, display: {Name: '{\"text\":\"" + codeHeader.getTemplateNameWithColors() + "\"}'}} 1";
			giveCommands.add(giveCommand);
			i++;
		}
		return giveCommands;
	}
	
	public enum PLAYER_RANK {
		NO_RANK,
		NOBLE,
		EMPEROR,
		MYTHIC,
		OVERLORD
	}
	
	public enum PLOT_SIZE {
		BASIC,
		LARGE,
		MASSIVE
	}

	public Set<CodeHeader> getCodeHeaders() {
		return codeBlocks.keySet();
	}

	public List<CodeBlock> getCodeHeaderBlocks(CodeHeader codeHeader) {
		return codeBlocks.get(codeHeader);
	}
}
