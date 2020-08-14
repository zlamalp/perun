package cz.metacentrum.perun.audit.events.VoManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.VoEvent;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Vo;

public class AdminGroupRemovedForVo extends AuditEvent implements EngineIgnoreEvent, GroupEvent, VoEvent {

	private Vo vo;
	private Group group;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AdminGroupRemovedForVo() {
	}

	public AdminGroupRemovedForVo(Group group, Vo vo) {
		this.vo = vo;
		this.group = group;
		this.message = formatMessage("%s was removed from admins of %s.", group, vo);
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
	public Vo getVo() {
		return vo;
	}

	@Override
	public String toString() {
		return message;
	}
}
