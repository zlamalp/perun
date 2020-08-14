package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Facility;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Facility object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface FacilityEvent {

	Facility getFacility();

}
