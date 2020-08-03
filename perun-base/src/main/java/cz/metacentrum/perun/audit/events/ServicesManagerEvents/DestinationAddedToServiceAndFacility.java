package cz.metacentrum.perun.audit.events.ServicesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.DestinationEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.ServiceEvent;
import cz.metacentrum.perun.core.api.Destination;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.Service;

public class DestinationAddedToServiceAndFacility extends AuditEvent implements FacilityEvent, ServiceEvent, DestinationEvent {

	private Destination destination;
	private Service service;
	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public DestinationAddedToServiceAndFacility() {
	}

	public DestinationAddedToServiceAndFacility(Destination destination, Service service, Facility facility) {
		this.destination = destination;
		this.facility = facility;
		this.service = service;
		this.message = formatMessage("%s added to %s and %s.", destination, service, facility);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Destination getDestination() {
		return destination;
	}

	@Override
	public Service getService() {
		return service;
	}

	@Override
	public Facility getFacility() {
		return facility;
	}

	@Override
	public String toString() {
		return message;
	}
}
