package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.RaceTrackerConsoleRunner;
import raceTracker.model.enums.Formula;
import raceTracker.model.enums.NetworkGame;
import raceTracker.model.enums.SafetyCarStatus;
import raceTracker.model.enums.SessionType;
import raceTracker.model.enums.Track;
import raceTracker.model.enums.Weather;

public class Session {
	
//uint8           m_weather;                   // Weather - 0 = clear, 1 = light cloud, 2 = overcast
//    // 3 = light rain, 4 = heavy rain, 5 = storm
//int8	    m_trackTemperature;          // Track temp. in degrees celsius
//int8	    m_airTemperature;            // Air temp. in degrees celsius
//uint8           m_totalLaps;                 // Total number of laps in this race
//uint16          m_trackLength;               // Track length in metres
//uint8           m_sessionType;               // 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P
//    // 5 = Q1, 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ
//    // 10 = R, 11 = R2, 12 = Time Trial
//int8            m_trackId;                   // -1 for unknown, 0-21 for tracks, see appendix
//uint8           m_formula;                   // Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2,
//    // 3 = F1 Generic
//uint16          m_sessionTimeLeft;           // Time left in session in seconds
//uint16          m_sessionDuration;           // Session duration in seconds
//uint8           m_pitSpeedLimit;             // Pit speed limit in kilometres per hour
//uint8           m_gamePaused;                // Whether the game is paused
//uint8           m_isSpectating;              // Whether the player is spectating
//uint8           m_spectatorCarIndex;         // Index of the car being spectated
//uint8           m_sliProNativeSupport;	 // SLI Pro support, 0 = inactive, 1 = active
//uint8           m_numMarshalZones;           // Number of marshal zones to follow
//MarshalZone     m_marshalZones[21];          // List of marshal zones – max 21
//uint8           m_safetyCarStatus;           // 0 = no safety car, 1 = full safety car
//    // 2 = virtual safety car
//uint8           m_networkGame;               // 0 = offline, 1 = online
//uint8           m_numWeatherForecastSamples; // Number of weather samples to follow
//WeatherForecastSample m_weatherForecastSamples[20];   // Array of weather forecast samples

	private final int trackTemperature,airTemperature,totalLaps,trackLength,sessionTimeLeft,sessionDuration,pitSpeedLimit,
	gamePaused,isSpectating,spectatorCarIndex,sliProNativeSupport;
	
	private final Weather weather;
	private final SessionType sessionType;
	private final Track trackId;
	private final Formula formula;
	private final List<MarshallZone> zones;
	
	// TODO not sent in time trial or never??
	private int numMarshalZones,numWeatherForecastSamples;
	private SafetyCarStatus safetyCarStatus;
	private NetworkGame networkGame;
	private List<WeatherForecastSample> weatherForecastSamples;
	
	public Session(byte[] data) throws IOException {
//		System.out.println("Session data: "+F1UdpReceiver.bytesToHex(data));		
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(data, 0, data.length));
		weather=Weather.getTypeByKey(leDis.readUnsignedByte()); //25
		trackTemperature=leDis.readByte(); //26
		airTemperature=leDis.readByte(); //27
		totalLaps=leDis.readUnsignedByte(); //28
		trackLength=leDis.readUnsignedShort(); //30
		sessionType=SessionType.getTypeByKey(leDis.readUnsignedByte()); //31
		int foo=leDis.readByte(); //32
		trackId=Track.getTypeByKey(foo); //33
		formula=Formula.getTypeByKey(leDis.readUnsignedByte()); //34
		sessionTimeLeft=leDis.readUnsignedShort(); //36
		sessionDuration=leDis.readUnsignedShort(); //38
		
		pitSpeedLimit=leDis.readUnsignedByte(); //39
		gamePaused=leDis.readUnsignedByte(); //40
		isSpectating=leDis.readUnsignedByte(); //41
		spectatorCarIndex=leDis.readUnsignedByte(); //42
		sliProNativeSupport=leDis.readUnsignedByte(); //43
		numMarshalZones=leDis.readUnsignedByte(); //44 ==> 19
		
		byte[] zoneData=new byte[MarshallZone.MARHSHALL_ZONES_TOTAL_SIZE];
		ByteBuffer bbZoneData = ByteBuffer.wrap(data,19,zoneData.length);
		bbZoneData.get(zoneData,0,zoneData.length);

		zones=MarshallZone.getMarshallZones(numMarshalZones,zoneData); // ==> 124
		
		// TODO not sent in time trial or never??		
		// safetyCarStatus=SafetyCarStatus.getTypeByKey(leDis.readUnsignedByte());
		//networkGame=NetworkGame.getTypeByKey(leDis.readUnsignedByte());

//		numWeatherForecastSamples=leDis.readUnsignedByte(); // ==> 127
//		
//		byte[] weatherForecastSamplesData=new byte[WeatherForecastSample.WEATHER_FORECAST_SAMPLES_TOTAL_SIZE];
//		ByteBuffer bbWeatherForecastSamplesData = ByteBuffer.wrap(data,127,weatherForecastSamplesData.length);
//		bbWeatherForecastSamplesData.get(weatherForecastSamplesData,0,weatherForecastSamplesData.length);
//
//		weatherForecastSamples=WeatherForecastSample.getWeatherForecastSamples(numWeatherForecastSamples, weatherForecastSamplesData);
	}

	public Weather getWeather() {
		return weather;
	}

	public int getTrackTemperature() {
		return trackTemperature;
	}

	public int getAirTemperature() {
		return airTemperature;
	}

	public int getTotalLaps() {
		return totalLaps;
	}

	public int getTrackLength() {
		return trackLength;
	}

	public SessionType getSessionType() {
		return sessionType;
	}

	public Track getTrackId() {
		return trackId;
	}

	public Formula getFormula() {
		return formula;
	}

	public int getSessionTimeLeft() {
		return sessionTimeLeft;
	}

	public int getSessionDuration() {
		return sessionDuration;
	}

	public int getPitSpeedLimit() {
		return pitSpeedLimit;
	}

	public int getGamePaused() {
		return gamePaused;
	}

	public int getIsSpectating() {
		return isSpectating;
	}

	public int getSpectatorCarIndex() {
		return spectatorCarIndex;
	}

	public int getSliProNativeSupport() {
		return sliProNativeSupport;
	}

	public int getNumMarshalZones() {
		return numMarshalZones;
	}
	
	

	public List<MarshallZone> getZones() {
		return zones;
	}

	
	
	public int getNumWeatherForecastSamples() {
		return numWeatherForecastSamples;
	}

	public SafetyCarStatus getSafetyCarStatus() {
		return safetyCarStatus;
	}

	public NetworkGame getNetworkGame() {
		return networkGame;
	}

	public List<WeatherForecastSample> getWeatherForecastSamples() {
		return weatherForecastSamples;
	}

	@Override
	public String toString() {
		return "Session [trackTemperature=" + trackTemperature + ", airTemperature=" + airTemperature + ", totalLaps="
				+ totalLaps + ", trackLength=" + trackLength + ", sessionTimeLeft=" + sessionTimeLeft
				+ ", sessionDuration=" + sessionDuration + ", pitSpeedLimit=" + pitSpeedLimit + ", gamePaused="
				+ gamePaused + ", isSpectating=" + isSpectating + ", spectatorCarIndex=" + spectatorCarIndex
				+ ", sliProNativeSupport=" + sliProNativeSupport + ", numMarshalZones=" + numMarshalZones
				+ ", numWeatherForecastSamples=" + numWeatherForecastSamples + ", weather=" + weather + ", sessionType="
				+ sessionType + ", trackId=" + trackId + ", formula=" + formula + ", zones=" + zones
				+ ", safetyCarStatus=" + safetyCarStatus + ", networkGame=" + networkGame + ", weatherForecastSamples="
				+ weatherForecastSamples + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + airTemperature;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + gamePaused;
		result = prime * result + isSpectating;
		result = prime * result + ((networkGame == null) ? 0 : networkGame.hashCode());
		result = prime * result + numMarshalZones;
		result = prime * result + numWeatherForecastSamples;
		result = prime * result + pitSpeedLimit;
		result = prime * result + ((safetyCarStatus == null) ? 0 : safetyCarStatus.hashCode());
		result = prime * result + sessionDuration;
		result = prime * result + sessionTimeLeft;
		result = prime * result + ((sessionType == null) ? 0 : sessionType.hashCode());
		result = prime * result + sliProNativeSupport;
		result = prime * result + spectatorCarIndex;
		result = prime * result + totalLaps;
		result = prime * result + ((trackId == null) ? 0 : trackId.hashCode());
		result = prime * result + trackLength;
		result = prime * result + trackTemperature;
		result = prime * result + ((weather == null) ? 0 : weather.hashCode());
		result = prime * result + ((weatherForecastSamples == null) ? 0 : weatherForecastSamples.hashCode());
		result = prime * result + ((zones == null) ? 0 : zones.hashCode());
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
		Session other = (Session) obj;
		if (airTemperature != other.airTemperature)
			return false;
		if (formula != other.formula)
			return false;
		if (gamePaused != other.gamePaused)
			return false;
		if (isSpectating != other.isSpectating)
			return false;
		if (networkGame != other.networkGame)
			return false;
		if (numMarshalZones != other.numMarshalZones)
			return false;
		if (numWeatherForecastSamples != other.numWeatherForecastSamples)
			return false;
		if (pitSpeedLimit != other.pitSpeedLimit)
			return false;
		if (safetyCarStatus != other.safetyCarStatus)
			return false;
		if (sessionDuration != other.sessionDuration)
			return false;
		if (sessionTimeLeft != other.sessionTimeLeft)
			return false;
		if (sessionType != other.sessionType)
			return false;
		if (sliProNativeSupport != other.sliProNativeSupport)
			return false;
		if (spectatorCarIndex != other.spectatorCarIndex)
			return false;
		if (totalLaps != other.totalLaps)
			return false;
		if (trackId != other.trackId)
			return false;
		if (trackLength != other.trackLength)
			return false;
		if (trackTemperature != other.trackTemperature)
			return false;
		if (weather != other.weather)
			return false;
		if (weatherForecastSamples == null) {
			if (other.weatherForecastSamples != null)
				return false;
		} else if (!weatherForecastSamples.equals(other.weatherForecastSamples))
			return false;
		if (zones == null) {
			if (other.zones != null)
				return false;
		} else if (!zones.equals(other.zones))
			return false;
		return true;
	}

}
