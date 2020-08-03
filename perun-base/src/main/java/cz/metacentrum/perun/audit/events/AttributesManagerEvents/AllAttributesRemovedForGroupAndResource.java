package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.ResourceEvent;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Resource;

public class AllAttributesRemovedForGroupAndResource extends AuditEvent implements EngineIgnoreEvent, GroupEvent, ResourceEvent {

	private Group group;
	private Resource resource;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AllAttributesRemovedForGroupAndResource() {
	}

	public AllAttributesRemovedForGroupAndResource(Group group, Resource resource) {
		this.group = group;
		this.resource = resource;
		this.message = formatMessage("All attributes removed for %s and %s.", group, resource);
	}

	@Override
	public Group getGroup() {
		return group;
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
