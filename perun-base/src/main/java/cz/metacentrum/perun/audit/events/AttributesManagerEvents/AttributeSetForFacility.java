package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.Facility;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeSetForFacility extends AuditEvent implements FacilityEvent, AttributeEvent {

	private Attribute attribute;
	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeSetForFacility() {
	}

	public AttributeSetForFacility(Attribute attribute, Facility facility) {
		this.attribute = attribute;
		this.facility = facility;
		this.message = formatMessage("%s set for %s.", attribute, facility);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public Facility getFacility() {
		return facility;
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
