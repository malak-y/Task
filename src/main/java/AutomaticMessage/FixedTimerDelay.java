package AutomaticMessage;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
@Startup
@Singleton
public class FixedTimerDelay {
	@EJB
	private Timer timer;

    @Lock(LockType.READ)
    @Schedule(second = "*/25", minute = "*", hour = "*", persistent = false)
    public void doSchedule() throws InterruptedException {
    	     timer.startTimer();      
            }

}
