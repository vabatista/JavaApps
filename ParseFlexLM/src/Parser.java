import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private static final Pattern PADRAO = Pattern.compile("ibmratl.+(IN:|OUT:|TIMESTAMP)");
	private static Map<String, Estatistica> resultado;
	private static Set<String> diasProcessados;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String caminhoArquivoLog = args[0];
		
		resultado = new HashMap<String, Estatistica>();
		diasProcessados = new HashSet<String>();
		
		try {
			FileInputStream fstream = new FileInputStream(caminhoArquivoLog);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				processarLinha(strLine);
			}
			
			for (Entry<String, Estatistica> k : resultado.entrySet())
			{
				System.out.println(k.getKey() + "\t" 
						+ String.valueOf(k.getValue().getMaximo()) 
						+ "\t" + String.valueOf(k.getValue().getMedia()/k.getValue().getDias()));
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

	private static void processarLinha(String strLine) {
		
		Matcher m = PADRAO.matcher(strLine);
		if (m.find()) {
			int fim = m.end();
			if (m.group().contains("TIMESTAMP")) {
				String data = recuperarData(strLine, fim);
				if (!diasProcessados.contains(data))
				{
					diasProcessados.add(data);
					for (String k : resultado.keySet()) {
						Estatistica e = recuperarItem(k);
						
						e.atualizaSomaMedia();
						e.incrementaDias();
						e.setContagemAtual(0);
					}
				}
			} else if (m.group().contains("IN")) {
				String produto = recuperarNomeProduto(strLine, fim);
				Estatistica e = recuperarItem(produto);
				e.diminuiContagem();
			} else if (m.group().contains("OUT")) {
				String produto = recuperarNomeProduto(strLine, fim);
				Estatistica e = recuperarItem(produto);
				e.aumentaContagem();
			}  
		}
	}

	private static String recuperarData(String strLine, int fim) {
		return strLine.substring(fim);
	}

	private static Estatistica recuperarItem(String produto) {
		if (!resultado.containsKey(produto)) {
			resultado.put(produto, new Estatistica());
		}
		return resultado.get(produto);
	}

	private static String recuperarNomeProduto(String strLine, int fim) {
		return strLine.substring(fim, strLine.indexOf(" ", fim + 1)).replaceAll("\"", "");
	}

}
