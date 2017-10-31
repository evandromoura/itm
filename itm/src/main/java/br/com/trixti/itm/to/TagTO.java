package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Tag;

public class TagTO {

	private Tag tag;
	private List<Tag> tags;
	private Tag tagPesquisa;
	
	
	public Tag getTag() {
		if (tag == null) {
			tag = new Tag();
		}
		return tag;
	}
	
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public List<Tag> getTags() {
		if (tags == null) {
			tags = new ArrayList<Tag>();
		}

		return tags;
	}
	
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Tag getTagPesquisa() {
		if (tagPesquisa == null) {
			tagPesquisa = new Tag();
		}
		return tagPesquisa;
	}

	public void setTagPesquisa(Tag tagPesquisa) {
		this.tagPesquisa = tagPesquisa;
	}

}