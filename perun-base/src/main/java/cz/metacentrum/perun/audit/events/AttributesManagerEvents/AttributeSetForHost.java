package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.HostEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.Host;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeSetForHost extends AuditEvent implements HostEvent, AttributeEvent {

	private Attribute attribute;
	private Host host;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeSetForHost() {
	}

	public AttributeSetForHost(Attribute attribute, Host host) {
		this.attribute = attribute;
		this.host = host;
		this.message = formatMessage("%s set for %s.", attribute, host);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public Host getHost() {
		return host;
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
