package org.yakindu.scr.chronometer;
import org.yakindu.scr.ITimer;

public class ChronometerStatemachine implements IChronometerStatemachine {

	private final boolean[] timeEvents = new boolean[5];

	private final class SCICImpl implements SCIC {

		private boolean right;

		public void raiseRight() {
			right = true;
		}

		private boolean up;

		public void raiseUp() {
			up = true;
		}

		private boolean left;

		public void raiseLeft() {
			left = true;
		}

		private long memTimer;
		public long getMemTimer() {
			return memTimer;
		}

		public void setMemTimer(long value) {
			this.memTimer = value;
		}

		private long timer;
		public long getTimer() {
			return timer;
		}

		public void setTimer(long value) {
			this.timer = value;
		}

		private long totalTime;
		public long getTotalTime() {
			return totalTime;
		}

		public void setTotalTime(long value) {
			this.totalTime = value;
		}

		private long lapTime;
		public long getLapTime() {
			return lapTime;
		}

		public void setLapTime(long value) {
			this.lapTime = value;
		}

		private boolean ring;
		public boolean getRing() {
			return ring;
		}

		public void setRing(boolean value) {
			this.ring = value;
		}

		public void clearEvents() {
			right = false;
			up = false;
			left = false;
		}

	}

	private SCICImpl sCIC;

	public enum State {
		main_region_Timer, main_region_Timer_r1_T_active, main_region_Timer_r1_T_active_r1_Running, main_region_Timer_r1_T_active_r1_Paused, main_region_Timer_r1_T_active_r1_Ringing, main_region_Timer_r1_T_Idle, main_region_Timer_r1_SetTimer, main_region_Stopwatch, main_region_Stopwatch_r2_Reset, main_region_Stopwatch_r2_S_active, main_region_Stopwatch_r2_S_active_r2_Running, main_region_Stopwatch_r2_S_active_r2_Laptime, $NullState$
	};

	private State[] historyVector = new State[4];
	private final State[] stateVector = new State[1];

	private int nextStateIndex;

	private ITimer timer;

	static {
	}

	public ChronometerStatemachine() {

		sCIC = new SCICImpl();
	}

	public void init() {
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}

		for (int i = 0; i < 4; i++) {
			historyVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();

		sCIC.memTimer = 0;

		sCIC.timer = 0;

		sCIC.totalTime = 0;

		sCIC.lapTime = 0;

		sCIC.ring = false;
	}

	public void enter() {
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		entryAction();

		nextStateIndex = 0;
		stateVector[0] = State.main_region_Timer_r1_T_Idle;

		historyVector[0] = stateVector[0];
	}

	public void exit() {
		switch (stateVector[0]) {
			case main_region_Timer_r1_T_active_r1_Running :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				timer.unsetTimer(this, 0);
				break;

			case main_region_Timer_r1_T_active_r1_Paused :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;
				break;

			case main_region_Timer_r1_T_active_r1_Ringing :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				sCIC.ring = false;
				break;

			case main_region_Timer_r1_T_Idle :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;
				break;

			case main_region_Timer_r1_SetTimer :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				timer.unsetTimer(this, 1);
				break;

			case main_region_Stopwatch_r2_Reset :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;
				break;

			case main_region_Stopwatch_r2_S_active_r2_Running :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				timer.unsetTimer(this, 2);
				break;

			case main_region_Stopwatch_r2_S_active_r2_Laptime :
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				timer.unsetTimer(this, 3);

				timer.unsetTimer(this, 4);
				break;

			default :
				break;
		}

		exitAction();
	}

	/**
	 * This method resets the incoming events (time events included).
	 */
	protected void clearEvents() {
		sCIC.clearEvents();

		for (int i = 0; i < timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}

	/**
	 * This method resets the outgoing events.
	 */
	protected void clearOutEvents() {
	}

	/**
	 * Returns true if the given state is currently active otherwise false.
	 */
	public boolean isStateActive(State state) {
		switch (state) {
			case main_region_Timer :
				return stateVector[0].ordinal() >= State.main_region_Timer
						.ordinal()
						&& stateVector[0].ordinal() <= State.main_region_Timer_r1_SetTimer
								.ordinal();
			case main_region_Timer_r1_T_active :
				return stateVector[0].ordinal() >= State.main_region_Timer_r1_T_active
						.ordinal()
						&& stateVector[0].ordinal() <= State.main_region_Timer_r1_T_active_r1_Ringing
								.ordinal();
			case main_region_Timer_r1_T_active_r1_Running :
				return stateVector[0] == State.main_region_Timer_r1_T_active_r1_Running;
			case main_region_Timer_r1_T_active_r1_Paused :
				return stateVector[0] == State.main_region_Timer_r1_T_active_r1_Paused;
			case main_region_Timer_r1_T_active_r1_Ringing :
				return stateVector[0] == State.main_region_Timer_r1_T_active_r1_Ringing;
			case main_region_Timer_r1_T_Idle :
				return stateVector[0] == State.main_region_Timer_r1_T_Idle;
			case main_region_Timer_r1_SetTimer :
				return stateVector[0] == State.main_region_Timer_r1_SetTimer;
			case main_region_Stopwatch :
				return stateVector[0].ordinal() >= State.main_region_Stopwatch
						.ordinal()
						&& stateVector[0].ordinal() <= State.main_region_Stopwatch_r2_S_active_r2_Laptime
								.ordinal();
			case main_region_Stopwatch_r2_Reset :
				return stateVector[0] == State.main_region_Stopwatch_r2_Reset;
			case main_region_Stopwatch_r2_S_active :
				return stateVector[0].ordinal() >= State.main_region_Stopwatch_r2_S_active
						.ordinal()
						&& stateVector[0].ordinal() <= State.main_region_Stopwatch_r2_S_active_r2_Laptime
								.ordinal();
			case main_region_Stopwatch_r2_S_active_r2_Running :
				return stateVector[0] == State.main_region_Stopwatch_r2_S_active_r2_Running;
			case main_region_Stopwatch_r2_S_active_r2_Laptime :
				return stateVector[0] == State.main_region_Stopwatch_r2_S_active_r2_Laptime;
			default :
				return false;
		}
	}

	/**
	 * Set the {@link ITimer} for the state machine. It must be set
	 * externally on a timed state machine before a run cycle can be correct
	 * executed.
	 * 
	 * @param timer
	 */
	public void setTimer(ITimer timer) {
		this.timer = timer;
	}

	/**
	 * Returns the currently used timer.
	 * 
	 * @return {@link ITimer}
	 */
	public ITimer getTimer() {
		return timer;
	}

	public void timeElapsed(int eventID) {
		timeEvents[eventID] = true;
	}

	public SCIC getSCIC() {
		return sCIC;
	}

	/* Entry action for statechart 'chronometer'. */
	private void entryAction() {
	}

	/* Exit action for state 'chronometer'. */
	private void exitAction() {
	}

	/* deep enterSequence with history in child r1 */
	private void deepEnterSequenceMain_region_Timer_r1() {
		switch (historyVector[0]) {
			case main_region_Timer_r1_T_active_r1_Running :
				deepEnterSequenceMain_region_Timer_r1_T_active_r1();
				break;

			case main_region_Timer_r1_T_active_r1_Paused :
				deepEnterSequenceMain_region_Timer_r1_T_active_r1();
				break;

			case main_region_Timer_r1_T_active_r1_Ringing :
				deepEnterSequenceMain_region_Timer_r1_T_active_r1();
				break;

			case main_region_Timer_r1_T_Idle :
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
				break;

			case main_region_Timer_r1_SetTimer :

				timer.setTimer(this, 1, 1 * 1000, true);

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_SetTimer;

				historyVector[0] = stateVector[0];
				break;

			default :
				break;
		}
	}

	/* deep enterSequence with history in child r1 */
	private void deepEnterSequenceMain_region_Timer_r1_T_active_r1() {
		switch (historyVector[1]) {
			case main_region_Timer_r1_T_active_r1_Running :

				timer.setTimer(this, 0, 1 * 1000, true);

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_active_r1_Running;

				historyVector[1] = stateVector[0];
				break;

			case main_region_Timer_r1_T_active_r1_Paused :
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_active_r1_Paused;

				historyVector[1] = stateVector[0];
				break;

			case main_region_Timer_r1_T_active_r1_Ringing :
				sCIC.ring = true;

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_active_r1_Ringing;

				historyVector[1] = stateVector[0];
				break;

			default :
				break;
		}
	}

	/* deep enterSequence with history in child r2 */
	private void deepEnterSequenceMain_region_Stopwatch_r2() {
		switch (historyVector[2]) {
			case main_region_Stopwatch_r2_Reset :
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
				break;

			case main_region_Stopwatch_r2_S_active_r2_Running :
				deepEnterSequenceMain_region_Stopwatch_r2_S_active_r2();
				break;

			case main_region_Stopwatch_r2_S_active_r2_Laptime :
				deepEnterSequenceMain_region_Stopwatch_r2_S_active_r2();
				break;

			default :
				break;
		}
	}

	/* deep enterSequence with history in child r2 */
	private void deepEnterSequenceMain_region_Stopwatch_r2_S_active_r2() {
		switch (historyVector[3]) {
			case main_region_Stopwatch_r2_S_active_r2_Running :

				timer.setTimer(this, 2, 1 * 1000, true);

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_S_active_r2_Running;

				historyVector[3] = stateVector[0];
				break;

			case main_region_Stopwatch_r2_S_active_r2_Laptime :

				timer.setTimer(this, 3, 5 * 1000, false);

				timer.setTimer(this, 4, 1 * 1000, true);

				sCIC.lapTime = sCIC.totalTime;

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_S_active_r2_Laptime;

				historyVector[3] = stateVector[0];
				break;

			default :
				break;
		}
	}

	/* The reactions of state Running. */
	private void reactMain_region_Timer_r1_T_active_r1_Running() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Timer_r1_T_active_r1_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 0);
					break;

				case main_region_Timer_r1_T_active_r1_Paused :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_T_active_r1_Ringing :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					sCIC.ring = false;
					break;

				case main_region_Timer_r1_T_Idle :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_SetTimer :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 1);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[2] != State.$NullState$) {
				deepEnterSequenceMain_region_Stopwatch_r2();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
			}
		} else {
			if (sCIC.right) {
				switch (stateVector[0]) {
					case main_region_Timer_r1_T_active_r1_Running :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 0);
						break;

					case main_region_Timer_r1_T_active_r1_Paused :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;
						break;

					case main_region_Timer_r1_T_active_r1_Ringing :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						sCIC.ring = false;
						break;

					default :
						break;
				}

				sCIC.timer = sCIC.memTimer;

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
			} else {
				if (sCIC.up) {
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 0);

					nextStateIndex = 0;
					stateVector[0] = State.main_region_Timer_r1_T_active_r1_Paused;

					historyVector[1] = stateVector[0];
				} else {
					if (sCIC.timer == 0) {
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 0);

						sCIC.ring = true;

						nextStateIndex = 0;
						stateVector[0] = State.main_region_Timer_r1_T_active_r1_Ringing;

						historyVector[1] = stateVector[0];
					} else {
						if (timeEvents[0]) {
							sCIC.timer -= 1;
						}
					}
				}
			}
		}
	}

	/* The reactions of state Paused. */
	private void reactMain_region_Timer_r1_T_active_r1_Paused() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Timer_r1_T_active_r1_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 0);
					break;

				case main_region_Timer_r1_T_active_r1_Paused :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_T_active_r1_Ringing :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					sCIC.ring = false;
					break;

				case main_region_Timer_r1_T_Idle :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_SetTimer :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 1);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[2] != State.$NullState$) {
				deepEnterSequenceMain_region_Stopwatch_r2();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
			}
		} else {
			if (sCIC.right) {
				switch (stateVector[0]) {
					case main_region_Timer_r1_T_active_r1_Running :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 0);
						break;

					case main_region_Timer_r1_T_active_r1_Paused :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;
						break;

					case main_region_Timer_r1_T_active_r1_Ringing :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						sCIC.ring = false;
						break;

					default :
						break;
				}

				sCIC.timer = sCIC.memTimer;

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
			} else {
				if (sCIC.up) {
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.setTimer(this, 0, 1 * 1000, true);

					nextStateIndex = 0;
					stateVector[0] = State.main_region_Timer_r1_T_active_r1_Running;

					historyVector[1] = stateVector[0];
				}
			}
		}
	}

	/* The reactions of state Ringing. */
	private void reactMain_region_Timer_r1_T_active_r1_Ringing() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Timer_r1_T_active_r1_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 0);
					break;

				case main_region_Timer_r1_T_active_r1_Paused :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_T_active_r1_Ringing :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					sCIC.ring = false;
					break;

				case main_region_Timer_r1_T_Idle :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_SetTimer :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 1);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[2] != State.$NullState$) {
				deepEnterSequenceMain_region_Stopwatch_r2();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
			}
		} else {
			if (sCIC.right) {
				switch (stateVector[0]) {
					case main_region_Timer_r1_T_active_r1_Running :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 0);
						break;

					case main_region_Timer_r1_T_active_r1_Paused :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;
						break;

					case main_region_Timer_r1_T_active_r1_Ringing :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						sCIC.ring = false;
						break;

					default :
						break;
				}

				sCIC.timer = sCIC.memTimer;

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
			} else {
			}
		}
	}

	/* The reactions of state T_Idle. */
	private void reactMain_region_Timer_r1_T_Idle() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Timer_r1_T_active_r1_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 0);
					break;

				case main_region_Timer_r1_T_active_r1_Paused :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_T_active_r1_Ringing :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					sCIC.ring = false;
					break;

				case main_region_Timer_r1_T_Idle :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_SetTimer :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 1);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[2] != State.$NullState$) {
				deepEnterSequenceMain_region_Stopwatch_r2();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
			}
		} else {
			if (sCIC.right) {
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				timer.setTimer(this, 1, 1 * 1000, true);

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_SetTimer;

				historyVector[0] = stateVector[0];
			} else {
				if (sCIC.up) {
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					sCIC.timer = sCIC.memTimer;

					timer.setTimer(this, 0, 1 * 1000, true);

					nextStateIndex = 0;
					stateVector[0] = State.main_region_Timer_r1_T_active_r1_Running;

					historyVector[1] = stateVector[0];

					historyVector[0] = stateVector[0];
				}
			}
		}
	}

	/* The reactions of state SetTimer. */
	private void reactMain_region_Timer_r1_SetTimer() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Timer_r1_T_active_r1_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 0);
					break;

				case main_region_Timer_r1_T_active_r1_Paused :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_T_active_r1_Ringing :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					sCIC.ring = false;
					break;

				case main_region_Timer_r1_T_Idle :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Timer_r1_SetTimer :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 1);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[2] != State.$NullState$) {
				deepEnterSequenceMain_region_Stopwatch_r2();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
			}
		} else {
			if (sCIC.right) {
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				timer.unsetTimer(this, 1);

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
			} else {
				if (sCIC.left) {
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 1);

					sCIC.memTimer = 0;

					timer.setTimer(this, 1, 1 * 1000, true);

					nextStateIndex = 0;
					stateVector[0] = State.main_region_Timer_r1_SetTimer;

					historyVector[0] = stateVector[0];
				} else {
					if (sCIC.up) {
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 1);

						sCIC.memTimer += 5;

						timer.setTimer(this, 1, 1 * 1000, true);

						nextStateIndex = 0;
						stateVector[0] = State.main_region_Timer_r1_SetTimer;

						historyVector[0] = stateVector[0];
					} else {
						if (timeEvents[1]) {
							sCIC.memTimer += 1;
						}
					}
				}
			}
		}
	}

	/* The reactions of state Reset. */
	private void reactMain_region_Stopwatch_r2_Reset() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Stopwatch_r2_Reset :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Stopwatch_r2_S_active_r2_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 2);
					break;

				case main_region_Stopwatch_r2_S_active_r2_Laptime :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 3);

					timer.unsetTimer(this, 4);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[0] != State.$NullState$) {
				deepEnterSequenceMain_region_Timer_r1();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
			}
		} else {
			if (sCIC.up) {
				nextStateIndex = 0;
				stateVector[0] = State.$NullState$;

				timer.setTimer(this, 2, 1 * 1000, true);

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_S_active_r2_Running;

				historyVector[3] = stateVector[0];

				historyVector[2] = stateVector[0];
			}
		}
	}

	/* The reactions of state Running. */
	private void reactMain_region_Stopwatch_r2_S_active_r2_Running() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Stopwatch_r2_Reset :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Stopwatch_r2_S_active_r2_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 2);
					break;

				case main_region_Stopwatch_r2_S_active_r2_Laptime :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 3);

					timer.unsetTimer(this, 4);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[0] != State.$NullState$) {
				deepEnterSequenceMain_region_Timer_r1();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
			}
		} else {
			if (sCIC.right) {
				switch (stateVector[0]) {
					case main_region_Stopwatch_r2_S_active_r2_Running :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 2);
						break;

					case main_region_Stopwatch_r2_S_active_r2_Laptime :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 3);

						timer.unsetTimer(this, 4);
						break;

					default :
						break;
				}

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
			} else {
				if (sCIC.up) {
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 2);

					timer.setTimer(this, 3, 5 * 1000, false);

					timer.setTimer(this, 4, 1 * 1000, true);

					sCIC.lapTime = sCIC.totalTime;

					nextStateIndex = 0;
					stateVector[0] = State.main_region_Stopwatch_r2_S_active_r2_Laptime;

					historyVector[3] = stateVector[0];
				} else {
					if (timeEvents[2]) {
						sCIC.totalTime += 1;
					}
				}
			}
		}
	}

	/* The reactions of state Laptime. */
	private void reactMain_region_Stopwatch_r2_S_active_r2_Laptime() {
		if (sCIC.left) {
			switch (stateVector[0]) {
				case main_region_Stopwatch_r2_Reset :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;
					break;

				case main_region_Stopwatch_r2_S_active_r2_Running :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 2);
					break;

				case main_region_Stopwatch_r2_S_active_r2_Laptime :
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 3);

					timer.unsetTimer(this, 4);
					break;

				default :
					break;
			}

			/* Enter the region with deep history */
			if (historyVector[0] != State.$NullState$) {
				deepEnterSequenceMain_region_Timer_r1();
			} else {
				nextStateIndex = 0;
				stateVector[0] = State.main_region_Timer_r1_T_Idle;

				historyVector[0] = stateVector[0];
			}
		} else {
			if (sCIC.right) {
				switch (stateVector[0]) {
					case main_region_Stopwatch_r2_S_active_r2_Running :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 2);
						break;

					case main_region_Stopwatch_r2_S_active_r2_Laptime :
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 3);

						timer.unsetTimer(this, 4);
						break;

					default :
						break;
				}

				nextStateIndex = 0;
				stateVector[0] = State.main_region_Stopwatch_r2_Reset;

				historyVector[2] = stateVector[0];
			} else {
				if (timeEvents[3]) {
					nextStateIndex = 0;
					stateVector[0] = State.$NullState$;

					timer.unsetTimer(this, 3);

					timer.unsetTimer(this, 4);

					timer.setTimer(this, 2, 1 * 1000, true);

					nextStateIndex = 0;
					stateVector[0] = State.main_region_Stopwatch_r2_S_active_r2_Running;

					historyVector[3] = stateVector[0];
				} else {
					if (sCIC.up) {
						nextStateIndex = 0;
						stateVector[0] = State.$NullState$;

						timer.unsetTimer(this, 3);

						timer.unsetTimer(this, 4);

						timer.setTimer(this, 2, 1 * 1000, true);

						nextStateIndex = 0;
						stateVector[0] = State.main_region_Stopwatch_r2_S_active_r2_Running;

						historyVector[3] = stateVector[0];
					} else {
						if (timeEvents[4]) {
							sCIC.totalTime += 1;
						}
					}
				}
			}
		}
	}

	public void runCycle() {

		clearOutEvents();

		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {

			switch (stateVector[nextStateIndex]) {
				case main_region_Timer_r1_T_active_r1_Running :
					reactMain_region_Timer_r1_T_active_r1_Running();
					break;
				case main_region_Timer_r1_T_active_r1_Paused :
					reactMain_region_Timer_r1_T_active_r1_Paused();
					break;
				case main_region_Timer_r1_T_active_r1_Ringing :
					reactMain_region_Timer_r1_T_active_r1_Ringing();
					break;
				case main_region_Timer_r1_T_Idle :
					reactMain_region_Timer_r1_T_Idle();
					break;
				case main_region_Timer_r1_SetTimer :
					reactMain_region_Timer_r1_SetTimer();
					break;
				case main_region_Stopwatch_r2_Reset :
					reactMain_region_Stopwatch_r2_Reset();
					break;
				case main_region_Stopwatch_r2_S_active_r2_Running :
					reactMain_region_Stopwatch_r2_S_active_r2_Running();
					break;
				case main_region_Stopwatch_r2_S_active_r2_Laptime :
					reactMain_region_Stopwatch_r2_S_active_r2_Laptime();
					break;
				default :
					// $NullState$
			}
		}

		clearEvents();
	}
}
