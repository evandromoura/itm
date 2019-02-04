package br.com.trixti.itm.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;

public class BasicCSVReader {
	private static final String SAMPLE_CSV_FILE_PATH = "/Users/evandro/novo.csv";
    private static final String SAMPLE_CSV_FILE = "/Users/evandro/novo_new.csv";


	public static void main(String[] args) throws IOException {

		Map<Integer, List<String>> mapaRegistros = new HashMap<Integer, List<String>>();
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id","protocolo","uniqueid","data_criacao","data_fechamento","status","grupo","assunto","usuario_criacao","data_ultima_atualizacao","usuario_ultima_atualizacao","id_contato	tipo","categoria","origem","atribuido_para","contem_anexo","data_sla","texto","reabertura","nome_beneficiario","telefone_beneficiario","endereco_beneficiario","email_beneficiario","codigo_beneficiario","cpf_cnpj_beneficiario","tipo_pessoa_beneficiario"));

		Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));

		int registro = 0;
		List<String> registros;
		for (CSVRecord csvRecord : csvParser) {
			
			registros = new ArrayList<String>();
			System.out.println("Record No - " + csvRecord.getRecordNumber());
			System.out.println("---------------");
			for (int i = 0; i < 26; i++) {
				if (i == 18) {
					registros.add(Jsoup.parse(csvRecord.get(i)).text());
				}else{
					registros.add(csvRecord.get(i));
				}
			}
			mapaRegistros.put(registro, registros);
			System.out.println("---------------\n\n");
			registro++;
		}
		
		for(int y=0;y<mapaRegistros.size();y++){
			csvPrinter.printRecord(y, mapaRegistros.get(y).toArray());
		}
		csvPrinter.flush(); 
	}
}