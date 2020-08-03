package cz.metacentrum.perun.audit.events.VoManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.audit.events.VoEvent;
import cz.metacentrum.perun.core.api.User;
import cz.metacentrum.perun.core.api.Vo;

public class AdminAddedForVo extends AuditEvent implements EngineIgnoreEvent, VoEvent, UserEvent {

	private User user;
	private Vo vo;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AdminAddedForVo() {
	}

	public AdminAddedForVo(User user, Vo vo) {
		this.user = user;
		this.vo = vo;
		this.message = formatMessage("%s was added as admin of %s.", user, vo);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public User getUser() {
		return user;
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
