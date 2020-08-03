package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.ResourceEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.Resource;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeSetForResource extends AuditEvent implements AttributeEvent, ResourceEvent {

	private Attribute attribute;
	private Resource resource;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeSetForResource() {
	}

	public AttributeSetForResource(Attribute attribute, Resource resource) {
		this.attribute = attribute;
		this.resource = resource;
		this.message = formatMessage("%s set for %s.", attribute, resource);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public Resource getResource() {
		return resource;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}
}
