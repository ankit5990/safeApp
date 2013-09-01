package com.mcafee.scor.safety.service;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.vo.RatedPath;

public interface GoogleMapService {

	RatedPath getRatedPath(Coordinates start, Coordinates end,
			TimeOfDay timeOfDay, Transport transport);

}
