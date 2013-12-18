package kafka.clients.producer;

import java.util.concurrent.TimeUnit;

import kafka.common.protocol.ErrorCodes;

/**
 * An asynchronously computed response from sending a record
 */
public class RecordSend {
	
	private final long relativeOffset;
	private final ProduceRequestResult result;
	
	RecordSend(long relativeOffset, ProduceRequestResult result) {
	  this.relativeOffset = relativeOffset;
		this.result = result;
	}
	
	// TODO: throw exception if there is an error
	public void await() throws InterruptedException {
		result.await();
	}
	
	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return result.await(timeout, unit);
	}
	
	public long offset() throws InterruptedException {
		result.await();
		if(result.errorCode() == ErrorCodes.NO_ERROR)
      return this.result.baseOffset() + this.relativeOffset;
		else
		  return -1L;
	}
	
}
