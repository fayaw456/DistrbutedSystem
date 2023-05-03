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
public class Node {

    private int id;
    private ArrayList<Node> neighbors;
    private boolean isLeader;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
        this.isLeader = false;
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    public void electLeader() {
        int maxId = id;
        for (Node neighbor : neighbors) {
            int neighborId = neighbor.getId();
            if (neighborId > maxId) {
                maxId = neighborId;
            }
        }
        if (maxId == id) {
            isLeader = true;
        } else {
            isLeader = false;
        }
    }

    public int getId() {
        return id;
    }

    public boolean isLeader() {
        return isLeader;
    }
}





