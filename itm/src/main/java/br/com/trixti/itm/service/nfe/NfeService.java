package br.com.trixti.itm.service.nfe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.nfe.NfeDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Nfe;
import br.com.trixti.itm.entity.NfeArquivo;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.uploadarquivo.UploadArquivoService;
import br.com.trixti.itm.util.UtilData;


@Stateless
public class NfeService extends AbstractService<Nfe> {

	private @Inject NfeDAO nfeDAO;
	private @Inject UploadArquivoService uploadArquivoService;
	
	@Override
	public AbstractDAO<Nfe> getDAO() {
		return nfeDAO;
	}
	
	public Nfe recuperarPorMesAno(String mes,String ano) {
		try{
			return nfeDAO.recuperarPorMesAno(mes, ano).get(0);
		}catch(Exception e){
			return null;
		}	
	}

	public void incluir(String mes, String ano) {
		Nfe entidade = new Nfe();
		UtilData utilData = new UtilData();
		Date data = new Date();
		entidade.setMes(mes);
		entidade.setAno(ano);
		data = utilData.ajustaData(data, 1, Integer.valueOf(entidade.getMes()), Integer.valueOf(entidade.getAno()), 0, 0, 0);
		entidade.setData(new Date());
		entidade.setNome("Nfe_"+entidade.getMes()+"_"+entidade.getAno()+".xml");
		data = utilData.subtrairMeses(data, 1);
		nfeDAO.incluir(entidade);
	}
	
	
	public List<NfeArquivo> gerarNfeArquivo(List<Boleto> boletos,Nfe nfe){
		List<NfeArquivo> arquivos = new ArrayList<NfeArquivo>();
		StringBuilder sb = new StringBuilder();
		for(Boleto boleto:boletos){
			NfeArquivo nfeArquivo = new NfeArquivo();
			sb.append("010101010101010101010101010101010101");
			nfeArquivo.setNfe(nfe);
			nfeArquivo.setConteudo(sb.toString());
			arquivos.add(nfeArquivo);
		}
		return arquivos;
	}
	
}






