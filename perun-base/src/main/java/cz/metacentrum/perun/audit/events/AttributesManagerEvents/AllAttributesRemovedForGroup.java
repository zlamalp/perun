package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.core.api.Group;

import java.util.List;

public class AllAttributesRemovedForGroup extends AuditEvent implements EngineIgnoreEvent, GroupEvent {

	private Group group;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AllAttributesRemovedForGroup() {
	}

	public AllAttributesRemovedForGroup(Group group) {
		this.group = group;
		this.message = formatMessage("All attributes removed for %s.", group);
	}

	@Override
	public Group getGroup() {
		return group;
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
