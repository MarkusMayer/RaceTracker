package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.RaceTrackerConsoleRunner;
import raceTracker.model.enums.Flag;
import raceTracker.model.enums.SessionType;
import raceTracker.model.enums.Weather;

public class WeatherForecastSample {

//uint8     m_sessionType;                     // 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P, 5 = Q1
//    // 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ, 10 = R, 11 = R2
//    // 12 = Time Trial
//uint8     m_timeOffset;                      // Time in minutes the forecast is for
//uint8     m_weather;                         // Weather - 0 = clear, 1 = light cloud, 2 = overcast
//    // 3 = light rain, 4 = heavy rain, 5 = storm
//int8      m_trackTemperature;                // Track temp. in degrees celsius
//int8      m_airTemperature;                  // Air temp. in degrees celsius

	public static int WEATHER_FORECAST_SAMPLES_TOTAL_SIZE=100;
	public static int WEATHER_FORECAST_SAMPLE_SIZE=5;
	
	private final SessionType sessionType;
	private final Weather weather;
	private final int timeOffset,trackTemperature,airTemperature;
	
	
	public static List<WeatherForecastSample> getWeatherForecastSamples(int numWeatherForecastSamples, byte[] weatherForecastSamplesData) throws IOException{
//		System.out.println("weatherForecastSample total Data: "+F1UdpReceiver.bytesToHex(weatherForecastSamplesData));
		List<WeatherForecastSample> zones=new ArrayList<>();
		ByteBuffer bb = ByteBuffer.wrap(weatherForecastSamplesData);
		for (int i=0;i<Math.min(numWeatherForecastSamples, 21);i++) {
			byte[] weatherForecastSampleData=new byte[WEATHER_FORECAST_SAMPLE_SIZE];
			bb.get(weatherForecastSampleData,0,weatherForecastSampleData.length);
			WeatherForecastSample mz=new WeatherForecastSample(weatherForecastSampleData);
			zones.add(mz);
		}
		return zones;
	}
	
	private WeatherForecastSample(byte[] weatherForecastSampleData) throws IOException {
//		System.out.println("weatherForecastSampleData: "+F1UdpReceiver.bytesToHex(weatherForecastSampleData));
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(weatherForecastSampleData, 0, weatherForecastSampleData.length));
		sessionType=SessionType.getTypeByKey(leDis.readUnsignedByte());
		timeOffset=leDis.readUnsignedByte();
		weather=Weather.getTypeByKey(leDis.readUnsignedByte());
		trackTemperature=leDis.readByte();
		airTemperature=leDis.readByte();
	}

	public static int getWEATHER_FORECAST_SAMPLES_TOTAL_SIZE() {
		return WEATHER_FORECAST_SAMPLES_TOTAL_SIZE;
	}

	public static int getWEATHER_FORECAST_SAMPLE_SIZE() {
		return WEATHER_FORECAST_SAMPLE_SIZE;
	}

	public SessionType getSessionType() {
		return sessionType;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getTimeOffset() {
		return timeOffset;
	}

	public int getTrackTemperature() {
		return trackTemperature;
	}

	public int getAirTemperature() {
		return airTemperature;
	}

	@Override
	public String toString() {
		return "WeatherForecastSample [sessionType=" + sessionType + ", weather=" + weather + ", timeOffset="
				+ timeOffset + ", trackTemperature=" + trackTemperature + ", airTemperature=" + airTemperature + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + airTemperature;
		result = prime * result + ((sessionType == null) ? 0 : sessionType.hashCode());
		result = prime * result + timeOffset;
		result = prime * result + trackTemperature;
		result = prime * result + ((weather == null) ? 0 : weather.hashCode());
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
		WeatherForecastSample other = (WeatherForecastSample) obj;
		if (airTemperature != other.airTemperature)
			return false;
		if (sessionType != other.sessionType)
			return false;
		if (timeOffset != other.timeOffset)
			return false;
		if (trackTemperature != other.trackTemperature)
			return false;
		if (weather != other.weather)
			return false;
		return true;
	}
	
}
