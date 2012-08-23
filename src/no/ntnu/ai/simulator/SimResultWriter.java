package no.ntnu.ai.simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Class for writing Rolleout simulation results. This class will accept
 * several BlockingQueues which it gathers results from
 *
 */
public class SimResultWriter implements Runnable {

	private final List<BlockingQueue<SimResult>> outputs = new ArrayList<BlockingQueue<SimResult>>();
	private boolean running = false;
	private static SimResultWriter instance = null;
	private BufferedWriter writer;

	private SimResultWriter(){
		//Do nothing, but create a private constructor so someone needs to
		//use the static method
	}

	@Override
	public void run() {
		running = true;
		if(outputs.isEmpty() || this.writer == null){
			throw new IllegalStateException("Class is not configured properly. " +
					"Outputs: " + outputs + ", File writer: " + writer);
		}
		try{
			int counter = 0;
			while(!outputs.isEmpty()){
				for(int i = 0; i < outputs.size(); i++){
					BlockingQueue<SimResult> queue = outputs.get(i);
					try {
						SimResult res = queue.take();
						try{
							writer.write(res.toString());
							writer.newLine();
							counter++;
							if(counter == 169){
								return;
							}
						}catch (IOException e){
							e.printStackTrace();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean addOutput(BlockingQueue<SimResult> out){
		if(!running){
			outputs.add(out);
		}
		return !running;
	}

	public boolean setOutputFilename(String name){
		if(!running){
			try {
				this.writer = new BufferedWriter(new FileWriter(name));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
		return !running;
	}

	public static SimResultWriter getInstance(){
		if(instance == null){
			instance = new SimResultWriter();
		}
		return instance;
	}

}
