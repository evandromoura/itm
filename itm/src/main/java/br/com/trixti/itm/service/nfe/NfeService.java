package br.com.trixti.itm.service.nfe;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.nfe.NfeDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Nfe;
import br.com.trixti.itm.entity.NfeArquivo;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.uploadarquivo.UploadArquivoService;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilString;

@Stateless
public class NfeService extends AbstractService<Nfe> {

	private @Inject NfeDAO nfeDAO;
	private @Inject UploadArquivoService uploadArquivoService;
	private @Inject ParametroService parametroService;

	@Override
	public AbstractDAO<Nfe> getDAO() {
		return nfeDAO;
	}

	public Nfe recuperarPorMesAno(String mes, String ano) {
		try {
			return nfeDAO.recuperarPorMesAno(mes, ano).get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Nfe recuperarPorBoleto(Boleto boleto) {
		return new Nfe();
	}

	public void incluir(String mes, String ano) {
		Nfe entidade = new Nfe();
		UtilData utilData = new UtilData();
		Date data = new Date();
		entidade.setMes(mes);
		entidade.setAno(ano);
		data = utilData.ajustaData(data, 1, Integer.valueOf(entidade.getMes()), Integer.valueOf(entidade.getAno()), 0,0, 0);
		entidade.setData(new Date());
		entidade.setNome("Nfe_" + entidade.getMes() + "_" + entidade.getAno() + ".txt");
		data = utilData.subtrairMeses(data, 1);
		
		nfeDAO.incluir(entidade);
	}
	
	private void comporNfePorBoleto(Boleto boleto){
		
		
	}
	
	
	

	public List<NfeArquivo> gerarNfeArquivo(List<Boleto> boletos, Nfe nfe) {
		try {
			Parametro parametro = parametroService.recuperarParametro();
			UtilArquivo utilArquivo = new UtilArquivo();
			UtilString utilString = new UtilString();
			UtilData utilData = new UtilData();
			List<NfeArquivo> arquivos = new ArrayList<NfeArquivo>();
			StringBuilder sb = new StringBuilder();
			File arquivoFinal = new File("arquivo-final.txt");
			File layout = utilArquivo.getFileFromBytes(
					UtilArquivo.converteInputStreamEmBytes(NfeService.class.getClassLoader().getResourceAsStream("layout-mod21.xml")),"layout-mod21.xml");
			FlatFile<Record> ff = Texgit.createFlatFile(layout);
			for (Boleto boleto : boletos) {
				Record nota = ff.createRecord("notas");
				nota.setValue("cpf_cnpj",utilString.retiraCaracteresEspeciais(boleto.getContrato().getCliente().getCpfCnpj()));
				nota.setValue("ie", "ISENTO");
				nota.setValue("razao_social", parametro.getNomeEmpresaCobranca());
				nota.setValue("uf", parametro.getUf());
				nota.setValue("classe_consumo", "0");
				nota.setValue("fase_tipo_utilizacao", "0");
				nota.setValue("grupo_tensao", "00");
				nota.setValue("cod_id_consumidor", utilString.completaComZerosAEsquerda(boleto.getContrato().getCliente().getId().toString(),12));
				nota.setValue("data_emissao", utilData.formatDate(boleto.getDataPagamento(), "yyyyMMdd"));
				nota.setValue("modelo", "21");
				nota.setValue("serie", "123");
				nota.setValue("numero", utilString.completaComZerosAEsquerda(boleto.getNossoNumero(), 9));
				nota.setValue("cod_aut_dig_doc_fiscal", "CRIAR O HASH");
				nota.setValue("valor_total", utilString.completaComZerosAEsquerda(boleto.getValor().toString().replace(".", ""),12));
				nota.setValue("bc_icms", utilString.completaComZerosAEsquerda("0",12));
				nota.setValue("icms_destacado", utilString.completaComZerosAEsquerda("0",12));
				nota.setValue("operacoes_isentas", utilString.completaComZerosAEsquerda("0",12));
				nota.setValue("outro_valores", utilString.completaComZerosAEsquerda("0",12));
				nota.setValue("situacao_documento", 1);
				nota.setValue("ano_mes_referencia", 2019);
				nota.setValue("referencia_item_nf", 19);
				nota.setValue("numero_terminal_tel", "61992988839");
				nota.setValue("ind_tipo_inf_campo1", "1");
				nota.setValue("tipo_cliente", "01");
				nota.setValue("subclasse_consumo", "01");
				nota.setValue("num_termi_tel_princ", "61992988839");
				nota.setValue("cnpj_emitente", "13699339000170");
				nota.setValue("num_cod_fat_comercial", "00000000000000012345");
				nota.setValue("valor_total_fat_comercial", boleto.getValor().toString().replace(".", ","));
				nota.setValue("data_leitura_anterior", "20190101");
				nota.setValue("data_leitura_atual", "20190101");
				nota.setValue("brancos1", "");
				nota.setValue("brancos2", "");
				nota.setValue("informacoes_adicionais", "Informacoes Add");
				nota.setValue("brancos3", "");
				nota.setValue("cod_aut_dig_registro", "");
				ff.addRecord(nota);
				List<String> conteudo = ff.write();
				for (String linha : conteudo) {
					sb.append(linha);
				}
				sb.append("\r\n");
			}
			
			NfeArquivo nfeArquivo = new NfeArquivo();
			nfeArquivo.setNfe(nfe);
			nfeArquivo.setConteudo(sb.toString());
			arquivos.add(nfeArquivo);
			return arquivos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Nfe recuperarCompleto(Integer id){
		return nfeDAO.recuperarCompleto(id);
	}

}
