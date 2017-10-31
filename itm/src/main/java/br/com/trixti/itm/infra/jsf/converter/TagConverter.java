package br.com.trixti.itm.infra.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.service.tag.TagService;



@Named
@FacesConverter(value= "tagConverter")
public class TagConverter implements Converter{
	
	 private @Inject TagService tagService;

	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Tag tag = new Tag();
		if (value != null && !value.isEmpty()){
			try {
				tag = this.tagService.recuperar(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return tag;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value instanceof Tag) {
			Tag tag = (Tag) value;
            return tag.getId().toString();
        } else {
            throw new IllegalArgumentException("Cannot convert object");
        }
	}

}
