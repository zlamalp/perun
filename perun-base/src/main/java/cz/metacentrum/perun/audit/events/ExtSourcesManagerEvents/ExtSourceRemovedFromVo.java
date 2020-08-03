package cz.metacentrum.perun.audit.events.ExtSourcesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.ExtSourceEvent;
import cz.metacentrum.perun.audit.events.VoEvent;
import cz.metacentrum.perun.core.api.ExtSource;
import cz.metacentrum.perun.core.api.Vo;

public class ExtSourceRemovedFromVo extends AuditEvent implements EngineIgnoreEvent, ExtSourceEvent, VoEvent {

	private ExtSource source;
	private Vo vo;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public ExtSourceRemovedFromVo() {
	}

	public ExtSourceRemovedFromVo(ExtSource source, Vo vo) {
		this.source = source;
		this.vo = vo;
		this.message = formatMessage("%s removed from %s.", source, vo);
	}

	@Override
	public ExtSource getExtSource() {
		return source;
	}

	@Override
	public Vo getVo() {
		return vo;
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
