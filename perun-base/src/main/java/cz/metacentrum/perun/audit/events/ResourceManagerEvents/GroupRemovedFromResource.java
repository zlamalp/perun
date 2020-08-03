package cz.metacentrum.perun.audit.events.ResourceManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.ResourceEvent;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Resource;

public class GroupRemovedFromResource extends AuditEvent implements GroupEvent, ResourceEvent {

	private Group group;
	private Resource resource;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public GroupRemovedFromResource() {
	}

	public GroupRemovedFromResource(Group group, Resource resource) {
		this.group = group;
		this.resource = resource;
		this.message = formatMessage("%s removed from %s", group, resource);
	}

	@Override
	public String getMessage() {
		return message;
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
	public String toString() {
		return message;
	}
}
