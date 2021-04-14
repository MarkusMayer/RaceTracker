package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

import raceTracker.RaceTrackerConsoleRunner;

public class PacketMotionData {
	
	public static final int PACKET_MOTION_DATA_TOTAL_SIZE=1464-24;
	
	
    // Extra player car ONLY data
//    float         m_suspensionPosition[4];      // Note: All wheel arrays have the following order:
//    float         m_suspensionVelocity[4];      // RL, RR, FL, FR
//    float         m_suspensionAcceleration[4];	// RL, RR, FL, FR
//    float         m_wheelSpeed[4];           	// Speed of each wheel
//    float         m_wheelSlip[4];               // Slip ratio for each wheel
//    float         m_localVelocityX;         	// Velocity in local space
//    float         m_localVelocityY;         	// Velocity in local space
//    float         m_localVelocityZ;         	// Velocity in local space
//    float         m_angularVelocityX;		    // Angular velocity x-component
//    float         m_angularVelocityY;           // Angular velocity y-component
//    float         m_angularVelocityZ;           // Angular velocity z-component
//    float         m_angularAccelerationX;       // Angular velocity x-component
//    float         m_angularAccelerationY;	    // Angular velocity y-component
//    float         m_angularAccelerationZ;       // Angular velocity z-component
//    float         m_frontWheelsAngle;           // Current front wheels angle in radians

	private final List<MotionData> motionDatas;
	private final WheelDataFloat suspensionPosition,suspensionVelocity,suspensionAcceleration,wheelSpeed,wheelSlip;
	private final float localVelocityX,localVelocityY,localVelocityZ,angularVelocityX,angularVelocityY,angularVelocityZ,angularAccelerationX,angularAccelerationY,angularAccelerationZ,frontWheelsAngle;
	
	public PacketMotionData(int numActiveCars,byte[] packetMotionData) throws IOException {
//		System.out.println("PacketMotionData length: "+packetMotionData.length+", Data: "+RaceTrackerConsoleRunner.bytesToHex(packetMotionData));		
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(packetMotionData, MotionData.MOTION_DATA_TOTAL_SIZE, packetMotionData.length));

		byte[] motionTotalData=new byte[MotionData.MOTION_DATA_TOTAL_SIZE];
		ByteBuffer bbMotionData = ByteBuffer.wrap(packetMotionData,0,packetMotionData.length);
		bbMotionData.get(motionTotalData,0,motionTotalData.length);

		motionDatas=MotionData.getMotions(numActiveCars,motionTotalData); // ==> 124
		
		suspensionPosition=new WheelDataFloat(leDis.readFloat(), leDis.readFloat(), leDis.readFloat(), leDis.readFloat());
		suspensionVelocity=new WheelDataFloat(leDis.readFloat(), leDis.readFloat(), leDis.readFloat(), leDis.readFloat());
		suspensionAcceleration=new WheelDataFloat(leDis.readFloat(), leDis.readFloat(), leDis.readFloat(), leDis.readFloat());
		wheelSpeed=new WheelDataFloat(leDis.readFloat(), leDis.readFloat(), leDis.readFloat(), leDis.readFloat());
		wheelSlip=new WheelDataFloat(leDis.readFloat(), leDis.readFloat(), leDis.readFloat(), leDis.readFloat());
		localVelocityX=leDis.readFloat();
		localVelocityY=leDis.readFloat();
		localVelocityZ=leDis.readFloat();
		angularVelocityX=leDis.readFloat();
		angularVelocityY=leDis.readFloat();
		angularVelocityZ=leDis.readFloat();
		angularAccelerationX=leDis.readFloat();
		angularAccelerationY=leDis.readFloat();
		angularAccelerationZ=leDis.readFloat();
		frontWheelsAngle=leDis.readFloat();
		
	}
	
	public List<MotionData> getMotions(){
		return Collections.unmodifiableList(motionDatas);
	}

	public static int getPacketMotionDataTotalSize() {
		return PACKET_MOTION_DATA_TOTAL_SIZE;
	}

	public List<MotionData> getMotionDatas() {
		return motionDatas;
	}

	public WheelDataFloat getSuspensionPosition() {
		return suspensionPosition;
	}

	public WheelDataFloat getSuspensionVelocity() {
		return suspensionVelocity;
	}

	public WheelDataFloat getSuspensionAcceleration() {
		return suspensionAcceleration;
	}

	public WheelDataFloat getWheelSpeed() {
		return wheelSpeed;
	}

	public WheelDataFloat getWheelSlip() {
		return wheelSlip;
	}

	public float getLocalVelocityX() {
		return localVelocityX;
	}

	public float getLocalVelocityY() {
		return localVelocityY;
	}

	public float getLocalVelocityZ() {
		return localVelocityZ;
	}

	public float getAngularVelocityX() {
		return angularVelocityX;
	}

	public float getAngularVelocityY() {
		return angularVelocityY;
	}

	public float getAngularVelocityZ() {
		return angularVelocityZ;
	}

	public float getAngularAccelerationX() {
		return angularAccelerationX;
	}

	public float getAngularAccelerationY() {
		return angularAccelerationY;
	}

	public float getAngularAccelerationZ() {
		return angularAccelerationZ;
	}

	public float getFrontWheelsAngle() {
		return frontWheelsAngle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(angularAccelerationX);
		result = prime * result + Float.floatToIntBits(angularAccelerationY);
		result = prime * result + Float.floatToIntBits(angularAccelerationZ);
		result = prime * result + Float.floatToIntBits(angularVelocityX);
		result = prime * result + Float.floatToIntBits(angularVelocityY);
		result = prime * result + Float.floatToIntBits(angularVelocityZ);
		result = prime * result + Float.floatToIntBits(frontWheelsAngle);
		result = prime * result + Float.floatToIntBits(localVelocityX);
		result = prime * result + Float.floatToIntBits(localVelocityY);
		result = prime * result + Float.floatToIntBits(localVelocityZ);
		result = prime * result + ((motionDatas == null) ? 0 : motionDatas.hashCode());
		result = prime * result + ((suspensionAcceleration == null) ? 0 : suspensionAcceleration.hashCode());
		result = prime * result + ((suspensionPosition == null) ? 0 : suspensionPosition.hashCode());
		result = prime * result + ((suspensionVelocity == null) ? 0 : suspensionVelocity.hashCode());
		result = prime * result + ((wheelSlip == null) ? 0 : wheelSlip.hashCode());
		result = prime * result + ((wheelSpeed == null) ? 0 : wheelSpeed.hashCode());
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
		PacketMotionData other = (PacketMotionData) obj;
		if (Float.floatToIntBits(angularAccelerationX) != Float.floatToIntBits(other.angularAccelerationX))
			return false;
		if (Float.floatToIntBits(angularAccelerationY) != Float.floatToIntBits(other.angularAccelerationY))
			return false;
		if (Float.floatToIntBits(angularAccelerationZ) != Float.floatToIntBits(other.angularAccelerationZ))
			return false;
		if (Float.floatToIntBits(angularVelocityX) != Float.floatToIntBits(other.angularVelocityX))
			return false;
		if (Float.floatToIntBits(angularVelocityY) != Float.floatToIntBits(other.angularVelocityY))
			return false;
		if (Float.floatToIntBits(angularVelocityZ) != Float.floatToIntBits(other.angularVelocityZ))
			return false;
		if (Float.floatToIntBits(frontWheelsAngle) != Float.floatToIntBits(other.frontWheelsAngle))
			return false;
		if (Float.floatToIntBits(localVelocityX) != Float.floatToIntBits(other.localVelocityX))
			return false;
		if (Float.floatToIntBits(localVelocityY) != Float.floatToIntBits(other.localVelocityY))
			return false;
		if (Float.floatToIntBits(localVelocityZ) != Float.floatToIntBits(other.localVelocityZ))
			return false;
		if (motionDatas == null) {
			if (other.motionDatas != null)
				return false;
		} else if (!motionDatas.equals(other.motionDatas))
			return false;
		if (suspensionAcceleration == null) {
			if (other.suspensionAcceleration != null)
				return false;
		} else if (!suspensionAcceleration.equals(other.suspensionAcceleration))
			return false;
		if (suspensionPosition == null) {
			if (other.suspensionPosition != null)
				return false;
		} else if (!suspensionPosition.equals(other.suspensionPosition))
			return false;
		if (suspensionVelocity == null) {
			if (other.suspensionVelocity != null)
				return false;
		} else if (!suspensionVelocity.equals(other.suspensionVelocity))
			return false;
		if (wheelSlip == null) {
			if (other.wheelSlip != null)
				return false;
		} else if (!wheelSlip.equals(other.wheelSlip))
			return false;
		if (wheelSpeed == null) {
			if (other.wheelSpeed != null)
				return false;
		} else if (!wheelSpeed.equals(other.wheelSpeed))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PacketMotionData [motionDatas=" + motionDatas + ", suspensionPosition=" + suspensionPosition
				+ ", suspensionVelocity=" + suspensionVelocity + ", suspensionAcceleration=" + suspensionAcceleration
				+ ", wheelSpeed=" + wheelSpeed + ", wheelSlip=" + wheelSlip + ", localVelocityX=" + localVelocityX
				+ ", localVelocityY=" + localVelocityY + ", localVelocityZ=" + localVelocityZ + ", angularVelocityX="
				+ angularVelocityX + ", angularVelocityY=" + angularVelocityY + ", angularVelocityZ=" + angularVelocityZ
				+ ", angularAccelerationX=" + angularAccelerationX + ", angularAccelerationY=" + angularAccelerationY
				+ ", angularAccelerationZ=" + angularAccelerationZ + ", frontWheelsAngle=" + frontWheelsAngle + "]";
	}

}
