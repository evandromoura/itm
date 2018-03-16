package br.com.trixti.itm.service.arquivosici;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.arquivosici.ArquivoSiciDAO;
import br.com.trixti.itm.entity.ArquivoSici;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.uploadarquivo.UploadArquivoService;
import br.com.trixti.itm.util.UtilData;


@Stateless
public class ArquivoSiciService extends AbstractService<ArquivoSici> {

	private @Inject ArquivoSiciDAO arquivoSiciDAO;
	private @Inject UploadArquivoService uploadArquivoService;
	
	@Override
	public AbstractDAO<ArquivoSici> getDAO() {
		return arquivoSiciDAO;
	}
	
	public ArquivoSici recuperarPorMesAno(String mes,String ano) {
		try{
			return arquivoSiciDAO.recuperarPorMesAno(mes, ano).get(0);
		}catch(Exception e){
			return null;
		}	
	}

	public void incluir(String mes, String ano) {
		ArquivoSici entidade = new ArquivoSici();
		UtilData utilData = new UtilData();
		Date data = new Date();
		entidade.setMes(mes);
		entidade.setAno(ano);
		data = utilData.ajustaData(data, 1, Integer.valueOf(entidade.getMes()), Integer.valueOf(entidade.getAno()), 0, 0, 0);
		entidade.setData(new Date());
		entidade.setNomeArquivo("SICI_"+entidade.getMes()+"_"+entidade.getAno()+".xml");
		data = utilData.subtrairMeses(data, 1);
		entidade.setXml(uploadArquivoService.gerarXml(data));
		arquivoSiciDAO.incluir(entidade);
	}

	
}






