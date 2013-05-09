package com.founder.sipbus.syweb.license.resource;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.licesemgmt.ILicenseManagement;
import com.founder.sipbus.licesemgmt.LicenseManagementFactory;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/license/serialnumber")
public class SerialNumberResource extends SyBaseResource {

	@Override
	protected void doInit() throws ResourceException {
	}

	@Get
	public Representation get(Representation entity) throws Exception {
		ILicenseManagement licenseManagement=LicenseManagementFactory.getLicenseManagement();
		String serialNumber=licenseManagement.generateSerialNumber("");
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(serialNumber));
	}

}
