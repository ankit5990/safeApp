package com.mcafee.scor.safety.vo;

import java.util.List;

import com.mcafee.scor.safety.service.RatedCoordinate;

public class RatedPath {
	private List<RatedCoordinate> wayPoints;
	private List<RatedCoordinate> completeRatedPath;
	public List<RatedCoordinate> getWayPoints() {
		return wayPoints;
	}
	public void setWayPoints(List<RatedCoordinate> wayPoints) {
		this.wayPoints = wayPoints;
	}
	public List<RatedCoordinate> getCompleteRatedPath() {
		return completeRatedPath;
	}
	public void setCompleteRatedPath(List<RatedCoordinate> completeRatedPath) {
		this.completeRatedPath = completeRatedPath;
	}
	
	
}
