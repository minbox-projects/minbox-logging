package org.minbox.framework.logging.client.admin.discovery.lb.support;

import org.minbox.framework.logging.client.MinBoxLoggingException;
import org.minbox.framework.logging.client.admin.discovery.lb.LoadBalanceNode;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The {@link org.minbox.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy} Poll strategy
 *
 * @author 恒宇少年
 * @see DefaultLoadBalanceStrategy
 * @see org.minbox.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy
 */
public class SmoothWeightedRoundRobinStrategy extends DefaultLoadBalanceStrategy {
    /**
     * load balance node {@link LoadBalanceNode}
     */
    private ConcurrentMap<String, LoadBalanceNode> nodes = new ConcurrentHashMap();

    private ReentrantLock lock = new ReentrantLock();

    /**
     * lookup load-balanced logging admin address {@link LoadBalanceNode#getAddress()}
     * lock admin address by {@link ReentrantLock}
     *
     * @param adminAddress logging admin address array
     * @return load-balanced logging admin address
     * @throws MinBoxLoggingException MinBox Logging Exception
     */
    @Override
    public String lookup(String[] adminAddress) throws MinBoxLoggingException {
        List<LoadBalanceNode> loadBalanceNodeList = initNodeList(adminAddress);
        loadBalanceNodeList.stream().forEach(loadBalanceNode -> nodes.put(loadBalanceNode.getAddress(), loadBalanceNode));
        try {
            lock.lock();
            return this.findMatchAddress();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Find match logging admin address {@link LoadBalanceNode#getAddress()}
     * Obtaining the Node with the Largest Weight
     *
     * @return load-balanced logging admin address
     */
    private String findMatchAddress() {
        int totalWeight = 0;
        LoadBalanceNode maxNode = null;
        int maxWeight = 0;
        Iterator<String> iterator = nodes.keySet().iterator();
        while (iterator.hasNext()) {
            LoadBalanceNode n = nodes.get(iterator.next());
            totalWeight += n.getInitWeight();
            n.setCurrentWeight(n.getCurrentWeight() + n.getInitWeight());
            if (maxNode == null || maxWeight < n.getCurrentWeight()) {
                maxNode = n;
                maxWeight = n.getCurrentWeight();
            }
        }
        maxNode.setCurrentWeight(maxNode.getCurrentWeight() - totalWeight);
        return maxNode.getAddress();
    }
}
