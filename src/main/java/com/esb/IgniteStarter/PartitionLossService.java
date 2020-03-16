package com.esb.IgniteStarter;

public interface PartitionLossService {

    /**
     * Поймали потерянную партицию
     * @param cache Кэш, в которой потерялась партиция
     * @param partitionNumber Номер потерянной партиции
     */
    public void addPartition(String cache, Integer partitionNumber);


}
