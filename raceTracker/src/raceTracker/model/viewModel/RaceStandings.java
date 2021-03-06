package raceTracker.model.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import raceTracker.model.enums.Driver;
import raceTracker.model.enums.ResultStatus;
import raceTracker.model.gameStructs.LapStruct;
import raceTracker.model.gameStructs.Participant;

public final class RaceStandings {
	private final List<RacePosition> pos = new ArrayList<>();

//TODO: Add times and delta times for session overall best lap / s1 / s2 / s3
	
	public RaceStandings() {
	}

	public RaceStandings(List<LapStruct> lapData, List<Participant> participants, RaceStandings curStandings) {
		Objects.requireNonNull(curStandings);
		if (lapData.size() == participants.size()) {
			for (int i = 0; i < lapData.size(); i++) {
				LapStruct aLap = lapData.get(i);
				Participant aParticipant = participants.get(i);
				if (aLap.getResultStatus() != ResultStatus.finished) {
					pos.add(new RacePosition(aLap, aParticipant));
				} else {
					RacePosition curPos = curStandings.getPositionForDriver(aParticipant.getDriverId());
					if (curPos.getResultStatus() == ResultStatus.finished) {
						pos.add(curPos);
					} else
						pos.add(curPos.finishRace(aLap));
				}
			}

			pos.sort((p1, p2) -> Integer.compare(p1.getCurPosition(), p2.getCurPosition()));

		} else {
			throw new IllegalArgumentException("lapData.size: " + lapData.size() + " != participants.size: "
					+ participants.size() + "Data: " + lapData + " -> " + participants);
		}
	}

	public List<RacePosition> getPos() {
		return Collections.unmodifiableList(pos);
	}

	@Override
	public String toString() {
		return "RaceStandings [pos=" + pos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RaceStandings other = (RaceStandings) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		return true;
	}

	public RacePosition getPositionForDriver(Driver driverId) {
		Objects.requireNonNull(driverId);
		for (RacePosition racePosition : pos) {
			if (racePosition.getDriverId() == driverId) {
				return racePosition;
			}
		}
		throw new IllegalArgumentException("DriverId not found: " + driverId);
	}

}
