package kukaWii.wiiHandle.Consumer;

import java.util.concurrent.BlockingQueue;

import kukaWii.wiiHandle.Packet.AbstractPacket;

/**
 * Basis-Interface f�r Consumer.
 * Diese m�ssen eine InputQueue registrieren k�nnen.
 * @author InternetMini
 *
 */
public interface IPacketConsumer {
	/**
	 * Registriert eine Queue bei dem Consumer.
	 * Diesem ist selbst �berlassen, was er damit tut
	 * @param queue
	 */
	public void registerQueue(BlockingQueue<AbstractPacket> inputQueue);
	
	
	/**
	 * L�sst einen Consumer starten.
	 */
	public void start();
	
	/**
	 * L�sst einen Consumer anhalten.
	 */
	public void stop();
	
	/**
	 * �berpr�ft die Antwortzeit.
	 */
	public void checkAntwortzeit(AbstractPacket packet);
}