package cz.metacentrum.perun.audit.events.ExtSourcesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.ExtSourceEvent;
import cz.metacentrum.perun.core.api.ExtSource;

public class ExtSourceCreated extends AuditEvent implements EngineIgnoreEvent, ExtSourceEvent {

	private ExtSource extSource;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public ExtSourceCreated() {
	}

	public ExtSourceCreated(ExtSource extSource) {
		this.extSource = extSource;
		this.message = formatMessage("%s created.", extSource);
	}

	@Override
	public ExtSource getExtSource() {
		return extSource;
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
