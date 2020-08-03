package cz.metacentrum.perun.audit.events.ServicesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeDefinitionEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.ServiceEvent;
import cz.metacentrum.perun.core.api.AttributeDefinition;
import cz.metacentrum.perun.core.api.Service;

public class AttributeAddedAsRequiredToService extends AuditEvent implements ServiceEvent, AttributeDefinitionEvent {

	private AttributeDefinition attribute;
	private Service service;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeAddedAsRequiredToService() {
	}

	public AttributeAddedAsRequiredToService(AttributeDefinition attribute, Service service) {
		this.attribute = attribute;
		this.service = service;
		this.message = formatMessage("%s added to %s as required attribute.", attribute, service);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public AttributeDefinition getAttributeDefinition() {
		return attribute;
	}

	@Override
	public Service getService() {
		return service;
	}

	@Override
	public String toString() {
		return message;
	}
}
