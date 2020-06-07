package world.estate;

public class Mine extends Estate{
	
	private int gain;
	
	public Mine() {
		this.gain = 2;
	}
	
	public int getGain() {
		return gain;
	}
	
	@Override
	public String getInfoString() {
		return "Gain: "+gain;
	}
	
}
