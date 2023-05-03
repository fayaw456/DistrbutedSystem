/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Peleges_Algorithm;

/**
 *
 * @author FAYAW
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int numNodes = 5;
        ElectionProcess process = new ElectionProcess(numNodes);
        process.run();
    }

}
