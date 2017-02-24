package br.com.trixti.itm.infra.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.service.produto.ProdutoService;



@Named
@FacesConverter(value= "produtoConverter")
public class ProdutoConverter implements Converter{
	
	 private @Inject ProdutoService produtoService;

	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Produto produto = new Produto();
		if (value != null && !value.isEmpty()){
			try {
				produto = this.produtoService.recuperar(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return produto;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value instanceof Produto) {
			Produto produto = (Produto) value;
            return produto.getId().toString();
        } else {
            throw new IllegalArgumentException("Cannot convert object");
        }
	}

}
