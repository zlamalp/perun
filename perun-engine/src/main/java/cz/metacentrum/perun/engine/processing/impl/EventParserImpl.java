package cz.metacentrum.perun.engine.processing.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.metacentrum.perun.core.api.Destination;
import cz.metacentrum.perun.core.api.PerunBean;
import cz.metacentrum.perun.engine.exceptions.InvalidEventMessageException;
import cz.metacentrum.perun.engine.processing.EventParser;
import cz.metacentrum.perun.rpclib.impl.JsonDeserializer;
import cz.metacentrum.perun.taskslib.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Service(value = "eventParser")
public class EventParserImpl implements EventParser {

	private static final Logger log = LoggerFactory.getLogger(EventParserImpl.class);

	private static final Map<Class<?>,Class<?>> mixinMap = new HashMap<>();
	private static final ObjectMapper mapper = new ObjectMapper();

	@JsonIgnoreProperties({"beanName"})
	private interface TaskMixIn {

		@JsonIgnore
		void setSchedule(LocalDateTime schedule);

		@JsonDeserialize
		@JsonSetter("scheduleAsLong")
		void setSchedule(Long scheduleAsLong);

		@JsonIgnore
		void setSentToEngine(LocalDateTime sentToEngine);

		@JsonDeserialize
		@JsonSetter("sentToEngineAsLong")
		void setSentToEngine(Long sentToEngineAsLong);

		@JsonIgnore
		void setStartTime(LocalDateTime startTime);

		@JsonDeserialize
		@JsonSetter("startTimeAsLong")
		void setStartTime(Long startTimeAsLong);

		@JsonIgnore
		void setEndTime(LocalDateTime endTime);

		@JsonDeserialize
		@JsonSetter("endTimeAsLong")
		void setEndTime(Long endTimeAsLong);

		@JsonIgnore
		void setGenStartTime(LocalDateTime genStartTime);

		@JsonDeserialize
		@JsonSetter("genStartTimeAsLong")
		void setGenStartTime(Long genStartTimeAsLong);

		@JsonIgnore
		void setGenEndTime(LocalDateTime genEndTime);

		@JsonDeserialize
		@JsonSetter("genEndTimeAsLong")
		void setGenEndTime(Long genEndTimeAsLong);

		@JsonIgnore
		void setSendStartTime(LocalDateTime sendStartTime);

		@JsonDeserialize
		@JsonSetter("sendStartTimeAsLong")
		void setSendStartTime(Long sendStartTimeAsLong);

		@JsonIgnore
		void setSendEndTime(LocalDateTime sendEndTime);

		@JsonDeserialize
		@JsonSetter("sendEndTimeAsLong")
		void setSendEndTime(Long sendEndTimeAsLong);

		@JsonIgnore
		LocalDateTime getSchedule();

		@JsonIgnore
		LocalDateTime getSentToEngine();

		@JsonIgnore
		LocalDateTime getEndTime();

		@JsonIgnore
		LocalDateTime getStartTime();

		@JsonIgnore
		LocalDateTime getGenStartTime();

		@JsonIgnore
		LocalDateTime getGenEndTime();

		@JsonIgnore
		LocalDateTime getSendStartTime();

		@JsonIgnore
		LocalDateTime getSendEndTime();

	}

	static {

		// configure JSON deserializer for dispatcher messages
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enableDefaultTyping();

		mixinMap.put(PerunBean.class, JsonDeserializer.PerunBeanMixIn.class);
		mixinMap.put(Destination.class, JsonDeserializer.DestinationMixIn.class);
		mixinMap.put(Task.class, TaskMixIn.class);

		mapper.setMixIns(mixinMap);

	}

	@Override
	public Task parseEvent(String event) throws InvalidEventMessageException {

		Task task = null;

		try {
			task = mapper.readValue(event, Task.class);
		} catch (IOException ex) {
			log.error("Can't parse JSON of Task!", ex);
			throw new InvalidEventMessageException("Can't parse JSON of Task!", ex);
		}

		// re-set delay and recurrence to default state from the service
		task.setDelay(task.getService().getDelay());
		task.setRecurrence(task.getService().getRecurrence());

		return task;

	}

}
