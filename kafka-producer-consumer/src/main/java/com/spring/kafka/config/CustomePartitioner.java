package com.spring.kafka.config;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomePartitioner implements Partitioner{
	private static final Logger LOGGER =
		      LoggerFactory.getLogger(CustomePartitioner.class);
	
	private String speedSensorName;

	@Override
	public void configure(Map<String, ?> config) {
		speedSensorName = config.get("speed.sensor.name").toString();
	}

	@Override
	public void close() {
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitionInfo = cluster.partitionsForTopic(topic);
		int numberOfPartitions = partitionInfo.size();
		int numberOfSpeedPartition = (int) Math.abs(numberOfPartitions * 0.3);
		int partition = 0;
		
		if(keyBytes == null || !(key instanceof String))
			throw new InvalidRecordException("All messagese must have sensor name as key");
		
		if(key.equals(speedSensorName))
			partition = Utils.toPositive(Utils.murmur2(valueBytes)) % numberOfSpeedPartition;
		
		else
			partition = Utils.toPositive(Utils.murmur2(keyBytes)) % (numberOfPartitions -numberOfSpeedPartition) + numberOfSpeedPartition;
		LOGGER.info("Key {} -> partition {}", key, partition);
		return partition;
	}
}
