package fis.htkh.services;

import fis.htkh.entities.Organization;
import fis.htkh.models.OrganizationModel;

import java.util.List;

public interface IOrganizationService {
	
	List<OrganizationModel> ListOrganization();
	
	List<OrganizationModel> FindByParentId(Long parentId);
	
	OrganizationModel GetDetail(Long id);
	
	Organization GetDetailById(Long id);
	
	boolean SaveData(OrganizationModel model);
	
	boolean EditData(OrganizationModel model);
	
	void DeleteData(Long id);
}
