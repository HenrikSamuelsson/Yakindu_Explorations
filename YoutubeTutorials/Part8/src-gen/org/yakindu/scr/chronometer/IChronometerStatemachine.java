package org.yakindu.scr.chronometer;
import org.yakindu.scr.IStatemachine;
import org.yakindu.scr.ITimerCallback;

public interface IChronometerStatemachine extends ITimerCallback, IStatemachine {

	public interface SCIC {
		public void raiseRight();
		public void raiseUp();
		public void raiseLeft();
		public long getMemTimer();
		public void setMemTimer(long value);
		public long getTimer();
		public void setTimer(long value);
		public long getTotalTime();
		public void setTotalTime(long value);
		public long getLapTime();
		public void setLapTime(long value);
		public boolean getRing();
		public void setRing(boolean value);

	}

	public SCIC getSCIC();

}
