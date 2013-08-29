package com.mcafee.scor.safety.service;

import java.util.List;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;

public interface GoogleMapService {

	List<RatedCoordinate> getRatedPath(Coordinates start, Coordinates end,
			TimeOfDay timeOfDay, Transport transport);

}
