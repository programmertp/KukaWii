package kukaWii.wiiHandle.Packet.Handle;

import java.util.concurrent.BlockingQueue;

import kukaWii.wiiHandle.Packet.Base.AbstractPacket;

public abstract class AbstractPacketFilter extends AbstractPacketProvider implements PacketConsumer{
	
	private BlockingQueue<AbstractPacket> input;
	private boolean run = true;
	
	@Override
	public void registerQueue(BlockingQueue<AbstractPacket> inputQueue) {
		input = inputQueue;
	}
	
	/**
	 * Diese Methode wird vom Filter aufgerufen, um ein Paket zu ver�ndern.
	 * @param input
	 * @return AbstractPacket, wenn null, dann wird dieses nicht in die outputQueueAufgenommen
	 */
	public abstract AbstractPacket compute(AbstractPacket input);

	@Override
	public void start() {
		this.run = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(run){
					try {
						AbstractPacket res = compute(input.take());
						if(res != null){
							providePacket(res);
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();	
	}
	
	public void stop(){
		this.run = false;
	}
	
}
