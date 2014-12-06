/*
 * Copyright (C) 2014 simonppg
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gametome.personajes;

/**
 *
 * @author simonppg
 */
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Alarma {
    Timer timer;
    TimerTask task;
    Callable func;

    public Alarma(int seconds, Callable func) {
        task = new RemindTask(func);
        this.func = func;
        timer = new Timer();
        timer.schedule(task, seconds*1000);
    }

    class RemindTask extends TimerTask {
        private final Callable func;

        private RemindTask(Callable func) {
            this.func = func;
        }
        
        public void run() {
            try {
                func.call();
                //System.out.format("Time's up!%n");
                timer.cancel();
                //Terminate the timer thread
            } catch (Exception ex) {
                Logger.getLogger(Alarma.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}