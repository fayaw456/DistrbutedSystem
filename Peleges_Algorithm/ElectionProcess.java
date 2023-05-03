/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Peleges_Algorithm;

import java.util.ArrayList;

/**
 *
 * @author FAYAW
 */
public class ElectionProcess {

    private ArrayList<Node> nodes;
//creates the desired number of nodes, 
//assigns them sequential IDs starting from 0, and connects each node to its predecessor in the list.
    public ElectionProcess(int numNodes) {
        nodes = new ArrayList<Node>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node(i));
        }
        for (int i = 0; i < numNodes; i++) {
            Node currentNode = nodes.get(i);
            if (i > 0) {
                Node prevNode = nodes.get(i - 1);
                currentNode.addNeighbor(prevNode);
                prevNode.addNeighbor(currentNode);
            }
        }
    }
//every node elect a leader based on the IDs of itself and its neighbors.
    public void run() {
        boolean isDone = false;
        int iteration = 0;
        while (!isDone) {
            iteration++;
            System.out.println("Iteration " + iteration);
            isDone = true;
            for (Node node : nodes) {
                node.electLeader();
            }
            for (Node node : nodes) {
                if (node.isLeader()) {
                    isDone = false;
                    System.out.println("Node " + node.getId() + " is the leader.");
                    break;
                }
            }
        }
        System.out.println("Election process complete.");
    }
}
