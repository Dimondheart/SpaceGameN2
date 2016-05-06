package xyz.digitalcookies.spacegame1.spaceentity;

import xyz.digitalcookies.spacegame1.PlaneVector;

public class AIShipSteering implements IntelligentSteering
{
	@Override
	public void applySteering(SpaceObject obj)
	{
		int rotation = 
				(int)
				(
					(int) obj.getBody().getVelocity().getDirectionDeg()
					- obj.getBody().getDirection().getDirectionDeg()
				);
		// Rotate CCW
		if ((0 < rotation && rotation < 180) || (rotation < -180))
		{
			obj.getBody().setRotation(60);
		}
		// Rotate CW
		else if ((-180 < rotation && rotation < 0) || (rotation >= 180))
		{
			obj.getBody().setRotation(-60);
		}
		// No rotation
		else
		{
			obj.getBody().setRotation(0);
		}
		if (obj.getBody().getVelocity().getMagnitude() != 0)
		{
			PlaneVector reqVector = obj.getBody().getVelocity().getUnitVector();
			reqVector.setMagnitude(-obj.getBody().getVelocity().getMagnitude()*2);
			obj.getBody().getSteering().addVector(reqVector);
		}
	}
}
