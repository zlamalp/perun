package cz.metacentrum.perun.audit.events.GroupManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.core.api.Group;

public class GroupSyncFinishedWithErrors extends AuditEvent implements EngineIgnoreEvent, GroupEvent {

	private Group group;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public GroupSyncFinishedWithErrors() {
	}

	public GroupSyncFinishedWithErrors(Group group) {
		this.group = group;
		this.message = formatMessage("%s synchronization finished with errors.", group);
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
	public String toString() {
		return message;
	}
}
