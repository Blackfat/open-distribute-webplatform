package com.blackfat.common.utils.number;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 *
 * twitter把存储系统从mysql迁移到Cassandra的过程中由于Cassandra没有顺序ID生成机制，于是自己开发了一套全局唯一ID生成器：snowflake。
 * 1.41位的时间序列（精确到毫秒，41位的长度可以使用69年）
 * 2.10位的机器表示(10位的长度最多支持部署1024个节点)
 * 3.12的计数序列号
 * 优点:高性能，低延迟，独立的应用；按时间有序
 */
public class SnowflakeIdWorker {
	
	    private final static Logger logger = LoggerFactory.getLogger(SnowflakeIdWorker.class);
	    private final long workerId;
	    private final long epoch = 1403854494756L;   // 时间起始标记点，作为基准，一般取系统的最近时间
	    private final long workerIdBits = 10L;      // 机器标识位数
	    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 机器ID最大值: 1023
	    private long sequence = 0L;                   // 0，并发控制
	    private final long sequenceBits = 12L;      //毫秒内自增位
	 
	    private final long workerIdShift = this.sequenceBits;                             // 12
	    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;// 22
	    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;                 // 4095,111111111111,12位
	    private long lastTimestamp = -1L;
	    private SnowflakeIdWorker(long workerId) {
	        if (workerId > this.maxWorkerId || workerId < 0) {
	            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
	        }
	        this.workerId = workerId;
	    }
	 
	    public synchronized long nextId() throws Exception {
	        long timestamp = SnowflakeIdWorker.timeGen();
	        if (this.lastTimestamp == timestamp) { // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环); 对新的timestamp，sequence从0开始
	            this.sequence = this.sequence + 1 & this.sequenceMask;
	            if (this.sequence == 0) {
	                timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
	            }
	        } else {
	            this.sequence = 0;
	        }
	 
	        if (timestamp < this.lastTimestamp) {
	            logger.error(String.format("clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
	            throw new Exception(String.format("clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
	        }
	 
	        this.lastTimestamp = timestamp;
	        return timestamp - this.epoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
	    }
	 
	    private static SnowflakeIdWorker flowIdWorker = new SnowflakeIdWorker(getworkerHostIp());
	    public static SnowflakeIdWorker getFlowIdWorkerInstance() {
	        return flowIdWorker;
	    }
	    /**
	     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
	     */
	    private long tilNextMillis(long lastTimestamp) {
	        long timestamp = SnowflakeIdWorker.timeGen();
	        while (timestamp <= lastTimestamp) {
	            timestamp = SnowflakeIdWorker.timeGen();
	        }
	        return timestamp;
	    }
	    /**
	     * 获得系统当前毫秒数
	     */
	    private static long timeGen() {
	        return System.currentTimeMillis();
	    }
	    /**
	     *
	     * @param bytes
	     * @return int
	     */
	    public static int getworkerHostIp() {
	        try {
	            byte[] bytes = InetAddress.getLocalHost().getAddress();
	            return Integer.valueOf(bytes[3] & 0xFF); 
	        } catch (Exception e) {
	            logger.error(e.getMessage(),e);
	            return 1;
	        }
	 
	    }
	    public static void main(String[] args) throws Exception {
	        SnowflakeIdWorker idWorker = SnowflakeIdWorker.getFlowIdWorkerInstance();
	        System.out.println(idWorker.nextId());
	        System.out.println(idWorker.nextId());
	    }

}
