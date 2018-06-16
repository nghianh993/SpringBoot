package fis.htkh.servicesimpl;

import fis.htkh.entities.Organization;
import fis.htkh.models.OrganizationModel;
import fis.htkh.repositories.OrganizationRepository;
import fis.htkh.services.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public List<OrganizationModel> ListOrganization() {
		return FindByParentId(0L);
	}

	@Override
	public List<OrganizationModel> FindByParentId(Long parentId) {
		List<Organization> lstOrganization = organizationRepository.findByParentid(parentId);
		List<OrganizationModel> lstData = new ArrayList<OrganizationModel>();
		for (Organization organization : lstOrganization) {
			OrganizationModel model = new OrganizationModel();
			model.setId(organization.getId());
			model.setText(organization.getName());
			model.setLever(organization.getLever());
			model.setNodes(FindByParentId(model.getId()));
			lstData.add(model);
		}
		return lstData;
	}

	@Override
	public boolean SaveData(OrganizationModel model) {
		Optional<Organization> parent = organizationRepository.findById(model.getParentId());
		if (!parent.isPresent())
			return false;

		Organization item = new Organization();
		item.setName(model.getText());
		item.setLever(parent.get().getLever() + 1);
		item.setParentid(model.getParentId());
		organizationRepository.save(item);
		return true;
	}

	@Override
	public OrganizationModel GetDetail(Long id) {
		Optional<Organization> item = organizationRepository.findById(id);
		if (item.isPresent()) {
			Organization data = item.get();
			OrganizationModel model = new OrganizationModel();
			model.setId(data.getId());			
			model.setLever(data.getLever());
			model.setParentId(data.getParentid());
			model.setText(data.getName());
			return model;
		}

		return null;
	}

	@Override
	public boolean EditData(OrganizationModel model) {
		Optional<Organization> parent = organizationRepository.findById(model.getParentId());
		if (!parent.isPresent())
			if(model.getId() != 1)
				return false;
		Optional<Organization> item = organizationRepository.findById(model.getId());
		if (item.isPresent()) {
			Organization data = item.get();
			data.setName(model.getText());
			if(data.getId() == 1) {
				data.setLever(0L);
				data.setParentid(0L);
			}else {
				data.setLever(parent.get().getLever() + 1);
				data.setParentid(model.getParentId());
			}
			organizationRepository.save(data);
			return true;
		}
		return false;
	}

	@Override
	public void DeleteData(Long id) {
		List<Organization> lstOrganization = organizationRepository.findByParentid(id);
		for (Organization organization : lstOrganization) {
			organizationRepository.deleteById(organization.getId());
		}
		organizationRepository.deleteById(id);
	}

	@Override
	public Organization GetDetailById(Long id) {
		Optional<Organization> organization = organizationRepository.findById(id);
		if(organization.isPresent())
			return organization.get();
		return null;
	}
}
