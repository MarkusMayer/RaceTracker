package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.model.enums.SurfaceType;

public class CarTelemetryData {
//	struct CarTelemetryData
//	{
//	    uint16    m_speed;                         // Speed of car in kilometres per hour
//	    float     m_throttle;                      // Amount of throttle applied (0.0 to 1.0)
//	    float     m_steer;                         // Steering (-1.0 (full lock left) to 1.0 (full lock right))
//	    float     m_brake;                         // Amount of brake applied (0.0 to 1.0)
//	    uint8     m_clutch;                        // Amount of clutch applied (0 to 100)
//	    int8      m_gear;                          // Gear selected (1-8, N=0, R=-1)
//	    uint16    m_engineRPM;                     // Engine RPM
//	    uint8     m_drs;                           // 0 = off, 1 = on
//	    uint8     m_revLightsPercent;              // Rev lights indicator (percentage)
//	    uint16    m_brakesTemperature[4];          // Brakes temperature (celsius)
//	    uint8     m_tyresSurfaceTemperature[4];    // Tyres surface temperature (celsius)
//	    uint8     m_tyresInnerTemperature[4];      // Tyres inner temperature (celsius)
//	    uint16    m_engineTemperature;             // Engine temperature (celsius)
//	    float     m_tyresPressure[4];              // Tyres pressure (PSI)
//	    uint8     m_surfaceType[4];                // Driving surface, see appendices
//	};

	public static final int CAR_TELEMTRY_DATA_SIZE = 58;
	public static final int CAR_TELEMTRY_DATA_TOTAL_SIZE = CAR_TELEMTRY_DATA_SIZE * 22;

	private final int speed, clutch, gear, engineRPM, drs,revLightsPercent,brakesTemperatureRL,brakesTemperatureRR,brakesTemperatureFL,brakesTemperatureFR,tyresSurfaceTemperatureRL,tyresSurfaceTemperatureRR,tyresSurfaceTemperatureFL,tyresSurfaceTemperatureFR,tyresInnerTemperatureRL,tyresInnerTemperatureRR,tyresInnerTemperatureFL,tyresInnerTemperatureFR,engineTemperature;
	private final float throttle,steer,brake,tyresPressureRL,tyresPressureRR,tyresPressureFL,tyresPressureFR;

	SurfaceType surfaceRL,surfaceRR,surfaceFL,surfaceFR;

	public static List<CarTelemetryData> getCarTelemetries(int numActiveCars,byte[] carTelemetryTotalData) throws IOException {
//		System.out.println("CarTelemetryTotalData: " + F1UdpReceiver.bytesToHex(carTelemetryTotalData));
		List<CarTelemetryData> carTelemetries = new ArrayList<>();
		ByteBuffer bb = ByteBuffer.wrap(carTelemetryTotalData);
		for (int i = 0; i < Math.min(numActiveCars, 22); i++) {
			byte[] carTelemetry = new byte[CAR_TELEMTRY_DATA_SIZE];
			bb.get(carTelemetry, 0, carTelemetry.length);
			CarTelemetryData cT = new CarTelemetryData(carTelemetry);
			carTelemetries.add(cT);
		}
		return carTelemetries;
	}

	public CarTelemetryData(byte[] carTelemetryData) throws IOException {
//		System.out.println("CarTelemetryData: " + F1UdpReceiver.bytesToHex(carTelemetryData));
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(carTelemetryData, 0, carTelemetryData.length));
		speed=leDis.readUnsignedShort();
		throttle=leDis.readFloat();
		steer=leDis.readFloat();
		brake=leDis.readFloat();
		clutch=leDis.readUnsignedByte();
		gear=leDis.readByte();
		engineRPM=leDis.readUnsignedShort();
		drs=leDis.readUnsignedByte();
		revLightsPercent=leDis.readUnsignedByte();
		brakesTemperatureRL=leDis.readUnsignedShort();
		brakesTemperatureRR=leDis.readUnsignedShort();
		brakesTemperatureFL=leDis.readUnsignedShort();
		brakesTemperatureFR=leDis.readUnsignedShort();
		tyresSurfaceTemperatureRL=leDis.readUnsignedByte();
		tyresSurfaceTemperatureRR=leDis.readUnsignedByte();
		tyresSurfaceTemperatureFL=leDis.readUnsignedByte();
		tyresSurfaceTemperatureFR=leDis.readUnsignedByte();
		tyresInnerTemperatureRL=leDis.readUnsignedByte();
		tyresInnerTemperatureRR=leDis.readUnsignedByte();
		tyresInnerTemperatureFL=leDis.readUnsignedByte();
		tyresInnerTemperatureFR=leDis.readUnsignedByte();
		engineTemperature=leDis.readUnsignedShort();
		tyresPressureRL=leDis.readFloat();
		tyresPressureRR=leDis.readFloat();
		tyresPressureFL=leDis.readFloat();
		tyresPressureFR=leDis.readFloat();
		surfaceRL=SurfaceType.getTypeByKey(leDis.readUnsignedByte());
		surfaceRR=SurfaceType.getTypeByKey(leDis.readUnsignedByte());
		surfaceFL=SurfaceType.getTypeByKey(leDis.readUnsignedByte());
		surfaceFR=SurfaceType.getTypeByKey(leDis.readUnsignedByte());
	}

	public static int getCarTelemtryDataTotalSize() {
		return CAR_TELEMTRY_DATA_TOTAL_SIZE;
	}

	public static int getCarTelemtryDataSize() {
		return CAR_TELEMTRY_DATA_SIZE;
	}

	public int getSpeed() {
		return speed;
	}

	public int getClutch() {
		return clutch;
	}

	public int getGear() {
		return gear;
	}

	public int getEngineRPM() {
		return engineRPM;
	}

	public int getDrs() {
		return drs;
	}

	public int getRevLightsPercent() {
		return revLightsPercent;
	}

	public int getBrakesTemperatureRL() {
		return brakesTemperatureRL;
	}

	public int getBrakesTemperatureRR() {
		return brakesTemperatureRR;
	}

	public int getBrakesTemperatureFL() {
		return brakesTemperatureFL;
	}

	public int getBrakesTemperatureFR() {
		return brakesTemperatureFR;
	}

	public int getTyresSurfaceTemperatureRL() {
		return tyresSurfaceTemperatureRL;
	}

	public int getTyresSurfaceTemperatureRR() {
		return tyresSurfaceTemperatureRR;
	}

	public int getTyresSurfaceTemperatureFL() {
		return tyresSurfaceTemperatureFL;
	}

	public int getTyresSurfaceTemperatureFR() {
		return tyresSurfaceTemperatureFR;
	}

	public int getTyresInnerTemperatureRL() {
		return tyresInnerTemperatureRL;
	}

	public int getTyresInnerTemperatureRR() {
		return tyresInnerTemperatureRR;
	}

	public int getTyresInnerTemperatureFL() {
		return tyresInnerTemperatureFL;
	}

	public int getTyresInnerTemperatureFR() {
		return tyresInnerTemperatureFR;
	}

	public int getEngineTemperature() {
		return engineTemperature;
	}

	public float getThrottle() {
		return throttle;
	}

	public float getSteer() {
		return steer;
	}

	public float getBrake() {
		return brake;
	}

	public float getTyresPressureRL() {
		return tyresPressureRL;
	}

	public float getTyresPressureRR() {
		return tyresPressureRR;
	}

	public float getTyresPressureFL() {
		return tyresPressureFL;
	}

	public float getTyresPressureFR() {
		return tyresPressureFR;
	}

	public SurfaceType getSurfaceRL() {
		return surfaceRL;
	}

	public SurfaceType getSurfaceRR() {
		return surfaceRR;
	}

	public SurfaceType getSurfaceFL() {
		return surfaceFL;
	}

	public SurfaceType getSurfaceFR() {
		return surfaceFR;
	}

	@Override
	public String toString() {
		return "CarTelemetryData [speed=" + speed  + ", throttle=" + throttle + ", steer=" + steer
				+ ", brake=" + brake+", clutch=" + clutch + ", gear=" + gear + ", engineRPM=" + engineRPM
				+ ", drs=" + drs + ", revLightsPercent=" + revLightsPercent + ", brakesTemperatureRL="
				+ brakesTemperatureRL + ", brakesTemperatureRR=" + brakesTemperatureRR + ", brakesTemperatureFL="
				+ brakesTemperatureFL + ", brakesTemperatureFR=" + brakesTemperatureFR + ", tyresSurfaceTemperatureRL="
				+ tyresSurfaceTemperatureRL + ", tyresSurfaceTemperatureRR=" + tyresSurfaceTemperatureRR
				+ ", tyresSurfaceTemperatureFL=" + tyresSurfaceTemperatureFL + ", tyresSurfaceTemperatureFR="
				+ tyresSurfaceTemperatureFR + ", tyresInnerTemperatureRL=" + tyresInnerTemperatureRL
				+ ", tyresInnerTemperatureRR=" + tyresInnerTemperatureRR + ", tyresInnerTemperatureFL="
				+ tyresInnerTemperatureFL + ", tyresInnerTemperatureFR=" + tyresInnerTemperatureFR
				+ ", engineTemperature=" + engineTemperature  + ", tyresPressureRL=" + tyresPressureRL + ", tyresPressureRR=" + tyresPressureRR
				+ ", tyresPressureFL=" + tyresPressureFL + ", tyresPressureFR=" + tyresPressureFR + ", surfaceRL="
				+ surfaceRL + ", surfaceRR=" + surfaceRR + ", surfaceFL=" + surfaceFL + ", surfaceFR=" + surfaceFR
				+ "]";
	}
	
	
}
