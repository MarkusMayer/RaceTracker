package raceTracker.model.gameStructs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.LittleEndianDataInputStream;

public class MotionData {
	
	public static final int MOTION_DATA_SIZE = 60;
	public static final int MOTION_DATA_TOTAL_SIZE = MOTION_DATA_SIZE * 22;
	
//    float         m_worldPositionX;           // World space X position
//    float         m_worldPositionY;           // World space Y position
//    float         m_worldPositionZ;           // World space Z position
//    float         m_worldVelocityX;           // Velocity in world space X
//    float         m_worldVelocityY;           // Velocity in world space Y
//    float         m_worldVelocityZ;           // Velocity in world space Z
//    int16         m_worldForwardDirX;         // World space forward X direction (normalised)
//    int16         m_worldForwardDirY;         // World space forward Y direction (normalised)
//    int16         m_worldForwardDirZ;         // World space forward Z direction (normalised)
//    int16         m_worldRightDirX;           // World space right X direction (normalised)
//    int16         m_worldRightDirY;           // World space right Y direction (normalised)
//    int16         m_worldRightDirZ;           // World space right Z direction (normalised)
//    float         m_gForceLateral;            // Lateral G-Force component
//    float         m_gForceLongitudinal;       // Longitudinal G-Force component
//    float         m_gForceVertical;           // Vertical G-Force component
//    float         m_yaw;                      // Yaw angle in radians
//    float         m_pitch;                    // Pitch angle in radians
//    float         m_roll;                     // Roll angle in radians

	private final float worldPositionX,worldPositionY,worldPositionZ,worldVelocityX,worldVelocityY,worldVelocityZ,worldForwardDirX,worldForwardDirY,worldForwardDirZ,worldRightDirX,worldRightDirY,worldRightDirZ,gForceLateral,gForceLongitudinal,gForceVertical,yaw,pitch,roll;

	public static List<MotionData> getMotions(int numActiveCars,byte[] motionTotalData) throws IOException {
//		System.out.println("CarMotionTotalData: " + F1UdpReceiver.bytesToHex(carTelemetryTotalData));
		List<MotionData> motions = new ArrayList<>();
		ByteBuffer bb = ByteBuffer.wrap(motionTotalData);
		for (int i = 0; i < Math.min(numActiveCars, 22); i++) {
			byte[] aMotion = new byte[MOTION_DATA_SIZE];
			bb.get(aMotion, 0, aMotion.length);
			MotionData aM = new MotionData(aMotion);
			System.out.println(aM);
			motions.add(aM);
		}
		return motions;
	}
	
	public MotionData(byte[] aMotion) throws IOException {
		LittleEndianDataInputStream leDis = new LittleEndianDataInputStream(
				new ByteArrayInputStream(aMotion, 0, aMotion.length));
		worldPositionX=leDis.readFloat();
		worldPositionY=leDis.readFloat();
		worldPositionZ=leDis.readFloat();
		worldVelocityX=leDis.readFloat();
		worldVelocityY=leDis.readFloat();
		worldVelocityZ=leDis.readFloat();
		worldForwardDirX=leDis.readShort();
		worldForwardDirY=leDis.readShort();
		worldForwardDirZ=leDis.readShort();
		worldRightDirX=leDis.readShort();
		worldRightDirY=leDis.readShort();
		worldRightDirZ=leDis.readShort();
		gForceLateral=leDis.readFloat();
		gForceLongitudinal=leDis.readFloat();
		gForceVertical=leDis.readFloat();
		yaw=leDis.readFloat();
		pitch=leDis.readFloat();
		roll=leDis.readFloat();
	}

	public static int getMotionDataSize() {
		return MOTION_DATA_SIZE;
	}

	public static int getMotionDataTotalSize() {
		return MOTION_DATA_TOTAL_SIZE;
	}

	public float getWorldPositionX() {
		return worldPositionX;
	}

	public float getWorldPositionY() {
		return worldPositionY;
	}

	public float getWorldPositionZ() {
		return worldPositionZ;
	}

	public float getWorldVelocityX() {
		return worldVelocityX;
	}

	public float getWorldVelocityY() {
		return worldVelocityY;
	}

	public float getWorldVelocityZ() {
		return worldVelocityZ;
	}

	public float getWorldForwardDirX() {
		return worldForwardDirX;
	}

	public float getWorldForwardDirY() {
		return worldForwardDirY;
	}

	public float getWorldForwardDirZ() {
		return worldForwardDirZ;
	}

	public float getWorldRightDirX() {
		return worldRightDirX;
	}

	public float getWorldRightDirY() {
		return worldRightDirY;
	}

	public float getWorldRightDirZ() {
		return worldRightDirZ;
	}

	public float getgForceLateral() {
		return gForceLateral;
	}

	public float getgForceLongitudinal() {
		return gForceLongitudinal;
	}

	public float getgForceVertical() {
		return gForceVertical;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(gForceLateral);
		result = prime * result + Float.floatToIntBits(gForceLongitudinal);
		result = prime * result + Float.floatToIntBits(gForceVertical);
		result = prime * result + Float.floatToIntBits(pitch);
		result = prime * result + Float.floatToIntBits(roll);
		result = prime * result + Float.floatToIntBits(worldForwardDirX);
		result = prime * result + Float.floatToIntBits(worldForwardDirY);
		result = prime * result + Float.floatToIntBits(worldForwardDirZ);
		result = prime * result + Float.floatToIntBits(worldPositionX);
		result = prime * result + Float.floatToIntBits(worldPositionY);
		result = prime * result + Float.floatToIntBits(worldPositionZ);
		result = prime * result + Float.floatToIntBits(worldRightDirX);
		result = prime * result + Float.floatToIntBits(worldRightDirY);
		result = prime * result + Float.floatToIntBits(worldRightDirZ);
		result = prime * result + Float.floatToIntBits(worldVelocityX);
		result = prime * result + Float.floatToIntBits(worldVelocityY);
		result = prime * result + Float.floatToIntBits(worldVelocityZ);
		result = prime * result + Float.floatToIntBits(yaw);
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
		MotionData other = (MotionData) obj;
		if (Float.floatToIntBits(gForceLateral) != Float.floatToIntBits(other.gForceLateral))
			return false;
		if (Float.floatToIntBits(gForceLongitudinal) != Float.floatToIntBits(other.gForceLongitudinal))
			return false;
		if (Float.floatToIntBits(gForceVertical) != Float.floatToIntBits(other.gForceVertical))
			return false;
		if (Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch))
			return false;
		if (Float.floatToIntBits(roll) != Float.floatToIntBits(other.roll))
			return false;
		if (Float.floatToIntBits(worldForwardDirX) != Float.floatToIntBits(other.worldForwardDirX))
			return false;
		if (Float.floatToIntBits(worldForwardDirY) != Float.floatToIntBits(other.worldForwardDirY))
			return false;
		if (Float.floatToIntBits(worldForwardDirZ) != Float.floatToIntBits(other.worldForwardDirZ))
			return false;
		if (Float.floatToIntBits(worldPositionX) != Float.floatToIntBits(other.worldPositionX))
			return false;
		if (Float.floatToIntBits(worldPositionY) != Float.floatToIntBits(other.worldPositionY))
			return false;
		if (Float.floatToIntBits(worldPositionZ) != Float.floatToIntBits(other.worldPositionZ))
			return false;
		if (Float.floatToIntBits(worldRightDirX) != Float.floatToIntBits(other.worldRightDirX))
			return false;
		if (Float.floatToIntBits(worldRightDirY) != Float.floatToIntBits(other.worldRightDirY))
			return false;
		if (Float.floatToIntBits(worldRightDirZ) != Float.floatToIntBits(other.worldRightDirZ))
			return false;
		if (Float.floatToIntBits(worldVelocityX) != Float.floatToIntBits(other.worldVelocityX))
			return false;
		if (Float.floatToIntBits(worldVelocityY) != Float.floatToIntBits(other.worldVelocityY))
			return false;
		if (Float.floatToIntBits(worldVelocityZ) != Float.floatToIntBits(other.worldVelocityZ))
			return false;
		if (Float.floatToIntBits(yaw) != Float.floatToIntBits(other.yaw))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MotionData [worldPositionX=" + worldPositionX + ", worldPositionY=" + worldPositionY
				+ ", worldPositionZ=" + worldPositionZ + ", worldVelocityX=" + worldVelocityX + ", worldVelocityY="
				+ worldVelocityY + ", worldVelocityZ=" + worldVelocityZ + ", worldForwardDirX=" + worldForwardDirX
				+ ", worldForwardDirY=" + worldForwardDirY + ", worldForwardDirZ=" + worldForwardDirZ
				+ ", worldRightDirX=" + worldRightDirX + ", worldRightDirY=" + worldRightDirY + ", worldRightDirZ="
				+ worldRightDirZ + ", gForceLateral=" + gForceLateral + ", gForceLongitudinal=" + gForceLongitudinal
				+ ", gForceVertical=" + gForceVertical + ", yaw=" + yaw + ", pitch=" + pitch + ", roll=" + roll + "]";
	}
	
	
}
