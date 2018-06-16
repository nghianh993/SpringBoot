package fis.htkh.models;

import java.util.ArrayList;
import java.util.List;

public class OrganizationModel {
	private Long id;
	private String text;
	private Long lever;
	private Long parentId;
	
	private List<OrganizationModel> nodes = new ArrayList<OrganizationModel>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getLever() {
		return lever;
	}

	public void setLever(Long lever) {
		this.lever = lever;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<OrganizationModel> getNodes() {
		return nodes;
	}

	public void setNodes(List<OrganizationModel> nodes) {
		this.nodes = nodes;
	}
}
