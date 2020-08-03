package cz.metacentrum.perun.audit.events.ExtSourcesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.ExtSourceEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.core.api.ExtSource;
import cz.metacentrum.perun.core.api.Group;

public class ExtSourceAddedToGroup extends AuditEvent implements EngineIgnoreEvent, ExtSourceEvent, GroupEvent {

	private ExtSource source;
	private Group group;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public ExtSourceAddedToGroup() {
	}

	public ExtSourceAddedToGroup(ExtSource source, Group group) {
		this.source = source;
		this.group = group;
		this.message = formatMessage("%s added to %s.", source, group);
	}

	@Override
	public ExtSource getExtSource() {
		return source;
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
