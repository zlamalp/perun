package cz.metacentrum.perun.audit.events.ResourceManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.ResourceEvent;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Resource;

/**
 * Event for setting the ResourceSelfService role for group.
 *
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class ResourceSelfServiceAddedForGroup extends AuditEvent implements EngineIgnoreEvent, GroupEvent, ResourceEvent {

	private Group group;
	private Resource resource;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public ResourceSelfServiceAddedForGroup() {
	}

	public ResourceSelfServiceAddedForGroup(Resource resource, Group group) {
		this.group = group;
		this.resource = resource;
		this.message = formatMessage("%s was added as ResourceSelfService for %s.", group, resource);
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
