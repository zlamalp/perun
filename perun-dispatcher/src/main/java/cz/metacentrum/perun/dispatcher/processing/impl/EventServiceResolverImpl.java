package cz.metacentrum.perun.dispatcher.processing.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cz.metacentrum.perun.audit.events.AttributeDefinitionEvent;
import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.HostEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.audit.events.ResourceEvent;
import cz.metacentrum.perun.audit.events.ServiceEvent;
import cz.metacentrum.perun.audit.events.ServicesEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.core.api.PerunClient;

import cz.metacentrum.perun.core.bl.PerunBl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cz.metacentrum.perun.core.api.AttributeDefinition;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Host;
import cz.metacentrum.perun.core.api.Member;
import cz.metacentrum.perun.core.api.PerunPrincipal;
import cz.metacentrum.perun.core.api.PerunSession;
import cz.metacentrum.perun.core.api.Resource;
import cz.metacentrum.perun.core.api.Service;
import cz.metacentrum.perun.core.api.User;
import cz.metacentrum.perun.core.api.exceptions.PrivilegeException;
import cz.metacentrum.perun.core.api.exceptions.ServiceNotExistsException;
import cz.metacentrum.perun.dispatcher.processing.EventServiceResolver;

/**
 * Implementation of EventServiceResolver.
 *
 * @see cz.metacentrum.perun.dispatcher.processing.EventServiceResolver
 *
 * @author Michal Karm Babacek
 * @author Michal Voců
 * @author David Šarman
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
@org.springframework.stereotype.Service(value = "eventServiceResolver")
public class EventServiceResolverImpl implements EventServiceResolver {

	private static final Logger log = LoggerFactory.getLogger(EventServiceResolverImpl.class);

	private Properties dispatcherProperties;
	private PerunBl perunBl;

	private PerunSession perunSession = null;

	// ----- setters -------------------------------------

	public Properties getDispatcherProperties() {
		return dispatcherProperties;
	}

	@javax.annotation.Resource(name="dispatcherPropertiesBean")
	public void setDispatcherProperties(Properties dispatcherProperties) {
		this.dispatcherProperties = dispatcherProperties;
	}

	public PerunBl getPerun() {
		return perunBl;
	}

	@Autowired
	public void setPerun(PerunBl perun) {
		this.perunBl = perun;
	}

	// ----- methods -------------------------------------

	@Override
	public Map<Facility, Set<Service>> resolveEvent(AuditEvent event) throws ServiceNotExistsException, PrivilegeException {

		log.info("Event - I am going to process event: {}", event);

		Map<Facility, Set<Service>> result = new HashMap<Facility, Set<Service>>();

		if (event instanceof EngineIgnoreEvent) {
			log.info("Event ignored {} facilities will be returned", result.size());
			return result;
		}

		// Prepare variables
		AttributeDefinition attributeDefinition = null;
		Facility facility = null;
		Resource resource = null;
		Group group = null;
		User user = null;
		Member member = null;
		Service service = null;
		Host host = null;

		// fill variables if possible
		if (event instanceof AttributeDefinitionEvent) {
			attributeDefinition = ((AttributeDefinitionEvent) event).getAttributeDefinition();
		}
		if (event instanceof AttributeEvent) {
			attributeDefinition = ((AttributeEvent) event).getAttribute();
		}
		if (event instanceof FacilityEvent) {
			facility = ((FacilityEvent) event).getFacility();
		}
		if (event instanceof ResourceEvent) {
			resource = ((ResourceEvent) event).getResource();
		}
		if (event instanceof GroupEvent) {
			group = ((GroupEvent) event).getGroup();
		}
		if (event instanceof UserEvent) {
			user = ((UserEvent) event).getUser();
		}
		if (event instanceof MemberEvent) {
			member = ((MemberEvent) event).getMember();
		}
		if (event instanceof ServiceEvent) {
			service = ((ServiceEvent) event).getService();
		}
		if (event instanceof HostEvent) {
			host = ((HostEvent) event).getHost();
		}

		System.out.println("---------");

		List<Resource> resourcesResolvedFromEvent = new ArrayList<Resource>();
		List<Service> servicesResolvedFromEvent = new ArrayList<Service>();

		// =============== Resolve facilities from event======================

		if (perunSession == null) {
			perunSession = perunBl.getPerunSession(new PerunPrincipal(
							dispatcherProperties.getProperty("perun.principal.name"),
							dispatcherProperties.getProperty("perun.principal.extSourceName"),
							dispatcherProperties.getProperty("perun.principal.extSourceType")),
					new PerunClient());
		}

		// Try to find FACILITY in event
		if (facility != null) {
			log.debug("Facility found in event. {}.", facility);
			resourcesResolvedFromEvent.addAll(perunBl.getFacilitiesManagerBl().getAssignedResources(perunSession, facility));
		} else {
			// Try to find RESOURCE in event
			if (resource != null) {
				resourcesResolvedFromEvent.add(resource);
			} else {
				// Try to find GROUP in event
				if (group != null) {
					resourcesResolvedFromEvent = perunBl.getResourcesManagerBl().getAssignedResources(perunSession, group);
				} else {
					// try to find USER in event
					if (user != null) {
						resourcesResolvedFromEvent = perunBl.getUsersManagerBl().getAllowedResources(perunSession, user);
					} else {
						// try to find MEMBER in event
						if (member != null) {
							resourcesResolvedFromEvent = perunBl.getResourcesManagerBl().getAllowedResources(perunSession, member);
						} else {
							// try to find HOST in event
							if (host != null) {
								log.debug("Host found in event.id= {}.", host.getId());
								facility = perunBl.getFacilitiesManagerBl().getFacilityForHost(perunSession, host);
								resourcesResolvedFromEvent.addAll(perunBl.getFacilitiesManagerBl().getAssignedResources(perunSession, facility));
							} else {
								log.warn("No match found for this event. Event={}", event);
							}
						}
					}
				}
			}
		}

		// Try to find SERVICE in event
		if (service != null) {
			servicesResolvedFromEvent.add(service);
		}

		// add other services from event if present
		if (event instanceof ServicesEvent) {
			servicesResolvedFromEvent.addAll(((ServicesEvent) event).getServices());
		}

		// FIXME - Following code is commented since we don't want to start propagation for messages like "ServiceUpdated".
		// Generally it could clog the propagations, when single service is assigned to the many facilities.
		// It also means, that messages to force/planServicePropagation for service (without facility specified) are ignored.
		/*
		if (servicesResolvedFromEvent.size() == 1 &&
				facilitiesResolvedFromEvent.isEmpty() &&
				resourcesResolvedFromEvent.isEmpty()) {
			// there was no proper sourcing object other than the service
			// so we will append all facilities with such service
			facilitiesResolvedFromEvent.addAll(perun.getFacilitiesManager().getAssignedFacilities(perunSession, service));
			for (Facility fac : facilitiesResolvedFromEvent) {
				try {
					resourcesResolvedFromEvent.addAll(perun.getFacilitiesManager()
							.getAssignedResources(perunSession, fac));
				} catch (FacilityNotExistsException e) {
					log.error("Facility {} was probably deleted, can't get resources for it.", fac, e);
				}
			}
		}
		*/

		for (Resource r : resourcesResolvedFromEvent) {

			Facility facilityResolvedFromEvent;
			List<Service> servicesResolvedFromResource;

			facilityResolvedFromEvent = perunBl.getResourcesManagerBl().getFacility(perunSession, r);
			servicesResolvedFromResource = perunBl.getResourcesManagerBl().getAssignedServices(perunSession, r);
			// process only services resolved from event if any
			if (!servicesResolvedFromEvent.isEmpty()) servicesResolvedFromResource.retainAll(servicesResolvedFromEvent);


			for (Service s : servicesResolvedFromResource) {

				if (attributeDefinition != null) {
					List<AttributeDefinition> serviceRequiredAttributes = perunBl.getAttributesManagerBl().getRequiredAttributesDefinition(perunSession, s);
					if (!serviceRequiredAttributes.contains(attributeDefinition)) continue;
				}

				if(!result.containsKey(facilityResolvedFromEvent)) {
					Set<Service> servicesToPut = new HashSet<Service>();
					servicesToPut.add(s);
					result.put(facilityResolvedFromEvent, servicesToPut);
				} else {
					result.get(facilityResolvedFromEvent).add(s);
				}
			}
		}

		log.info("{} facilities will be returned", result.size());
		return result;

	}

}
