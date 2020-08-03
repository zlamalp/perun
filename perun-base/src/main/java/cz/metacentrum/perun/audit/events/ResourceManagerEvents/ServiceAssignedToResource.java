package cz.metacentrum.perun.audit.events.ResourceManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.ResourceEvent;
import cz.metacentrum.perun.audit.events.ServiceEvent;
import cz.metacentrum.perun.core.api.Resource;
import cz.metacentrum.perun.core.api.Service;

public class ServiceAssignedToResource extends AuditEvent implements ServiceEvent, ResourceEvent {

	private Service service;
	private Resource resource;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public ServiceAssignedToResource() {
	}

	public ServiceAssignedToResource(Service service, Resource resource) {
		this.service = service;
		this.resource = resource;
		this.message = formatMessage("%s asigned to %s", service, resource);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Service getService() {
		return service;
	}

	@Override
	public Resource getResource() {
		return resource;
	}

	@Override
	public String toString() {
		return message;
	}
}
