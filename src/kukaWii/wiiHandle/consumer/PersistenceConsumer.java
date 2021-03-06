
package kukaWii.wiiHandle.consumer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import kukaWii.wiiHandle.packet.AbstractPacket;
import kukaWii.wiiHandle.packet.AccelerometerPacket;
import kukaWii.wiiHandle.packet.MotionPlusPacket;

public class PersistenceConsumer extends AbstractPacketConsumer{

	private List<AbstractPacket> packets = new ArrayList<AbstractPacket>();
	private int accelPackets;
	private int mopluPackets;
	
	private long mopluTimestamp = System.currentTimeMillis();
	private long accelTimestamp = System.currentTimeMillis();
	
	private int sysoutRate = 100;
	
	private int serialRun = 1;
	
	@Override
	protected void consume(AbstractPacket packet) {
		packets.add(packet);
		if(packet instanceof MotionPlusPacket){
			mopluPackets++;
			if(mopluPackets%sysoutRate==0){
				System.out.println("MotionPlus Speed: "+(sysoutRate*1000/(packet.getTimestampMillis() -mopluTimestamp))+" Packets/sec");
				mopluTimestamp = packet.getTimestampMillis();
				mopluPackets = 0;
			}
		}else if(packet instanceof AccelerometerPacket){
			accelPackets++;
			if(accelPackets%sysoutRate==0){
				System.out.println("AccelPacket Speed: "+(sysoutRate*1000/(packet.getTimestampMillis() -accelTimestamp))+" Packets/sec");
				accelTimestamp = packet.getTimestampMillis();
				accelPackets = 0;
			}
		}
		
		if(packets.size()==100000){
			System.out.println("Begin Serialization");
			 try {
				FileOutputStream file = new FileOutputStream( "packets"+serialRun+".pck" );
				ObjectOutputStream o = new ObjectOutputStream( file );
				o.writeObject(packets);
				o.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println("Serialization completed. "+"packets"+serialRun+".pck");
			 serialRun++;
			 
		}
	}

}
